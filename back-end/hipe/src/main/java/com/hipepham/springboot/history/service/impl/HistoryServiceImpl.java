/*
 * Copyright (C) FPT Software 2020, Inc - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.hipepham.springboot.history.service.impl;

import com.hipepham.springboot.common.base.service.BaseService;
import com.hipepham.springboot.history.entity.History;
import com.hipepham.springboot.history.entity.HistoryValue;
import com.hipepham.springboot.history.form.HistoryForm;
import com.hipepham.springboot.history.repository.HistoryRepository;
import com.hipepham.springboot.history.service.HistoryService;
import com.hipepham.springboot.history.service.HistoryValueService;
import com.hipepham.springboot.history.vo.HistoryVO;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * The type History service.
 */
@Service
@Log4j2
public class HistoryServiceImpl extends BaseService implements HistoryService {
    /**
     * The History repository.
     */
    private HistoryRepository historyRepository;
    /**
     * The History value service.
     */
    private HistoryValueService historyValueService;

    /**
     * Instantiates a new History service.
     *
     * @param pHistoryRepository   the p history repository
     * @param pHistoryValueService the p history value service
     */
    public HistoryServiceImpl(final HistoryRepository pHistoryRepository,
                              final HistoryValueService pHistoryValueService) {
        this.historyRepository = pHistoryRepository;
        this.historyValueService = pHistoryValueService;
    }

    /**
     * Save history.
     *
     * @param form the form
     * @return the history
     */
    @Override
    public History save(final HistoryForm form) {
        try {
            String username = super.getLoggedInUsername();
            History history = new History(form.getType(),
                    form.getAction(), super.getUserIp(), form.getObjectId());
            fillBaseInfo(history, username, false);
            History result = historyRepository.save(history);
            if (result.getId() != null) {
                List<HistoryValue> historyValues =
                        this.detectChange(result.getId(), form.getOldValue(),
                                form.getNewValue(), form.getProperties());
                if (!ObjectUtils.isEmpty(historyValues)) {
                    historyValueService.save(historyValues);
                }
            }
            return result;
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    @Override
    public List<HistoryVO> getAllHistoryRequest(String requestId, String objectId, int pPageIndex, int pageSize) {
        int pageIndex = pPageIndex-1;
        pageIndex = pageIndex > 0 ? pageIndex : 0;
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
		return historyRepository.getHistoryRequest(requestId, pageable);
    }

    @Override
    public int totalHistoryRequest(String requestId, String objectId) {
        return historyRepository.totalHistoryRequest(requestId);
    }


    /**
     * Detect change list.
     *
     * @param historyId  the history id
     * @param oldValue   the old value
     * @param newValue   the new value
     * @param properties the properties
     * @return the list
     * @throws NoSuchFieldException   the no such field exception
     * @throws IllegalAccessException the illegal access exception
     */
    private List<HistoryValue> detectChange(final Long historyId,
                                            final Object oldValue,
                                            final Object newValue,
                                            final List<String> properties)
            throws NoSuchFieldException, IllegalAccessException {
        List<HistoryValue> result = new ArrayList<>();
        String username = super.getLoggedInUsername();
        if (oldValue != null
                && newValue != null
                && !ObjectUtils.isEmpty(properties)) {
            for (String property : properties) {
                Field fieldOld = oldValue.getClass()
                        .getDeclaredField(property);
                fieldOld.setAccessible(true);
                Field fieldNew = newValue.getClass()
                        .getDeclaredField(property);
                fieldNew.setAccessible(true);
                String valueOld = fieldOld.get(oldValue) != null
                        ? fieldOld.get(oldValue).toString() : "";
                String valueNew = fieldNew.get(newValue) != null
                        ? fieldNew.get(newValue).toString() : "";
                if (!valueOld.equals(valueNew)) {
                    HistoryValue historyValue = new HistoryValue(historyId,
                            property, valueOld, valueNew);
                    super.fillBaseInfo(historyValue, username, false);
                    result.add(historyValue);
                }
            }
        }
        return result;
    }
}

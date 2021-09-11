/*
 * Copyright (C) FPT Software 2020, Inc - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.hipepham.springboot.history.service.impl;

import com.hipepham.springboot.common.base.service.BaseService;
import com.hipepham.springboot.history.entity.HistoryValue;
import com.hipepham.springboot.history.repository.HistoryValueRepository;
import com.hipepham.springboot.history.service.HistoryValueService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type History value service.
 */
@Service
public class HistoryValueServiceImpl extends BaseService implements HistoryValueService {

    /**
     * The History value repository.
     */
    private HistoryValueRepository historyValueRepository;


    /**
     * Instantiates a new History value service.
     *
     * @param pHistoryValueRepository the history value repository
     */
    public HistoryValueServiceImpl(
            final HistoryValueRepository pHistoryValueRepository) {
        this.historyValueRepository = pHistoryValueRepository;
    }

    /**
     * Save list.
     *
     * @param values the values
     * @return the list
     */
    @Override
    public List<HistoryValue> save(final List<HistoryValue> values) {
        return historyValueRepository.saveAll(values);
    }

    /**
     * Save history.
     *
     * @param value the value
     * @return the history
     */
    @Override
    public HistoryValue save(final HistoryValue value) {
        return historyValueRepository.save(value);
    }

    /**
     * Gets by history id.
     *
     * @param historyId the history id
     * @return the by history id
     */
    @Override
    public List<HistoryValue> getByHistoryId(final Long historyId) {
        return historyValueRepository.findAllByHistoryId(historyId);
    }
}

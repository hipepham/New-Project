/*
 * Copyright (C) FPT Software 2020, Inc - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.hipepham.springboot.history.service;

import com.hipepham.springboot.history.entity.History;
import com.hipepham.springboot.history.form.HistoryForm;
import com.hipepham.springboot.history.vo.HistoryVO;

import java.util.List;

/**
 * The interface History service.
 */
public interface HistoryService {
    /**
     * Save history.
     *
     * @param form the form
     * @return the history
     */
    History save(HistoryForm form);

    List<HistoryVO> getAllHistoryRequest(String requestId, String objectId, int pageIndex, int pageSize);

    int totalHistoryRequest(String requestId, String objectId);
}

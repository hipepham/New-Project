/*
 * Copyright (C) FPT Software 2020, Inc - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.hipepham.springboot.history.service;

import com.hipepham.springboot.history.entity.HistoryValue;

import java.util.List;

/**
 * The interface History value service.
 */
public interface HistoryValueService {
    /**
     * Save list.
     *
     * @param values the values
     * @return the list
     */
    List<HistoryValue> save(List<HistoryValue> values);

    /**
     * Save history.
     *
     * @param value the value
     * @return the history
     */
    HistoryValue save(HistoryValue value);

    /**
     * Gets by history id.
     *
     * @param historyId the history id
     * @return the by history id
     */
    List<HistoryValue> getByHistoryId(Long historyId);
}

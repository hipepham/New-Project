/*
 * Copyright (C) FPT Software 2020, Inc - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.hipepham.springboot.history.repository;

import com.hipepham.springboot.history.entity.HistoryValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface History value repository.
 */
@Repository
public interface HistoryValueRepository
        extends JpaRepository<HistoryValue, Long> {
    /**
     * Find all by history id list.
     *
     * @param historyId the history id
     * @return the list
     */
    List<HistoryValue> findAllByHistoryId(Long historyId);
}

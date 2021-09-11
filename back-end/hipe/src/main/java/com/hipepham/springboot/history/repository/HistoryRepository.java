/*
 * Copyright (C) FPT Software 2020, Inc - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.hipepham.springboot.history.repository;

import com.hipepham.springboot.history.entity.History;
import com.hipepham.springboot.history.vo.HistoryVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface History repository.
 */
@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
    /**
     * Find all by created by list.
     *
     * @param username the username
     * @return the list
     */
    List<History> findAllByCreatedBy(String username);

    @Query(value = "Select new com.hipepham.springboot.history.vo.HistoryVO(h.action, hv.oldValue, hv.newValue, hv.createdTime, hv.property, h.modifiedBy) " +
            " FROM History h " +
            " JOIN HistoryValue hv ON h.id = hv.historyId" +
            " WHERE 1=1 AND h.objectId = :requestId AND h.type IN ('T_REQUEST_CONFIRM','T_REQUEST_TO_USE')")
    List<HistoryVO> getHistoryRequest(@Param("requestId") String requestId, Pageable pageable);

    @Query(value = "Select COUNT(1) " +
            " FROM History h " +
            " JOIN HistoryValue hv ON h.id = hv.historyId" +
            " WHERE 1=1 AND h.objectId = :requestId AND h.type IN ('T_REQUEST_CONFIRM','T_REQUEST_TO_USE')")
    int totalHistoryRequest(@Param("requestId") String requestId);
}

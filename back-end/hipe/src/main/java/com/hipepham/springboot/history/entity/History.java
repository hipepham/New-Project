/*
 * Copyright (C) FPT Software 2020, Inc - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.hipepham.springboot.history.entity;

import com.hipepham.springboot.common.base.entity.BaseEntity;
import com.hipepham.springboot.history.constants.HistoryConstants;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The type History.
 */
@Entity
@Table(name = HistoryConstants.TABLE_NAME)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class History extends BaseEntity {
    /**
     * The Type.
     */
    @Column(name = HistoryConstants.COLUMN_TYPE)
    private String type;
    /**
     * The Action.
     */
    @Column(name = HistoryConstants.COLUMN_ACTION)
    private String action;
    /**
     * The Ip address.
     */
    @Column(name = HistoryConstants.COLUMN_IP_ADDRESS)
    private String ipAddress;
    /**
     * The Object id.
     */
    @Column(name = HistoryConstants.COLUMN_OBJECT_ID)
    private String objectId;
}

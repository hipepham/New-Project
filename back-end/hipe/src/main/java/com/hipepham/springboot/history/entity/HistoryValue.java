/*
 * Copyright (C) FPT Software 2020, Inc - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.hipepham.springboot.history.entity;

import com.hipepham.springboot.common.base.entity.BaseEntity;
import com.hipepham.springboot.history.constants.HistoryValueConstants;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * The type History value.
 */
@Entity
@Table(name = HistoryValueConstants.TABLE_NAME)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class HistoryValue extends BaseEntity {
    /**
     * The History id.
     */
    private Long historyId;
    /**
     * The Property.
     */
    private String property;
    /**
     * The Old value.
     */
    @Lob
    private String oldValue;
    /**
     * The New value.
     */
    @Lob
    private String newValue;
}

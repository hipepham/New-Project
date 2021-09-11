/*
 * Copyright (C) FPT Software 2020, Inc - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.hipepham.springboot.common.base.entity;

import com.hipepham.springboot.common.base.constant.BaseEntityConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The type Base entity.
 */
@MappedSuperclass
@Getter
@Setter
@ToString
public class BaseEntity implements Serializable {
    /**
     * The Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = BaseEntityConstants.COLUMN_ID)
    private Long id;

    /**
     * The Created time.
     */
    @Column(name = BaseEntityConstants.COLUMN_CREATED_TIME, nullable = false)
    private Date createdTime;

    /**
     * The Modified time.
     */
    @Column(name = BaseEntityConstants.COLUMN_MODIFIED_TIME, nullable = false)
    private Date modifiedTime;

    /**
     * The Created by.
     */
    @Column(name = BaseEntityConstants.COLUMN_CREATED_BY, nullable = false)
    private String createdBy;

    /**
     * The Modified by.
     */
    @Column(name = BaseEntityConstants.COLUMN_MODIFIED_BY, nullable = false)
    private String modifiedBy;
}

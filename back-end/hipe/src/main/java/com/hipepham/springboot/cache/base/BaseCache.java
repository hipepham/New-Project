/*
 * Copyright (C) FPT Software 2020, Inc - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.hipepham.springboot.cache.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.index.Indexed;

/**
 * The type Base cache.
 */
@Getter
@Setter
@ToString
public class BaseCache {
    /**
     * The Id.
     */
    @Id
    @Indexed
    private Long id;
    /**
     * The Created time.
     */
    private String createdTime;
    /**
     * The Modified time.
     */
    private String modifiedTime;
    /**
     * The Created by.
     */
    private String createdBy;
    /**
     * The Modified by.
     */
    private String modifiedBy;
}

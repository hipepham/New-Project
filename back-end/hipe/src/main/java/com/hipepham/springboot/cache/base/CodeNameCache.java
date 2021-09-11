/*
 * Copyright (C) FPT Software 2020, Inc - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.hipepham.springboot.cache.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.redis.core.index.Indexed;

/**
 * The type Code name cache.
 */
@Getter
@Setter
@ToString
public class CodeNameCache extends BaseCache {
    /**
     * The Code.
     */
    private String code;
    /**
     * The Lower code.
     */
    @Indexed
    private String lowerCode;
    /**
     * The Name.
     */
    private String name;
    /**
     * The Lower name.
     */
    private String lowerName;
}

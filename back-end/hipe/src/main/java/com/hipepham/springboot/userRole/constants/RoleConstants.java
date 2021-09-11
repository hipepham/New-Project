/*
 * Copyright (C) FPT Software 2020, Inc - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.hipepham.springboot.userRole.constants;

/**
 * The type Role constants.
 */
public final class RoleConstants {
    /**
     * The constant TABLE_NAME.
     */
    public static final String TABLE_NAME = "T_ROLES";
    public static final String COLUMN_CODE = "code";
    public static final String COLUMN_NAME = "name";
    /**
     * The constant COLUMN_DESCRIPTION.
     */
    public static final String COLUMN_DESCRIPTION = "description";
    /**
     * The constant COLUMN_WEIGHT.
     */
    public static final String COLUMN_WEIGHT = "weight";


    public static final String COLUMN_DELETED = "deleted";
    /**
    * Hide constructor.
    */
    private RoleConstants() {
        //Default.
    }
}

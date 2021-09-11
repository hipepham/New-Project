/*
 * Copyright (C) FPT Software 2020, Inc - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.hipepham.springboot.userRole.constants;

/**
 * The type Permission constants.
 */
public final class PermissionConstants {
    /**
     * The constant TABLE_NAME.
     */
    public static final String TABLE_NAME = "T_PERMISSION";
    /**
     * The constant COLUMN_PERMISSION_CODE.
     */
    public static final String COLUMN_PERMISSION_CODE = "permission_code";
    /**
     * The constant COLUMN_PERMISSION_TYPE_CODE.
     */
    public static final String COLUMN_PERMISSION_TYPE_CODE =
            "permission_type_code";
    /**
     * The constant COLUMN_OBJECT_TYPE_CODE.
     */
    public static final String COLUMN_OBJECT_TYPE_CODE = "object_type_code";
    /**
     * The constant COLUMN_DELETED.
     */
    public static final String COLUMN_DELETED = "deleted";
    /**
    * Hide constructor.
    */
    private PermissionConstants() {
        //Default.
    }
}

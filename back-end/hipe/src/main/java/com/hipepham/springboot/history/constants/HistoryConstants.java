/*
 * Copyright (C) FPT Software 2020, Inc - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.hipepham.springboot.history.constants;

/**
 * The type History constants.
 */
public final class HistoryConstants {
    /**
     * The constant TABLE_NAME.
     */
    public static final String TABLE_NAME = "T_HISTORY";
    /**
     * The constant COLUMN_TYPE.
     */
    public static final String COLUMN_TYPE = "type";
    /**
     * The constant COLUMN_ACTION.
     */
    public static final String COLUMN_ACTION = "action";
    /**
     * The constant COLUMN_OBJECT_ID.
     */
    public static final String COLUMN_OBJECT_ID = "object_id";
    /**
     * The constant COLUMN_IP_ADDRESS.
     */
    public static final String COLUMN_IP_ADDRESS = "ip_address";
    /**
     * The constant ACTION_CREATE.
     */
    public static final String ACTION_CREATE = "CREATE";
    /**
     * The constant ACTION_UPDATE.
     */
    public static final String ACTION_UPDATE = "UPDATE";
    /**
     * The constant ACTION_CANCEL.
     */
    public static final String ACTION_CANCEL = "CANCEL";
    /**
     * The constant ACTION_DELETE.
     */
    public static final String ACTION_DELETE = "DELETE";
    /**
     * The constant ACTION_ADMIN_DELETE.
     */
    public static final String ACTION_ADMIN_DELETE = "ADMIN_DELETE";
    /**
     * The constant ACTION_ADMIN_CREATE.
     */
    public static final String ACTION_ADMIN_CREATE = "ADMIN_CREATE";
    /**
     * The constant ACTION_ADMIN_UPDATE.
     */
    public static final String ACTION_ADMIN_UPDATE = "ADMIN_UPDATE";
    /**
    * Hide constructor.
    */
    private HistoryConstants() {
        //Default.
    }
}

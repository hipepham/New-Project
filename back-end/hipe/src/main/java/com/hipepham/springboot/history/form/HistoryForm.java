/*
 * Copyright (C) FPT Software 2020, Inc - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.hipepham.springboot.history.form;

import lombok.*;

import java.util.List;

/**
 * The type History form.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class HistoryForm {
    /**
     * The Object id.
     */
    private String objectId;
    /**
     * The Type.
     */
    private String type;
    /**
     * The Action.
     */
    private String action;
    /**
     * The Old value.
     */
    private Object oldValue;
    /**
     * The New value.
     */
    private Object newValue;
    /**
     * The Properties.
     */
    private List<String> properties;
}

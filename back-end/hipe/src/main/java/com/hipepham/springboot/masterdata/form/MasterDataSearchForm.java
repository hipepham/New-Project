/*
 * Copyright (C) FPT Software 2020, Inc - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.hipepham.springboot.masterdata.form;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * The type Master data search form.
 */
@Getter
@Setter
@ToString
public class MasterDataSearchForm {
    /**
     * The Type.
     */
    private String type;
    /**
     * The List Type.
     */
    private List<String> types;
    /**
     * The Code.
     */
    private String code;
    /**
     * The Name.
     */
    private String name;
}

/*
 * Copyright (C) FPT Software 2020, Inc - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.hipepham.springboot.masterdata.form;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * The type Master data form.
 */
@Getter
@Setter
@ToString
public class MasterDataForm {
    /**
     * The Name.
     */
    @NotBlank
    private String name;
    /**
     * The Is active.
     */
    private boolean active;
}

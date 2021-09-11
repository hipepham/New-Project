/*
 * Copyright (C) FPT Software 2020, Inc - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.hipepham.springboot.masterdata.form;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * The type Master data update form.
 */
@Getter
@Setter
@ToString
public class MasterDataUpdateForm extends MasterDataForm {
    /**
     * The id.
     */
    @NotNull
    private Long id;
}

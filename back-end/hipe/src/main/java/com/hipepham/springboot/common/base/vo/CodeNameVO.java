/*
 * Copyright (C) FPT Software 2020, Inc - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.hipepham.springboot.common.base.vo;

import com.fasterxml.jackson.annotation.JsonView;
import com.hipepham.springboot.common.base.ApplicationEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CodeNameVO extends BaseVO {
    /**
     * The Code.
     */
    @JsonView(ApplicationEvent.OnNormalView.class)
    private String code;
    /**
     * The Lower code.
     */
    @JsonView(ApplicationEvent.OnNormalView.class)
    private String lowerCode;
    /**
     * The Name.
     */
    @JsonView(ApplicationEvent.OnNormalView.class)
    private String name;
    /**
     * The Lower name.
     */
    @JsonView(ApplicationEvent.OnNormalView.class)
    private String lowerName;
}

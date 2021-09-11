/*
 * Copyright (C) FPT Software 2020, Inc - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.hipepham.springboot.masterdata.vo;

import com.fasterxml.jackson.annotation.JsonView;
import com.hipepham.springboot.common.base.ApplicationEvent;
import com.hipepham.springboot.common.base.vo.CodeNameVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The type Master data vo.
 */
@Getter
@Setter
@ToString
public class MasterDataVO extends CodeNameVO {
    /**
     * The Type.
     */
    @JsonView(ApplicationEvent.OnNormalView.class)
    private String type;

    /**
     * The Using.
     */
    private boolean using;

    /**
     * The Is active.
     */
    @JsonView(ApplicationEvent.OnAdminView.class)
    private boolean active = true;

    /**
     * The Is delete.
     */
    @JsonView(ApplicationEvent.OnAdminView.class)
    private boolean deleted = false;
}

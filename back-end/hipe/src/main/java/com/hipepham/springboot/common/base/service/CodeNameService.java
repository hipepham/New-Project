/*
 * Copyright (C) FPT Software 2020, Inc - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.hipepham.springboot.common.base.service;

import org.springframework.stereotype.Component;
import com.hipepham.springboot.common.base.entity.CodeNameEntity;

@Component
public class CodeNameService extends BaseService {
    public void fillBaseInfo(final CodeNameEntity entity,
                             final String account, boolean isUpdate) {
        super.fillBaseInfo(entity, account, isUpdate);
        entity.setLowerCode(entity.getCode().toLowerCase());
        entity.setLowerName(entity.getName().toLowerCase());
    }
}

/*
 * Copyright (C) FPT Software 2020, Inc - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.hipepham.springboot.masterdata.helper;
import com.hipepham.springboot.common.base.constant.CodeNameConstants;
import com.hipepham.springboot.common.base.helper.BaseHelper;
import com.hipepham.springboot.common.base.helper.HelperResult;
import com.hipepham.springboot.common.constant.Constants;
import com.hipepham.springboot.common.response.vo.Message;
import com.hipepham.springboot.masterdata.constant.MasterDataMessageConstants;
import com.hipepham.springboot.masterdata.form.MasterDataCreateForm;
import com.hipepham.springboot.masterdata.form.MasterDataUpdateForm;
import com.hipepham.springboot.masterdata.service.MasterDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Master data helper.
 */
@Component
public class MasterDataHelper extends BaseHelper {

    /**
     * The Master data service.
     */
    private MasterDataService masterDataService;

    /**
     * Instantiates a new Master data helper.
     *
     * @param service the service
     */
    @Autowired
    public MasterDataHelper(final MasterDataService service) {
        this.masterDataService = service;
    }

    /**
     * Validate create helper result.
     *
     * @param form the form
     * @return the helper result
     */
    public HelperResult validateCreate(final MasterDataCreateForm form) {
        List<Message> messages = new ArrayList<>();
        if (masterDataService.checkCodeExist(form.getCode(), form.getType())) {
            String msgKey =
                    MasterDataMessageConstants.MASTER_DATA_CODE_IS_EXIST;
            String msg = super.getMessageUtils().getMessage(msgKey,
                    form.getCode(), form.getType());
            messages.add(new Message(CodeNameConstants.CODE, msg));
        }
        return HelperResult.buildResult(messages);
    }

    /**
     * Validate update helper result.
     *
     * @param form the form
     * @return the helper result
     */
    public HelperResult validateUpdate(final MasterDataUpdateForm form) {
        List<Message> messages = new ArrayList<>();
        if (masterDataService.isUsing(form.getId())) {
            String msgKey = MasterDataMessageConstants.MASTER_DATA_IS_USING;
            messages.add(new Message(Constants.ERROR,
                    this.getMessageUtils().getMessage(msgKey)));
        }
        return HelperResult.buildResult(messages);
    }

    /**
     * Validate delete helper result.
     *
     * @param id the id
     * @return the helper result
     */
    public HelperResult validateDelete(final Long id) {
        List<Message> messages = new ArrayList<>();
        if (masterDataService.isUsing(id)) {
            String msgKey = MasterDataMessageConstants.MASTER_DATA_IS_USING;
            messages.add(new Message(Constants.ERROR,
                    this.getMessageUtils().getMessage(msgKey)));
        }
        return HelperResult.buildResult(messages);
    }
}

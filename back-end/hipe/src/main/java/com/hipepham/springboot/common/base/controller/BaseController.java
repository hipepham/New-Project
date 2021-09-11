/*
 * Copyright (C) FPT Software 2020, Inc - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.hipepham.springboot.common.base.controller;

import com.hipepham.springboot.common.constant.CommonMessageConstants;
import com.hipepham.springboot.common.jwt.utils.JwtTokenUtils;
import com.hipepham.springboot.common.response.utils.ResponseUtils;
import com.hipepham.springboot.common.response.vo.Message;
import com.hipepham.springboot.common.response.vo.ResponseMessage;
import com.hipepham.springboot.common.utils.MessageUtils;
import com.hipepham.springboot.common.constant.Constants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Base controller.
 */
@Component
public class BaseController {
    /**
     * The Message utils.
     */
    private MessageUtils messageUtils;
    /**
     * The Validator.
     */
    private Validator validator;
    /**
     * The Model mapper.
     */
    private ModelMapper modelMapper;

    /**
     * Forbidden.
     *
     * @return the response entity
     */
    public ResponseEntity forbidden() {
        List<Message> messages = new ArrayList<>();
        ResponseMessage responseMessage = new ResponseMessage();
        String msgKey = "common.forbidden";
        Message msg = new Message();
        msg.setCode("error");
        msg.setMsg(
                getMessageUtils().getMessage(msgKey)
        );
        messages.add(msg);
        responseMessage.setSuccess(false);
        responseMessage.setMessage(messages);
        return ResponseUtils.buildResponseMessage(false,
                responseMessage, HttpStatus.FORBIDDEN);
    }

    /**
     * Gets error message.
     *
     * @return the error message
     */
    public Message getErrorMessage() {
        return new Message(
                Constants.ERROR,
                messageUtils.getMessage(CommonMessageConstants.ERROR)
        );
    }

    public Message getErrorMessage(final Exception e) {
        return new Message(
                Constants.ERROR,
                e.getMessage()
        );
    }

    /**
     * Gets logged in username.
     *
     * @return the logged in username
     */
    public String getLoggedInUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    /**
     * Gets message utils.
     *
     * @return the message utils
     */
    public MessageUtils getMessageUtils() {
        return messageUtils;
    }

    /**
     * Sets message utils.
     *
     * @param pMessageUtils the p message utils
     */
    @Autowired
    public void setMessageUtils(final MessageUtils pMessageUtils) {
        messageUtils = pMessageUtils;
    }

    /**
     * Gets validator.
     *
     * @return the validator
     */
    public Validator getValidator() {
        return validator;
    }

    /**
     * Sets validator.
     *
     * @param pValidator the p validator
     */
//    @Autowired
//    @Qualifier("AppValidator")
//    public void setValidator(final Validator pValidator) {
//        validator = pValidator;
//    }

    /**
     * Sets model mapper.
     *
     * @param pModelMapper the p model mapper
     */
    @Autowired
    public void setModelMapper(final ModelMapper pModelMapper) {
        modelMapper = pModelMapper;
    }

    /**
     * Gets model mapper.
     *
     * @return the model mapper
     */
    public ModelMapper getModelMapper() {
        return modelMapper;
    }
}

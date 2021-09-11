/*
 * Copyright (C) FPT Software 2020, Inc - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.hipepham.springboot.common.response.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ResponseMessage {
    /**
     * Success flag.
     */
    private boolean success;
    /**
     * List message.
     */
    private List<Message> message;
    /**
     * Response data.
     */
    private Object data;
}

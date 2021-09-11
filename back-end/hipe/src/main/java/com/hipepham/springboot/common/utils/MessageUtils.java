/*
 * Copyright (C) FPT Software 2020, Inc - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.hipepham.springboot.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

import static org.springframework.context.i18n.LocaleContextHolder.getLocale;

/**
 * Message utility class.
 */
@Component
public class MessageUtils {

    /**
     * Message source.
     */
    private MessageSource messageSource;

    /**
     * Get message.
     * @param key message key
     * @return message
     */
    public String getMessage(final String key) {
        return messageSource.getMessage(key, null, getLocale());
    }

    /**
     * Get message.
     * @param key message key
     * @param args message params
     * @return message
     */
    public String getMessage(final String key, final Object... args) {
        return messageSource.getMessage(key, args, getLocale());
    }

    /**
     * Get message.
     * @param key message key
     * @param args message params
     * @param pLocale locale
     * @return message
     */
    public String getMessage(
            final String key, final Locale pLocale, final Object... args) {
        return messageSource.getMessage(key, args, pLocale);
    }

    /**
     * Set message source.
     * @param pMessageSource MessageSource object
     */
    @Autowired
    public void setMessageSource(final MessageSource pMessageSource) {
        messageSource = pMessageSource;
    }
}

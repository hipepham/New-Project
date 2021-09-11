/*
 * Copyright (C) FPT Software 2020, Inc - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.hipepham.springboot.common.utils;

import com.hipepham.springboot.common.constant.Constants;
import org.apache.commons.codec.binary.Base64;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.util.ObjectUtils;
import com.hipepham.springboot.common.excel.utility.DateUtils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The type Common utils.
 */
public final class CommonUtils {
    
    /**
    * Hide constructor.
    */
    private CommonUtils() {
        //Default.
    }

    /**
     * Gets email username.
     *
     * @param email the email
     * @return the email username
     */
    public static String getEmailUsername(final String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    /**
     * To list long list.
     *
     * @param objs the objs
     * @return the list
     */
    public static List<Long> toListLong(final Object[] objs) {
        List<Long> result = new ArrayList<>();
        if (!ObjectUtils.isEmpty(objs)) {
            for (Object obj : objs) {
                result.add(new Long(obj+""));
            }
        }
        return result;
    }

    /**
     * Create page pageable.
     *
     * @param pageIndex the page index
     * @param pageSize  the page size
     * @return the pageable
     */
    public static Pageable createPage(final int pageIndex, final int pageSize) {
        int pageNo = pageIndex - 1;
        if (pageNo < 0) {
            pageNo = 0;
        }
        return PageRequest.of(pageNo, pageSize);
    }


    /**
     * Gets date to string converter.
     *
     * @return the date to string converter
     */
    public static Converter<Date, String> dateToStringConverter() {
        return new Converter<Date, String>() {
            @Override
            public String convert(final MappingContext<Date, String> ctx) {
                return ctx.getSource() != null
                        ? DateUtils.dateToString(ctx.getSource(),
                        Constants.DEFAULT_DATE_FORMAT) : "";
            }
        };
    }

    /**
     * String to date converter converter.
     *
     * @return the converter
     */
    public static Converter<String, Date> stringToDateConverter() {
        return new Converter<String, Date>() {
            @Override
            public Date convert(final MappingContext<String, Date> ctx) {
                return (ctx.getSource() != null && !ctx.getSource().isEmpty())
                        ? DateUtils.stringToDate(ctx.getSource(),
                        Constants.DEFAULT_DATE_FORMAT) : null;
            }
        };
    }

    /**
     * Create basic authen http header http headers.
     *
     * @param username the username
     * @param password the password
     * @return the http headers
     */
    public static HttpHeaders createBasicAuthenHttpHeader(
            final String username, final String password) {
        return new HttpHeaders() {{
            String auth =  username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(Charset.forName("US-ASCII")) );
            String authHeader = "Basic " + new String( encodedAuth );
            set( "Authorization", authHeader );
        }};
    }
}

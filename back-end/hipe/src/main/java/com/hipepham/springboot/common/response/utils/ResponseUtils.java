/*
 * Copyright (C) FPT Software 2020, Inc - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.hipepham.springboot.common.response.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.hipepham.springboot.common.response.vo.ResponseMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * Response utility class.
 */
public final class ResponseUtils {

    /**
     * Hide constructor.
     */
    private ResponseUtils() {
        //Default.
    }

    /**
     * Build REST response message.
     *
     * @param success         success flag.
     * @param responseMessage ResponseMessage object.
     * @return ResponseEntity object.
     */
    public static ResponseEntity buildResponseMessage(
            final boolean success,
            final ResponseMessage responseMessage) {
        responseMessage.setSuccess(success);
        if (success) {
            return ResponseEntity.ok(responseMessage);
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST).body(responseMessage);
        }
    }

    /**
     * Build response message.
     *
     * @param success         the success
     * @param responseMessage the response message
     * @param errStatus       the err status
     * @return the response entity
     */
    public static ResponseEntity buildResponseMessage(
            final boolean success,
            final ResponseMessage responseMessage,
            final HttpStatus errStatus) {
        responseMessage.setSuccess(success);
        if (success) {
            return ResponseEntity.ok(responseMessage);
        } else {
            return ResponseEntity
                    .status(errStatus).body(responseMessage);
        }
    }


    /**
     * To json with view.
     *
     * @param obj  the obj
     * @param view the view
     * @return the string
     * @throws JsonProcessingException the json processing exception
     */
    public static String toJsonWithView(final Object obj, final Class view)
            throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        //mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        return mapper.writerWithView(view)
                .writeValueAsString(obj);
    }

    /**
     * Build response with view.
     *
     * @param obj  the obj
     * @param view the view
     * @return the object
     * @throws JsonProcessingException the json processing exception
     */
    public static Object buildResponseWithView(
            final Object obj, final Class view)
            throws JsonProcessingException {
        Gson gson = new Gson();
        return gson.fromJson(toJsonWithView(obj, view), Object.class);
    }

    /**
     * Build search result.
     *
     * @param results the results
     * @param totals  the totals
     * @param view    the view
     * @return the map
     * @throws JsonProcessingException the json processing exception
     */
    public static Map<String, Object> buildSearchResult(
            final Object results, final long totals, final Class view)
            throws JsonProcessingException {
        Map<String, Object> mapResults = new HashMap<>();
        mapResults.put("totals", totals);
        mapResults.put("items", buildResponseWithView(
                results, view));
        return mapResults;
    }
}

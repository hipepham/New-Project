package com.hipepham.springboot.common.base.helper;

import com.hipepham.springboot.common.response.vo.Message;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper result class.
 */
public class HelperResult {
    /**
     * success class.
     */
    @Getter
    @Setter
    private boolean success;
    /**
     * message.
     */
    @Getter
    @Setter
    private List<Message> message;

    /**
     * Default constructor.
     */
    public HelperResult() {
    }

    /**
     * Constructor with init params.
     * @param success success flag
     */
    public HelperResult(final boolean success) {
        this.success = success;
    }

    /**
     * Constructor with init params.
     * @param success success flag
     * @param message message
     */
    public HelperResult(final boolean success, final List<Message> message) {
        this.success = success;
        this.message = message;
    }

    /**
     * Build result from message.
     * @param messages list message
     * @return HelperResult object
     */
    public static HelperResult buildResult(final List<Message> messages) {
        HelperResult result = new HelperResult();
        if (messages.size() > 0) {
            result.setSuccess(false);
            result.setMessage(messages);
        } else {
            result.setSuccess(true);
            result.setMessage(new ArrayList<>());
        }
        return result;
    }
}

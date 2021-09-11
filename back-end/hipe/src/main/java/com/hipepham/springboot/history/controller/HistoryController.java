package com.hipepham.springboot.history.controller;

import com.hipepham.springboot.common.base.ApplicationEvent;
import com.hipepham.springboot.common.base.controller.BaseController;
import com.hipepham.springboot.common.response.utils.ResponseUtils;
import com.hipepham.springboot.common.response.vo.Message;
import com.hipepham.springboot.common.response.vo.ResponseMessage;
import com.hipepham.springboot.history.service.HistoryService;
import com.hipepham.springboot.history.vo.HistoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(HistoryController.HISTORY_PATH)
public class HistoryController extends BaseController {
    public static final String HISTORY_PATH = "/history";

    private HistoryService historyService;

    @Autowired
    public HistoryController(final HistoryService historyService) {
        this.historyService = historyService;
    }
    @GetMapping("/get-history-request")
    public ResponseEntity getAllHistoryRequest(final @RequestParam String requestId,
                                               final @RequestParam String objectId,
                                               final @RequestParam int pageIndex,
                                               final @RequestParam int pageSize){
        ResponseMessage responseMessage = new ResponseMessage();
        List<HistoryVO> historyVOS;
        List<Message> messages = new ArrayList<>();
        responseMessage.setSuccess(false);
        try{
			historyVOS = historyService.getAllHistoryRequest(requestId, objectId, pageIndex, pageSize);
            int total = historyService.totalHistoryRequest(requestId, objectId);
            Object data = ResponseUtils.buildSearchResult(historyVOS,total,
                    ApplicationEvent.OnAdminView.class);
            responseMessage.setData(data);
            responseMessage.setSuccess(true);
        }catch (Exception e){
            messages.add(super.getErrorMessage());
        }
        responseMessage.setMessage(messages);
        return ResponseUtils.buildResponseMessage(
                responseMessage.isSuccess(), responseMessage);
    }

}

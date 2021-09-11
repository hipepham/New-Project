package com.hipepham.springboot.masterdata.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hipepham.springboot.common.base.ApplicationEvent;
import com.hipepham.springboot.common.base.controller.BaseController;
import com.hipepham.springboot.common.base.helper.HelperResult;
import com.hipepham.springboot.common.constant.Constants;
import com.hipepham.springboot.common.response.utils.ResponseUtils;
import com.hipepham.springboot.common.response.vo.Message;
import com.hipepham.springboot.common.response.vo.ResponseMessage;
import com.hipepham.springboot.masterdata.constant.MasterDataMessageConstants;
import com.hipepham.springboot.masterdata.entity.MasterData;
import com.hipepham.springboot.masterdata.form.MasterDataCreateForm;
import com.hipepham.springboot.masterdata.form.MasterDataSearchForm;
import com.hipepham.springboot.masterdata.form.MasterDataUpdateForm;
import com.hipepham.springboot.masterdata.helper.MasterDataHelper;
import com.hipepham.springboot.masterdata.service.MasterDataService;
import com.hipepham.springboot.masterdata.vo.MasterDataVO;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(MasterDataController.MASTER_DATA_PATH)
public class MasterDataController extends BaseController {

    /**
     * The constant MASTER_DATA_PATH.
     */
    public static final String MASTER_DATA_PATH = "/master-data";

    /**
     * The constant OBJECT_TYPE_CODE.
     */
    public static final String OBJECT_TYPE_CODE = "MASTER_DATA";

    /**
     * The Master data service.
     */
    @Autowired
    private MasterDataService masterDataService;

    @Autowired
    private MasterDataHelper masterDataHelper;


    /**
     * List response entity.
     *
     * @return the response entity
     */
    @GetMapping("/list")
    public ResponseEntity list() {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setData(masterDataService.list());
        responseMessage.setSuccess(true);
        return ResponseUtils.buildResponseMessage(true, responseMessage);
    }

    /**
     * Search response entity.
     *
     * @param code     the code
     * @param name     the name
     * @param type     the type
     * @param types    the types
     * @param pageNo   the page no
     * @param pageSize the page size
     * @return the response entity
     */
    @GetMapping("/search")
    public ResponseEntity search(
            final @RequestParam(value = "code", required = false) String code,
            final @RequestParam(value = "name", required = false) String name,
            final @RequestParam(value = "type", required = false) String type,
            final @RequestParam(value = "types", required = false)
                    List<String> types,
            final @RequestParam(value = "pageNo") int pageNo,
            final @RequestParam(value = "pageSize") int pageSize) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setSuccess(false);
        List<Message> messages = new ArrayList<>();
        try {
            MasterDataSearchForm form = new MasterDataSearchForm();
            form.setType(type);
            form.setTypes(types);
            form.setName(name);
            form.setCode(code);
            List<MasterData> masterDataList = masterDataService.search(
                    form, pageNo, pageSize);
            long total = masterDataService.count(form);
            List<MasterDataVO> result = super.getModelMapper().map(
                    masterDataList, new TypeToken<List<MasterDataVO>>() { }
                            .getType());
            Object mapResult = ResponseUtils.buildSearchResult(result,
                    total, ApplicationEvent.OnAdminView.class);
            responseMessage.setData(mapResult);
            responseMessage.setSuccess(true);
        } catch (Exception e) {
            messages.add(new Message("error", e.getMessage()));
            responseMessage.setSuccess(false);
        }
        responseMessage.setMessage(messages);
        return ResponseUtils.buildResponseMessage(
                responseMessage.isSuccess(), responseMessage);
    }

    /**
     * Get response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/{id}")
    public ResponseEntity get(final @PathVariable("id") Long id) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setSuccess(false);
        List<Message> messages = new ArrayList<>();
        try {
            MasterData masterData = masterDataService.get(id, true);
            if (masterData != null) {
                MasterDataVO vo = super.getModelMapper()
                        .map(masterData, MasterDataVO.class);
                responseMessage.setData(ResponseUtils
                        .buildResponseWithView(vo,
                                ApplicationEvent.OnAdminView.class));
                responseMessage.setSuccess(true);
            } else {
                String msgKey =
                        MasterDataMessageConstants.MASTER_DATA_NOT_FOUND;
                messages.add(new Message(Constants.ERROR,
                        super.getMessageUtils().getMessage(msgKey)));
            }
        } catch (Exception e) {
            messages.add(new Message("error", e.getMessage()));
            responseMessage.setSuccess(false);
        }
        responseMessage.setMessage(messages);
        return ResponseUtils.buildResponseMessage(
                responseMessage.isSuccess(), responseMessage);
    }

    /**
     * Gets list.
     *
     * @param ids the ids
     * @return the list
     */
    @GetMapping
    public ResponseEntity getList(@RequestParam final List<Long> ids) {
        ResponseMessage msg = new ResponseMessage();
        msg.setSuccess(true);
        List<MasterData> data = masterDataService.get(ids, true);
        msg.setData(data
                .stream()
                .map(value -> {
                    try {
                        return ResponseUtils.buildResponseWithView(
                                value, ApplicationEvent.OnNormalView.class);
                    } catch (JsonProcessingException pE) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList())
        );
        return ResponseUtils.buildResponseMessage(true, msg);
    }

    /**
     * Gets by type.
     *
     * @param types   the types
     * @param typeIds the type ids
     * @return the by type
     */
    @GetMapping("/get-by-type")
    public ResponseEntity getByType(
            final @RequestParam(value = "type", required = false)
                    List<String> types,
            final @RequestParam(value = "typeIds", required = false)
                    List<Long> typeIds) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setSuccess(false);
        List<Message> messages = new ArrayList<>();
        try {
            List<MasterData> masterDataList = new
                    ArrayList<>();
            if (!ObjectUtils.isEmpty(types)) {
                masterDataList = masterDataService.getByTypeInList(types, true);
            } else if (!ObjectUtils.isEmpty(typeIds)) {
                masterDataList = masterDataService.getByListTypeId(typeIds, true);
            }
            if (masterDataList != null) {
                List<MasterDataVO> result = super.getModelMapper().map(
                        masterDataList, new TypeToken<List<MasterDataVO>>() { }
                                .getType());
                Object mapResults = ResponseUtils.buildSearchResult(result,
                        result.size(), ApplicationEvent.OnNormalView.class);
                responseMessage.setData(mapResults);
                responseMessage.setSuccess(true);
            }
        } catch (Exception e) {
            messages.add(new Message("error", e.getMessage()));
            responseMessage.setSuccess(false);
        }
        responseMessage.setMessage(messages);
        return ResponseUtils.buildResponseMessage(
                responseMessage.isSuccess(), responseMessage);
    }

    /**
     * Gets by type and code.
     *
     * @param type the type
     * @param code the code
     * @return the by type and code
     */
    @GetMapping("/get-by-type-and-code")
    public ResponseEntity getByTypeAndCode(
            final @RequestParam("type") String type,
            final @RequestParam("code") String code) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setSuccess(false);
        List<Message> messages = new ArrayList<>();
        try {
            MasterData masterData = masterDataService
                    .getByTypeAndCode(type, code, true);
            if (masterData != null) {
                MasterDataVO vo = super.getModelMapper().map(
                        masterData, MasterDataVO.class);
                Object result = ResponseUtils.buildResponseWithView(vo,
                        ApplicationEvent.OnNormalView.class);
                responseMessage.setData(result);
                responseMessage.setSuccess(true);
            }
        } catch (Exception e) {
            messages.add(new Message("error", e.getMessage()));
            responseMessage.setSuccess(false);
        }
        responseMessage.setMessage(messages);
        return ResponseUtils.buildResponseMessage(
                responseMessage.isSuccess(), responseMessage);
    }

    /**
     * Create response entity.
     *
     * @param form the form
     * @return the response entity
     */
    @PostMapping
    @PreAuthorize("hasPermission('" + MasterDataController.OBJECT_TYPE_CODE + "', 'CREATE')")
    public ResponseEntity create(
            final @Valid @RequestBody MasterDataCreateForm form) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setSuccess(false);
        List<Message> messages = new ArrayList<>();
        try {
            HelperResult helperResult = masterDataHelper.validateCreate(form);
            if (!helperResult.isSuccess()) {
                messages.addAll(helperResult.getMessage());
            } else {
                String username = super.getLoggedInUsername();
                MasterData masterData =
                        masterDataService.create(form, username);
                String msgKey = MasterDataMessageConstants
                        .MASTER_DATA_CREATE_FAILED;
                String msgCode = Constants.ERROR;
                if (masterData != null && masterData.getId() != null) {
                    msgKey = MasterDataMessageConstants
                            .MASTER_DATA_CREATE_SUCCESS;
                    msgCode = Constants.SUCCESS;
                    responseMessage.setSuccess(true);
                }
                String msg = this.getMessageUtils().getMessage(msgKey);
                messages.add(new Message(msgCode, msg));
            }
        } catch (Exception e) {
            messages.add(new Message(Constants.ERROR, e.getMessage()));
            responseMessage.setSuccess(false);
        }
        responseMessage.setMessage(messages);
        return ResponseUtils.buildResponseMessage(
                responseMessage.isSuccess(), responseMessage);
    }

    /**
     * Update response entity.
     *
     * @param form the form
     * @return the response entity
     */
    @PutMapping
    @PreAuthorize("hasPermission('" + MasterDataController.OBJECT_TYPE_CODE + "', 'UPDATE')")
    public ResponseEntity update(
            final @Valid @RequestBody MasterDataUpdateForm form) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setSuccess(false);
        List<Message> messages = new ArrayList<>();
        try {
            HelperResult helperResult = masterDataHelper.validateUpdate(form);
            if (!helperResult.isSuccess()) {
                messages.addAll(helperResult.getMessage());
            } else {
                String username = super.getLoggedInUsername();
                MasterData masterData =
                        masterDataService.update(form, username);
                String msgKey = MasterDataMessageConstants
                        .MASTER_DATA_UPDATE_FAILED;
                String msgCode = Constants.ERROR;
                if (masterData != null && masterData.getId() != null) {
                    msgKey = MasterDataMessageConstants
                            .MASTER_DATA_UPDATE_SUCCESS;
                    msgCode = Constants.SUCCESS;
                    responseMessage.setSuccess(true);
                }
                String msg = this.getMessageUtils().getMessage(msgKey);
                messages.add(new Message(msgCode, msg));
            }
        } catch (Exception e) {
            messages.add(new Message("error", e.getMessage()));
            responseMessage.setSuccess(false);
        }
        responseMessage.setMessage(messages);
        return ResponseUtils.buildResponseMessage(
                responseMessage.isSuccess(), responseMessage);
    }

    /**
     * Delete response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @PreAuthorize("hasPermission('" + MasterDataController.OBJECT_TYPE_CODE + "', 'DELETE')")
    @DeleteMapping
    public ResponseEntity delete(final @RequestParam("id") Long id) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setSuccess(false);
        List<Message> messages = new ArrayList<>();
        try {
            HelperResult helperResult = masterDataHelper.validateDelete(id);
            if (!helperResult.isSuccess()) {
                messages.addAll(helperResult.getMessage());
            } else {
                masterDataService.delete(id);
                String msgKey =
                        MasterDataMessageConstants.MASTER_DATA_DELETE_SUCCESS;
                responseMessage.setSuccess(true);
                messages.add(new Message(Constants.SUCCESS,
                        super.getMessageUtils().getMessage(msgKey)));
            }
        } catch (Exception e) {
            messages.add(new Message("error", e.getMessage()));
            responseMessage.setSuccess(false);
        }
        responseMessage.setMessage(messages);
        return ResponseUtils.buildResponseMessage(
                responseMessage.isSuccess(), responseMessage);
    }

    /**
     * Gets by type and code in.
     *
     * @param type  the type
     * @param codes the codes
     * @return the by type and code in
     */
    @GetMapping("/get-by-type-and-codes")
    public ResponseEntity getByTypeAndCodeIn(
            final @RequestParam("type") String type,
            final @RequestParam("codes") Set<String> codes) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setSuccess(true);
        List<Message> messages = new ArrayList<>();
        try {
            List<MasterData> masterDataList =
                    masterDataService.getByTypeAndCodeInList(
                            type, codes, true);
            if (!ObjectUtils.isEmpty(masterDataList)) {
                List<MasterDataVO> result = this.getModelMapper().map(
                        masterDataList,
                        new TypeToken<List<MasterDataVO>>() { }.getType());
                responseMessage.setData(
                        ResponseUtils.buildResponseWithView(result,
                                ApplicationEvent.OnNormalView.class));
            }
        } catch (Exception e) {
            messages.add(new Message("error", e.getMessage()));
            responseMessage.setSuccess(false);
        }
        responseMessage.setMessage(messages);
        return ResponseUtils.buildResponseMessage(
                responseMessage.isSuccess(), responseMessage);
    }
}

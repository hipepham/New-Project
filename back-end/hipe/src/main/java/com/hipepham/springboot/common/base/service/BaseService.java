/*
 * Copyright (C) FPT Software 2020, Inc - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.hipepham.springboot.common.base.service;

import com.hipepham.springboot.common.base.entity.BaseEntity;
import com.hipepham.springboot.history.entity.History;
import com.hipepham.springboot.history.form.HistoryForm;
import com.hipepham.springboot.history.service.HistoryService;
import com.hipepham.springboot.history.service.impl.HistoryServiceImpl;
import com.hipepham.springboot.auth.SystemRoleEnum;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.Map;

/**
 * The type Base service.
 */
@Component
@Log4j2
public class BaseService {
    /**
     * ModelMapper.
     */
    private ModelMapper modelMapper;
    /**
     * The Entity manager.
     */
    @PersistenceContext
    private EntityManager entityManager;
    /**
     * The Application context.
     */
    private ApplicationContext applicationContext;

    /**
     * Fill base info.
     *
     * @param entity   the entity
     * @param account  the account
     * @param isUpdate the is update
     */
    public void fillBaseInfo(final BaseEntity entity,
                                   final String account, boolean isUpdate) {
        Date now = new Date();
        if (!isUpdate) {
            entity.setCreatedBy(account);
            entity.setCreatedTime(now);
            entity.setModifiedTime(now);
            entity.setModifiedBy(account);
        } else {
            entity.setModifiedBy(account);
            entity.setModifiedTime(new Date());
        }
    }

    /**
     * Init query params.
     *
     * @param query  the query
     * @param params the params
     */
    public void initQueryParams(final Query query,
                                final Map<String, Object> params) {
        if (!ObjectUtils.isEmpty(params)) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Check system admin role boolean.
     *
     * @param account the account
     * @param exclude the exclude
     * @return the boolean
     */
    public boolean checkSystemAdminRole(final String account,
                                        final String exclude) {
        boolean check = false;
//        UserRoleService userRoleService =
//                this.applicationContext.getBean(UserRoleServiceImpl.class);
//        List<String> userRoleCodes = userRoleService
//                .getRoleCodeByAccount(account);
//        if (!ObjectUtils.isEmpty(userRoleCodes)) {
//            if (!SystemRoleEnum.SUPER_ADMIN.getCode()
//                    .equalsIgnoreCase(exclude)) {
//                check = userRoleCodes
//                        .contains(SystemRoleEnum.SUPER_ADMIN.getCode());
//            }
//            if (!check && !SystemRoleEnum.ADMIN.getCode()
//                    .equalsIgnoreCase(exclude) ) {
//                check = userRoleCodes
//                        .contains(SystemRoleEnum.ADMIN.getCode());
//            }
//            if (!check && !SystemRoleEnum.BOV.getCode()
//                    .equalsIgnoreCase(exclude) ) {
//                check = userRoleCodes
//                        .contains(SystemRoleEnum.BOV.getCode());
//            }
//        }
        return check;
    }

    /**
     * Check system admin role boolean.
     *
     * @param account the account
     * @return the boolean
     */
    public boolean checkSystemAdminRole(final String account) {
        return checkSystemAdminRole(account, null);
    }

    /**
     * Check system admin role boolean.
     *
     * @param account the account
     * @return the boolean
     */
    public boolean checkSystemAdminRoleExceptBov(final String account) {
        return checkSystemAdminRole(account, SystemRoleEnum.BOV.getCode());
    }

    /**
     * Init paging.
     *
     * @param query    the query
     * @param pageNo   the page no
     * @param pageSize the page size
     */
    public void initPaging(final Query query, int pageNo, int pageSize) {
        query.setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize);
    }

    /**
     * Gets user ip.
     *
     * @return the user ip
     */
    public String getUserIp() {
//        WebAuthenticationDetails request = (WebAuthenticationDetails)
//                SecurityContextHolder.getContext().getAuthentication()
//                        .getDetails();
//        return request.getRemoteAddress();
        Map<String, String> details= (Map<String, String>)
                SecurityContextHolder.getContext().getAuthentication().getDetails();
        return details.get("ip");
    }

    /**
     * Get access token.
     * @return
     */
    public String getAccessToken() {
        Map<String, String> details= (Map<String, String>)
                SecurityContextHolder.getContext().getAuthentication().getDetails();
        return details.get("accessToken");
    }

    /**
     * Create history history.
     *
     * @param form the form
     * @return the history
     */
    public History createHistory(final HistoryForm form) {
        try {
            HistoryService historyService =
                    this.applicationContext.getBean(HistoryServiceImpl.class);
            return historyService.save(form);
        } catch (Exception e) {
            log.error(e);
            return null;
        }

    }

    /**
     * Gets model mapper.
     *
     * @return the model mapper
     */
    public ModelMapper getModelMapper() {
        return modelMapper;
    }

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
     * Gets entity manager.
     *
     * @return the entity manager
     */
    public EntityManager getEntityManager() {
        return entityManager;
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
     * Gets application context.
     *
     * @return the application context
     */
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * Sets application context.
     *
     * @param pApplicationContext the p application context
     */
    @Autowired
    public void setApplicationContext(
            final ApplicationContext pApplicationContext) {
        applicationContext = pApplicationContext;
    }

}

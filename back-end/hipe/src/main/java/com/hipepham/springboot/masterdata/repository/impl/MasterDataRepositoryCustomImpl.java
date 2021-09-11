/*
 * Copyright (C) FPT Software 2020, Inc - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.hipepham.springboot.masterdata.repository.impl;

import com.hipepham.springboot.common.repository.BaseRepository;
import com.hipepham.springboot.masterdata.entity.MasterData;
import com.hipepham.springboot.masterdata.form.MasterDataSearchForm;
import com.hipepham.springboot.masterdata.repository.MasterDataRepositoryCustom;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Master data repository custom.
 */
@Repository
@Transactional
public class MasterDataRepositoryCustomImpl extends BaseRepository
        implements MasterDataRepositoryCustom {
    /**
     * Count by code and type long.
     *
     * @param code the code
     * @param type the type
     * @return the long
     */
    @Override
    public long countByCodeAndType(final String code, final String type) {
        String sql = "select count(o) from MasterData o "
                + "where o.lowerCode = lower(:code) "
                + "and o.type = :type "
                + "and o.deleted = false";
        TypedQuery<Long> query = super.getEntityManager().createQuery(sql,
                Long.class);
        query.setParameter("code", code);
        query.setParameter("type", type);
        return query.getSingleResult();
    }

    /**
     * Count by code and type long.
     *
     * @param code  the code
     * @param type  the type
     * @param idNot the id not
     * @return the long
     */
    @Override
    public long countByCodeAndType(final String code,
                                   final String type,
                                   final long idNot) {
        String sql = "select count(o) from MasterData o "
                + "where o.lowerCode = lower(:code) "
                + "and o.type = :type "
                + "and o.deleted = false and o.id != :id";
        TypedQuery<Long> query = super.getEntityManager().createQuery(sql,
                Long.class);
        query.setParameter("code", code);
        query.setParameter("type", type);
        query.setParameter("id", idNot);
        return query.getSingleResult();
    }

    /**
     * Search list.
     *
     * @param form     the form
     * @param pageNo   the page no
     * @param pageSize the page size
     * @return the list
     */
    @Override
    public List<MasterData> search(final MasterDataSearchForm form,
                                   final int pageNo, final int pageSize) {
        TypedQuery<MasterData> query =
                this.buildSearchQuery(form, MasterData.class, false);
        super.initPaging(query, pageNo, pageSize);
        return query.getResultList();
    }

    /**
     * Count.
     *
     * @param form the form
     * @return the list
     */
    @Override
    public long count(final MasterDataSearchForm form) {
        TypedQuery<Long> query =
                this.buildSearchQuery(form, Long.class, true);
        return query.getSingleResult();
    }


    /**
     * Build search query typed query.
     *
     * @param <T>   the type parameter
     * @param form  the form
     * @param clazz the clazz
     * @param count the count
     * @return the typed query
     */
    private <T> TypedQuery<T> buildSearchQuery(
            final MasterDataSearchForm form,
            final Class<T> clazz, final boolean count) {
        String baseQuery = "select o from MasterData o "
                + "where o.deleted = false ";
        if (count) {
            baseQuery = "select count(o) from MasterData o "
                    + "where o.deleted = false ";
        }
        StringBuilder sql = new StringBuilder(baseQuery);
        Map<String, Object> params = new HashMap<>();
        if (!ObjectUtils.isEmpty(form.getCode())) {
            sql.append("and o.lowerCode like lower(:code) ");
            params.put("code", "%" + form.getCode() + "%");
        }
        if (!ObjectUtils.isEmpty(form.getName())) {
            sql.append("and o.lowerName like lower(:name) ");
            params.put("name", "%" + form.getName() + "%");
        }
        if (!ObjectUtils.isEmpty(form.getType())) {
            sql.append("and o.type like lower(:type) ");
            params.put("type", "%" + form.getType() + "%");
        }
        if (!ObjectUtils.isEmpty(form.getTypes())) {
            sql.append("and o.type in (:types) ");
            params.put("types", form.getTypes());
        }
        return super.createQuery(sql.toString(), params, clazz);
    }
}

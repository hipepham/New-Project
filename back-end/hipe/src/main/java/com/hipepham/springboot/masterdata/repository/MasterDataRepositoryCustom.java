/*
 * Copyright (C) FPT Software 2020, Inc - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.hipepham.springboot.masterdata.repository;

import com.hipepham.springboot.masterdata.entity.MasterData;
import com.hipepham.springboot.masterdata.form.MasterDataSearchForm;

import java.util.List;

/**
 * The interface Master data repository custom.
 */
public interface MasterDataRepositoryCustom {
    /**
     * Count by code and type long.
     *
     * @param code the code
     * @param type the type
     * @return the long
     */
    long countByCodeAndType(String code, String type);

    /**
     * Count by code and type long.
     *
     * @param code  the code
     * @param type  the type
     * @param idNot the id not
     * @return the long
     */
    long countByCodeAndType(String code, String type, long idNot);

    /**
     * Search list.
     *
     * @param form     the form
     * @param pageNo   the page no
     * @param pageSize the page size
     * @return the list
     */
    List<MasterData> search(MasterDataSearchForm form,
                            int pageNo, int pageSize);

    /**
     * Count list.
     *
     * @param form the form
     * @return the list
     */
    long count(MasterDataSearchForm form);
}

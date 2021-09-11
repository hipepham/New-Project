/*
 * Copyright (C) FPT Software 2020, Inc - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.hipepham.springboot.cache.service;

import com.hipepham.springboot.cache.hash.MasterDataCache;
import com.hipepham.springboot.masterdata.entity.MasterData;

import java.util.List;
import java.util.Set;

/**
 * The interface Master data cache service.
 */
public interface MasterDataCacheService {
    /**
     * Get master data cache.
     *
     * @param id the id
     * @return the master data cache
     */
    MasterDataCache get(Long id);

    /**
     * Gets by type.
     *
     * @param type the type
     * @return the by type
     */
    List<MasterDataCache> getByType(String type);

    /**
     * Gets by type and code.
     *
     * @param type the type
     * @param code the code
     * @return the by type and code
     */
    MasterDataCache getByTypeAndCode(String type, String code);

    /**
     * Save master data cache.
     *
     * @param masterData the master data
     * @return the master data cache
     */
    MasterDataCache save(MasterData masterData);

    /**
     * Save list.
     *
     * @param masterData the master data
     * @return the list
     */
    List<MasterDataCache> save(List<MasterData> masterData);

    /**
     * Delete.
     *
     * @param id the id
     */
    void delete(Long id);

    /**
     * Gets by type in list.
     *
     * @param type the type
     * @return the by type in list
     */
    List<MasterDataCache> getByTypeInList(List<String> type);

    /**
     * Gets by list type id.
     *
     * @param types the types
     * @return the by list type id
     */
    List<MasterDataCache> getByListTypeId(List<Long> types);

    /**
     * Gets by type and code in list.
     *
     * @param type  the type
     * @param codes the codes
     * @return the by type and code in list
     */
    List<MasterDataCache> getByTypeAndCodeInList(String type,
                                                 Set<String> codes);

    /**
     * Gets by id in list.
     *
     * @param ids the ids
     * @return the by id in list
     */
    List<MasterDataCache> getByIdInList(List<Long> ids);
}

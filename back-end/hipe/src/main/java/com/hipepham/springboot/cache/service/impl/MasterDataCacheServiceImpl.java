/*
 * Copyright (C) FPT Software 2020, Inc - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.hipepham.springboot.cache.service.impl;

import com.hipepham.springboot.cache.hash.MasterDataCache;
import com.hipepham.springboot.cache.helper.MasterDataCacheHelper;
import com.hipepham.springboot.cache.repository.MasterDataCacheRepository;
import com.hipepham.springboot.cache.service.MasterDataCacheService;
import com.hipepham.springboot.masterdata.entity.MasterData;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The type Master data cache service.
 */
@Service
public class MasterDataCacheServiceImpl
        implements MasterDataCacheService {
    /**
     * The Master data cache repository.
     */
    private MasterDataCacheRepository masterDataCacheRepository;
    /**
     * The Master data cache helper.
     */
    private MasterDataCacheHelper masterDataCacheHelper;
    /**
     * The Local cache.
     */
    private static Map<Long, MasterDataCache> localCache = new HashMap<>();

    /**
     * Instantiates a new Master data cache service.
     *
     * @param pMasterDataCacheRepository the p master data cache repository
     * @param pMasterDataCacheHelper     the p master data cache helper
     */
    public MasterDataCacheServiceImpl(
            final MasterDataCacheRepository pMasterDataCacheRepository,
            final MasterDataCacheHelper pMasterDataCacheHelper) {
        this.masterDataCacheRepository = pMasterDataCacheRepository;
        this.masterDataCacheHelper = pMasterDataCacheHelper;
    }

    /**
     * Get master data cache.
     *
     * @param id the id
     * @return the master data cache
     */
    @Override
    public MasterDataCache get(final Long id) {
        if (localCache != null && localCache.containsKey(id)) {
            return localCache.get(id);
        } else {
            return masterDataCacheRepository.findById(id).orElse(null);
        }
    }

    /**
     * Gets by type.
     *
     * @param type the type
     * @return the by type
     */
    @Override
    public List<MasterDataCache> getByType(final String type) {
        return masterDataCacheRepository.findAllByType(type);
    }

    /**
     * Gets by type and code.
     *
     * @param type the type
     * @param code the code
     * @return the by type and code
     */
    @Override
    public MasterDataCache getByTypeAndCode(final String type,
                                            final String code) {
        return masterDataCacheRepository.findByTypeAndLowerCode(type,
                code.toLowerCase());
    }

    /**
     * Save master data cache.
     *
     * @param masterData the master data
     * @return the master data cache
     */
    @Override
    public MasterDataCache save(final MasterData masterData) {
        MasterDataCache cache = masterDataCacheRepository.save(
                masterDataCacheHelper.convertFromEntity(masterData));
        if (cache != null) {
            localCache.put(cache.getId(), cache);
        }
        return cache;
    }

    /**
     * Save list.
     *
     * @param masterData the master data
     * @return the list
     */
    @Override
    public List<MasterDataCache> save(final List<MasterData> masterData) {
        List<MasterDataCache> result = new ArrayList<>();
        masterDataCacheRepository.saveAll(
                masterDataCacheHelper.convertFromListEntity(masterData))
                .forEach(result::add);
        localCache = result.stream().collect(
                Collectors.toMap(MasterDataCache::getId, item -> item));
        return result;
    }

    /**
     * Delete.
     *
     * @param id the id
     */
    @Override
    public void delete(final Long id) {
        localCache.remove(id);
        masterDataCacheRepository.deleteById(id);
    }

    /**
     * Gets by type in list.
     *
     * @param type the type
     * @return the by type in list
     */
    @Override
    public List<MasterDataCache> getByTypeInList(final List<String> type) {
        Set<MasterDataCache> sets = new HashSet<>();
        for (String t : type) {
            List<MasterDataCache> caches = this.getByType(t);
            sets.addAll(caches);
        }
        return new ArrayList<>(sets);
    }

    /**
     * Gets by list type id.
     *
     * @param types the types
     * @return the by list type id
     */
    @Override
    public List<MasterDataCache> getByListTypeId(
            final List<Long> types) {
        Set<MasterDataCache> sets = new HashSet<>();
        for (Long id : types) {
            MasterDataCache cache = this.get(id);
            if (cache != null && cache.getCode() != null ) {
                List<MasterDataCache> list =
                        this.getByType(cache.getCode());
                sets.addAll(list);
            }
        }
        return new ArrayList<>(sets);
    }

    /**
     * Gets by type and code in list.
     *
     * @param type  the type
     * @param codes the codes
     * @return the by type and code in list
     */
    @Override
    public List<MasterDataCache> getByTypeAndCodeInList(
            final String type,
            final Set<String> codes) {
        Set<MasterDataCache> caches = new HashSet<>();
        for (String code : codes) {
            MasterDataCache cache = this.getByTypeAndCode(type, code);
            if (cache != null) {
                caches.add(cache);
            }
        }
        return new ArrayList<>(caches);
    }

    /**
     * Gets by id in list.
     *
     * @param ids the ids
     * @return the by id in list
     */
    @Override
    public List<MasterDataCache> getByIdInList(final List<Long> ids) {
        List<MasterDataCache> result = new ArrayList<>();
        masterDataCacheRepository.findAllById(ids).forEach(result::add);
        return result;
    }
}

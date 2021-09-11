/*
 * Copyright (C) FPT Software 2020, Inc - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.hipepham.springboot.cache.helper;

import com.hipepham.springboot.cache.hash.MasterDataCache;
import com.hipepham.springboot.common.base.helper.BaseHelper;
import com.hipepham.springboot.common.utils.CommonUtils;
import com.hipepham.springboot.masterdata.entity.MasterData;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The type Master data cache helper.
 */
@Component
public class MasterDataCacheHelper extends BaseHelper {
    /**
     * Convert from entity master data cache.
     *
     * @param masterData the master data
     * @return the master data cache
     */
    public MasterDataCache convertFromEntity(final MasterData masterData) {
        ModelMapper modelMapper = super.getModelMapper();
        modelMapper.addConverter(CommonUtils.dateToStringConverter());
        return modelMapper.map(masterData, MasterDataCache.class);
    }

    /**
     * Convert from list entity list.
     *
     * @param masterDataList the master data list
     * @return the list
     */
    public List<MasterDataCache> convertFromListEntity(
            final List<MasterData> masterDataList) {
        ModelMapper modelMapper = super.getModelMapper();
        modelMapper.addConverter(CommonUtils.dateToStringConverter());
        return modelMapper.map(masterDataList,
                new TypeToken<List<MasterDataCache>>() {}.getType());
    }

    /**
     * Convert to entity master data.
     *
     * @param cache the cache
     * @return the master data
     */
    public MasterData convertToEntity(final MasterDataCache cache) {
        ModelMapper modelMapper = super.getModelMapper();
        modelMapper.addConverter(CommonUtils.stringToDateConverter());
        return modelMapper.map(cache, MasterData.class);
    }

    /**
     * Convert to list entity list.
     *
     * @param caches the master data list
     * @return the list
     */
    public List<MasterData> convertToListEntity(
            final List<MasterDataCache> caches) {
        ModelMapper modelMapper = super.getModelMapper();
        modelMapper.addConverter(CommonUtils.stringToDateConverter());
        return modelMapper.map(caches,
                new TypeToken<List<MasterData>>() {}.getType());
    }
}

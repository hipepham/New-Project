package com.hipepham.springboot.masterdata.service.impl;

import com.hipepham.springboot.cache.hash.MasterDataCache;
import com.hipepham.springboot.cache.helper.MasterDataCacheHelper;
import com.hipepham.springboot.cache.service.MasterDataCacheService;
import com.hipepham.springboot.common.base.service.CodeNameService;
import com.hipepham.springboot.common.utils.ListUtils;
import com.hipepham.springboot.common.utils.StringUtils;
import com.hipepham.springboot.masterdata.entity.MasterData;
import com.hipepham.springboot.masterdata.form.MasterDataCreateForm;
import com.hipepham.springboot.masterdata.form.MasterDataSearchForm;
import com.hipepham.springboot.masterdata.form.MasterDataUpdateForm;
import com.hipepham.springboot.masterdata.repository.MasterDataRepository;
import com.hipepham.springboot.masterdata.service.MasterDataService;
import com.hipepham.springboot.masterdata.vo.MasterDataVO;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class MasterDataServiceImpl extends CodeNameService implements MasterDataService {
    /**
     * The Master data repository.
     */
    @Autowired
    private MasterDataRepository masterDataRepository;
    /**
     * The Master data cache service.
     */

    @Autowired
    private MasterDataCacheService masterDataCacheService;


    /**
     * The Master data cache helper.
     */

    @Autowired
    private MasterDataCacheHelper masterDataCacheHelper;

    /**
     * Instantiates a new Master data service.
     *
     * @param pMasterDataRepository   the p master data repository
     * @param pMasterDataCacheService the p master data cache service
     */
//    public MasterDataServiceImpl(
//            final MasterDataRepository pMasterDataRepository,
//            final MasterDataCacheService pMasterDataCacheService,
//            final MasterDataCacheHelper pMasterDataCacheHelper) {
//        this.masterDataRepository = pMasterDataRepository;
//        this.masterDataCacheService = pMasterDataCacheService;
//        this.masterDataCacheHelper = pMasterDataCacheHelper;
//    }

    /**
     * List list.
     *
     * @return the list
     */
    @Override
    public List<MasterData> list() {
        return masterDataRepository.findAllByDeletedFalseAndActiveTrue();
    }

    /**
     * Get master data by id.
     *
     * @param id the id
     * @return the master data
     */
    @Override
    public MasterData get(final long id, final boolean usingCache) {
        if (!usingCache) {
            return masterDataRepository.findById(id).orElse(null);
        } else {
            MasterDataCache cache = masterDataCacheService.get(id);
            if (cache == null) {
                return masterDataRepository.findById(id).orElse(null);
            } else {
                return masterDataCacheHelper.convertToEntity(cache);
            }
        }
    }

    /**
     * Get master data.
     *
     * @param ids        the ids
     * @param usingCache the using cache
     * @return the master data
     */
    @Override
    public List<MasterData> get(final List<Long> ids,
                                final boolean usingCache) {
        List<MasterData> masterDataList = new ArrayList<>();
        if (!usingCache) {
            System.out.println("Get master data not using cache 1 ===> ");
            masterDataList = masterDataRepository.findAllByIdList(ids);
            masterDataList.forEach(element -> {
                System.out.println(element.getName());
            });
            return masterDataList;
        } else {
            System.out.println("Get master data caches ===> ");
            List<MasterDataCache> caches = masterDataCacheService.getByIdInList(ids);
            caches.forEach(element -> {
                System.out.println(element.getName() );
            });
            if (!ObjectUtils.isEmpty(caches)) {
                return masterDataCacheHelper.convertToListEntity(caches);
            } else {
                System.out.println("Get master data not using cache 2 ===> ");
                masterDataList = masterDataRepository.findAllByIdList(ids);
                masterDataList.forEach(element -> {
                    System.out.println(element.getName());
                });
                return masterDataList;
            }
        }
    }

    /**
     * Gets name.
     *
     * @param id         the id
     * @return the name
     */
    @Override
    public String getName(final Long id) {
        if (id == null) {
            return "";
        }
        MasterData data = get(id, true);
        return data != null ? data.getName() : "";
    }

    /**
     * Gets master data by type.
     *
     * @param type       the type
     * @param usingCache the using cache
     * @return the by type
     */
    @Override
    public List<MasterData> getByType(final String type,
                                      boolean usingCache) {
        if (!usingCache) {
            return masterDataRepository.findActiveMasterDataByType(type);
        } else {
            List<MasterDataCache> dataCacheList =
                    masterDataCacheService.getByType(type);
            if (!ObjectUtils.isEmpty(dataCacheList)) {
                return masterDataCacheHelper.convertToListEntity(dataCacheList);
            } else {
                return masterDataRepository.findActiveMasterDataByType(type);
            }
        }
    }

    /**
     * Check code exist boolean.
     *
     * @param code the code
     * @return the boolean
     */
    @Override
    public boolean checkCodeExist(final String code) {
        return masterDataRepository.existsByDeletedAndCode(false, code);
    }

    /**
     * Check code exist.
     *
     * @param code the code
     * @param type the type
     * @return the boolean
     */
    @Override
    public boolean checkCodeExist(final String code, final String type) {
        return masterDataRepository.countByCodeAndType(code, type) > 0;
    }

    /**
     * Check code exist boolean.
     *
     * @param code  the code
     * @param type  the type
     * @param idNot the id not
     * @return the boolean
     */
    @Override
    public boolean checkCodeExist(final String code,
                                  final String type,
                                  final long idNot) {
        return masterDataRepository.countByCodeAndType(code, type, idNot) > 0;
    }

    /**
     * Check id list in type.
     *
     * @param ids  the ids
     * @param type the type
     * @return the boolean
     */
    @Override
    public boolean checkIdListInType(final List<Long> ids, final String type) {
        if (ListUtils.isBlank(ids)) {
            return true;
        }
        long count =
                masterDataRepository.countActiveMasterDataInIdList(type, ids);
        return count == ids.size();
    }

    /**
     * Create master data.
     *
     * @param form      the form
     * @param createdBy the created by
     * @return the master data
     */
    @Override
    public MasterData create(final MasterDataCreateForm form,
                             final String createdBy) {
        MasterData data = super.getModelMapper().map(form, MasterData.class);
        super.fillBaseInfo(data, createdBy, false);
        MasterData result = masterDataRepository.save(data);
        if (result != null && result.getId() != null) {
            masterDataCacheService.save(result);
        }
        return result;
    }

    /**
     * Update master data.
     *
     * @param form      the form
     * @param updatedBy the updated by
     * @return the boolean
     */
    @Override
    public MasterData update(final MasterDataUpdateForm form,
                             final String updatedBy) {
        MasterData masterData =
                masterDataRepository.findById(form.getId()).orElse(null);
        if (masterData != null) {
            super.getModelMapper().map(form, masterData);
            fillBaseInfo(masterData, updatedBy, true);
            masterDataRepository.save(masterData);
            masterDataCacheService.save(masterData);
        }
        return masterData;
    }

    /**
     * Validate field boolean.
     *
     * @param fieldValue the field value
     * @param fieldType  the field type
     * @return the boolean
     */
    @Override
    public boolean validateField(final String fieldValue,
                                 final String fieldType) {
        if (StringUtils.isBlank(fieldValue)) {
            return true;
        }
        List<Long> valueList = StringUtils.convertIdStrToLong(fieldValue);
        return checkIdListInType(valueList, fieldType);
    }

    /**
     * Validate sub field.
     *
     * @param parentFieldValue the parent field value
     * @param subFileValue     the sub file value
     * @return the boolean
     */
    @Override
    public boolean validateSubField(final String parentFieldValue,
                                    final String subFileValue) {
        if (StringUtils.isBlank(parentFieldValue)) {
            return true;
        }
        Long parentValue =
                StringUtils.convertIdStrToLong(parentFieldValue).get(0);
        String code = get(parentValue, false).getCode();
        return validateField(subFileValue, code);
    }

    /**
     * Delete.
     *
     * @param id the id
     */
    @Override
    public void delete(final long id) {
        masterDataRepository.deleteById(id);
        masterDataCacheService.delete(id);
    }

    /**
     * Gets by type and code.
     *
     * @param type       the type
     * @param code       the code
     * @param usingCache the using cache
     * @return the by type and code
     */
    @Override
    public MasterData getByTypeAndCode(final String type,
                                       final String code,
                                       final boolean usingCache) {
        if (!usingCache) {
            return masterDataRepository.getOneByTypeAndCode(type, code);
        } else {
            MasterDataCache cache =
                    masterDataCacheService.getByTypeAndCode(type, code);
            if (cache == null) {
                return masterDataRepository.getOneByTypeAndCode(type, code);
            } else {
                return masterDataCacheHelper.convertToEntity(cache);
            }
        }
    }

    /**
     * Gets by type in list.
     *
     * @param types      the types
     * @param usingCache the using cache
     * @return the by type in list
     */
    @Override
    public List<MasterData> getByTypeInList(final List<String> types,
                                            final boolean usingCache) {
        List<MasterData> result;
        if (!usingCache) {
            result = masterDataRepository.findAllByTypeIn(types);
        } else {
            List<MasterDataCache> caches =
                    masterDataCacheService.getByTypeInList(types);
            if (ObjectUtils.isEmpty(caches)) {
                result = masterDataRepository.findAllByTypeIn(types);
            } else {
                result = masterDataCacheHelper.convertToListEntity(caches);
            }
        }
//        Collections.sort(result);
        return result;
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
        return masterDataRepository.search(form, pageNo, pageSize);
    }

    /**
     * Count.
     *
     * @param form the form
     * @return the long
     */
    @Override
    public long count(final MasterDataSearchForm form) {
        return masterDataRepository.count(form);
    }

    /**
     * Gets by list type id.
     *
     * @param types      the types
     * @param usingCache the using cache
     * @return the by list type id
     */
    @Override
    public List<MasterData> getByListTypeId(final List<Long> types,
                                            final boolean usingCache) {
        List<MasterData> result;
        if (!usingCache) {
            result = masterDataRepository.findAllByTypeIdIn(types);
        } else {
            List<MasterDataCache> caches =
                    masterDataCacheService.getByListTypeId(types);
            if (!ObjectUtils.isEmpty(caches)) {
                result = masterDataCacheHelper.convertToListEntity(caches);
            } else {
                result = masterDataRepository.findAllByTypeIdIn(types);
            }
        }
//        Collections.sort(result);
        return result;
    }

    /**
     * Is using boolean.
     *
     * @param id the id
     * @return the boolean
     */
    @Override
    public boolean isUsing(final Long id) {
        return masterDataRepository.existsById(id);
    }

    /**
     * Get by type and code in list list.
     *
     * @param type       the type
     * @param codes      the codes
     * @param usingCache the using cache
     * @return the list
     */
    @Override
    public List<MasterData> getByTypeAndCodeInList(final String type,
                                                   final Set<String> codes,
                                                   final boolean usingCache) {
        if (!usingCache) {
            return masterDataRepository
                    .findAllByTypeAndLowerCodeIn(type, codes);
        } else {
            List<MasterDataCache> caches = masterDataCacheService
                    .getByTypeAndCodeInList(type, codes);
            if (!ObjectUtils.isEmpty(caches)) {
                return masterDataCacheHelper.convertToListEntity(caches);
            } else {
                return masterDataRepository
                        .findAllByTypeAndLowerCodeIn(type, codes);
            }
        }

    }

    /**
     * Convert to list vo list.
     *
     * @param listMasterData the list master data
     * @return the list
     */
    public List<MasterDataVO> convertToListVO(
            final List<MasterData> listMasterData) {
        return this.getModelMapper().map(listMasterData,
                new TypeToken<List<MasterDataVO>>() { }.getType());
    }

//    @Override
//    public List<String> getTechnologyForCicd(Long contentId, String fieldKey) {
//        return masterDataRepository.getTechnologyForCicd(contentId, fieldKey);
//    }
}

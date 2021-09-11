package com.hipepham.springboot.masterdata.service;

import com.hipepham.springboot.masterdata.entity.MasterData;
import com.hipepham.springboot.masterdata.form.MasterDataCreateForm;
import com.hipepham.springboot.masterdata.form.MasterDataSearchForm;
import com.hipepham.springboot.masterdata.form.MasterDataUpdateForm;

import java.util.List;
import java.util.Set;

/**
 * The interface Master data service.
 */
public interface MasterDataService {
    /**
     * List list.
     *
     * @return the list
     */
    List<MasterData> list();

    /**
     * Get master data by id.
     *
     * @param id         the id
     * @param usingCache the using cache
     * @return the master data
     */
    MasterData get(long id, boolean usingCache);

    /**
     * Get master data.
     *
     * @param ids        the ids
     * @param usingCache the using cache
     * @return the master data
     */
    List<MasterData> get(List<Long> ids, boolean usingCache);

    /**
     * Gets name.
     *
     * @param id the id
     * @return the name
     */
    String getName(Long id);

    /**
     * Gets master data by type.
     *
     * @param type       the type
     * @param usingCache the using cache
     * @return the by type
     */
    List<MasterData> getByType(String type, boolean usingCache);

    /**
     * Check code exist boolean.
     *
     * @param code the code
     * @return the boolean
     */
    boolean checkCodeExist(String code);

    /**
     * Check code exist.
     *
     * @param code the code
     * @param type the type
     * @return the boolean
     */
    boolean checkCodeExist(String code, String type);

    /**
     * Check code exist boolean.
     *
     * @param code  the code
     * @param type  the type
     * @param idNot the id not
     * @return the boolean
     */
    boolean checkCodeExist(String code, String type, long idNot);

    /**
     * Check id list in type.
     *
     * @param ids  the ids
     * @param type the type
     * @return the boolean
     */
    boolean checkIdListInType(List<Long> ids, String type);

    /**
     * Create master data.
     *
     * @param form      the form
     * @param createdBy the created by
     * @return the master data
     */
    MasterData create(MasterDataCreateForm form, String createdBy);

    /**
     * Update master data.
     *
     * @param form      the form
     * @param updatedBy the updated by
     * @return the boolean
     */
    MasterData update(MasterDataUpdateForm form, String updatedBy);


    /**
     * Validate field.
     *
     * @param fieldValue the field value
     * @param fieldType  the field type
     * @return the boolean
     */
    boolean validateField(String fieldValue, String fieldType);

    /**
     * Validate sub field.
     *
     * @param parentFieldValue the parent field value
     * @param subFileValue     the sub file value
     * @return the boolean
     */
    boolean validateSubField(String parentFieldValue,
                             String subFileValue);


    /**
     * Delete.
     *
     * @param id the id
     */
    void delete(long id);

    /**
     * Gets by type and code.
     *
     * @param type       the type
     * @param code       the code
     * @param usingCache the using cache
     * @return the by type and code
     */
    MasterData getByTypeAndCode(String type, String code, boolean usingCache);

    /**
     * Gets by type in list.
     *
     * @param types      the types
     * @param usingCache the using cache
     * @return the by type in list
     */
    List<MasterData> getByTypeInList(List<String> types, boolean usingCache);

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
     * Count.
     *
     * @param form the form
     * @return the long
     */
    long count(MasterDataSearchForm form);

    /**
     * Gets by list type id.
     *
     * @param types      the types
     * @param usingCache the using cache
     * @return the by list type id
     */
    List<MasterData> getByListTypeId(final List<Long> types,
                                     boolean usingCache);

    /**
     * Is using boolean.
     *
     * @param id the id
     * @return the boolean
     */
    boolean isUsing(final Long id);

    /**
     * Get by type and code in list list.
     *
     * @param type       the type
     * @param codes      the codes
     * @param usingCache the using cache
     * @return the list
     */
    List<MasterData> getByTypeAndCodeInList(String type, Set<String> codes,
                                            boolean usingCache);

    /**
     *
     * @param contentId
     * @return
     */
//    List<String> getTechnologyForCicd(Long contentId,String fieldKey);
}

package com.hipepham.springboot.masterdata.repository;

import com.hipepham.springboot.masterdata.entity.MasterData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface MasterDataRepository extends JpaRepository<MasterData, Long>, MasterDataRepositoryCustom {
    /**
     * Find all by id list list.
     *
     * @param ids the ids
     * @return the list
     */
    @Query("FROM MasterData WHERE active = true AND deleted = false "
            + "AND id in ?1")
    List<MasterData> findAllByIdList(List<Long> ids);

    /**
     * Exists by delete and code boolean.
     *
     * @param isDelete the is delete
     * @param code     the code
     * @return the boolean
     */
    boolean existsByDeletedAndCode(Boolean isDelete, String code);

    /**
     * Find all by type list.
     *
     * @param type the type
     * @return the list
     */
    @Query(value = "SELECT m FROM MasterData m "
            + "WHERE m.active=true AND m.type = :type")
    List<MasterData> findActiveMasterDataByType(@Param("type") String type);

    /**
     * Count active master data in id list.
     *
     * @param type the type
     * @param ids  the ids
     * @return the long
     */
    @Query(value = "SELECT count(m.id) FROM MasterData m "
            + "WHERE m.active = true AND m.id IN :ids AND m.type = :type")
    long countActiveMasterDataInIdList(@Param("type") String type,
                                       @Param("ids") List ids);

    /**
     * Find by type and code and active is true.
     *
     * @param type the type
     * @param code the code
     * @return the master data
     */
    MasterData findByTypeAndCodeAndActiveIsTrue(String type, String code);

    /**
     * Gets one by type and code.
     *
     * @param type the type
     * @param code the code
     * @return the one by type and code
     */
    @Query("select o from MasterData o where o.active = true "
            + "and o.deleted = false "
            + "and o.lowerCode = lower(:code) and o.type = :type ")
    MasterData getOneByTypeAndCode(final @Param("type") String type,
                                   final @Param("code") String code);

    /**
     * Find by type in list.
     *
     * @param types the types
     * @return the list
     */
    @Query("select o from MasterData o where o.deleted = false "
            + "and o.active = true and o.type in (:types) order by o.name asc")
    List<MasterData> findAllByTypeIn(@Param("types") List<String> types);

    /**
     * Gets confidential level.
     *
     * @param id the id
     * @return List<String>       list
     */
    @Query("SELECT m.name FROM MasterData m WHERE  m.id = :id AND m.type = 'confidential_level' ")
    List<String> findByConfidentialLevel(final @Param("id") String id);

    /**
     * Count all by code and type and deleted false and active true.
     *
     * @param code the code
     * @param type the type
     * @return the long
     */
    long countAllByCodeAndTypeAndDeletedFalseAndActiveTrue(String code,
                                                           String type);

    /**
     * Exists by lower code and type and active true and deleted false.
     *
     * @param code the code
     * @param type the type
     * @return the boolean
     */
    boolean existsByLowerCodeAndTypeAndActiveTrueAndDeletedFalse(String code,
                                                                 String type);

    /**
     * Delete.
     *
     * @param id the id
     */
    @Query("update MasterData m set m.deleted = true where m.id = :id")
    @Modifying
    void deleteById(long id);

    /**
     * Find all by deleted false and active true list.
     *
     * @return the list
     */
    List<MasterData> findAllByDeletedFalseAndActiveTrue();

    /**
     * Find all by type id in list.
     *
     * @param types the types
     * @return the list
     */
    @Query("select m from MasterData m where m.deleted = false "
            + "and m.active = true and m.type in ("
            + "select m1.code from MasterData m1 where m1.id in (:types) "
            + "and m1.active = true and m1.deleted = false )")
    List<MasterData> findAllByTypeIdIn(@Param("types") List<Long> types);

    /**
     * Find all by type and lower code in list.
     *
     * @param type  the type
     * @param codes the codes
     * @return the list
     */
    @Query("select m from MasterData m "
            + "where m.active = true and m.deleted = false "
            + "and m.type = :type and m.lowerCode in (:codes)")
    List<MasterData> findAllByTypeAndLowerCodeIn(
            @Param("type") String type, @Param("codes") Set<String> codes);

    /**
     *
     * @param contentID
     * @param fieldKey
     * @return
     */
//    @Query("select m.name from MasterData m"
//            + " join IPMultipleValue mt on m.id = mt.value"
//            + " where m.deleted = false "
//            + " and m.active = true and mt.ipContentId =:contentId and mt.key =:fieldKey")
//    List<String> getTechnologyForCicd(@Param("contentId") Long contentId,@Param("fieldKey") String fieldKey);
}

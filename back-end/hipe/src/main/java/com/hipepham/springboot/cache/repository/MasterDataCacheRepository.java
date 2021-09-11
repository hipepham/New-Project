package com.hipepham.springboot.cache.repository;

import com.hipepham.springboot.cache.hash.MasterDataCache;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Master data cache repository.
 */
@Repository
public interface MasterDataCacheRepository
        extends CrudRepository<MasterDataCache, Long>,
        QueryByExampleExecutor<MasterDataCache> {
    /**
     * Find all by type list.
     *
     * @param type the type
     * @return the list
     */
    List<MasterDataCache> findAllByType(String type);

    /**
     * Find all by type and lower code list.
     *
     * @param type the type
     * @param code the code
     * @return the list
     */
    MasterDataCache findByTypeAndLowerCode(String type, String code);

    /**
     * Find all by type in list.
     *
     * @param type the type
     * @return the list
     */
    List<MasterDataCache> findAllByTypeIn(List<String> type);
}

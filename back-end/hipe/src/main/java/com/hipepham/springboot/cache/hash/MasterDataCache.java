package com.hipepham.springboot.cache.hash;

import com.hipepham.springboot.cache.base.CodeNameCache;
import com.hipepham.springboot.masterdata.constant.MasterDataConstant;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

/**
 * The type Master data cache.
 */
@Getter
@Setter
@RedisHash(MasterDataConstant.TABLE_NAME)
@ToString
public class MasterDataCache extends CodeNameCache {
    /**The Type.
     *
     */
    @Indexed
    private String type;

    /**
     * The Using.
     */
    private boolean using;

    /**
     * The Is active.
     */
    private boolean active = true;

    /**
     * The Is delete.
     */
    private boolean deleted = false;
}

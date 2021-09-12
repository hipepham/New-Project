package com.hipepham.springboot.masterdata.entity;

import com.hipepham.springboot.common.base.entity.BaseEntity;
import com.hipepham.springboot.masterdata.constant.MasterDataConstant;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Table(name = MasterDataConstant.TABLE_NAME)
@Entity
@ToString
public class MasterData extends BaseEntity {

    @Column(name = MasterDataConstant.COLUMN_CODE, nullable = false)
    private String code;

    @Column(name = MasterDataConstant.COLUMN_LOW_CODE, nullable = false)
    private String lowerCode;

    @Column(name = MasterDataConstant.COLUMN_NAME, nullable = false)
    private String name;

    @Column(name = MasterDataConstant.COLUMN_LOW_NAME, nullable = false)
    private String lowerName;

    @Column(name = MasterDataConstant.COLUMN_TYPE, nullable = false)
    private String type;

    @Column(name = MasterDataConstant.COLUMN_ACTIVE, nullable = false)
    private Boolean active;
}

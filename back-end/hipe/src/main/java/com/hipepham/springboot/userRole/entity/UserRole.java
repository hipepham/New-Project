package com.hipepham.springboot.userRole.entity;

import com.hipepham.springboot.common.base.entity.BaseEntity;
import com.hipepham.springboot.userRole.constants.UserRoleConstant;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = UserRoleConstant.TABLE_NAME)
public class UserRole extends BaseEntity {

    @Column(name = UserRoleConstant.COLUMN_DELETED, nullable = false)
    private Boolean deleted;

    @Column(name = UserRoleConstant.COLUMN_ACCOUNT, nullable = false)
    private String account;

    @Column(name = UserRoleConstant.COLUMN_ROLE_CODE, nullable = false)
    private String roleCode;
}

package com.hipepham.springboot.auth.entity;

import com.hipepham.springboot.auth.constants.RoleConstant;
import com.hipepham.springboot.common.base.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "T_ROLE")
public class Role extends BaseEntity {

    @Column(name = RoleConstant.COLUMN_ROLE_CODE)
    private String roleCode;

    @Column(name = RoleConstant.COLUMN_ROLE_NAME)
    private String roleName;

}

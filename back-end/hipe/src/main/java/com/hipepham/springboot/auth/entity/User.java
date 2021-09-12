package com.hipepham.springboot.auth.entity;

import com.hipepham.springboot.auth.constants.UserConstant;
import com.hipepham.springboot.common.base.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = UserConstant.TABLE_NAME)
@Getter
@Setter
@ToString
public class User extends BaseEntity {

    @Column(name = UserConstant.COLUMN_EMAIL, nullable = false)
    private String email;

    @Column(name = UserConstant.COLUMN_USERNAME, nullable = false)
    private String username;

    @Column(name = UserConstant.COLUMN_PASSWORD, nullable = false)
    private String password;

    @Column(name = UserConstant.COLUMN_FULL_NAME, nullable = false)
    private String fullName;

    @Column(name = UserConstant.COLUMN_COLUMN_SEX, nullable = false)
    private Boolean sex;

    @Column(name = UserConstant.COLUMN_DATE_OF_BIRTH, nullable = false)
    private Date dateOfBirth;

    @Column(name = UserConstant.COLUMN_LOCATION, nullable = false)
    private String location;

    @Column(name = UserConstant.COLUMN_PHONE_NUMBER, nullable = false)
    private String phoneNumber;

    @Column(name = UserConstant.COLUMN_AVATAR)
    private Long avatar;

    @Column(name = UserConstant.COLUMN_ROLE_CODE, nullable = false)
    private String roleCode;
}

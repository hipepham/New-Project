package com.hipepham.springboot.user.entity;

import com.hipepham.springboot.common.base.entity.BaseEntity;
import com.hipepham.springboot.user.constant.UserConstants;
import lombok.*;

import javax.persistence.*;

import java.util.Date;

@Entity
@Table(name = UserConstants.TABLE_NAME)
@Getter
@Setter
@ToString
public class User extends BaseEntity {

    @Column(name = UserConstants.COLUMN_DELETED, nullable = false)
    private Boolean deleted;

    @Column(name = UserConstants.COLUMN_ACCOUNT, nullable = false)
    private String account;

    @Column(name = UserConstants.COLUMN_FULL_NAME, nullable = false)
    private String fullName;

    @Column(name = UserConstants.COLUMN_DISPLAY_NAME, nullable = false)
    private String displayName;

    @Column(name = UserConstants.COLUMN_COLUMN_SEX, nullable = false)
    private Boolean sex;

    @Column(name = UserConstants.COLUMN_DATE_OF_BIRTH, nullable = false)
    private Date dateOfBirth;

    @Column(name = UserConstants.COLUMN_LOCATION, nullable = false)
    private String location;

    @Column(name = UserConstants.COLUMN_EMAIL_ADDR, nullable = false)
    private String emailAddress;

    @Column(name = UserConstants.COLUMN_LOWER_EMAIL_ADDR, nullable = false)
    private String lowerEmailAddress;

    @Column(name = UserConstants.COLUMN_PHONE_NUMBER, nullable = false)
    private String phoneNumber;

    @Column(name = UserConstants.COLUMN_AVATAR)
    private Long avatar;
}

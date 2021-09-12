package com.hipepham.springboot.auth.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateForm {
    private String createdBy;

    private Date createdTime;

    private String modifiedBy;

    private Date modifiedTime;

    private Boolean deleted;

    private Long roleID;

    private String name;

    private String username;

    private String password;

    private String email;

    private String address;

    private Date dob;
}

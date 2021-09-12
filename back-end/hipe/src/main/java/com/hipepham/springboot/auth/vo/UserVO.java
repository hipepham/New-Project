package com.hipepham.springboot.auth.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {
    private Long id;

    private Long roleID;

    private String name;

    private String username;

    private String password;

    private String email;

    private String address;

    private Date dob;

    private String roleCode;

    private String roleName;
}

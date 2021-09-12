package com.hipepham.springboot.auth.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserForm {
    private String username;
    private String password;
}

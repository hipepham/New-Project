package com.hipepham.springboot.userRole;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SystemRoleEnum {

	SUPER_ADMIN("super_admin", "Super Admin"),
	ADMIN("admin", "Admin"),
	BOV("bov", "BOV"),
	REVIEWER("reviewer", "Reviewer");
	private String code;
	private String name;
}

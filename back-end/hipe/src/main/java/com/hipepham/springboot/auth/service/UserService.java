package com.hipepham.springboot.auth.service;

import com.hipepham.springboot.auth.entity.User;

public interface UserService {
	
	/**
	 * 
	 * @param username
	 * @return
	 */
    User findByUsername(String username);
}

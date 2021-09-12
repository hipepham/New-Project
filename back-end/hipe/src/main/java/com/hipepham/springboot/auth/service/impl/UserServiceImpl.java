package com.hipepham.springboot.auth.service.impl;

import com.hipepham.springboot.auth.entity.User;
import com.hipepham.springboot.auth.repository.UserRepository;
import com.hipepham.springboot.auth.service.UserService;
import com.hipepham.springboot.common.base.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseService implements UserService {
	
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}

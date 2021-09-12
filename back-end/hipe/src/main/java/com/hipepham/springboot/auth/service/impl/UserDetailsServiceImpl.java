package com.hipepham.springboot.auth.service.impl;

import com.hipepham.springboot.auth.entity.User;
import com.hipepham.springboot.auth.repository.UserRepository;
import com.hipepham.springboot.auth.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
       User user = userRepository.findByUsername(username);
        if (ObjectUtils.isEmpty(user)) throw new UsernameNotFoundException(username);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
     
            grantedAuthorities.add(new SimpleGrantedAuthority(user.getRoleCode().toString()));
        

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
        		user.getPassword(), grantedAuthorities);
    }
}

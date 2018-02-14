package com.hbu.searchdata.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @program: searchdata
 * @description: DetailServer实现类
 * @author: Chensiming
 * @create: 2018-01-29 14:29
 **/
@Component
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    PasswordEncoder passwordEncoder;
    private Logger logger= LoggerFactory.getLogger(getClass());
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if(!s.equals("admin"))return null;
        //根据用户名查找用户信息
        logger.info("登录用户名"+s);
        String password=passwordEncoder.encode("123456");
        logger.info("加密后密码"+password);
        // FIXME: 2018/1/29
        return new User(s,password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }


}

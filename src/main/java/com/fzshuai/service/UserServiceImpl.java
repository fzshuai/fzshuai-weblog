package com.fzshuai.service;

import com.fzshuai.dao.UserRepository;
import com.fzshuai.po.User;
import com.fzshuai.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;

/**
 * @author 软件二班傅同学
 * @date 2021-01-22 21:17
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username,
                MD5Utils.code(password));
        return user;
    }
}

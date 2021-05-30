package com.fzshuai.service;

import com.fzshuai.po.User;

/**
 * @author 软件二班傅同学
 * @description TODO
 * @date 2021-02-07 22:28
 */
public interface UserService {

    User checkUser(String username, String password);
}

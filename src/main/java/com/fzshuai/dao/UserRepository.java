package com.fzshuai.dao;

import com.fzshuai.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 软件二班傅同学
 * @description TODO
 * @date 2021-02-03 23:41
 */
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsernameAndPassword(String username, String password);
}

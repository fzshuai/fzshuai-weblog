package com.fzshuai.dao;

import com.fzshuai.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author 软件二班傅同学
<<<<<<< HEAD
 * @date 2021-01-23 19:07
 */
public interface TypeRepository extends JpaRepository<Type, Long> {
=======
 * @description TODO
 * @date 2021-02-03 19:40
 */
public interface TypeRepository extends JpaRepository<Type,Long> {
>>>>>>> d30a2ee (项目第一次提交)

    Type findByName(String name);

    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);
}

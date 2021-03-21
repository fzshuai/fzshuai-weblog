package com.fzshuai.service;

import com.fzshuai.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author 软件二班傅同学
<<<<<<< HEAD
 * @date 2021-01-23 18:57
=======
 * @description TODO
 * @date 2021-02-13 18:50
>>>>>>> d30a2ee (项目第一次提交)
 */
public interface TypeService {

    Type saveType(Type type);

    Type getType(Long id);

    Type getTypeByName(String name);

<<<<<<< HEAD
    Page<Type> listType(Pageable pageable);

=======
    // 分页查询
    Page<Type> listType(Pageable pageable);

    // 获取所有的类别
>>>>>>> d30a2ee (项目第一次提交)
    List<Type> listType();

    List<Type> listTypeTop(Integer size);

    Type updateType(Long id, Type type);

    void deleteType(Long id);
}

package com.fzshuai.dao;

import com.fzshuai.po.Picture;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 软件二班傅同学
 * @description TODO
 * @date 2021-02-04 20:01
 */
public interface PictureRepository extends JpaRepository<Picture,Long> {

    @Override
    List<Picture> findAll(Sort Sort);
}
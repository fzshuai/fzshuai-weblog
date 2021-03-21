package com.fzshuai.service;

import com.fzshuai.po.Picture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author 软件二班傅同学
 * @description TODO
 * @date 2021-02-13 20:07
 */
public interface PictureService {

    List<Picture> listPicture();

    Picture savePicture(Picture picture);

    Picture getPicture(Long id);

    Page<Picture> listPicture(Pageable pageable);

    Picture updatePicture(Long id, Picture picture);

    void deletePicture(Long id);
}
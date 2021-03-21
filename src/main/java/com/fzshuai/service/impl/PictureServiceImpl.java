package com.fzshuai.service.impl;

import com.fzshuai.NotFoundException;
import com.fzshuai.dao.PictureRepository;
import com.fzshuai.po.Picture;
import com.fzshuai.service.PictureService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * @author 软件二班傅同学
 * @description TODO
 * @date 2021-02-13 20:36
 */
@Service
public class PictureServiceImpl implements PictureService {

    @Autowired
    private PictureRepository pictureRepository;

    @Transactional
    @Override
    public List<Picture> listPicture() {
        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        return pictureRepository.findAll(sort);

    }

    @Transactional
    @Override
    public Picture savePicture(Picture picture) {
        return pictureRepository.save(picture);
    }

    @Transactional
    @Override
    public Picture getPicture(Long id) {
        return pictureRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Picture> listPicture(Pageable pageable) {
        return pictureRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Picture updatePicture(Long id, Picture picture) {
        Picture P =pictureRepository.findById(id).orElse(null);
        if(P == null){
            throw new NotFoundException("不存在该照片");
        }
        BeanUtils.copyProperties(picture,P);
        return pictureRepository.save(P);
    }

    @Transactional
    @Override
    public void deletePicture(Long id) {
        pictureRepository.deleteById(id);
    }
}
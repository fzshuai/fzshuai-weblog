/**
 * Copyright (C), 2015-2020, LSTAR
 * FileName: Picture
 * Author:   OneStar
 * Date:     2020/3/19 17:18
 * Description: 照片墙实体类
 * History:
 * <author>          <time>          <version>          <desc>
 * oneStar           修改时间           版本号              描述
 */
package com.fzshuai.po;

import javax.persistence.*;

/**
 * @author 软件二班傅同学
 * @description TODO
 * @date 2021-02-03 19:05
 */
@Entity
@Table(name = "t_picture")
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String picturename;
    private String picturetime;
    private String pictureaddress;
    private String picturedescription;

    public Picture() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPicturename() {
        return picturename;
    }

    public void setPicturename(String picturename) {
        this.picturename = picturename;
    }

    public String getPicturetime() {
        return picturetime;
    }

    public void setPicturetime(String picturetime) {
        this.picturetime = picturetime;
    }

    public String getPictureaddress() {
        return pictureaddress;
    }

    public void setPictureaddress(String pictureaddress) {
        this.pictureaddress = pictureaddress;
    }

    public String getPicturedescription() {
        return picturedescription;
    }

    public void setPicturedescription(String picturedescription) {
        this.picturedescription = picturedescription;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "id=" + id +
                ", picturename='" + picturename + '\'' +
                ", picturetime='" + picturetime + '\'' +
                ", pictureaddress='" + pictureaddress + '\'' +
                ", picturedescription='" + picturedescription + '\'' +
                '}';
    }
}
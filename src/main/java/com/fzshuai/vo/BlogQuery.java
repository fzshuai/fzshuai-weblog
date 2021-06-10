package com.fzshuai.vo;

/**
 * @author 软件二班傅同学
 * @description TODO
 * @date 2021-02-05 21:01
 */
public class BlogQuery {

    private String title;
    // 类别通过id标识
    private Long typeId;
    private boolean recommend;

    public BlogQuery() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }
}

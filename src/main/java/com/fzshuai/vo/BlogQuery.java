package com.fzshuai.vo;

/**
 * @author 软件二班傅同学
<<<<<<< HEAD
 * @date 2021-01-26 22:16
=======
 * @description TODO
 * @date 2021-02-05 21:01
>>>>>>> d30a2ee (项目第一次提交)
 */
public class BlogQuery {

    private String title;
<<<<<<< HEAD
=======
    // 类别通过id标识
>>>>>>> d30a2ee (项目第一次提交)
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
<<<<<<< HEAD

    @Override
    public String toString() {
        return "BlogQuery{" +
                "title='" + title + '\'' +
                ", typeId=" + typeId +
                ", recommend=" + recommend +
                '}';
    }
=======
>>>>>>> d30a2ee (项目第一次提交)
}

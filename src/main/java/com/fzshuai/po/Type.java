package com.fzshuai.po;

<<<<<<< HEAD
import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
=======
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
>>>>>>> d30a2ee (项目第一次提交)
import java.util.ArrayList;
import java.util.List;

/**
 * @author 软件二班傅同学
<<<<<<< HEAD
 * @date 2021-01-20 14:53
=======
 * @description TODO
 * @date 2021-02-03 19:23
>>>>>>> d30a2ee (项目第一次提交)
 */
@Entity
@Table(name = "t_type")
public class Type {

    @Id
<<<<<<< HEAD
    @GeneratedValue
=======
    @GeneratedValue(strategy = GenerationType.IDENTITY)
>>>>>>> d30a2ee (项目第一次提交)
    private Long id;
    @NotBlank(message = "分类名称不能为空")
    private String name;

    @OneToMany(mappedBy = "type")
    private List<Blog> blogs = new ArrayList<>();

    public Type() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

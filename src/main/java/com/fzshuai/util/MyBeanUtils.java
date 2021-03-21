package com.fzshuai.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 软件二班傅同学
<<<<<<< HEAD
 * @date 2021-01-31 14:13
 */
public class MyBeanUtils {


    /**
     * 获取所有的属性值为空属性名数组
=======
 * @description TODO
 * @date 2021-02-05 22:36
 */
public class MyBeanUtils {

    /**
     * 获取所有的属性值为空属性名数组
     *
>>>>>>> d30a2ee (项目第一次提交)
     * @param source
     * @return
     */
    public static String[] getNullPropertyNames(Object source) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(source);
<<<<<<< HEAD
        PropertyDescriptor[] pds =  beanWrapper.getPropertyDescriptors();
=======
        PropertyDescriptor[] pds = beanWrapper.getPropertyDescriptors();
>>>>>>> d30a2ee (项目第一次提交)
        List<String> nullPropertyNames = new ArrayList<>();
        for (PropertyDescriptor pd : pds) {
            String propertyName = pd.getName();
            if (beanWrapper.getPropertyValue(propertyName) == null) {
                nullPropertyNames.add(propertyName);
            }
        }
        return nullPropertyNames.toArray(new String[nullPropertyNames.size()]);
    }
<<<<<<< HEAD

=======
>>>>>>> d30a2ee (项目第一次提交)
}

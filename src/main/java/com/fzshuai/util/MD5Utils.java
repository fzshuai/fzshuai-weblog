package com.fzshuai.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author 软件二班傅同学
<<<<<<< HEAD
 * @date 2021-01-22 22:31
 */
public class MD5Utils {

    public static String  code(String str) {

        try {
            MessageDigest md = null;
            md = MessageDigest.getInstance("MD5");
=======
 * @description TODO
 * @date 2021-02-05 21:34
 */
public class MD5Utils {

    /**
     * MD5加密类
     *
     * @param str 要加密的字符串
     * @return 加密后的字符串
     */
    public static String code(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
>>>>>>> d30a2ee (项目第一次提交)
            md.update(str.getBytes());
            byte[] byteDigest = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
<<<<<<< HEAD

=======
>>>>>>> d30a2ee (项目第一次提交)
            for (int offset = 0; offset < byteDigest.length; offset++) {
                i = byteDigest[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
<<<<<<< HEAD
            //32位密码
            return buf.toString();
            //16位密码
=======
            //32位加密
            return buf.toString();
            // 16位的加密
>>>>>>> d30a2ee (项目第一次提交)
            //return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static void main(String[] args) {
<<<<<<< HEAD

        System.out.println(code("123"));
    }

=======
        System.out.println(code("032598"));
    }
>>>>>>> d30a2ee (项目第一次提交)
}

package com.szjz.utils;

import java.util.Random;

/**
 * @author szjz
 * @date 2019/5/8 18:11
 * 生成主键工具
 */
public class KeyUtil {

    /**
     * 生成唯一的主键
     * 格式：时间+随机数
     */

    //防止多线程情况下重复 添加 synchronized
    public static synchronized String genUniqueKey() {
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;//生成6位随机数
        return System.currentTimeMillis() + number + "";
    }
}

package com.szjz.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author szjz
 * @date 2019/5/14 15:06
 * 枚举工具类
 */

public class EnumUtil {

    private static List<String> list = new ArrayList<>();

    /**
     * 通过枚举code 获取枚举类
     *
     * @param code      枚举代码
     * @param enumClass 枚举类class
     * @param <T>       泛型 继承与CodeEnum
     * @return
     */
    public static <T extends SuperEnum> T getByCode(Integer code, Class<T> enumClass) {
        for (T each : enumClass.getEnumConstants()) {
            if (each.getCode().equals(code)) {
                return each;
            }
        }
        return null;
    }

    /**
     * 通过枚举名称 获取枚举类
     *
     * @param name      枚举名称
     * @param enumClass 枚举类
     * @param <T>       泛型 继承与CodeEnum
     * @return
     */
    public static <T extends SuperEnum> T getEnum(String name, Class<T> enumClass) {
        for (T each : enumClass.getEnumConstants()) {
            list.add(each.getName());
        }

        for (T each : enumClass.getEnumConstants()) {
            if (each.getName().equals(name)) {
                return each;
            }
        }

        return null;
    }

    /**
     * 检查枚举名称是否是该枚举范围内的
     * @param name
     * @param enumClass
     * @param <T>
     * @return
     */
    public static <T extends SuperEnum> T checkEnum(String name, Class<T> enumClass) {
        T anEnum = getEnum(name, enumClass);
        String str = ",";
        if (anEnum == null) {
            for (int i = 0; i < list.size(); i++) {
                String enumName = list.get(i);
                if (i != list.size() - 1) {
                    str = enumName + str;
                } else {
                    str = enumName;
                }
            }

//            throw new BusinessException("请选择正确的枚举类型: "+ str);
        }
        return anEnum;
    }


}


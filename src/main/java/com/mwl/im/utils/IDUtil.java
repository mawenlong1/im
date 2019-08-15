package com.mwl.im.utils;

import java.util.UUID;

/**
 * @author mawenlong
 * @date 2019/02/20
 */
public class IDUtil {

    public static Integer count = 0;

    public static String randomId() {
        return UUID.randomUUID().toString().split("-")[0];
    }

    public static Integer randomInt() {
        return count++;
    }

}

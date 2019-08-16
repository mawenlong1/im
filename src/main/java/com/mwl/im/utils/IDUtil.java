package com.mwl.im.utils;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author mawenlong
 * @date 2019/02/20
 */
public class IDUtil {

    public static AtomicInteger count = new AtomicInteger(0);

    public static String randomId() {
        return UUID.randomUUID().toString().split("-")[0];
    }

    public static Integer randomInt() {
        return count.getAndIncrement();
    }

}

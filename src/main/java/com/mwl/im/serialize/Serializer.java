package com.mwl.im.serialize;

import com.mwl.im.serialize.impl.JSONSerializer;

/**
 * @author mawenlong
 * @date 2019-02-18 22:15
 */
public interface Serializer {
    Serializer DEFAULT = new JSONSerializer();

    /**
     * 序列化算法
     *
     * @return
     */
    byte getSerializerAlogrithm();

    /**
     * java对象转换为二进制数值
     *
     * @param object
     *
     * @return
     */
    byte[] serialize(Object object);

    /**
     * 二进制数组转换为java对象
     *
     * @param clazz
     * @param bytes
     * @param <T>
     *
     * @return
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);
}

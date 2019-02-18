package com.mwl.im.serialize.impl;

import com.alibaba.fastjson.JSON;
import com.mwl.im.serialize.Serializer;
import com.mwl.im.serialize.SerializerAlgorithm;

/**
 * @author mawenlong
 * @date 2019-02-18 22:16
 */
public class JSONSerializer implements Serializer {
    @Override
    public byte getSerializerAlogrithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}

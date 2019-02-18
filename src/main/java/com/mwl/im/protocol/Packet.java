package com.mwl.im.protocol;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author mawenlong
 * @date 2019-02-18 22:04
 * <p>
 * 通行过程中传递的java对象
 */
@Data
public abstract class Packet {
    /**
     * 协议版本
     */
    @JSONField(deserialize = false, serialize = false)
    private Byte version = 1;

    /**
     * 命令
     */
    @JSONField(deserialize = false, serialize = false)
    public abstract Byte getCommand();
}

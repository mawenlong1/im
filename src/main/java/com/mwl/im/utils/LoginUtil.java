package com.mwl.im.utils;

import com.mwl.im.attribute.Attributes;
import io.netty.channel.Channel;
import io.netty.util.Attribute;

/**
 * @author mawenlong
 * @date 2019/02/19
 */
public class LoginUtil {

    public static void markAsLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static Boolean hasLogin(Channel channel) {
        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);
        return loginAttr != null;
    }
}

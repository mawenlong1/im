package com.mwl.im.session;

import lombok.Data;

/**
 * @author mawenlong
 * @date 2019-02-19 22:23
 */
@Data
public class Session {
    private String userId;
    private String userName;

    public Session(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Session{" +
               "userId='" + userId + '\'' +
               ", userName='" + userName + '\'' +
               '}';
    }
}

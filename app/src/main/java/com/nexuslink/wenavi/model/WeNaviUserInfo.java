package com.nexuslink.wenavi.model;

/**
 * Created by 18064 on 2018/2/4.
 */

public class WeNaviUserInfo {

    private String nickName;

    private String userName;

    private String password;

    private String avatar;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public enum Field {
        NICKNAME,
    }
}

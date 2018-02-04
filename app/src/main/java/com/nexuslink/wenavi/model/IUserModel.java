package com.nexuslink.wenavi.model;

import cn.jpush.im.android.api.model.UserInfo;

/**
 * Created by aplrye on 17-8-27.
 */

public interface IUserModel {

    void login(String account, String pw);

    void register(String account, String pw);

    void updateNickName(String name);
}

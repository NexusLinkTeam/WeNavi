package com.nexuslink.wenavi.model;

import com.nexuslink.wenavi.callback.NetCallBack;
import com.nexuslink.wenavi.common.Constant;
import com.nexuslink.wenavi.presenter.UserPresenter;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

/**
 * Created by aplrye on 17-8-27.
 */

public class UserModel implements IUserModel {

    private static UserModel instance;
    NetCallBack netCallBack;

    @Override
    public void login(String account, String pw) {
        //第三方请求
        JMessageClient.login(account, pw, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if(i==0){
                    netCallBack.requestOk(Constant.LOGIN);
                } else {
                    netCallBack.requestFail(Constant.LOGIN,s);
                }
            }
        });
    }

    @Override
    public void register(String account, String pw) {
        JMessageClient.register(account.trim(), pw.trim(), new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if(i==0){
                    netCallBack.requestOk(Constant.REGISTER);
                } else {
                    netCallBack.requestFail(Constant.REGISTER,s);
                }
            }
        });
    }

    @Override
    public void updateNickName(String name) {
        UserInfo userInfo = JMessageClient.getMyInfo();
        userInfo.setNickname(name.trim());
        JMessageClient.updateMyInfo(UserInfo.Field.nickname, userInfo, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (i == 0) {
                    netCallBack.requestOk(Constant.UPDATE);
                } else
                    netCallBack.requestFail(Constant.UPDATE,s);
                {
                }
            }
        });

    }

    public void setTestCallback(UserPresenter userPresenter) {
        netCallBack = userPresenter;
    }

    public static UserModel getInstance() {
        if (instance == null) {
            synchronized (UserModel.class){
                if (instance == null) {
                    instance = new UserModel();
                }
            }
        }
        return instance;
    }
}

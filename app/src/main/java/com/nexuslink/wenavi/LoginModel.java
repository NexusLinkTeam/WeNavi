package com.nexuslink.wenavi;

import android.os.Handler;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

/**
 * Created by aplrye on 17-8-27.
 */

public class LoginModel implements ILoginModel {

    TestCallBack testCallBack;

    @Override
    public void queryFromRemote(String account, String pw) {
//        //模拟网络请求
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                testCallBack.verificationOk();
//            }
//        },2000);
        //第三方请求
        JMessageClient.login(account, pw, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if(i==0){
                    testCallBack.verificationOk();
                }
            }
        });
    }

    @Override
    public void setTestCallback(LoginPresenter loginPresenter) {
        testCallBack = loginPresenter;
    }
}

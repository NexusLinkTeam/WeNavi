package com.nexuslink.wenavi;

import android.os.Handler;

/**
 * Created by aplrye on 17-8-27.
 */

public class LoginModel implements ILoginModel {

    TestCallBack testCallBack;

    @Override
    public void queryFromRemote(String account, String pw) {
        //模拟网络请求
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                testCallBack.verificationOk();
            }
        },2000);
    }

    @Override
    public void setTestCallback(LoginPresenter loginPresenter) {
        testCallBack = loginPresenter;
    }
}

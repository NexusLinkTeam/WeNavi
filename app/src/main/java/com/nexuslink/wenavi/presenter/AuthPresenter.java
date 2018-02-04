package com.nexuslink.wenavi.presenter;

import android.util.Log;

import com.nexuslink.wenavi.R;
import com.nexuslink.wenavi.base.BaseApp;
import com.nexuslink.wenavi.callback.ServerResultCallback;
import com.nexuslink.wenavi.common.Constant;
import com.nexuslink.wenavi.common.Service;
import com.nexuslink.wenavi.contract.AuthContract;
import com.nexuslink.wenavi.model.IServerModel;
import com.nexuslink.wenavi.model.WeNaviUserInfo;
import com.nexuslink.wenavi.model.jmessage.JMessageServerModel;
import com.nexuslink.wenavi.ui.main.PrincipalActivity;
import com.nexuslink.wenavi.util.SPUtil;

/**
 *  登录与注册处理者
 * Created by 18064 on 2018/1/30.
 */

public class AuthPresenter implements AuthContract.Presenter, ServerResultCallback {

    private AuthContract.View view;
    private IServerModel model;

    public AuthPresenter(AuthContract.View view) {
        this.view = view;
        this.model = JMessageServerModel.getInstance();
        model.setCallback(this);
    }

    @Override
    public void login(String account, String pass) {
        view.showProgress(true);
        Log.d("LOGIN", "login:  尝试登陆");
        model.login(account, pass);
    }

    @Override
    public void signUp(String account, String pass) {
        view.showProgress(true);
        model.register(account, pass);
    }

    @Override
    public <T> void onSuccess(T result, int code) {
        switch (code) {
            case Service.SIGN_UP:
                view.showProgress(false);
                view.shortToast(R.string.register_success);
                WeNaviUserInfo weNaviUserInfo = (WeNaviUserInfo) result;
                view.onFinishRegister(weNaviUserInfo.getUserName(), weNaviUserInfo.getPassword());
                break;
            case Service.SIGN_IN:
                Log.d("LOGIN", "onSuccess: 回调成功");
                SPUtil.putAndApply(BaseApp.getBaseApplicationContext(), Constant.IS_LOGIN, true);
                view.openActivity(PrincipalActivity.class, null);
                view.showProgress(false);
                view.shortToast(R.id.login_success);
                break;
        }
    }

    @Override
    public void onFail(String exception, int code) {
        view.shortToast(exception);
//        switch (code) {
//            case Service.SIGN_UP:
//                view.showProgress(false);
//                break;
//            case Service.SIGN_IN:
//                view.showProgress(false);
//                break;
//        }
        //登录与注册fail处理相同
        view.showProgress(false);
        Log.d("LOGIN", "onFail: 回调失败");
    }
}

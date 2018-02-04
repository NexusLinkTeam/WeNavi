//package com.nexuslink.wenavi.model;
//
//import com.nexuslink.wenavi.base.BaseApp;
//import com.nexuslink.wenavi.common.Constant;
//import com.nexuslink.wenavi.contract.AuthContract;
//import com.nexuslink.wenavi.util.JmessageResultIIntercept;
//import com.nexuslink.wenavi.util.SPUtil;
//import com.nexuslink.wenavi.util.ToastFormat;
//
//import cn.jpush.im.android.api.JMessageClient;
//import cn.jpush.im.api.BasicCallback;
//
///**
// * Created by 18064 on 2018/1/30.
// */
//
//public class AuthModel implements AuthContract.Model {
//
//    private AuthContract.Presenter presenter;
//
//    public AuthModel(AuthContract.Presenter presenter) {
//        this.presenter = presenter;
//    }
//
//    @Override
//    public void auth(final String account, String pass) {
//        JMessageClient.login(account, pass, new BasicCallback() {
//            @Override
//            public void gotResult(int i, String s) {
//                if(i == 0){
//                    presenter.loginSuccess();
//                    SPUtil.putAndApply(BaseApp.getBaseApplicationContext(),Constant.IS_LOGIN, true);
//                    SPUtil.putAndApply(BaseApp.getBaseApplicationContext(), Constant.USERNAME, account);
//                } else {
//                    presenter.loginFail(JmessageResultIIntercept.intercept(s) +
//                    "(" + s + ")");
//                }
//            }
//        });
//    }
//
//    @Override
//    public void createAccount(final String account, final String pass) {
//        JMessageClient.register(account, pass, new BasicCallback() {
//            @Override
//            public void gotResult(int i, String s) {
//                if(i == 0){
//                    presenter.signUpSuccess(account,pass);
//                } else {
//                    presenter.signUpFail(s);
//                }
//            }
//        });
//    }
//}

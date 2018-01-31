package com.nexuslink.wenavi.contract;

/**
 * Created by 18064 on 2018/1/30.
 */

public interface AuthContract {
    interface Model {
        void auth(String account, String pass);

        void createAccount(String account, String pass);
    }

    interface View {
        void showProgress(boolean b);

        void showToast(int result);

        void showToast(String result);

        void openActivity(Class<?> activityClass);

        void onFinishRegister(String account, String pass);
    }

    interface Presenter {
        void login(String account, String pass);

        void loginSuccess();

        void loginFail(String result);

        void signUp(String account, String pass);

        void signUpSuccess(String account, String pass);

        void signUpFail(String result);
    }
}

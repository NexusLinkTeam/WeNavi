package com.nexuslink.wenavi.presenter;

import com.nexuslink.wenavi.R;
import com.nexuslink.wenavi.contract.AuthContract;
import com.nexuslink.wenavi.model.AuthModel;
import com.nexuslink.wenavi.ui.main.HomeActivity;
import com.nexuslink.wenavi.ui.main.PrincipalActivity;

/**
 * Created by 18064 on 2018/1/30.
 */

public class AuthPresenter implements AuthContract.Presenter {

    private AuthContract.View view;
    private AuthContract.Model model;

    public AuthPresenter(AuthContract.View view) {
        this.view = view;
        this.model = new AuthModel(this);
    }

    @Override
    public void login(String account, String pass) {
        view.showProgress(true);
        model.auth(account, pass);
    }

    @Override
    public void loginSuccess() {
        view.showProgress(false);
        view.showToast(R.id.login_success);
        view.openActivity(PrincipalActivity.class);
    }

    @Override
    public void loginFail(String result) {
        view.showProgress(false);
        view.showToast(result);
    }

    @Override
    public void signUp(String account, String pass) {
        view.showProgress(true);
        model.createAccount(account, pass);
    }

    @Override
    public void signUpSuccess(String account, String pass) {
        view.showProgress(false);
        view.showToast(R.string.register_success);
        view.onFinishRegister(account, pass);
    }

    @Override
    public void signUpFail(String result) {
        view.showProgress(false);
        view.showToast(result);
    }
}

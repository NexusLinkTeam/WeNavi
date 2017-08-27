package com.nexuslink.wenavi;

import android.widget.EditText;

import java.util.Objects;

/**
 * Created by aplrye on 17-8-27.
 */

public class LoginPresenter implements LoginContract.Presenter,TestCallBack{
    private LoginContract.View view;
    private ILoginModel model;

    public LoginPresenter(LoginContract.View view, ILoginModel model) {
        this.view = view;
        this.model = model;
        view.setPresenter(this);
        model.setTestCallback(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void login(EditText accountEditTx, EditText pwEditTx) {
        String account = accountEditTx.getText().toString();
        String pw = pwEditTx.getText().toString();
        if (Objects.equals("", account) || Objects.equals("", pw)) {
            view.showNotifyInfo("Password or account is null");
        } else {
            view.showProgress(true);
            model.queryFromRemote(account, pw);
        }
    }

    @Override
    public void verificationOk() {
        view.showHome();
        view.showNotifyInfo("Welcome");
    }
}

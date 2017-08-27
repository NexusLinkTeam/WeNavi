package com.nexuslink.wenavi;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginContract.View {

    @BindView(R.id.editTx_account)
    EditText accountEditTx;

    @BindView(R.id.editTx_password)
    EditText pwEditTx;

    @OnClick(R.id.text_sign_up)
    void onSignUpClick() {
        openActivity(RegisterActivity.class, null);
    }

    @OnClick(R.id.btn_login)
    void onLoginClick() {
        presenter.login(accountEditTx,pwEditTx);
    }

    private LoginContract.Presenter presenter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("登录");
        progressDialog.setMessage("加载中...");

        new LoginPresenter(this,new LoginModel());
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void showHome() {
        openActivity(MainActivity.class, null);
        finish();
    }

    @Override
    public void showProgress(boolean bool) {
        if (bool) {
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showNotifyInfo(String text) {
        shortToast(text);
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        this.presenter = presenter;
    }
}

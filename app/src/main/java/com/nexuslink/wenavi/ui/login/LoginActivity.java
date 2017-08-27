package com.nexuslink.wenavi.ui.login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.EditText;

import com.nexuslink.wenavi.R;
import com.nexuslink.wenavi.base.BaseActivity;
import com.nexuslink.wenavi.contract.UserContract;
import com.nexuslink.wenavi.model.UserModel;
import com.nexuslink.wenavi.presenter.UserPresenter;
import com.nexuslink.wenavi.ui.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements UserContract.View {

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

    private UserContract.Presenter presenter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initProgressDialog();
        new UserPresenter(this,UserModel.getInstance());
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("登录");
        progressDialog.setMessage("加载中...");
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
    public void setPresenter(UserContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
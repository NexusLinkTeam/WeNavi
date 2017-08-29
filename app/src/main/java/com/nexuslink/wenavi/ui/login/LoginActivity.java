package com.nexuslink.wenavi.ui.login;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.google.gson.jpush.Gson;
import com.nexuslink.wenavi.R;
import com.nexuslink.wenavi.base.BaseActivity;
import com.nexuslink.wenavi.common.Constant;
import com.nexuslink.wenavi.contract.UserContract;
import com.nexuslink.wenavi.model.UserModel;
import com.nexuslink.wenavi.model.WeNaviLocation;
import com.nexuslink.wenavi.model.WeNaviMessage;
import com.nexuslink.wenavi.presenter.UserPresenter;
import com.nexuslink.wenavi.ui.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements UserContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.editTx_account)
    EditText accountEditTx;

    @BindView(R.id.editTx_password)
    EditText pwEditTx;

    @BindView(R.id.progressBar_load)
    ProgressBar loadProgressBar;

    @BindView(R.id.scroll_login_form)
    ScrollView loginFormScroll;

    @OnClick(R.id.text_sign_up)
    void onSignUpClick() {
        openActivity(RegisterActivity.class, null);
    }

    @OnClick(R.id.btn_login)
    void onLoginClick() {
        presenter.login(accountEditTx, pwEditTx);
    }

    private UserContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
        new UserPresenter(this, UserModel.getInstance());
    }

    private void initView() {
        toolbar.setTitle(R.string.sign_in);

        // TODO: 17-8-29 待封装
        try {
            ImageView bottomBar = (ImageView) findViewById(R.id.bar_bottom);
            if (isBottomNaviBarExist(this)) {
                bottomBar.setVisibility(View.VISIBLE);
            } else {
                bottomBar.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            loadProgressBar.setVisibility(View.VISIBLE);
            loginFormScroll.setVisibility(View.GONE);
        } else {
            loadProgressBar.setVisibility(View.GONE);
            loginFormScroll.setVisibility(View.VISIBLE);
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
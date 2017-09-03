package com.nexuslink.wenavi.ui.login;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.nexuslink.wenavi.R;
import com.nexuslink.wenavi.base.BaseActivity;
import com.nexuslink.wenavi.contract.UserContract;
import com.nexuslink.wenavi.model.UserModel;
import com.nexuslink.wenavi.presenter.UserPresenter;
import com.nexuslink.wenavi.ui.main.HomeActivity;
import com.nexuslink.wenavi.ui.main.MainActivity;
import com.nexuslink.wenavi.util.ActivityCollector;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity implements UserContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.editTx_name)
    EditText nameEditTx;

    @BindView(R.id.editTx_account)
    EditText accountEditTx;

    @BindView(R.id.editTx_password)
    EditText pwEditTx;

    @BindView(R.id.scroll_register_form)
    ScrollView registerFormScroll;

    @BindView(R.id.btn_sign_up)
    AppCompatButton btnSignUp;

    @BindView(R.id.progressBar_load)
    ProgressBar loadProgressBar;

    private UserContract.Presenter presenter;

    @OnClick(R.id.text_login)
    void onClickLogin() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initView();
        toolbar.setTitle(R.string.register);
        new UserPresenter(this, UserModel.getInstance());
    }

    private void initView() {
        btnSignUp.setEnabled(false);
        nameEditTx.addTextChangedListener(new TextChange());
        accountEditTx.addTextChangedListener(new TextChange());
        pwEditTx.addTextChangedListener(new TextChange());
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

    @OnClick(R.id.btn_sign_up)
    public void onClick() {
        presenter.register(nameEditTx, accountEditTx, pwEditTx);
    }

    @Override
    public void setPresenter(UserContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showHome() {
        ActivityCollector.finishAll();
        openActivity(HomeActivity.class, null);
    }


    @Override
    public void showNotifyInfo(String text) {
        shortToast(text);
    }

    @Override
    public void showProgress(boolean bool) {
        if (bool) {
            loadProgressBar.setVisibility(View.VISIBLE);
            registerFormScroll.setVisibility(View.GONE);
        } else {
            loadProgressBar.setVisibility(View.GONE);
            registerFormScroll.setVisibility(View.VISIBLE);
        }
    }

    private class TextChange implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (TextUtils.isEmpty(nameEditTx.getText().toString().trim()) || TextUtils.isEmpty(accountEditTx.getText().toString().trim()) ||
                    TextUtils.isEmpty(pwEditTx.getText().toString().trim())) {
                btnSignUp.setEnabled(false);
            } else {
                btnSignUp.setEnabled(true);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}

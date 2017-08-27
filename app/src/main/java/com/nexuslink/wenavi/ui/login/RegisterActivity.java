package com.nexuslink.wenavi.ui.login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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

public class RegisterActivity extends BaseActivity implements UserContract.View{

    @BindView(R.id.editTx_name)
    EditText nameEditTx;

    @BindView(R.id.editTx_account)
    EditText accountEditTx;

    @BindView(R.id.editTx_password)
    EditText pwEditTx;

    @BindView(R.id.btn_sign_up)
    AppCompatButton btnSignUp;

    private UserContract.Presenter presenter;
    private ProgressDialog progressDialog;// TODO: 17-8-27 被废弃了，ProgressBar替换？？

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

        new UserPresenter(this, UserModel.getInstance());
    }

    private void initView(){
        btnSignUp.setEnabled(false);
        nameEditTx.addTextChangedListener(new TextChange());
        accountEditTx.addTextChangedListener(new TextChange());
        pwEditTx.addTextChangedListener(new TextChange());
        initProgressDialog();
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("登录");
        progressDialog.setMessage("加载中...");
    }

    @OnClick(R.id.btn_sign_up)
    public void onClick() {
        presenter.register(nameEditTx,accountEditTx,pwEditTx);
//        JMessageClient.register(accountEditTx.getText().toString().trim(), pwEditTx.getText().toString().trim(), new BasicCallback() {
//            @Override
//            public void gotResult(int i, String s) {
//                if(i==0){
//                    Log.e(RegisterActivity.this.getPackageName(),"onClick");
//                    Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
//                    JMessageClient.login(accountEditTx.getText().toString().trim(), pwEditTx.getText().toString().trim(), new BasicCallback() {
//                        @Override
//                        public void gotResult(int i, String s) {
//                            if(i==0){
//                                UserInfo userInfo = JMessageClient.getMyInfo();
//                                userInfo.setNickname(NameEditTx.getText().toString().trim());
//                                Log.e("TAG",userInfo.getNickname());
//                                JMessageClient.updateMyInfo(UserInfo.Field.nickname, userInfo, new BasicCallback() {
//                                    @Override
//                                    public void gotResult(int i, String s) {
//                                        if(i==0){
//                                            Log.e("TAG","上传数据成功");
//                                        }else {
//                                            Log.e("TAG","上传数据失败");
//                                        }
//                                    }
//                                });
//                                openActivity(MainActivity.class,null);
//                            }
//                        }
//                    });
//                }
//            }
//        });
    }

    @Override
    public void setPresenter(UserContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showHome() {
        // TODO: 17-8-27 loginActivity 是不是还没有删除？？加个controller??
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

    private class TextChange implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(TextUtils.isEmpty(nameEditTx.getText().toString().trim())||TextUtils.isEmpty(accountEditTx.getText().toString().trim())||
                   TextUtils.isEmpty(pwEditTx.getText().toString().trim()) ){
                btnSignUp.setEnabled(false);
            }else {
                btnSignUp.setEnabled(true);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}

package com.nexuslink.wenavi.ui.login;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.nexuslink.wenavi.R;
import com.nexuslink.wenavi.base.BaseActivity;
import com.nexuslink.wenavi.ui.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.editTx_name)
    EditText NameEditTx;

    @BindView(R.id.editTx_account)
    EditText accountEditTx;

    @BindView(R.id.editTx_password)
    EditText pwEditTx;
    @BindView(R.id.btn_sign_up)
    AppCompatButton btnSignUp;

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
    }

    private void initView(){
        btnSignUp.setEnabled(false);
        NameEditTx.addTextChangedListener(new TextChange());
        accountEditTx.addTextChangedListener(new TextChange());
        pwEditTx.addTextChangedListener(new TextChange());
    }
    @OnClick(R.id.btn_sign_up)
    public void onClick() {
        Log.e(this.getPackageName(),"onClick");
        JMessageClient.register(accountEditTx.getText().toString().trim(), pwEditTx.getText().toString().trim(), new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if(i==0){
                    Log.e(RegisterActivity.this.getPackageName(),"onClick");
                    Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                    JMessageClient.login(accountEditTx.getText().toString().trim(), pwEditTx.getText().toString().trim(), new BasicCallback() {
                        @Override
                        public void gotResult(int i, String s) {
                            if(i==0){
                                UserInfo userInfo = JMessageClient.getMyInfo();
                                userInfo.setNickname(NameEditTx.getText().toString().trim());
                                Log.e("TAG",userInfo.getNickname());
                                JMessageClient.updateMyInfo(UserInfo.Field.nickname, userInfo, new BasicCallback() {
                                    @Override
                                    public void gotResult(int i, String s) {
                                        if(i==0){
                                            Log.e("TAG","上传数据成功");
                                        }else {
                                            Log.e("TAG","上传数据失败");
                                        }
                                    }
                                });
                                openActivity(MainActivity.class,null);
                            }
                        }
                    });
                }
            }
        });
    }

    class TextChange implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(TextUtils.isEmpty(NameEditTx.getText().toString().trim())||TextUtils.isEmpty(accountEditTx.getText().toString().trim())||
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

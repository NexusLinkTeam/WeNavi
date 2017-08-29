package com.nexuslink.wenavi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.nexuslink.wenavi.BaseApp;
import com.nexuslink.wenavi.R;
import com.nexuslink.wenavi.base.BaseActivity;
import com.nexuslink.wenavi.common.Constant;
import com.nexuslink.wenavi.ui.login.LoginActivity;
import com.nexuslink.wenavi.ui.main.MainActivity;
import com.nexuslink.wenavi.util.SPUtil;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean isLogin = (boolean) SPUtil
                        .get(BaseApp.getBaseApplicationContext(), Constant.IS_LOGIN,false);
                Class target;
                if (isLogin) {
                    target = MainActivity.class;
                } else {
                    target = LoginActivity.class;
                }
                Intent intent = new Intent(WelcomeActivity.this,target);
                startActivity(intent);
                finish();
            }
        },1500);
    }
}

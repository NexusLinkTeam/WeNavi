package com.nexuslink.wenavi.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

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
        initView();
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
        },500);
    }

    private void initView() {
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
}

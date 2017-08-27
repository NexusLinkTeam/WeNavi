package com.nexuslink.wenavi;

import android.app.Application;

import cn.jpush.im.android.api.JMessageClient;

/**
 * Created by ASUS-NB on 2017/8/12.
 */

public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JMessageClient.init(this,true);
    }
}

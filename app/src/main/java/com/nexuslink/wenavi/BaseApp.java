package com.nexuslink.wenavi;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import cn.jpush.im.android.api.JMessageClient;

/**
 * Created by ASUS-NB on 2017/8/12.
 */

public class BaseApp extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        JMessageClient.init(this,true);
        context= this;
    }
    public  static Context getBaseApplicationContext(){
        return context;
    }
    public static DaoSession getDaosession() {
        DaoSession daoSession;
        DaoMaster daoMaster;
        DaoMaster.DevOpenHelper helper;
        SQLiteDatabase db;
        helper = new DaoMaster.DevOpenHelper(BaseApp.getBaseApplicationContext(), "Example-DB", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        return daoSession;
    }
}

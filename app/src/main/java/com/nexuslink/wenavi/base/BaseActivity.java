package com.nexuslink.wenavi.base;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * Created by alphrye on 17-8-7.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    //某些国产手机下面没有导航栏
    public boolean isBottomNaviBarExist(Context context) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        return hasNavigationBar;
    }

    public void openActivity(Class<?> tartget, Bundle bundle){
        Intent intent = new Intent(this,tartget);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void shortToast(String text){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

    public void longToast(String text){
        Toast.makeText(this,text,Toast.LENGTH_LONG).show();
    }
}

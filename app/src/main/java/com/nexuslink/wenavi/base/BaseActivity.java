package com.nexuslink.wenavi.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.nexuslink.wenavi.R;
import com.nexuslink.wenavi.util.ActivityCollector;
import com.nexuslink.wenavi.util.SPUtil;
import com.nexuslink.wenavi.util.ThemeManager;
import com.nexuslink.wenavi.util.ToastFormat;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by alphrye on 17-8-7.
 */

public class BaseActivity extends AppCompatActivity implements BaseView{

//    private int themeId;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        //透明状态栏
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        }

//        themeId = ThemeManager.getSavedThemeId(this);
//        if (themeId != 0) {
//            setTheme(themeId);
//        }
        ActivityCollector.add(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading));
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (themeId != ThemeManager.getSavedThemeId(this)) {
//            recreate();
//        }
    }

    //某些国产手机下面没有导航栏
//    public boolean isBottomNaviBarExist(Context context) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        boolean hasNavigationBar = false;
//        Resources rs = context.getResources();
//        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
//        if (id > 0) {
//            hasNavigationBar = rs.getBoolean(id);
//        }
//        return hasNavigationBar;
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.remove(this);
    }

    @Override
    public void showProgress(boolean b) {
        if (b) {
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }
    }

    @Override
    public void shortToast(int toast) {
        Toast.makeText(this,
                ToastFormat.show(getText(toast).toString()),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void shortToast(String toast) {
        Toast.makeText(this, ToastFormat.show(toast),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openActivity(Class<?> activityClass, Bundle bundle) {
        Intent intent = new Intent(this,activityClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }
}

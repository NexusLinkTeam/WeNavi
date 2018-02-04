package com.nexuslink.wenavi.base;

import android.os.Bundle;

/**
 * Created by aplrye on 17-8-27.
 */

public interface BaseView {
    /**
     * 展示progress
     * @param b 是否展示
     */
    void showProgress(boolean b);

    /**
     * 展示Toast
     * @param toast toast内容
     */
    void shortToast(String toast);

    /**
     * 展示Toast
     * @param toast toast内容
     */
    void shortToast(int toast);

    /**
     * 打开一个Activity
     * @param activityClass 要打开Activity
     * @param bundle 额外数据
     */
    void openActivity(Class<?> activityClass, Bundle bundle);

    /**
     *  finish Activity
     */
    void finishActivity();
}

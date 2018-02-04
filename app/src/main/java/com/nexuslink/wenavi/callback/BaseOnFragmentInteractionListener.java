package com.nexuslink.wenavi.callback;

import android.os.Bundle;

/**
 * OnFragmentInteractionListener 的基类
 * Created by 18064 on 2018/2/4.
 */

public interface BaseOnFragmentInteractionListener {
    /**
     * Fragment 通过依赖的 Activity打开Fragment
     * @param activityClass 目标Activity
     * @param bundle 数据
     */
    void openActivityByActivity(Class<?> activityClass, Bundle bundle);
}

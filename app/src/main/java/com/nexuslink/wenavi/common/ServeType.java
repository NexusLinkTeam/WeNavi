package com.nexuslink.wenavi.common;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.nexuslink.wenavi.common.Constant.GET_USER_INFO_TARGET;
import static com.nexuslink.wenavi.common.Constant.GET_USER_INFO_USER;
import static com.nexuslink.wenavi.common.Constant.NONE;
import static com.nexuslink.wenavi.common.Constant.SIGN_IN;
import static com.nexuslink.wenavi.common.Constant.SIGN_UP;

/**
 * 业务
 * Created by 18064 on 2018/2/5.
 */
@IntDef({NONE,SIGN_UP, SIGN_IN, GET_USER_INFO_USER,GET_USER_INFO_TARGET})
@Retention(RetentionPolicy.SOURCE)
public @interface ServeType {
}

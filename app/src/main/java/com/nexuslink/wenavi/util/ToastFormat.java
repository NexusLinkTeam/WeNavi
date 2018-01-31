package com.nexuslink.wenavi.util;

/**
 * 控制Toast弹出格式
 * Created by 18064 on 2018/1/30.
 */

public class ToastFormat {
    public static String show(String s) {
        return JmessageResultIIntercept.intercept(s) +
                "(" + s + ")";
    }
}

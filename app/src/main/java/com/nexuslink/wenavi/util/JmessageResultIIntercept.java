package com.nexuslink.wenavi.util;

/**
 *  JMessage返回结果拦截
 * Created by 18064 on 2018/1/30.
 */

public class JmessageResultIIntercept {
    public static String intercept(String result) {
        switch (result) {
            case "Invalid username.":
                return  "用户名无效.";
            case "invalid password":
                return "密码错误";
            case "user exist":
                return "用户已存在";
            case "user not exist":
                return "用户不存在";
            default:
                return "新result，修改JmessageResultIIntercept.";
        }
    }
}

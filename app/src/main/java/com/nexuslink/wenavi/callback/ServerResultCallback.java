package com.nexuslink.wenavi.callback;

import com.nexuslink.wenavi.common.ServeType;

/**
 *  服务器回调
 * Created by 18064 on 2018/2/3.
 */

public interface ServerResultCallback {
    /**
     * 成功时回调
     * @param result 回调结果
     * @param <T> 结果类型
     * @param code 业务代码
     */
    <T> void onSuccess(T result, @ServeType int code);

    /**
     *  失败时回调
     * @param exception 异常原因
     * @param code 业务代码
     */
    void onFail(String exception, int code);
}

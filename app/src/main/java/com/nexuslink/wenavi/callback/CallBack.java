package com.nexuslink.wenavi.callback;

import java.util.List;

/**
 * Created by aplrye on 17-8-31.
 */

public interface CallBack<T> {

    void onSuccess(List<T> beans);

    void onSuccess(T bean);

    void onFail(int code,String exception);
}

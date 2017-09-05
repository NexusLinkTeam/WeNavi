package com.nexuslink.wenavi.callback;

import java.util.List;

/**
 * Created by aplrye on 17-9-4.
 */

public interface SimpleCallback {
    void onSuccess(int code);

    void onFail(int code,String exception);
}

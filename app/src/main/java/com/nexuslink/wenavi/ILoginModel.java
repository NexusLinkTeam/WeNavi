package com.nexuslink.wenavi;

/**
 * Created by aplrye on 17-8-27.
 */

public interface ILoginModel {

    void queryFromRemote(String account, String pw);

    void setTestCallback(LoginPresenter loginPresenter);
}

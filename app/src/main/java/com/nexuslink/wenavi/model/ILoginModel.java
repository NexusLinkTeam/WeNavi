package com.nexuslink.wenavi.model;

import com.nexuslink.wenavi.presenter.LoginPresenter;

/**
 * Created by aplrye on 17-8-27.
 */

public interface ILoginModel {

    void queryFromRemote(String account, String pw);

    void setTestCallback(LoginPresenter loginPresenter);
}

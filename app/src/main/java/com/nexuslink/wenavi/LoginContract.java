package com.nexuslink.wenavi;

import android.widget.EditText;

/**
 * Created by aplrye on 17-8-27.
 */

public interface LoginContract {
    interface Presenter extends BasePresenter {

        void login(EditText accountEditTx, EditText pwEditTx);
    }

    interface View extends BaseView<Presenter> {
        void showHome();

        void showProgress(boolean bool);

        void showNotifyInfo(String text);
    }
}

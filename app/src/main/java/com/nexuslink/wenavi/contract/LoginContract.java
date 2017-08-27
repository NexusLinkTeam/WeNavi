package com.nexuslink.wenavi.contract;

import android.widget.EditText;

import com.nexuslink.wenavi.base.BasePresenter;
import com.nexuslink.wenavi.base.BaseView;

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

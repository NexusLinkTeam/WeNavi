package com.nexuslink.wenavi.contract;

import com.nexuslink.wenavi.base.BaseView;

/**
 * Created by 18064 on 2018/1/30.
 */

public interface AuthContract {
    interface Model {
        void auth(String account, String pass);

        void createAccount(String account, String pass);
    }

    interface View extends BaseView{

        void onFinishRegister(String account, String pass);
    }

    interface Presenter {
        /**
         * 登录
         * @param account 账户
         * @param pass 密码
         */
        void login(String account, String pass);

        /**
         * 注册
         * @param account 账户
         * @param pass 密码
         */
        void signUp(String account, String pass);
    }
}

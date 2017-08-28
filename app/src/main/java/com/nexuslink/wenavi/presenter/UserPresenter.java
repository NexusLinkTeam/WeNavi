package com.nexuslink.wenavi.presenter;

import android.util.Log;
import android.widget.EditText;

import com.nexuslink.wenavi.callback.NetCallBack;
import com.nexuslink.wenavi.common.Constant;
import com.nexuslink.wenavi.contract.UserContract;
import com.nexuslink.wenavi.model.UserModel;

import java.util.Objects;


/**
 * Created by aplrye on 17-8-27.
 */

public class UserPresenter implements UserContract.Presenter, NetCallBack {
    private UserContract.View view;
    private UserModel model;

    private String name;
    private String account;
    private String pw;

    public UserPresenter(UserContract.View view, UserModel model) {
        this.view = view;
        this.model = model;
        view.setPresenter(this);
        model.setNetCallback(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void login(EditText accountEditTx, EditText pwEditTx) {
        account = accountEditTx.getText().toString();
        pw = pwEditTx.getText().toString();
        if (Objects.equals("", account) || Objects.equals("", pw)) {
            view.showNotifyInfo("Password or account is null");
        } else {
            view.showProgress(true);
            model.login(account, pw);
        }
    }

    @Override
    public void register(EditText nameEditTx, EditText accountEditTx, EditText pwEditTx) {
        name = nameEditTx.getText().toString();
        account = accountEditTx.getText().toString();
        pw = pwEditTx.getText().toString();
        if (Objects.equals("", name) || Objects.equals("", account) || Objects.equals("", pw)) {
            //有了TextWatcher应该永远不会执行了
            view.showNotifyInfo("Password or account is null");
        } else {
            view.showProgress(true);// TODO: 17-8-27 改下title
            model.register(account, pw);
        }
    }

    @Override
    public void requestOk(int flag) {
        switch (flag) {
            case Constant.REGISTER:
                view.showNotifyInfo("register success, try login");
                view.showProgress(true);
                model.login(account, pw);
                break;
            case Constant.LOGIN:
                //直接登录的话没有updateInfo过程
                if (name != null) {
                    view.showProgress(false);
                    model.updateNickName(name);
                } else {
                    view.showProgress(false);
                    view.showNotifyInfo("Welcome");
                    view.showHome();
                }
                break;
            case Constant.UPDATE:
                view.showProgress(false);
                view.showNotifyInfo("Welcome");
                view.showHome();
                break;
        }
    }

    @Override
    public void requestFail(int flag, String s) {
        view.showProgress(false);
        Log.d("Debug", "requestFail");
        view.showNotifyInfo(s);
    }
}

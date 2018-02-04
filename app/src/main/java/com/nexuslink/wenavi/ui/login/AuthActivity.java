package com.nexuslink.wenavi.ui.login;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.nexuslink.wenavi.R;
import com.nexuslink.wenavi.base.BaseActivity;
import com.nexuslink.wenavi.contract.AuthContract;
import com.nexuslink.wenavi.presenter.AuthPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 授权页面
 * @author 18064
 */
public class AuthActivity extends BaseActivity
        implements SignInFragment.OnFragmentInteractionListener,
        SignUpFragment.OnFragmentInteractionListener,
        AuthContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    /**
     *  登录Fragment
     */
    private SignInFragment signInFragment;

    /**
     *  注册Fragment
     */
    private SignUpFragment signUpFragment;

    /**
     *  标记当前fragment
     */
    private int currentFragmentIndex = 0;

    private AuthPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);
        presenter = new AuthPresenter(this);
        initView();
    }

    @Override
    public void onBackPressed() {
        if (currentFragmentIndex == 1) {
            backToSignIn();
            return;
        }
        super.onBackPressed();
    }

    /**
     * 初始化
     */
    private void initView() {
        updateToolbar(R.string.sign_in);
        signInFragment = SignInFragment.newInstance("","");
        getSupportFragmentManager().beginTransaction()
                .add(R.id.content,signInFragment)
                .replace(R.id.content, signInFragment)
                .commit();
    }

    /**
     * 更新toolbar
     * @param title 标题
     */
    private void updateToolbar(int title) {
        toolbar.setTitle(title);
    }

    @Override
    public void login(String account, String pass) {
        presenter.login(account,pass);
    }

    @Override
    public void register(String account, String pass) {
        presenter.signUp(account, pass);
    }

    @Override
    public void openSignUp() {
        signUpFragment = SignUpFragment.newInstance("","");
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content, signUpFragment)
                .replace(R.id.content, signUpFragment)
                .commit();
        currentFragmentIndex = 1;
        updateToolbar(R.string.sign_up);
    }

    @Override
    public void backToSignIn() {
        getSupportFragmentManager()
                .beginTransaction()
                .remove(signUpFragment)
                .replace(R.id.content, signInFragment)
                .commit();
        currentFragmentIndex = 0;
        updateToolbar(R.string.sign_in);
    }

    @Override
    public void onFinishRegister(String account, String pass) {
        getSupportFragmentManager()
                .beginTransaction()
                .remove(signInFragment)
                .replace(R.id.content, SignInFragment.newInstance(account, pass))
                .commit();
        currentFragmentIndex = 0;
    }
}

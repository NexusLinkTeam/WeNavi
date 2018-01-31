package com.nexuslink.wenavi.ui.login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.nexuslink.wenavi.R;
import com.nexuslink.wenavi.base.BaseActivity;
import com.nexuslink.wenavi.contract.AuthContract;
import com.nexuslink.wenavi.presenter.AuthPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AuthActivity extends BaseActivity implements SignInFragment.OnFragmentInteractionListener, SignUpFragment.OnFragmentInteractionListener, AuthContract.View {

    private SignInFragment signInFragment;

    private SignUpFragment signUpFragment;

    private AuthPresenter presenter;
    /**
     *  标记当前fragment
     */
    private int currentFragmentIndex = 0;

    private ProgressDialog progressDialog;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);
        presenter = new AuthPresenter(this);
        initView();
    }

    private void initView() {
        updateToolbar(R.string.sign_in);
        signInFragment = SignInFragment.newInstance("","");
        getSupportFragmentManager().beginTransaction()
                .add(R.id.content,signInFragment)
                .replace(R.id.content, signInFragment)
                .commit();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.sign_in));
        progressDialog.setMessage(getString(R.string.loading));
    }

    @Override
    public void backSignIn() {
        getSupportFragmentManager()
                .beginTransaction()
                .remove(signUpFragment)
                .replace(R.id.content, signInFragment)
                .commit();
        currentFragmentIndex = 0;
        updateToolbar(R.string.sign_in);
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
    public void login(String account, String pass) {
        presenter.login(account,pass);
    }

    /**
     * 更新toolbar
     * @param title 标题
     */
    private void updateToolbar(int title) {
        toolbar.setTitle(title);
    }

    @Override
    public void onBackPressed() {
        if (currentFragmentIndex == 1) {
            backSignIn();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void showProgress(boolean b) {
        if (b) {
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showToast(int result) {
        Toast.makeText(this, getString(result), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(String result) {
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openActivity(Class<?> activityClass) {
        openActivity(activityClass, null);
        finish();
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

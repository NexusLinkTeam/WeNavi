package com.nexuslink.wenavi.ui.login;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.nexuslink.wenavi.R;
import com.nexuslink.wenavi.util.FormCheckHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录Fragment
 * @author 18064
 */
public class
SignInFragment extends Fragment {

    @BindView(R.id.editTx_account)
    EditText accountEditTx;

    @BindView(R.id.editTx_password)
    EditText pwEditTx;

    @BindView(R.id.btn_login)
    Button loginBt;

    @OnClick(R.id.text_sign_up)
    void onSignUpClick() {
        mListener.openSignUp();
    }

    @OnClick(R.id.btn_login)
    void onLoginClick() {
        mListener.login(accountEditTx.getText().toString(),
                pwEditTx.getText().toString());
    }

    private static final String ACCOUNT = "account";

    private static final String PASS = "pass";

    private String account;

    private String pass;

    private OnFragmentInteractionListener mListener;

    public static SignInFragment newInstance(String param1, String param2) {
        SignInFragment fragment = new SignInFragment();
        Bundle args = new Bundle();
        args.putString(ACCOUNT, param1);
        args.putString(PASS, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            account = getArguments().getString(ACCOUNT);
            pass = getArguments().getString(PASS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        ButterKnife.bind(this,view);
        initView();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * 初始化
     */
    private void initView() {
        loginBt.setEnabled(false);
        FormCheckHelper.check(loginBt, accountEditTx, pwEditTx);
        accountEditTx.setText(account);
        pwEditTx.setText(pass);
    }

    public interface OnFragmentInteractionListener {
        void openSignUp();

        void login(String account, String pass);
    }
}

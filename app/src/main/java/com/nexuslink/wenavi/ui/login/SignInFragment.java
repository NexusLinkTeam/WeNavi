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
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SignInFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SignInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignInFragment extends Fragment {

    //==========================================================================================
    @BindView(R.id.editTx_account)
    EditText accountEditTx;

    @BindView(R.id.editTx_password)
    EditText pwEditTx;

    @BindView(R.id.btn_login)
    Button loginBt;

    @OnClick(R.id.text_sign_up)
    void onSignUpClick() {
//        openActivity(RegisterActivity.class, null);
        mListener.openSignUp();
    }

    @OnClick(R.id.btn_login)
    void onLoginClick() {
        mListener.login(accountEditTx.getText().toString(), pwEditTx.getText().toString());
    }
    //==========================================================================================

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ACCOUNT = "account";
    private static final String PASS = "pass";

    // TODO: Rename and change types of parameters
    private String account;
    private String pass;

    private OnFragmentInteractionListener mListener;

    public SignInFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignInFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        // Inflate the layout for this fragment
        //==========================================================================================
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        ButterKnife.bind(this,view);
        initView();
        return view;
        //==========================================================================================

    }

    private void initView() {
        loginBt.setEnabled(false);
        FormCheckHelper.check(loginBt, accountEditTx, pwEditTx);
        accountEditTx.setText(account);
        pwEditTx.setText(pass);
    }

    public void onButtonPressed() {
        if (mListener != null) {
            mListener.openSignUp();
        }
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

    public interface OnFragmentInteractionListener {
        void openSignUp();

        void login(String account, String pass);
    }
    //==============================================================================================
}

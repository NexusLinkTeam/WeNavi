package com.nexuslink.wenavi;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * Created by ASUS-NB on 2017/8/27.
 */

public class AddFriendActivity extends BaseActivity {
    @BindView(R.id.et_searchUser)
    EditText etSearchUser;
    @BindView(R.id.iv_clear)
    ImageView ivClear;
    @BindView(R.id.btn_search)
    Button btnSearch;
    @BindView(R.id.img_head)
    ImageView imgHead;
    @BindView(R.id.search_name)
    TextView mSearchName;
    @BindView(R.id.search_addBtn)
    Button searchAddBtn;
    @BindView(R.id.search_result)
    LinearLayout searchResult;
    ProgressDialog dialog;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnSearch.setEnabled(false);
        etSearchUser.addTextChangedListener(new TextChange());
        dialog = new ProgressDialog(this);
    }

    @OnClick({R.id.iv_clear, R.id.btn_search, R.id.search_addBtn, R.id.search_result})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_clear:
                break;
            case R.id.btn_search:
                hideKb();
                String searchName = etSearchUser.getText().toString().trim();
                if (!TextUtils.isEmpty(searchName)) {
                    dialog.show();
                    JMessageClient.getUserInfo(searchName, new GetUserInfoCallback() {
                        @Override
                        public void gotResult(int i, String s, UserInfo userInfo) {
                            if (i == 0) {
                                dialog.dismiss();
                                searchResult.setVisibility(View.VISIBLE);
                                searchAddBtn.setVisibility(View.VISIBLE);
                                mSearchName.setText(userInfo.getUserName());
                            } else {
                                Toast.makeText(AddFriendActivity.this, "该用户不存在", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                break;
            case R.id.search_addBtn:
                break;
            case R.id.search_result:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id ==android.R.id.home){
            finish();
        }
        return true;
    }

    public class TextChange implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int end, int count) {
            if (etSearchUser.getText().length() > 0) {
                ivClear.setVisibility(View.VISIBLE);
                btnSearch.setEnabled(true);
            } else {
                ivClear.setVisibility(View.INVISIBLE);
                btnSearch.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    /**
     * 隐藏软键盘
     */
    private void hideKb() {
        InputMethodManager manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        //获取当前焦点的view
        if (manager.isActive() && getCurrentFocus() != null) {
            //焦点view所依附的window的令牌(token)
            if (getCurrentFocus().getWindowToken() != null) {
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
}

package com.nexuslink.wenavi.ui.main.profile;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nexuslink.wenavi.R;
import com.nexuslink.wenavi.common.Constant;
import com.nexuslink.wenavi.model.SettingItem;
import com.nexuslink.wenavi.ui.adapter.SettingListAdapter;
import com.nexuslink.wenavi.ui.login.AuthActivity;
import com.nexuslink.wenavi.ui.setting.ThemeChangeActivity;
import com.nexuslink.wenavi.util.ActivityCollector;
import com.nexuslink.wenavi.util.SPUtil;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 个人页面
 */
public class ProfileFragment extends Fragment {

    @BindView(R.id.setting_list)
    ListView listView;

    @BindView(R.id.nickname)
    TextView nickname;

    @BindView(R.id.account)
    TextView account;

    @BindView(R.id.avatar)
    CircleImageView avatar;

    private List<SettingItem> settingItems = Arrays.asList(
            new SettingItem(R.mipmap.ic_launcher, "个人资料"),
            new SettingItem(R.mipmap.ic_launcher, "主题切换"),
            new SettingItem(R.mipmap.ic_launcher, "退出登录"));

    private static final String ARG_PARAM1 = "param1";

    private static final String ARG_PARAM2 = "param2";

    private String mParam1;

    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
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
     *  初始化view
     */
    private void initView() {
        SettingListAdapter adapter = new SettingListAdapter(getContext(), settingItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mListener.openActivityByActivity(PersonalActivity.class,null);
                        break;
                    case 1:
                        mListener.openActivityByActivity(ThemeChangeActivity.class, null);
                        break;
                    case 2:
                        mListener.logout();
                        ActivityCollector.finishAll();
                        mListener.openActivityByActivity(AuthActivity.class, null);
                        SPUtil.putAndApply(getContext(),Constant.IS_LOGIN,false);
                        break;
                }
            }
        });
        initProfile();
    }

    /**
     *  设置个人界面数据
     */
    private void initProfile() {
        // 加载头像
        String avatarUrl = (String) SPUtil.get(getContext(), Constant.USERNAME,getString(R.string.string_default));
        Glide.with(getContext()).load(avatarUrl).error(R.drawable.default_avator).into(avatar);
        // 设置昵称
        String nikeNameText = (String) SPUtil.get(getContext(), Constant.NICKNAME,getString(R.string.nick_name_default));
        nickname.setText(nikeNameText);
        // 设置账号
        String userNameText = (String) SPUtil.get(getContext(), Constant.USERNAME,getString(R.string.string_default));
        account.setText(userNameText);
    }

    public interface OnFragmentInteractionListener {

        void openActivityByActivity(Class<?> activityClass, Bundle bundle);

        void logout();
    }
}

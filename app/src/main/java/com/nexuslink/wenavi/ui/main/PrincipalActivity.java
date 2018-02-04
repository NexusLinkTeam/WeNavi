package com.nexuslink.wenavi.ui.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.nexuslink.wenavi.R;
import com.nexuslink.wenavi.base.BaseActivity;
import com.nexuslink.wenavi.common.Constant;
import com.nexuslink.wenavi.contract.PrincipalContract;
import com.nexuslink.wenavi.model.ConversationItem;
import com.nexuslink.wenavi.presenter.PrincipalPresenter;
import com.nexuslink.wenavi.ui.main.friends.ConversationFragment;
import com.nexuslink.wenavi.ui.main.profile.ProfileFragment;
import com.nexuslink.wenavi.ui.verify.AddFriendActivity;
import com.nexuslink.wenavi.ui.verify.FriendVerifyActivity;
import com.nexuslink.wenavi.ui.verify.RefreshConversationEvent;
import com.nexuslink.wenavi.util.BitmapHelper;
import com.nexuslink.wenavi.util.SPUtil;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.ContactNotifyEvent;

public class PrincipalActivity extends BaseActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener,
        MapFragment.OnFragmentInteractionListener,
        ConversationFragment.OnFragmentInteractionListener,
        ProfileFragment.OnFragmentInteractionListener,
        PrincipalContract.View{

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    /**
     *  地图Fragment
     */
    private MapFragment mapFragment;

    /**
     *  个人Fragment
     */
    private ProfileFragment profileFragment;

    /**
     *  会话Fragment
     */
    private ConversationFragment conversationFragment;

    private static final int AVATAR_SIZE = 96;

    private PrincipalContract.Presenter presenter;

    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        JMessageClient.registerEventReceiver(this);
        ButterKnife.bind(this);
        presenter = new PrincipalPresenter(this);
        initView();
        String userName = (String) SPUtil.get(
                this,
                Constant.USERNAME,
                getString(R.string.string_default));
        presenter.saveUserProfile(userName);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        bottomNavigationView.setSelectedItemId(R.id.map);
    }

    @Override
    protected void onDestroy() {
        JMessageClient.unRegisterEventReceiver(this);
        super.onDestroy();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.map:
                switchFragment(currentFragment, mapFragment);
                currentFragment = mapFragment;
                return true;
            case R.id.friends:
                switchFragment(currentFragment, conversationFragment);
                currentFragment = conversationFragment;
                return true;
            case R.id.profile:
                switchFragment(currentFragment, profileFragment);
                currentFragment = profileFragment;
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_friend_verify:
                openActivity(FriendVerifyActivity.class, null);
                break;
            case R.id.add_friends:
                openActivity(AddFriendActivity.class, null);
                break;
        }
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void openActivityByActivity(Class<?> activityClass, Bundle bundle) {
        openActivity(activityClass, bundle);
    }

//    @Override
//    public List<Conversation> getConversationList() {
//        return presenter.loadChatList();
//    }

    @Override
    public void logout() {
        presenter.logout();
    }

    @Override
    public void showAvatar(String avatar) {

    }

    @Override
    public List<ConversationItem> getConversationItemList() {
        return presenter.getConversationItemList();
    }

    @Override
    public void replaceEmptyFragment() {
        showEmpty(R.id.content);
    }

    @Override
    public void updateBadgeNum(int num) {
        // TODO: 2018/2/2
    }

    @Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
    public void onEvent(RefreshConversationEvent event) {
        Log.d("ConversationEvent","收到消息");
//        conversationFragment.updateConversation();
    }

    /**
     *  接受事件(JMessage)
     * @param event 事件
     */
    public void onEvent(ContactNotifyEvent event) {
        String reason = event.getReason();
        String from = event.getFromUsername();

        switch (event.getType()) {
            case invite_received://收到好友邀请
                shortToast("收到好友邀请");
                presenter.receiveInvitation(from, reason);
                break;
            case invite_accepted://对方接收了你的好友邀请
                shortToast("对方接收了你的好友邀请");
                break;
            case invite_declined://对方拒绝了你的好友邀请
                shortToast("对方拒绝了你的好友邀请");
                break;
            case contact_deleted://对方将你从好友中删除
                shortToast("对方将你从好友中删除");
                break;
            default:
                break;
        }
    }

    /**
     *  初始化
     */
    private void initView() {
        toolbar.setTitle("   " + getString( R.string.app_name));
        initAvatar();
        presenter.loadMessageNum();
        setSupportActionBar(toolbar);
        mapFragment = MapFragment.newInstance("","");
        profileFragment = ProfileFragment.newInstance("","");
        conversationFragment = ConversationFragment.newInstance("","");
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content, mapFragment)
                .add(R.id.content, profileFragment)
                .add(R.id.content, conversationFragment)
                .hide(profileFragment)
                .hide(conversationFragment)
                .show(mapFragment)
                .commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        currentFragment = mapFragment;
    }

    /**
     *  设置头像
     */
    private void initAvatar() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.default_avator);
        Bitmap bitmapResize = BitmapHelper.resizeBitmap(bitmap, AVATAR_SIZE,AVATAR_SIZE);
        Bitmap roundBitmap = BitmapHelper.
                getRoundedCornerBitmap(bitmapResize,AVATAR_SIZE / 2);
        Drawable drawable = new BitmapDrawable(getResources(),roundBitmap);
        toolbar.setLogo(drawable);
    }

    /**
     * 切换Fragment
     * @param from 取代的fragment
     * @param to 替代的fragment
     */
    private void switchFragment(Fragment from, Fragment to) {
        if (currentFragment != to ) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (!to.isAdded()) {
                transaction.hide(from).add(R.id.content, to).commit();
            } else {
                transaction.hide(from).show(to).commit();
            }
        }
    }
}

package com.nexuslink.wenavi.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.MyLocationStyle;
import com.nexuslink.wenavi.base.BaseApp;
import com.nexuslink.wenavi.DaoSession;
import com.nexuslink.wenavi.FriendVerify;
import com.nexuslink.wenavi.FriendVerifyDao;
import com.nexuslink.wenavi.R;
import com.nexuslink.wenavi.base.BaseActivity;
import com.nexuslink.wenavi.callback.OnItemClickListener;
import com.nexuslink.wenavi.contract.MainContract;
import com.nexuslink.wenavi.model.MainModel;
import com.nexuslink.wenavi.model.TextMessage;
import com.nexuslink.wenavi.model.WeNaviLocation;
import com.nexuslink.wenavi.model.WeNaviMessage;
import com.nexuslink.wenavi.presenter.MainPresenter;
import com.nexuslink.wenavi.ui.SettingsActivity;
import com.nexuslink.wenavi.ui.friend.AddFriendActivity;
import com.nexuslink.wenavi.ui.friend.ChatListAdapter;
import com.nexuslink.wenavi.ui.friend.FriendListAdapter;
import com.nexuslink.wenavi.ui.friend.FriendVerifyActivity;
import com.nexuslink.wenavi.util.AnimUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.event.ContactNotifyEvent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.UserInfo;

public class HomeActivity extends BaseActivity implements MainContract.View, OnItemClickListener, AMapLocationListener, View.OnClickListener {

    @BindView(R.id.friend_verification_num)
    TextView mFriendVerificationNum;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.view_map)
    MapView mMapView;
    @BindView(R.id.sheet_bottom_friends)
    RecyclerView mFriendsRecyclerView;
    @BindView(R.id.sheet_bottom_chat)
    RecyclerView mChatRecyclerView;
    @BindView(R.id.text_position)
    TextView mPosition;
    @BindView(R.id.view_bottom_friends)
    LinearLayout mFriendsBottomView;
    @BindView(R.id.view_bottom_chat)
    LinearLayout mChatBottomView;
    @BindView(R.id.editor_bar)
    LinearLayout mEditorBar;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.edit_message)
    EditText mMessageEdTx;

    private MainContract.Presenter presenter;
    private AMap aMap;
    private AMapLocationClientOption mLocationOption;
    private AMapLocationClient mLocationClient;
    private UiSettings mUiSettings;
    private MyLocationStyle myLocationStyle;
    private String itemName;

    private BottomSheetBehavior mBottomSheetBehaviorFriends;
    private BottomSheetBehavior mBottomSheetBehaviorChat;

    private List<Conversation> mConversations;
    private FriendListAdapter mFriendListAdapter;
    private ChatListAdapter mChatListAdapter;

    private HomeActivity.BackgroundHandler backgroundHandler;

    private DaoSession daoSession = BaseApp.getDaosession();
    private FriendVerifyDao verifyDao = daoSession.getFriendVerifyDao();

    private List<TextMessage> textMessages;

    private WeNaviLocation[] locations;


    @OnClick(R.id.draw_line)
    void draw() {
        //绘制路线
    }

    @OnClick(R.id.eraser)
    void eraser() {
        //擦除路线
        // TODO: 17-9-7 ??本地擦除 擦除对方????
    }

    @OnClick(R.id.send_line)
    void sendLine(){
        //确认发送
        if (locations != null && locations.length != 0){
            presenter.sendLineMessage(locations);
        }
    }

    @OnClick(R.id.image_send)
    void sendSimpleMessage() {
        presenter.sendTextMessage(mMessageEdTx);
    }

    @OnClick(R.id.view_bottom_friends)
    void showFriends() {
        presenter.openFriendsList();
    }

    @OnClick(R.id.view_bottom_chat)
    void showChats() {
        presenter.openChatListFromSelf();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JMessageClient.registerEventReceiver(this);
        ButterKnife.bind(this);
        mMapView.onCreate(savedInstanceState);
        initView();
        initSheetCallBack();
        new MainPresenter(this, new MainModel());
    }

    private void initSheetCallBack() {
        mBottomSheetBehaviorFriends.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    showBottomFriends(true);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        mBottomSheetBehaviorChat.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    showBottomChat(true);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    private void initView() {
        setSupportActionBar(mToolbar);
        if (aMap == null) {
            aMap = mMapView.getMap();
            mUiSettings = aMap.getUiSettings();
            //mUiSettings.setZoomControlsEnabled(false);
            mUiSettings.setZoomPosition(1);
        }
        mConversations = new ArrayList<>();
        textMessages = new ArrayList<>();
        mFriendsRecyclerView = (RecyclerView) findViewById(R.id.sheet_bottom_friends);
        mChatRecyclerView = (RecyclerView) findViewById(R.id.sheet_bottom_chat);
        mBottomSheetBehaviorFriends = BottomSheetBehavior.from(mFriendsRecyclerView);
        mBottomSheetBehaviorChat = BottomSheetBehavior.from(mChatRecyclerView);
        mChatRecyclerView.setVisibility(View.GONE);
        mFriendsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mChatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFriendListAdapter = new FriendListAdapter(this, mConversations);
        mChatListAdapter = new ChatListAdapter(this, textMessages);
        mFriendsRecyclerView.setAdapter(mFriendListAdapter);
        mChatRecyclerView.setAdapter(mChatListAdapter);
        mFriendListAdapter.setOnItemClickListener(this);
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
        //myLocationStyle.radiusFillColor(R.color.colorAccent);
        //myLocationStyle.strokeColor(R.color.colorPrimary);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

        //声明mLocationOption对象
        mLocationClient = new AMapLocationClient(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mLocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mLocationClient.startLocation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
        presenter.loadFriendsList();
        List<FriendVerify> list = verifyDao.queryBuilder().listLazy();
        Log.e("TAG", "" + list.size());
        if (list.size() == 0) {
            mFriendVerificationNum.setVisibility(View.INVISIBLE);
        } else {
            mFriendVerificationNum.setVisibility(View.VISIBLE);
            mFriendVerificationNum.setText(list.size() + "");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        JMessageClient.unRegisterEventReceiver(this);
        if (null != mLocationClient) {
            mLocationClient.onDestroy();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mChatRecyclerView.getVisibility() == View.VISIBLE) {
            presenter.closeChatList();
            presenter.openFriendsList();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_friend_verify) {
            startActivity(new Intent(this, FriendVerifyActivity.class));
            return true;
        } else if (id == R.id.add_friends) {
            startActivity(new Intent(this, AddFriendActivity.class));
            return true;
        } else {
            openActivity(SettingsActivity.class, null);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 收到在线消息
     *
     * @param event
     */
    public void onEvent(MessageEvent event) {
        cn.jpush.im.android.api.model.Message message = event.getMessage();
        UserInfo userInfo = (UserInfo) message.getTargetInfo();
        String targetUserName = userInfo.getUserName();
        Conversation conv = JMessageClient.getSingleConversation(targetUserName);
        backgroundHandler.sendMessage(backgroundHandler.obtainMessage(REFRESH_CONVERSATION_LIST, conv));
        Log.e("收到消息的线程是:", Thread.currentThread().getName());
    }

    /**
     * 收到好友变更事件
     *
     * @param event
     */
    public void onEvent(ContactNotifyEvent event) {
        Log.e("TAG", "收到事件");
        switch (event.getType()) {
            case invite_received://收到好友邀请
                Log.e("TAG", "收到好友邀请");
                String userName = event.getFromUsername();
                Log.e("TAG", userName);
                final String reason = event.getReason();
                JMessageClient.getUserInfo(userName, new GetUserInfoCallback() {
                    @Override
                    public void gotResult(int i, String s, UserInfo userInfo) {
                        if (i == 0) {
                            Log.e("TAG", "成功了啊");
                            FriendVerify verify = new FriendVerify();
//                            verify.setAvatar(userInfo.getAvatarFile().getAbsolutePath());
                            verify.setHello(reason);
                            verify.setNickName(userInfo.getNickname());
                            verify.setUserName(userInfo.getUserName());
                            verifyDao.insert(verify);
                            List<FriendVerify> list = verifyDao.queryBuilder().listLazy();
                            Log.e("TAG", "" + list.size());
                            if (list.size() == 0) {
                                mFriendVerificationNum.setVisibility(View.INVISIBLE);
                            } else {
                                mFriendVerificationNum.setVisibility(View.VISIBLE);
                                mFriendVerificationNum.setText(list.size() + "");
                            }
                        }
                        Log.e("TAG", "失败了");

                    }
                });
                break;
            case invite_accepted://对方接收了你的好友邀请
                //...
                break;
            case invite_declined://对方拒绝了你的好友邀请
                //...
                break;
            case contact_deleted://对方将你从好友中删除
                //...
                break;
            default:
                break;
        }
    }

    private static final int REFRESH_CONVERSATION_LIST = 0x3000;

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void chatListVisible(Boolean b) {
        if (b) {
            mChatRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mChatRecyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void friendListVisible(Boolean b) {
        if (b) {
            mFriendsRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mFriendsRecyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showEditorBar(Boolean b) {
        if (b) {
            // TODO: 17-9-1 动画
            mEditorBar.setVisibility(View.VISIBLE);
        } else {
            ///// TODO: 17-9-1
            mEditorBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showBottomFriends(boolean b) {
        if (b) {
            mFriendsBottomView.setVisibility(View.VISIBLE);
        } else {
            mFriendsBottomView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showBottomChat(boolean b) {
        if (b) {
            mChatBottomView.startAnimation(AnimUtil.UPACTION);
            mChatBottomView.setVisibility(View.VISIBLE);
        } else {
            mChatBottomView.startAnimation(AnimUtil.DOWNACTION);
            mChatBottomView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showFriendsSheet() {
        mFriendsRecyclerView.setVisibility(View.VISIBLE);
        mBottomSheetBehaviorFriends.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @Override
    public void showFriendsList(List<Conversation> conversations) {
        mFriendListAdapter.addConversations(conversations);
    }

    @Override
    public void showChatSheet() {
        mChatRecyclerView.setVisibility(View.VISIBLE);
        mBottomSheetBehaviorChat.setState(BottomSheetBehavior.STATE_EXPANDED);
    }


    @Override
    public void showMap() {

    }

    @Override
    public void showMyPosition() {

    }

    @Override
    public void showDrawLine() {

    }

    @Override
    public void hideFriendsSheet() {
        mBottomSheetBehaviorFriends.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    @Override
    public void cleanInput() {
        mMessageEdTx.setText("");
    }

    @Override
    public void showInfo(String exception) {
        Toast.makeText(this, exception, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void insertNewMessage(TextMessage textMessage) {
        mChatListAdapter.insertDataToHead(textMessage);
    }

    @Override
    public void onItemClick(int pos) {
        itemName = mFriendListAdapter.getmDatas().get(pos - 1).getTitle();
        //在点击时获得当前点击name,询问连接,连接成功后回调打开ChatList,返回时,取消与对方链接
        presenter.sendSureMessage(itemName);
        // TODO: 17-9-7 打开一个Progress,等待对方回应
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                Double Latitude = aMapLocation.getLatitude();//获取纬度
                Double longitude = aMapLocation.getLongitude();//获取经度
                aMapLocation.getAccuracy();//获取精度信息
                //在此获得当前地址并且显示出来
                String aoiName = aMapLocation.getAoiName();
                mPosition.setText(aoiName);
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }

    @Override
    public void onClick(View view) {

    }


    public class BackgroundHandler extends Handler {
        public BackgroundHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REFRESH_CONVERSATION_LIST:
                    Conversation conv = (Conversation) msg.obj;
                    mFriendListAdapter.set2Top(conv);
                    Log.e("background处理的线程是:", Thread.currentThread().getName());
            }
        }
    }
}

package com.nexuslink.wenavi;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.MyLocationStyle;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, AMapLocationListener {


    private static final String TAG = "MainActivity";
    private AMap aMap;
    private AMapLocationClientOption mLocationOption;
    private AMapLocationClient mLocationClient;
    private UiSettings mUiSettings;//定义一个UiSettings对象
    private BottomSheetBehavior mBottomSheetBehaviorFriends;
    private BottomSheetBehavior mBottomSheetBehaviorChat;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.view_map)
    MapView mMapView;
    @BindView(R.id.sheet_bottom_friends)
    RecyclerView mFriendsRecyclerView;
    @BindView(R.id.sheet_bottom_chat)
    RecyclerView mChatRecyclerView;
    @BindView(R.id.text_position)
    TextView position;
    private boolean isChatting = false;//是否正在聊天界面

    //测试数据
    private String[] names = {
            "Jack", "Tom", "Carina", "Charlotte", "Christina",
            "Juliana", "Maureen", "Milly", "Oprah", "Paula",
            "Rita", "Sandy", "Yolanda", "Ken", "Kent"
    };

    private int avatars[] = {
            R.drawable.t1,
            R.drawable.t2,
            R.drawable.t3,
            R.drawable.t4,
            R.drawable.t5,
            R.drawable.t6,
            R.drawable.t7,
            R.drawable.t8,
            R.drawable.t9,
            R.drawable.t10,
            R.drawable.t11,
            R.drawable.t12,
            R.drawable.t13,
            R.drawable.t14,
            R.drawable.t15
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        //bottomBar控制
        try {
            ImageView bottomBar = (ImageView) findViewById(R.id.bar_bottom);
            Log.d(TAG, "onCreate: " + isBottomNaviBarExist(this));
            if (isBottomNaviBarExist(this)) {
                bottomBar.setVisibility(View.VISIBLE);
            } else {
                bottomBar.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        //Amap对象获取
        if (aMap == null) {
            aMap = mMapView.getMap();
            mUiSettings = aMap.getUiSettings();
            //mUiSettings.setZoomControlsEnabled(false);
            mUiSettings.setZoomPosition(1);
        }
        //定位
        initBottomSheet();

        //请求完数据后回调部分
        FriendListAdapter friendListAdapter = new FriendListAdapter(this, avatars, names);
        mFriendsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFriendsRecyclerView.setAdapter(friendListAdapter);
        friendListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick() {
                //通过设置可见度切换
                mFriendsRecyclerView.setVisibility(View.GONE);
                mChatRecyclerView.setVisibility(View.VISIBLE);

                ChatListAdapter chatListAdapter = new ChatListAdapter(MainActivity.this);
                mChatRecyclerView.setAdapter(chatListAdapter);
                mChatRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                isChatting = true;
            }
        });

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomView.setVisibility(View.VISIBLE);
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initBottomSheet() {
        mFriendsRecyclerView = (RecyclerView) findViewById(R.id.sheet_bottom_friends);
        mBottomSheetBehaviorFriends = BottomSheetBehavior.from(mFriendsRecyclerView);
        mBottomSheetBehaviorFriends.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_EXPANDED:
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        mBottomSheetBehaviorChat.setState(mBottomSheetBehaviorFriends.getState());
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        mChatRecyclerView = (RecyclerView) findViewById(R.id.sheet_bottom_chat);
        mBottomSheetBehaviorChat = BottomSheetBehavior.from(mChatRecyclerView);
        mBottomSheetBehaviorChat.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_EXPANDED:
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        mBottomSheetBehaviorFriends.setState(mBottomSheetBehaviorChat.getState());
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        //开始定位
        startLocation();
    }

    private void showLocation() {
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
        //myLocationStyle.radiusFillColor(R.color.colorAccent);
        //myLocationStyle.strokeColor(R.color.colorPrimary);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

    }

    @Override
    public void onBackPressed() {

        if (isChatting) {
            mFriendsRecyclerView.setVisibility(View.VISIBLE);
            mChatRecyclerView.setVisibility(View.GONE);
            isChatting = false;
            return;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
        if (null != mLocationClient) {
            mLocationClient.onDestroy();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    public void startLocation() {
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
        showLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                Double Latitude = amapLocation.getLatitude();//获取纬度
                Double longitude = amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                //在此获得当前地址并且显示出来
                String aoiName = amapLocation.getAoiName();
                position.setText(aoiName);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

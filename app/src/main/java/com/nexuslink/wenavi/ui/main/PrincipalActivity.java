package com.nexuslink.wenavi.ui.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.nexuslink.wenavi.R;
import com.nexuslink.wenavi.base.BaseActivity;
import com.nexuslink.wenavi.util.BitmapHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PrincipalActivity extends BaseActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener,
        MapFragment.OnFragmentInteractionListener,
        FriendsFragment.OnFragmentInteractionListener,
        ProfileFragment.OnFragmentInteractionListener {

    private static final int AVATAR_SIZE = 96
            ;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    private MapFragment mapFragment;

    private ProfileFragment profileFragment;

    private FriendsFragment friendsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        toolbar.setTitle("   " + getString( R.string.app_name));
        initAvatar();
        setSupportActionBar(toolbar);
        mapFragment = MapFragment.newInstance("","");
        profileFragment = ProfileFragment.newInstance("","");
        friendsFragment = FriendsFragment.newInstance("","");
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content, mapFragment)
                .add(R.id.content, profileFragment)
                .add(R.id.content, friendsFragment)
                .replace(R.id.content, mapFragment)
                .commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.map:
                replace(mapFragment);
                return true;
            case R.id.friends:
                replace(friendsFragment);
                return true;
            case R.id.profile:
                replace(profileFragment);
                return true;
            default:
                return false;
        }
    }

    private void replace(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

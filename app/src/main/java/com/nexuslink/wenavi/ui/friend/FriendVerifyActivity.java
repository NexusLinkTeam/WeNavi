package com.nexuslink.wenavi.ui.friend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.nexuslink.wenavi.BaseApp;
import com.nexuslink.wenavi.DaoSession;
import com.nexuslink.wenavi.FriendVerify;
import com.nexuslink.wenavi.FriendVerifyDao;
import com.nexuslink.wenavi.R;
import com.nexuslink.wenavi.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

/**
 * Created by ASUS-NB on 2017/8/28.
 */

public class FriendVerifyActivity extends BaseActivity implements FriendVerifyAdapter.OnItemClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    DaoSession daoSession;
    FriendVerifyDao verifyDao;
    List<FriendVerify> verifies = new ArrayList<>();
    List<String> uIds = new ArrayList<>();
    private int verifyNum = 0;
    FriendVerifyAdapter mAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_verify);
        initView();
        initData();
        requestData();
    }
    private void initView(){
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAdapter = new FriendVerifyAdapter(this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    private void initData(){
        daoSession = BaseApp.getDaosession();
        verifyDao = daoSession.getFriendVerifyDao();
        verifies = verifyDao.queryBuilder().listLazy();
        verifyNum = verifies.size();
        Log.e("TAG",verifyNum+"");
        for(int i=0;i<verifyNum;i++){
            uIds.add(verifies.get(i).getUserName());
            Log.e("TAG",uIds.get(i)+"");
        }
    }

    /**\
     * 根据id请求用户的数据
     * @param
     * @return
     */
    private void requestData(){
        for(int i=0;i<verifyNum;i++){
            Log.e("TAG",uIds.get(i)+"");
            JMessageClient.getUserInfo(uIds.get(i), new GetUserInfoCallback() {
                @Override
                public void gotResult(int respondCode, String s, UserInfo userInfo) {
                    if(respondCode==0){
                        Log.e("TAG",s);
                        FriendVerify verify = verifyDao.queryBuilder().where(FriendVerifyDao.Properties.UserName.eq(userInfo.getUserName())).unique();
                        Log.e("TAG","gotResult");
                        Log.e("TAG",userInfo.getUserName());
                        verify.setNickName(userInfo.getUserName());
                        Log.e("TAG","gotResult");
                        mAdapter.addData(verify);
                        Log.e("TAG","gotResult");
                        verifyDao.update(verify);
                        Log.e("TAG","gotResult");
                    }
                }
            });
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemAgree(final String uId) {
        ContactManager.acceptInvitation(uId, BaseApp.APPKEY, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if(i==0){
                    Toast.makeText(FriendVerifyActivity.this,"添加好友成功",Toast.LENGTH_SHORT).show();
                    Log.e("TAG",uId);
                    List<FriendVerify> verify = verifyDao.queryBuilder().where(FriendVerifyDao.Properties.UserName.eq(uId)).list();
                    Log.e("TAG","恩恩"+verify.toString());

                    finish();
                }
            }
        });
    }

    @Override
    public void onItemRefuse(final String uId) {
        ContactManager.declineInvitation(uId, BaseApp.APPKEY, "", new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if(i==0){
                    Toast.makeText(FriendVerifyActivity.this,"已拒绝用户"+uId+"的好友请求",Toast.LENGTH_SHORT).show();
                    Log.e("TAG",uId);
                    FriendVerify verify = verifyDao.queryBuilder().where(FriendVerifyDao.Properties.UserName.eq(uId)).unique();
                    Log.e("TAG","恩恩"+verify.toString());
                    verifyDao.deleteByKey(verify.getId());
                    finish();
                }
            }
        });
    }
}

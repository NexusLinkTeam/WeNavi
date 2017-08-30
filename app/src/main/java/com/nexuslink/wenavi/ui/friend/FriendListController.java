package com.nexuslink.wenavi.ui.friend;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

import com.nexuslink.wenavi.callback.OnItemClickListener;
import com.nexuslink.wenavi.util.SortConvList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;

/**
 * Created by ASUS-NB on 2017/8/29.
 */

public class FriendListController  {
    private Context mContext;
    private FriendListAdapter mAdapter;
    private List<Conversation> mDatas = new ArrayList<Conversation>();
    private RecyclerView recyclerView;
    private RecyclerView chatRecyclerView;
    public FriendListController(Context context, RecyclerView recyclerView,RecyclerView chatRecyclerView){
         mContext = context;
        this.recyclerView = recyclerView;
        this.chatRecyclerView = chatRecyclerView;
        initFriendListAdapter();

    }
    private void initFriendListAdapter(){
        mDatas = JMessageClient.getConversationList();
        if(mDatas!=null&&mDatas.size()>0){
            SortConvList sortConvList = new SortConvList();
            Collections.sort(mDatas,sortConvList);
        }else {
            //如果没好友怎么办
        }
        mAdapter = new FriendListAdapter(mContext,mDatas);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick() {
                recyclerView.setVisibility(View.GONE);
                chatRecyclerView.setVisibility(View.VISIBLE);
            }
        });
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    }



}

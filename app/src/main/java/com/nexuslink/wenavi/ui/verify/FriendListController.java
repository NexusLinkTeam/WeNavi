//package com.nexuslink.wenavi.ui.verify;
//
//import android.app.Activity;
//import android.os.Handler;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//
//import com.nexuslink.wenavi.callback.OnItemClickListener;
//import com.nexuslink.wenavi.ui.adapter.ConversationListAdapter;
//import com.nexuslink.wenavi.util.SortConvList;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//import cn.jpush.im.android.api.JMessageClient;
//import cn.jpush.im.android.api.model.Conversation;
//
///**
// * Created by ASUS-NB on 2017/8/29.
// */
//
//public class FriendListController  implements OnItemClickListener {
//    private Activity mContext;
//    private ConversationListAdapter mAdapter;
//    private List<Conversation> mDatas = new ArrayList<Conversation>();
//    private RecyclerView recyclerView;
//    private RecyclerView chatRecyclerView;
//    public FriendListController(Activity context, RecyclerView recyclerView, RecyclerView chatRecyclerView){
//         mContext = context;
//        this.recyclerView = recyclerView;
//        this.chatRecyclerView = chatRecyclerView;
//        initFriendListAdapter();
//    }
//    public ConversationListAdapter getAdapter(){
//        return mAdapter;
//    }
//    private void initFriendListAdapter(){
//        mDatas = JMessageClient.getConversationList();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mContext.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        mDatas = JMessageClient.getConversationList();
//                        if(mDatas!=null&&mDatas.size()>0){
//                            SortConvList sortConvList = new SortConvList();
//                            Collections.sort(mDatas,sortConvList);
//                        }else {
//                            //如果没好友怎么办
//                        }
//                        Log.e("initFriendListAdapter",mDatas.size()+"");
//                        mAdapter = new ConversationListAdapter(mContext,mDatas);
//                        mAdapter.setOnItemClickListener(FriendListController.this);
//                        recyclerView.setAdapter(mAdapter);
//                        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//                        Log.e("initFriendListAdapter",mDatas.size()+"");
//                    }
//                });
//            }
//        },3000);
//    }
//
//    @Override
//    public void onItemClick(int pos) {
//        /*if(pos>0){
//            recyclerView.setVisibility(View.GONE);
//            chatRecyclerView.startAnimation(AnimUtil.UPACTION);
//            chatRecyclerView.setVisibility(View.VISIBLE);
//            Conversation conv = mDatas.get(pos - 1);
//            String tatgetId = ((UserInfo)conv.getTargetInfo()).getNickName();
//            ChatListAdapter adapter = new ChatListAdapter(mContext,tatgetId);
//            chatRecyclerView.setAdapter(adapter);
//        }*/
//    }
//}

package com.nexuslink.wenavi.ui.main.friends;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.nexuslink.wenavi.R;
import com.nexuslink.wenavi.base.BaseActivity;
import com.nexuslink.wenavi.common.Constant;
import com.nexuslink.wenavi.contract.ChatContract;
import com.nexuslink.wenavi.model.ChatItem;
import com.nexuslink.wenavi.presenter.ChatPresenter;
import com.nexuslink.wenavi.ui.adapter.ChatListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;

public class ChatActivity extends BaseActivity implements ChatContract.View{

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.list_chat)
    RecyclerView recyclerView;

    private String targetUsername;

    private String targetNickname;

    private String targetAvatar;

    private Conversation currentConversation;

    private ChatContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        presenter = new ChatPresenter(this);
        initView();
    }

    private void initView() {
        //获取昵称，账号，头像
        Intent intent = getIntent();
        targetNickname = intent.getStringExtra(Constant.NICKNAME);
        targetUsername = intent.getStringExtra(Constant.USERNAME);
        targetAvatar = intent.getStringExtra(Constant.AVATAR);
//        currentConversation = (Conversation) intent.getSerializableExtra(Constant.CONVERSATION);

        toolbar.setTitle(targetNickname);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        List<ChatItem> chatItems = presenter.loadChatList(targetUsername);
        Log.d("ChatList", "initView: chatItem大小：" + chatItems.size());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ChatListAdapter chatListAdapter = new ChatListAdapter(this, chatItems);
        recyclerView.setAdapter(chatListAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() ==  android.R.id.home){
            finish();
        }
        return true;
    }
}

package com.nexuslink.wenavi.model;

import com.nexuslink.wenavi.contract.ChatContract;
import com.nexuslink.wenavi.presenter.ChatPresenter;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;

/**
 * Created by 18064 on 2018/2/2.
 */

public class ChatModel implements ChatContract.Model {

    private ChatContract.Presenter presenter;

    public ChatModel(ChatContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public Conversation loadHistory(String username) {
         return JMessageClient.getSingleConversation(username);
    }

    @Override
    public void sendMessage() {

    }
}

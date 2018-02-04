package com.nexuslink.wenavi.presenter;

import com.nexuslink.wenavi.callback.ServerResultCallback;
import com.nexuslink.wenavi.contract.ChatContract;
import com.nexuslink.wenavi.model.ChatItem;
import com.nexuslink.wenavi.model.jmessage.JMessageServerModel;

import java.util.List;

import cn.jpush.im.android.api.model.Conversation;

/**
 * Created by 18064 on 2018/2/2.
 */

public class ChatPresenter implements ChatContract.Presenter, ServerResultCallback {

    private ChatContract.View view;

    private JMessageServerModel model;

    public ChatPresenter(ChatContract.View view) {
        this.view = view;
        model = JMessageServerModel.getInstance();
        model.setCallback(this);
    }

    @Override
    public List<ChatItem> loadChatList(String targetUsername) {
        return model.getChatList(targetUsername);
    }

    @Override
    public <T> void onSuccess(T result, int code) {

    }

    @Override
    public void onFail(String exception, int code) {
        view.shortToast(exception);
    }
}

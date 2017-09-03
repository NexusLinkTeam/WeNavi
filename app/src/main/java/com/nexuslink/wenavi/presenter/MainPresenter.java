package com.nexuslink.wenavi.presenter;

import com.nexuslink.wenavi.callback.CallBack;
import com.nexuslink.wenavi.contract.MainContract;
import com.nexuslink.wenavi.model.MainModel;

import java.util.List;

import cn.jpush.im.android.api.model.Conversation;

/**
 * Created by aplrye on 17-8-31.
 */

public class MainPresenter implements MainContract.Presenter,CallBack<Conversation> {
    private MainContract.View  view;
    private MainModel model;

    public MainPresenter(MainContract.View view, MainModel model) {
        this.view = view;
        this.model = model;
        view.setPresenter(this);
        model.setConversationCallback(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void loadFriendsList() {
        model.loadConversation();
    }

    @Override
    public void openFriendsList() {
        view.showBottomFriends(false);
        view.showFriendsSheet();
    }

    @Override
    public void closeFriendList() {
        view.hideFriendsSheet();
    }

    @Override
    public void openChatListFromFirst() {
        view.showChatSheet();
        view.showEditorBar(true);
        view.friendListVisible(false);
    }

    @Override
    public void openChatListFromSelf() {
        view.showChatSheet();
        view.showBottomChat(false);
    }

    @Override
    public void closeChatList() {
        view.chatListVisible(false);
        view.showEditorBar(false);
        view.showBottomChat(false);
    }

    @Override
    public void onSuccess(List<Conversation> beans) {
        view.showFriendsList(beans);
    }

    @Override
    public void onSuccess(Conversation bean) {

    }

    @Override
    public void onFail(int code,String exception) {

    }
}

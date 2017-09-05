package com.nexuslink.wenavi.contract;

import android.widget.EditText;

import com.nexuslink.wenavi.base.BasePresenter;
import com.nexuslink.wenavi.base.BaseView;
import com.nexuslink.wenavi.model.TextMessage;

import java.util.List;

import cn.jpush.im.android.api.model.Conversation;

/**
 * Created by aplrye on 17-8-31.
 */

public interface MainContract {
    interface Presenter extends BasePresenter {

        void loadFriendsList();

        void openFriendsList();

        void closeFriendList();

        void openChatListFromFirst();

        void openChatListFromSelf();

        void closeChatList();

        void sendTextMessage(String targetName,EditText mMessageEdTx);
    }

    interface View extends BaseView<Presenter> {

        void chatListVisible(Boolean b);

        void friendListVisible(Boolean b);

        void showEditorBar(Boolean b);

        void showBottomFriends(boolean b);

        void showBottomChat(boolean b);

        void showFriendsSheet();

        void showFriendsList(List<Conversation> userInfos);

        void showChatSheet();

        void showMap();

        void showMyPosition();

        void showDrawLine();

        void hideFriendsSheet();

        void cleanInput();

        void showInfo(String exception);

        void insertNewMessage(TextMessage textMessage);
    }
}

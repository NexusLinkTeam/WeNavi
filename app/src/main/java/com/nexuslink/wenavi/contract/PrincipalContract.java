package com.nexuslink.wenavi.contract;

import com.nexuslink.wenavi.base.BaseView;
import com.nexuslink.wenavi.model.ConversationItem;

import java.util.List;

import cn.jpush.im.android.api.event.ContactNotifyEvent;
import cn.jpush.im.android.api.model.Conversation;

/**
 * Created by 18064 on 2018/1/31.
 */

public interface PrincipalContract {
    interface Model {
        void getUserInfo(String username);

        void logout();

        void save(ContactNotifyEvent event);

        void loadFriendsList();

        int loadMessageNum();

        List<Conversation> loadConversationList();
    }

    interface View extends BaseView{
        void showAvatar(String avatar);

        /**
         * 更新badge数目
         * @param num 等待更新的数目
         */
        void updateBadgeNum(int num);
    }

    interface Presenter {

        void saveUserProfile(String username);

        void logout();

        void receiveInvitation(String from, String reason);

        void loadMessageNum();

//        List<Conversation> loadChatList();

        List<ConversationItem> getConversationItemList();
    }
}

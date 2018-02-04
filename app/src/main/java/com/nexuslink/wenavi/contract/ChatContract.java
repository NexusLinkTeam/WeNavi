package com.nexuslink.wenavi.contract;

import com.nexuslink.wenavi.base.BaseView;
import com.nexuslink.wenavi.model.ChatItem;

import java.util.List;

import cn.jpush.im.android.api.model.Conversation;

/**
 *
 * Created by 18064 on 2018/2/2.
 */

public interface ChatContract {
    interface Model {
        Conversation loadHistory(String name);
        void sendMessage();
    }

    interface View extends BaseView{
    }

    interface Presenter {
        List<ChatItem> loadChatList(String targetUsername);
    }
}

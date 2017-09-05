package com.nexuslink.wenavi.model;

import com.nexuslink.wenavi.callback.CallBack;
import com.nexuslink.wenavi.presenter.MainPresenter;

import java.util.List;

import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;

/**
 * Created by aplrye on 17-8-31.
 */

public interface IMainModel {
    void loadConversation();

    void sendMessageToTarget(String targetName, String text, int code);

    List<TextMessage>  getHistoryWithTarget(Conversation conversation);
}

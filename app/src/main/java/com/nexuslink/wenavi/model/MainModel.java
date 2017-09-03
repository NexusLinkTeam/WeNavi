package com.nexuslink.wenavi.model;

import com.nexuslink.wenavi.callback.CallBack;
import com.nexuslink.wenavi.model.IMainModel;
import com.nexuslink.wenavi.util.SortConvList;

import java.util.Collections;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;

/**
 * Created by aplrye on 17-8-31.
 */

public class MainModel implements IMainModel {
    private static final int WITHOUT_FRIENDS = 0;
    private CallBack<Conversation> mConversationCallBack;

    @Override
    public void loadConversation() {
        List<Conversation> mDatas = JMessageClient.getConversationList();
        if (mDatas != null && mDatas.size() > 0) {
            SortConvList sortConvList = new SortConvList();
            Collections.sort(mDatas, sortConvList);
            mConversationCallBack.onSuccess(mDatas);
        } else {
            //如果没好友怎么办
            mConversationCallBack.onFail(WITHOUT_FRIENDS, "No Friends");
        }
    }

    public void setConversationCallback(CallBack<Conversation> callBack) {
        mConversationCallBack = callBack;
    }
}

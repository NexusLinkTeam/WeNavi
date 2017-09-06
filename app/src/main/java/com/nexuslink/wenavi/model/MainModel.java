package com.nexuslink.wenavi.model;

import android.util.Log;

import com.nexuslink.wenavi.callback.CallBack;
import com.nexuslink.wenavi.callback.SimpleCallback;
import com.nexuslink.wenavi.common.Constant;
import com.nexuslink.wenavi.model.IMainModel;
import com.nexuslink.wenavi.util.SortConvList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.enums.MessageDirect;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;


/**
 * Created by aplrye on 17-8-31.
 */

public class MainModel implements IMainModel {
    private static final int WITHOUT_FRIENDS = 0;
    private CallBack<Conversation> mConversationCallBack;
    private SimpleCallback mSendMessageCallback;

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

    public void setSendMessageCallback(SimpleCallback sendMessageCallback) {
        this.mSendMessageCallback = sendMessageCallback;
    }

    /**
     * 此方法用于发送消息，包括发送当前位置，当前绘制路线，普通消息
     *
     * @param text       此处Text为封装为json格式的字符串
     * @param code       标志当前消息类型
     */
    @Override
    public void sendMessageToTarget(String targetName,String text, final int code) {
        cn.jpush.im.android.api.model.Message message =
                JMessageClient.createSingleTextMessage(targetName, "", text);//appkey为空默认设置为当前应用
        message.setOnSendCompleteCallback(new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (i == 0) {
                    mSendMessageCallback.onSuccess(code);
                } else {
                    mSendMessageCallback.onFail(code, s);
                }
            }
        });
        JMessageClient.sendMessage(message);
    }

    /**
     * 获取聊天历史
     *
     * @param conversation 想要获取历史的对话对象
     * @return 聊天历史
     */

    // TODO: 17-9-4 目前会话量较少，获取全部记录
    //https://docs.jiguang.cn/jmessage/client/im_sdk_android/ 参考此处getMessagesFromNewest与
    //getMessagesFromOldest对历史进行分页处理
    
    @Override
    public List<TextMessage> getHistoryWithTarget(Conversation conversation) {
        //由于这里的Message是一个抽象类，这里将Message中的数据信息抽取出来合成自己的信息类
        List<Message> messages = conversation.getAllMessage();
        List<TextMessage> textMessages = new ArrayList<>();
        for (Message message : messages){
            if (Objects.equals(message.getFromType(), "text")){
                //文字信息
                int type;
                if (message.getDirect() == MessageDirect.send){
                      type = Constant.CONVERSATION_ME;
                } else {
                    type = Constant.CONVERSATION_YOU;
                }
                String content = message.getContent().toString();
                textMessages.add(new TextMessage(type,content));
            } else {
                Log.d("debug", "getHistoryWithTarget: "+"获取type与‘text’不匹配");
            }
        }
        return textMessages;
    }
}

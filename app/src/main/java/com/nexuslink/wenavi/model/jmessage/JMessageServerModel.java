package com.nexuslink.wenavi.model.jmessage;

import android.util.Log;

import com.nexuslink.wenavi.callback.ServerResultCallback;
import com.nexuslink.wenavi.common.Constant;
import com.nexuslink.wenavi.common.ServeType;
import com.nexuslink.wenavi.model.ChatItem;
import com.nexuslink.wenavi.model.ChatMessage;
import com.nexuslink.wenavi.model.ConversationItem;
import com.nexuslink.wenavi.model.IServerModel;
import com.nexuslink.wenavi.model.WeNaviUserInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

/**
 *  JMessage请求模型封装
 * Created by 18064 on 2018/2/3.
 */

public class JMessageServerModel implements IServerModel {

    private static final int SIMPLE_RESULT = -1;

    private  JMessageResultHelper helper;

    private ServerResultCallback callback;

    private static JMessageServerModel instance;

    public static JMessageServerModel getInstance() {
        if (instance == null) {
            synchronized (JMessageServerModel.class) {
                if (instance == null) {
                    instance = new JMessageServerModel();
                }
            }
        }
        return instance;
    }

    private JMessageServerModel() {
        helper = new JMessageResultHelper();
    }

    @Override
    public void setCallback(ServerResultCallback callback) {
        this.callback = callback;
    }

    @Override
    public void register(final String username, final String password) {
        JMessageClient.register(username, password, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                try {
                    WeNaviUserInfo weNaviUserInfo = new WeNaviUserInfo();
                    weNaviUserInfo.setUserName(username);
                    weNaviUserInfo.setPassword(password);
                    helper.handleResult(i, s,weNaviUserInfo, Constant.SIGN_IN);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void login(String username, String password) {
        JMessageClient.login(username, password, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                try {
                    Log.d("LOGIN", "gotResult: 登陆成功");
                    helper.handleResult(i, s,null, Constant.SIGN_IN);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void logout() {
        JMessageClient.logout();
    }

    @Override
    public void getUserInfo(final String username, final int flag) {
        JMessageClient.getUserInfo(username, new GetUserInfoCallback() {
            @Override
            public void gotResult(int i, String s, UserInfo userInfo) {
                try {
                    //userinfo转wenaviuserinfo降低对JMessage的耦合
                     WeNaviUserInfo weNaviUserInfo = new WeNaviUserInfo();
                     weNaviUserInfo.setUserName(userInfo.getUserName());
                     weNaviUserInfo.setNickName(userInfo.getNickname());
                     weNaviUserInfo.setAvatar(userInfo.getAvatar());
                    helper.handleResult(i, s, weNaviUserInfo, flag);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

//    @Override
//    public void updateMyInfo(WeNaviUserInfo.Field field, WeNaviUserInfo weNaviUserInfo) {
//        UserInfoImpl userInfo = new UserInfoImpl();
//        UserInfo.Field updateField = null;
//        switch (field) {
//            case NICKNAME:
//                updateField = UserInfo.Field.nickname;
//                break;
//        }
//
//        // 将weNaviUserInfo的信息转移到userInfoImpl
//        userInfo.setNickname(weNaviUserInfo.getNickName());
//
//        if (field != null) {
//            JMessageClient.updateMyInfo(updateField ,userInfo, new BasicCallback() {
//                @Override
//                public void gotResult(int i, String s) {
//                    try {
//                        helper.handleResult(i, s);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        }
//    }

    @Override
    public void updateUserPassword(String oldPassword, String newPassword) {
        JMessageClient.updateUserPassword(oldPassword, newPassword, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                try {
                    helper.handleResult(i, s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void updateUserAvatar(File avatar) {
        JMessageClient.updateUserAvatar(avatar, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                try {
                    helper.handleResult(i, s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

//    @Override
//    public void createSingeConversation(String targetUsername) {
//        Conversation conversation = Conversation.createSingleConversation(targetUsername);
//    }

//    @Override
//    public void getSingleConversation(String targetUserName) {
//        conversationController.getSingleConversation(targetUserName);
//        Conversation conversation = JMessageClient.getSingleConversation(targetUserName);
//        //...
//    }

//    @Override
//    public void deleteSingleConversation(String targetUserName) {
//        boolean isDelete = JMessageClient.deleteSingleConversation(targetUserName);
//        try {
//            helper.handleResult(isDelete?1:0,"delete single conversation fail.");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void createGroupConversation(long groupID) {
//        Conversation.createGroupConversation(groupID);
//    }

//    @Override
//    public void getGroupConversation(long groupID) {
//        Conversation conversation = JMessageClient.getGroupConversation(groupID);
//        //..
//    }

//    @Override
//    public void deleteGroupConversation(long groupID) {
//        boolean isDelete  = JMessageClient.deleteGroupConversation(groupID);
//        try {
//            helper.handleResult(isDelete?1:0,"delete group conversation fail.");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        //...
//    }

    @Override
    public List<ConversationItem> getConversationList() throws Exception {
        List<ConversationItem> conversationItemList = new ArrayList<>();
        List<Conversation> conversationList = JMessageClient.getConversationList();
        Log.d("Conversation", "getConversationList: 请求Conversation大小：" + conversationList.size());
        for (int i = 0; i < conversationList.size(); i++) {
            ConversationItem conversationItem = new ConversationItem();
            Conversation currentConversation = conversationList.get(i);
            conversationItem.setLastMessage(currentConversation.getLatestMessage().getContent().toJson());
            if (currentConversation.getTargetInfo() instanceof UserInfo) {
                UserInfo  userInfo = (UserInfo)currentConversation.getTargetInfo();
                conversationItem.setUserName(userInfo.getUserName());
                conversationItem.setNickName(userInfo.getNickname());
                conversationItem.setAvatar(userInfo.getAvatar());
                conversationItemList.add(conversationItem);
            } else {
                throw new Exception("targetUserInfo 不能转化为 UserInfo ");
            }
        }
        return conversationItemList;
    }

    @Override
    public List<ChatItem> getChatList(String targetUsername) {
        Conversation conversation = JMessageClient.getSingleConversation(targetUsername);
        List<Message> messages = conversation.getAllMessage();
        List<ChatItem> chatItemList = new ArrayList<>();
        for (Message message : messages) {
            ChatItem chatItem = new ChatItem();
            UserInfo from = message.getFromUser();
            chatItem.setUsername(from.getUserName());
            chatItem.setNickName(from.getNickname());
            chatItem.setAvatar(from.getAvatar());
            ChatMessage chatMessage = new ChatMessage(message.getContent().toJson());
            chatItem.setChatMessage(chatMessage);
            chatItemList.add(chatItem);
        }
        return chatItemList;
    }

//    @Override
//    public void getUnReadMsgCnt() {
//        int num = conversation.getUnReadMsgCnt();
//    }
//
//    @Override
//    public void resetUnreadCount() {
////
//    }
//
//    @Override
//    public void setUnReadMessageCnt(int count) {
////
//    }
//
//    @Override
//    public void getAllUnReadMsgCount() {
////
//    }
//
//    @Override
//    public void createSingleTextMessage(String targetUsername, String text) {
//
//    }
//
//    @Override
//    public void createGroupTextMessage(long groupID, String text) {
//
//    }
//
//    @Override
//    public void createSingleImageMessage(String username, File imageFile) {
//
//    }
//
//    @Override
//    public void createGroupImageMessage(long groupID, File imageFile) {
//
//    }
//
//    @Override
//    public void createSingleVoiceMessage(String username, File voiceFile, int duration) {
//
//    }
//
//    @Override
//    public void createGroupVoiceMessage(long groupID, File voiceFile, int duration) {
//
//    }
//
//    @Override
//    public void createSingleLocationMessage(String username, double latitude, double longitude, int scale, String address) {
//
//    }
//
//    @Override
//    public void createGroupLocationMessage(long groupId, double latitude, double longitude, int scale, String address) {
//
//    }
//
//    @Override
//    public void createSingleFileMessage(String userName, File file, String fileName) {
//
//    }
//
//    @Override
//    public void createGroupFileMessage(long groupID, File file, String fileName) {
//
//    }
//
//    @Override
//    public void createSingleCustomMessage(String username, String appKey, Map<? extends String, ? extends String> valuesMap) {
//
//    }
//
//    @Override
//    public void createGroupCustomMessage(long groupID, Map<? extends String, ?> valuesMap) {
//
//    }
    /**
     *  JMessage结果返回帮助类
     */
    private class JMessageResultHelper {

        private static final int RESULT_OK = 0;

        public void handleResult(int resultCode, String exception) throws Exception {
            this.handleResult(resultCode, exception, null, Constant.NONE);
        }

        public <T> void handleResult(int resultCode, String exception, T result, @ServeType int code) throws Exception {
            if (callback == null) {
                throw new Exception("ServerResultCallback can not be null");
            }
            if (resultCode == RESULT_OK) {
                callback.onSuccess(result, code);
            } else {
                callback.onFail(exception, code);
            }
        }
    }
}

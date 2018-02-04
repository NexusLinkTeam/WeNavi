package com.nexuslink.wenavi.model;

import com.nexuslink.wenavi.callback.ServerResultCallback;

import java.io.File;
import java.util.List;

/**
 *  后台需要提供的操作
 *  Created by 18064 on 2018/2/3.
 */

public interface IServerModel {

    //===========================================注册与登录=========================================
    /**
     *  注册新用户
     * @param username 用户名
     * @param password 密码
     */
    void register (String username, String password);

    /**
     *  登录
     * @param username 用户名
     * @param password 密码
     */
    void login(String username, String password);

    /**
     *  登出
     */
    void logout();

    //===========================================多端同时在线=======================================
    // TODO: 2018/2/3 升级到 v2.3.0， 支持多端同时在线

    //===========================================用户属性维护=======================================
    /**
     *  获取用户信息
     * @param username 获取信息用户
     * @param flag  查询flag
     */
    void getUserInfo(String username, int flag);

    // TODO: 2018/2/4 用户跟新字段解决方法
//    /**
//     * 更新用户信息
//     * @param field 跟新字段
//     * @param userInfo 用户信息
//     */
//    void updateMyInfo(WeNaviUserInfo.Field field, WeNaviUserInfo userInfo);

    /**
     * 更新密码
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    void updateUserPassword(String oldPassword, String newPassword);

    /**
     * 跟新头像
     * @param avatar 头像
     */
    void updateUserAvatar(File avatar);

    //===========================================本地会话管理=======================================
//    /**
//     *  创建单聊会话
//     * @param targetUsername 会话对象
//     */
//    void createSingeConversation (String targetUsername);
//
//    /**
//     * 获取单聊会话
//     * @param targetUserName 会话对象
//     */
//    void getSingleConversation(String targetUserName);
//
//    /**
//     *  删除单个单聊会话
//     * @param targetUserName 会话对象
//     */
//    void deleteSingleConversation(String targetUserName);
//
//    /**
//     * 创建群聊会话
//     * @param groupID 组id
//     */
//    void createGroupConversation (long groupID);

//    /**
//     * 获取单个群聊会话
//     * @param groupID 组id
//     */
//    void getGroupConversation(long groupID);

//    /**
//     * 删除单个群聊会话
//     * @param groupID 组id
//     */
//    void deleteGroupConversation(long groupID);
//
    /**
     *
     * 获得会话列表
     * @return 会话项
     */
    List<ConversationItem> getConversationList() throws Exception;

    /**
     *  获取聊天记录
     * @param targetUsername 目标对象
     * @return 聊天记录项
     */
    List<ChatItem> getChatList(String targetUsername);
//    /**
//     * 获取单个会话未读消息数
//     */
//    void getUnReadMsgCnt();
//
//    /**
//     * 重置单个会话未读消息数
//     */
//    void resetUnreadCount();
//
//    /**
//     * 手动设置会话未读消息数
//     * @param count 设置的未读消息数
//     */
//    void setUnReadMessageCnt(int count);
//
//    /**
//     * 获取所有会话未读消息总数
//     */
//    void getAllUnReadMsgCount();
//
//    //===========================================本地会话管理=======================================
//
//    /**
//     *  创建文字消息
//     * @param targetUsername 目标对象
//     * @param text 文本内容
//     */
//    void createSingleTextMessage(String targetUsername, String text);
//
//    /**
//     * 创建群文字消息
//     * @param groupID 群组id
//     * @param text 文本内容
//     */
//    void createGroupTextMessage(long groupID, String text);
//
//    /**
//     *  创建单一图片消息
//     * @param username 目标对象
//     * @param imageFile 图片文件
//     */
//    void createSingleImageMessage(String username, File imageFile);
//
//    /**
//     * 创建群组图片消息
//     * @param groupID 群组id
//     * @param imageFile 图片文件
//     */
//    void createGroupImageMessage(long groupID, File imageFile);
//
//    /**
//     * 创建单一声音消息
//     * @param username 目标对象
//     * @param voiceFile 声音文件
//     * @param duration 时长
//     */
//    void createSingleVoiceMessage(String username, File voiceFile, int duration);
//
//    /**
//     * 创建群组声音消息
//     * @param groupID 群组id
//     * @param voiceFile 声音文件
//     * @param duration 时长
//     */
//    void createGroupVoiceMessage(long groupID, File voiceFile, int duration);
//
//    /**
//     *  创建一条单聊地理位置消息
//     * @param username  聊天对象的用户名
//     * @param latitude  纬度信息
//     * @param longitude 经度信息
//     * @param scale     地图缩放比例
//     * @param address   详细地址信息
//     */
//    void createSingleLocationMessage(String username, double latitude,
//                                     double longitude, int scale, String address);
//
//    /**
//     *  创建一条群聊地理位置消息
//     * @param groupId   群组groupID
//     * @param latitude  纬度信息
//     * @param longitude 经度信息
//     * @param scale     地图缩放比例
//     * @param address   详细地址信息
//     */
//    void createGroupLocationMessage(long groupId, double latitude, double longitude,
//                                    int scale, String address);
//
//    /**
//     * 创建一条单聊file消息
//     * @param userName 聊天对象的用户名
//     * @param file     发送的文件
//     * @param fileName 指定发送的文件名称,如果不填或为空，则默认使用文件原名。
//     */
//    void createSingleFileMessage(String userName, File file, String fileName);
//
//    /**
//     * 创建一条群聊file消息
//     * @param groupID  群组groupID
//     * @param file     发送的文件
//     * @param fileName 指定发送的文件名称,如果不填或为空，则默认使用文件原名。
//     */
//    void createGroupFileMessage(long groupID, File file, String fileName);
//
//    /**
//     * 创建一条单聊自定义消息
//     * @param username  聊天对象username
//     * @param appKey    聊天对象所属应用的appKey
//     * @param valuesMap 包含自定义键值对的map
//     */
//    void createSingleCustomMessage(String username, String appKey,
//                                   Map<? extends String, ? extends String> valuesMap);
//
//    /**
//     * 创建一条群聊自定义消息
//     * @param groupID   群组groupID
//     * @param valuesMap 包含了自定义键值对的map
//     */
//    void createGroupCustomMessage(long groupID,
//                                  Map<? extends String, ?> valuesMap);

//    void sendMessage(Message message);

    void setCallback(ServerResultCallback callback);
}


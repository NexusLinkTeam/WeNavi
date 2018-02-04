package com.nexuslink.wenavi.presenter;

import com.nexuslink.wenavi.DaoSession;
import com.nexuslink.wenavi.FriendVerify;
import com.nexuslink.wenavi.FriendVerifyDao;
import com.nexuslink.wenavi.base.BaseApp;
import com.nexuslink.wenavi.callback.ServerResultCallback;
import com.nexuslink.wenavi.common.Constant;
import com.nexuslink.wenavi.common.Service;
import com.nexuslink.wenavi.contract.PrincipalContract;
import com.nexuslink.wenavi.model.ConversationItem;
import com.nexuslink.wenavi.model.IServerModel;
import com.nexuslink.wenavi.model.WeNaviUserInfo;
import com.nexuslink.wenavi.model.jmessage.JMessageServerModel;
import com.nexuslink.wenavi.util.SPUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 18064 on 2018/1/31.
 */

public class PrincipalPresenter implements PrincipalContract.Presenter, ServerResultCallback {

    private PrincipalContract.View view;

    private IServerModel model;

    private FriendVerifyDao friendVerifyDao;

    private String reason;

    public PrincipalPresenter(PrincipalContract.View view) {
        this.view = view;
        this.model = JMessageServerModel.getInstance();
        model.setCallback(this);
        DaoSession daoSession = BaseApp.getDaosession();
        friendVerifyDao = daoSession.getFriendVerifyDao();
    }

    @Override
    public void saveUserProfile(String username) {
        model.getUserInfo(username, Service.GET_USER_INFO_USER);
    }

    @Override
    public void logout() {
        model.logout();
    }

    @Override
    public void receiveInvitation(String from, String reason) {
        model.getUserInfo(from, Service.GET_USER_INFO_TARGET);
        this.reason = reason;
    }

    @Override
    public void loadMessageNum() {
        int num = friendVerifyDao.queryBuilder().list().size();
        view.updateBadgeNum(num);
    }

    @Override
    public List<ConversationItem> getConversationItemList() {
        try {
            return model.getConversationList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

//    @Override
//    public List<Conversation> loadChatList() {
//        return model.loadConversationList();
//    }

    @Override
    public <T> void onSuccess(T result, int code) {
        switch (code) {
            case Service.GET_USER_INFO_USER:
                WeNaviUserInfo weNaviUserInfo = (WeNaviUserInfo) result;
                SPUtil.putAndApply(BaseApp.getBaseApplicationContext(),
                        Constant.USERNAME,
                        weNaviUserInfo.getUserName());
                SPUtil.putAndApply(BaseApp.getBaseApplicationContext(),
                        Constant.NICKNAME,
                        weNaviUserInfo.getNickName());
                SPUtil.putAndApply(BaseApp.getBaseApplicationContext(),
                        Constant.AVATAR,
                        weNaviUserInfo.getAvatar());
                break;
            case Service.GET_USER_INFO_TARGET:
                //存储邀请消息
                weNaviUserInfo = (WeNaviUserInfo) result;
                FriendVerify verify = new FriendVerify();
                verify.setAvatar(weNaviUserInfo.getAvatar());
                verify.setNickName(weNaviUserInfo.getNickName());
                verify.setUserName(weNaviUserInfo.getUserName());
                verify.setHello(reason);
                friendVerifyDao.insert(verify);
                break;
        }
    }

    @Override
    public void onFail(String exception, int code) {
        view.shortToast(exception);
    }
}

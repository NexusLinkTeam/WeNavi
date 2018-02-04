//package com.nexuslink.wenavi.model;
//
//import android.util.Log;
//
//import com.nexuslink.wenavi.DaoSession;
//import com.nexuslink.wenavi.FriendVerify;
//import com.nexuslink.wenavi.FriendVerifyDao;
//import com.nexuslink.wenavi.base.BaseApp;
//import com.nexuslink.wenavi.common.Constant;
//import com.nexuslink.wenavi.contract.PrincipalContract;
//import com.nexuslink.wenavi.util.SPUtil;
//
//import java.util.List;
//
//import cn.jpush.im.android.api.ContactManager;
//import cn.jpush.im.android.api.JMessageClient;
//import cn.jpush.im.android.api.callback.GetUserInfoCallback;
//import cn.jpush.im.android.api.callback.GetUserInfoListCallback;
//import cn.jpush.im.android.api.event.ContactNotifyEvent;
//import cn.jpush.im.android.api.model.Conversation;
//import cn.jpush.im.android.api.model.UserInfo;
//
///**
// * Created by 18064 on 2018/1/31.
// */
//
//public class PrincipalModel implements PrincipalContract.Model {
//
//    private PrincipalContract.Presenter presenter;
//
//    private FriendVerifyDao friendVerifyDao;
//
//    public PrincipalModel(PrincipalContract.Presenter presenter) {
//        this.presenter = presenter;
//        DaoSession daoSession = BaseApp.getDaosession();
//        friendVerifyDao = daoSession.getFriendVerifyDao();
//    }
//
//    @Override
//    public void getUserInfo(String username) {
//        JMessageClient.getUserInfo(username, new GetUserInfoCallback() {
//            @Override
//            public void gotResult(int i, String s, UserInfo userInfo) {
//                if (i == 0) {
//                    SPUtil.putAndApply(BaseApp.getBaseApplicationContext(), Constant.AVATAR, userInfo.getAvatar());
//                    SPUtil.putAndApply(BaseApp.getBaseApplicationContext(), Constant.NICKNAME,userInfo.getNickname());
//                    SPUtil.putAndApply(BaseApp.getBaseApplicationContext(), Constant.USERNAME,userInfo.getNickname());
//                    presenter.getUserInfoSuccess(userInfo);
//                }else {
//                    presenter.getUserInfoFail(s);
//                }
//            }
//        });
//    }
//
//    @Override
//    public void logout() {
//        JMessageClient.logout();
//    }
//
//    @Override
//    public void save(ContactNotifyEvent event) {
//        String userName = event.getFromUsername();
//        final String reason = event.getReason();
//        JMessageClient.getUserInfo(userName, new GetUserInfoCallback() {
//            @Override
//            public void gotResult(int i, String s, UserInfo userInfo) {
//                if(i==0){
//                    Log.e("TAG","成功了啊");
//                    FriendVerify verify = new FriendVerify();
//                    verify.setAvatar(userInfo.getAvatar());
//                    verify.setHello(reason);
//                    verify.setNickName(userInfo.getNickname());
//                    verify.setUserName(userInfo.getUserName());
//                    friendVerifyDao.insert(verify);
//                } else {
//                    presenter.saveMessageFail(s);
//                }
//
//            }
//        });
//    }
//
//    @Override
//    public void loadFriendsList() {
//        ContactManager.getFriendList(new GetUserInfoListCallback() {
//            @Override
//            public void gotResult(int i, String s, List<UserInfo> list) {
//                if (i == 0) {
//                    presenter.loadFriendsSuccess(list);
//                } else {
//                    presenter.loadFriendsFail(s);
//                }
//            }
//        }) ;
//    }
//
//    @Override
//    public int loadMessageNum() {
//        return friendVerifyDao.queryBuilder().list().size();
//    }
//
//    @Override
//    public List<Conversation> loadConversationList() {
//        return JMessageClient.getConversationList();
//    }
//}

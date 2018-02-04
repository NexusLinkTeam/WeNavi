//package com.nexuslink.wenavi.presenter;
//
//import android.content.Context;
//import android.util.Log;
//import android.widget.EditText;
//
//import com.nexuslink.wenavi.callback.CallBack;
//import com.nexuslink.wenavi.callback.SimpleCallback;
//import com.nexuslink.wenavi.common.Constant;
//import com.nexuslink.wenavi.contract.MainContract;
//import com.nexuslink.wenavi.model.MainModel;
//import com.nexuslink.wenavi.model.TextMessage;
//import com.nexuslink.wenavi.model.WeNaviLocation;
//import com.nexuslink.wenavi.model.WeNaviMessage;
//
//import java.util.List;
//import java.util.Objects;
//
//import cn.jpush.im.android.api.JMessageClient;
//import cn.jpush.im.android.api.model.Conversation;
//import cn.jpush.im.api.BasicCallback;
//
//import static cn.jpush.im.android.tasks.GetUserInfoListTask.IDType.username;
//
///**
// * Created by aplrye on 17-8-31.
// */
//
//public class MainPresenter implements MainContract.Presenter, CallBack<Conversation>, SimpleCallback {
//    private MainContract.View view;
//    private MainModel model;
//    private String targetName;//获取双向定位成功时才设置此字段的值
//    private String tmpName;
//
//    public MainPresenter(MainContract.View view, MainModel model) {
//        this.view = view;
//        this.model = model;
//        view.setPresenter(this);
//        model.setConversationCallback(this);
//        model.setSendMessageCallback(this);
//    }
//
//    @Override
//    public void start() {
//
//    }
//
//    @Override
//    public void loadFriendsList() {
//        model.loadConversation();
//    }
//
//    @Override
//    public void openFriendsList() {
//        view.showBottomFriends(false);
//        view.showFriendsSheet();
//    }
//
//    @Override
//    public void closeFriendList() {
//        view.hideFriendsSheet();
//    }
//
//    @Override
//    public void openChatListFromFirst() {
//        view.showChatSheet();
//        view.showEditorBar(true);
//        view.friendListVisible(false);
//    }
//
//    @Override
//    public void openChatListFromSelf() {
//        view.showChatSheet();
//        view.showBottomChat(false);
//    }
//
//    @Override
//    public void closeChatList() {
//        view.chatListVisible(false);
//        view.showEditorBar(false);
//        view.showBottomChat(false);
//    }
//
//    /**
//     * 发送文字信息
//     *
//     * @param mMessageEdTx 编辑框
//     */
//
//    @Override
//    public void sendTextMessage(EditText mMessageEdTx) {
//        String text = mMessageEdTx.getText().toString();
//        if (Objects.equals(text, "")) {
//            view.showInfo("不能发送空消息哦");
//        } else {
//            //需要将信息内容封装成一个WeNaviMessage，然后转化为Json
//            WeNaviMessage weNaviMessage = new WeNaviMessage();
//            weNaviMessage.setType(Constant.SIMPLE_MESSAGE);
//            weNaviMessage.setContent(text);
//            String content = weNaviMessage.toJSONObject();
//            model.sendMessageToTarget(getTargetName(), text, Constant.CODE_MESSAGE_SEND);
//            view.insertNewMessage(new TextMessage(Constant.CONVERSATION_ME, content));
//            // TODO: 17-9-4 提升体验
//            //view.showSendProgress(true);显示进度
//        }
//    }
//
//    /**
//     * 发送位置
//     *
//     * @param longitude 经
//     * @param latitude  纬
//     */
//
//    @Override
//    public void sendLocationMessage(Double longitude, Double latitude) {
//        WeNaviMessage weNaviMessage = new WeNaviMessage();
//        weNaviMessage.setType(Constant.LOCATION_MESSAGE);
//        weNaviMessage.setLocation(new WeNaviLocation(longitude, latitude));
//        String content = weNaviMessage.toJSONObject();
//        model.sendMessageToTarget(getTargetName(), content, Constant.CODE_LOCATION_SEND);
//    }
//
//    /**
//     * 发送Line
//     *
//     * @param locations 坐标集合的数组
//     */
//
//    @Override
//    public void sendLineMessage(WeNaviLocation[] locations) {
//        WeNaviMessage weNaviMessage = new WeNaviMessage();
//        weNaviMessage.setType(Constant.DRAW_MESSAGE);
//        weNaviMessage.setLocations(locations);
//        String content = weNaviMessage.toJSONObject();
//        model.sendMessageToTarget(getTargetName(), content, Constant.CODE_LINE_SEND);
//    }
//
//    /**
//     * 发送连接信息
//     *
//     * @param itemName 选中Item的name
//     */
//
//    @Override
//    public void sendSureMessage(String itemName) {
//        tmpName = itemName;//暂存，成功设置为target，失败，等待重置
//        WeNaviMessage weNaviMessage = new WeNaviMessage();
//        weNaviMessage.setType(Constant.CONNECT_MESSAGE);
//        weNaviMessage.setConnect(true);//bool类型默认为false
//        String content = weNaviMessage.toJSONObject();
//        model.sendMessageToTarget(getTargetName(), content, Constant.CODE_SURE_SEND);
//    }
//
//    // TODO: 17-9-6 两个回调的存在意义
//    @Override
//    public void onSuccess(List<Conversation> beans) {
//        view.showFriendsList(beans);
//    }
//
//    @Override
//    public void onSuccess(Conversation bean) {
//
//    }
//
//    /**
//     * 各类发送事件成功后的回调
//     *
//     * @param code 类别，通过判断code判断回调成功后处理的事务
//     */
//
//    @Override
//    public void onSuccess(int code) {
//        switch (code) {
//            case Constant.CODE_MESSAGE_SEND:
//                // TODO: 17-9-4  添加progress
//                //view.hideProgress();
//                view.cleanInput();
//                view.showInfo("发送成功");
//                break;
//            case Constant.CODE_LOCATION_SEND:
//                //向对方发送位置成功，控制台打印
//                Log.d("debug", "发送当前位置...");
//                break;
//            case Constant.CODE_LINE_SEND:
//                view.showInfo("发送路线成功");
//                break;
//            case Constant.CODE_SURE_SEND:
//                //将当前的itemName设置给target,开放接口
//                // TODO: 17-9-6 ？？？ 应该是对方应答成功后才给Presenter的target赋值，允许调用其他消息发送
//                view.showInfo("发送请求成功");
//                break;
//        }
//    }
//
//    /**
//     * 各类事件失败后的回调
//     *
//     * @param code      回调出处
//     * @param exception 异常原因
//     */
//
//    @Override
//    public void onFail(int code, String exception) {
//        view.showInfo(exception);
//    }
//
//    public String getTargetName() {
//        return targetName;
//    }
//}

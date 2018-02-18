package com.nexuslink.wenavi.common;

/**
 * Created by aplrye on 17-8-27.
 */

public interface Constant {
    int LOGIN = 0;
    int REGISTER = 1;
    int UPDATE = 2;
    //消息类型
    int SIMPLE_MESSAGE = 0;
    int CONNECT_MESSAGE = 1;
    int LOCATION_MESSAGE = 2;
    int DRAW_MESSAGE = 3;

    String IS_LOGIN = "is_login";

    int CODE_MESSAGE_SEND = 1;
    int CODE_LOCATION_SEND = 2;
    int CODE_LINE_SEND = 3;
    int CODE_SURE_SEND = 4;

    int CONVERSATION_ME = 0;
    int CONVERSATION_YOU = 1;

    String AVATAR = "avatar";
    String USERNAME = "username";
    String CONVERSATION = "conversation";
    String NICKNAME = "nickname";

    int SIGN_UP = 0;
    int SIGN_IN = 1;
    int GET_USER_INFO_USER = 2;
    int GET_USER_INFO_TARGET = 3;
    int NONE = -1;
}

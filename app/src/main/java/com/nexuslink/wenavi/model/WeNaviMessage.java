package com.nexuslink.wenavi.model;

import com.google.gson.jpush.Gson;

/**
 *
 * Created by alphrye on 17-8-29.
 */

public class WeNaviMessage {
    private int type;//根据type来获取对应的内容
    //!！！注意：一条Message实际上只包含有一个type和以下4个属性之一，其他为空
    private boolean connect;//true为连接请求，false为取消连接请求，其他消息为空
    private String content;//为空为其他消息，不为空为普通消息
    private WeNaviLocation[] locations;//为空为其他消息，不为空为绘制地图消息
    private WeNaviLocation location;//为空为其他消息，不为空为发送位置消息

    public int getType() {
        return type;
    }

    public boolean isConnect() {
        return connect;
    }

    public String getContent() {
        return content;
    }

    public WeNaviLocation[] getLocations() {
        return locations;
    }

    public WeNaviLocation getLocation() {
        return location;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setConnect(boolean connect) {
        this.connect = connect;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLocations(WeNaviLocation[] locations) {
        this.locations = locations;
    }

    public void setLocation(WeNaviLocation location) {
        this.location = location;
    }

    /**
     * 转化当前对象为JSON格式字符串（字符串转化为String参考GSON）
     * @return JSON格式字符串
     */

    public String toJSONObject(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}

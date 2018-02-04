package com.nexuslink.wenavi.model;

/**
 * Created by 18064 on 2018/1/31.
 */

public class SettingItem {
    private int icon;

    private String settingText;

    public SettingItem(int icon, String settingText) {
        this.icon = icon;
        this.settingText = settingText;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getSettingText() {
        return settingText;
    }

    public void setSettingText(String settingText) {
        this.settingText = settingText;
    }
}

package com.nexuslink.wenavi.util;

import android.content.Context;

import com.nexuslink.wenavi.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by 18064 on 2018/1/31.
 */

public class ThemeManager {

    private static final Object POSITION_DEFAULT = 1;

    private static int themeId;

    private static int position;

    private static final String POSITION = "position";

    private static final String THEME = "theme";

    public static List<Theme> themes = Arrays.asList(
            new Theme("夜间模式",R.style.AppTheme_Dark,R.color.colorPrimary_Dark),
            new Theme("少女粉",R.style.AppTheme_Default,R.color.colorPrimary_Default),
            new Theme("姨妈红",R.style.AppTheme_Red,R.color.colorPrimary_Red),
            new Theme("煎蛋黄",R.style.AppTheme_Yellow,R.color.colorPrimary_Yellow),
            new Theme("早苗绿",R.style.AppTheme_Green,R.color.colorPrimary_Green),
            new Theme("基佬紫",R.style.AppTheme_Purple,R.color.colorPrimary_Purple)
            );

    public static void save(Context context, int pos, int themeId) {
        SPUtil.putAndApply(context, THEME,themeId);
        SPUtil.putAndApply(context, POSITION, pos);
    }

    public static int getSavedThemeId(Context context) {
        return (int) SPUtil.get(context ,THEME,0);
    }

    public static int getSavedPosition(Context context) {
        return (int) SPUtil.get(context, POSITION,POSITION_DEFAULT);
    }

    public static class Theme {
        private String mData;
        private int mStyle;
        private int mColor;

        public Theme(String mData, int mStyle, int mColor) {
            this.mData = mData;
            this.mStyle = mStyle;
            this.mColor = mColor;
        }

        public String getmData() {
            return mData;
        }

        public void setmData(String mData) {
            this.mData = mData;
        }

        public int getmStyle() {
            return mStyle;
        }

        public void setmStyle(int mStyle) {
            this.mStyle = mStyle;
        }

        public int getmColor() {
            return mColor;
        }

        public void setmColor(int mColor) {
            this.mColor = mColor;
        }
    }
}

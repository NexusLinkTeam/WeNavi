package com.nexuslink.wenavi.util;

import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * Created by aplrye on 17-8-31.
 */

public class AnimUtil {
    //// TODO: 17-8-31 待修改
    public static final Animation UPACTION = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                                       Animation.RELATIVE_TO_SELF, 0.0f,Animation.RELATIVE_TO_SELF,
                1.0f,Animation.RELATIVE_TO_SELF, 0.0f);
    public static final Animation DOWNACTION = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
            -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
}

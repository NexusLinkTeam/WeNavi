package com.nexuslink.wenavi.util;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

/**
 *  表单填写帮助类
 * Created by 18064 on 2018/1/30.
 */

public class FormCheckHelper {
    public static void check(final Button buttonConfirm, final EditText ... editTexts) {
        for (EditText editText : editTexts) {
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (checkStatus(editTexts)) {
                        buttonConfirm.setEnabled(false);
                    } else {
                        buttonConfirm.setEnabled(true);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    private static boolean checkStatus(EditText[] editTexts) {
        for (EditText editText : editTexts) {
            if (TextUtils.isEmpty(editText.getText().toString())) {
                return true;
            }
        }
        return false;
    }
}

package com.nexuslink.wenavi;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by aplrye on 17-8-15.
 */

class HeaderViewHolder extends RecyclerView.ViewHolder {
    TextView text,friendVerifyNum;
    public HeaderViewHolder(View view) {
        super(view);
        text = view.findViewById(R.id.friend_text);
        friendVerifyNum = view.findViewById(R.id.friend_verification_num);
    }
}

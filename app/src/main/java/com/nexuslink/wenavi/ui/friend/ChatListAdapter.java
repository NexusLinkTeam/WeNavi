package com.nexuslink.wenavi.ui.friend;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nexuslink.wenavi.R;
import com.nexuslink.wenavi.common.Constant;
import com.nexuslink.wenavi.model.TextMessage;
import com.nexuslink.wenavi.model.WeNaviMessage;
import com.nexuslink.wenavi.ui.main.HeaderViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.im.android.api.enums.MessageDirect;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;

/**
 * Created by alphrye on 17-8-7.
 */

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder> {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_BODY = 1;
    private Context mContext;
    private List<TextMessage> messages;

    public ChatListAdapter(Context context,List<TextMessage> messageContents) {
        this.mContext = context;
        this.messages = messageContents;
    }

    @Override
    public ChatListAdapter.ChatListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_chat, parent, false);
        return new ChatListAdapter.ChatListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatListAdapter.ChatListViewHolder holder, int position) {
        TextMessage currentMessage = messages.get(position);
        // TODO: 17-9-4 拓展： 当前版本type都为Text
        // currentMessage.getContentType();

        Gson gson = new Gson();
        WeNaviMessage weNaviMessage = gson.fromJson(currentMessage.getMessageContent(), WeNaviMessage.class);

        if(currentMessage.getType()== Constant.CONVERSATION_ME){
            //隐藏对方的
            holder.contentYou.setVisibility(View.GONE);
            holder.contentMe.setVisibility(View.VISIBLE);
            holder.textMe.setText(weNaviMessage.getContent());
        } else if (currentMessage.getType() == Constant.CONVERSATION_YOU) {
            //隐藏自己的
            holder.contentMe.setVisibility(View.VISIBLE);
            holder.contentYou.setVisibility(View.GONE);
            holder.textYou.setText(weNaviMessage.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void insertDataToHead(TextMessage textMessage) {
//        在最开始位置添加
        messages.add(0,textMessage);
        notifyDataSetChanged();
    }

    public class ChatListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.content_me)
        LinearLayout contentMe;

        @BindView(R.id.content_you)
        LinearLayout contentYou;

        @BindView(R.id.text_me)
        TextView textMe;

        @BindView(R.id.text_you)
        TextView textYou;

        public ChatListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}

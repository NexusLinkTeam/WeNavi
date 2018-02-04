package com.nexuslink.wenavi.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nexuslink.wenavi.R;
import com.nexuslink.wenavi.model.ConversationItem;
import com.nexuslink.wenavi.ui.main.HeaderViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by alphrye on 17-8-7.
 */


public class ConversationListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_BODY = 1;
    private int headerCount = 0;//header数目
    private Context mContext;
    private FriendListViewHolder.OnItemClickListener mOnItemClickListener;
    private static final int REFRESH_CONVERSATION_LIST = 0x3003;
    private List<ConversationItem> conversationItemList;
//    private Handler mUiHandler = new Handler() {
//        @Override
//        public void handleMessage(android.os.Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case REFRESH_CONVERSATION_LIST:
//                    Log.e("mUiHandler的线程", Thread.currentThread().getName());
//                    notifyDataSetChanged();
//            }
//        }
//    };

    public ConversationListAdapter(Context context) {
        this.mContext = context;
    }

    public ConversationListAdapter(Context context, List<ConversationItem> conversationItemList) {
        this.mContext = context;
        this.conversationItemList = conversationItemList;
    }

//    public void set2Top(Conversation conv) {
//        for (Conversation conversation : mDatas) {
//            if (conversation.getId().equals(conv.getId())) {
//                mDatas.remove(conversation);
//                mDatas.add(0, conv);
//                mUiHandler.removeMessages(REFRESH_CONVERSATION_LIST);
//                mUiHandler.sendEmptyMessageDelayed(REFRESH_CONVERSATION_LIST, 200);
//                return;
//            }
//        }
//        mDatas.add(0, conv);
//        mUiHandler.removeMessages(REFRESH_CONVERSATION_LIST);
//        mUiHandler.sendEmptyMessageDelayed(REFRESH_CONVERSATION_LIST, 200);
//    }

    @Override
    public int getItemViewType(int position) {
        if (headerCount != 0) {
            if (position == 0) {
                return TYPE_HEADER;
            }
        }
        return TYPE_BODY;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case TYPE_HEADER:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_header, parent, false);
                return new HeaderViewHolder(view);
            case TYPE_BODY:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_friends, parent, false);
                return new FriendListViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof FriendListViewHolder) {
            ConversationItem conversationItem = conversationItemList.get(position - headerCount);
            //  设置item 点击事件
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(conversationItemList.get(position));
                    }
                }
            });
            Glide.with(mContext)
                    .load(conversationItem.getAvatar())
                    .error(R.drawable.default_avator)
                    .into(((FriendListViewHolder) holder).avatarImg);
            ((FriendListViewHolder) holder).nameText.setText(conversationItem.getNickName());
            ((FriendListViewHolder) holder).tvLastMsg.setText(conversationItem.getLastMessage());
        }
//            final Conversation convItem = mDatas.get(position );
//            if (convItem.getType().equals(ConversationType.single)) {
//                UserInfo feedBack = (UserInfo) convItem.getTargetInfo();
//                if (feedBack.getUserName().equals("feedback_Android")) {
//                    JMessageClient.deleteSingleConversation("feedback_Android", feedBack.getAppKey());
//                    mDatas.remove(position - 1);
//                    notifyDataSetChanged();
//                }
//            }
//            Message lastMsg = convItem.getLatestMessage();
//            if (lastMsg != null) {
//                String content = ((TextContent) lastMsg.getContent()).getText();
//                ((FriendListViewHolder) holder).tvLastMsg.setText(content);
//            }
//            ((FriendListViewHolder) holder).nameText.setText(convItem.getTitle());
//            final UserInfo userInfo = (UserInfo) convItem.getTargetInfo();
//            if (userInfo != null) {


//                userInfo.getAvatarBitmap(new GetAvatarBitmapCallback() {
//                    @Override
//                    public void gotResult(int i, String s, Bitmap bitmap) {
//            if (i == 0) {
//                ((FriendListViewHolder) holder).avatarImg.setImageBitmap(bitmap);
//            } else {
//                ((FriendListViewHolder) holder).avatarImg.setImageResource(R.drawable.default_avator);
//            }
//        }
//                });
//            } else {
//                ((FriendListViewHolder) holder).avatarImg.setImageResource(R.drawable.default_avator);
//            }
//            if (convItem.getUnReadMsgCnt() > 0) {
//                ((FriendListViewHolder) holder).converNum.setVisibility(View.VISIBLE);
//                if (convItem.getUnReadMsgCnt() < 100) {
//                    ((FriendListViewHolder) holder).converNum.setText(String.valueOf(convItem.getUnReadMsgCnt()));
//                } else {
//                    ((FriendListViewHolder) holder).converNum.setText("99+");
//                }
//            }
    }

    @Override
    public int getItemCount() {
//        return mDatas.size() + headerCount;
        return conversationItemList.size();
    }

    public void setOnItemClickListener(FriendListViewHolder.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

//    public void addConversations(List<Conversation> conversations) {
//        mDatas.clear();
//        mDatas.addAll(conversations);
//        notifyDataSetChanged();
//    }

    public void refresh(List<ConversationItem> conversationItemList) {
        this.conversationItemList = conversationItemList;
        notifyDataSetChanged();
    }

    public static class FriendListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name_friend)
        TextView nameText;
        @BindView(R.id.avatar_friend)
        CircleImageView avatarImg;
        TextView tvLastMsg, converNum;
        private View itemView;

        public FriendListViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            tvLastMsg = itemView.findViewById(R.id.text_history_last);
            converNum = itemView.findViewById(R.id.conver_num);
            ButterKnife.bind(this, itemView);
        }

        public interface OnItemClickListener {

            void onItemClick(ConversationItem conversationItem);
        }
    }

//    public List<Conversation> getmDatas() {
//        return mDatas;
//    }
}

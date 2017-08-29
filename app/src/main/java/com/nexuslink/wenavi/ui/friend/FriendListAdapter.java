package com.nexuslink.wenavi.ui.friend;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nexuslink.wenavi.callback.OnItemClickListener;
import com.nexuslink.wenavi.R;
import com.nexuslink.wenavi.ui.main.HeaderViewHolder;

import java.util.ArrayList;
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


class FriendListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_BODY = 1;
    private int headerCount = 1;//header数目
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    private List<Conversation> mDatas = new ArrayList<>();

    public FriendListAdapter(Context context,List<Conversation> mDatas) {
        this.mContext = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FriendListViewHolder) {
            Conversation convItem = mDatas.get(position-1);
            if(convItem.getType().equals(ConversationType.single)){
                UserInfo feedBack = (UserInfo) convItem.getTargetInfo();
                if (feedBack.getUserName().equals("feedback_Android")) {
                    JMessageClient.deleteSingleConversation("feedback_Android", feedBack.getAppKey());
                    mDatas.remove(position-1);
                    notifyDataSetChanged();
                }
            }
            Message lastMsg = convItem.getLatestMessage();
            if(lastMsg != null){
                String content = ((TextContent)lastMsg.getContent()).getText();
                ((FriendListViewHolder) holder).tvLastMsg.setText(content);
            }
            ((FriendListViewHolder) holder).nameText.setText(convItem.getTitle());
            UserInfo userInfo = (UserInfo) convItem.getTargetInfo();
            if(userInfo!=null){
                userInfo.getAvatarBitmap(new GetAvatarBitmapCallback() {
                    @Override
                    public void gotResult(int i, String s, Bitmap bitmap) {
                        if(i ==0){
                            ((FriendListViewHolder) holder).avatarImg.setImageBitmap(bitmap);
                        }else {
                            ((FriendListViewHolder) holder).avatarImg.setImageResource(R.drawable.t2);
                        }
                    }
                });
            }else {
                ((FriendListViewHolder) holder).avatarImg.setImageResource(R.drawable.t2);
            }
            if(convItem.getUnReadMsgCnt()>0){
                ((FriendListViewHolder) holder).converNum.setVisibility(View.VISIBLE);
                if (convItem.getUnReadMsgCnt() < 100) {
                    ((FriendListViewHolder) holder).converNum.setText(String.valueOf(convItem.getUnReadMsgCnt()));
                } else {
                    ((FriendListViewHolder) holder).converNum.setText("99+");
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size() + headerCount;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public class FriendListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name_friend)
        TextView nameText;
        @BindView(R.id.avatar_friend)
        CircleImageView avatarImg;
        TextView tvLastMsg,converNum;
        private View itemView;

        public FriendListViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            tvLastMsg = itemView.findViewById(R.id.text_history_last);
            converNum = itemView.findViewById(R.id.conver_num);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick();
                }
            });
        }
    }
}

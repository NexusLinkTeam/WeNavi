package com.nexuslink.wenavi;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by alphrye on 17-8-7.
 */

class FriendListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_BODY = 1;
    private int headerCount = 1;//header数目
    private int[] avatars;
    private String[] names;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public FriendListAdapter(Context context, int[] avatars, String[] names) {
        this.mContext = context;
        this.avatars = avatars;
        this.names = names;
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FriendListViewHolder) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick();
                    }
                }
            });
            ((FriendListViewHolder) holder).nameText.setText(names[position - headerCount]);
            ((FriendListViewHolder) holder).avatarImg.setImageResource(avatars[position - headerCount]);
            ((FriendListViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick();
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return names.length + headerCount;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public class FriendListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name_friend)
        TextView nameText;
        @BindView(R.id.avatar_friend)
        CircleImageView avatarImg;

        private View itemView;

        public FriendListViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
}

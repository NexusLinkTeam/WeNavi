package com.nexuslink.wenavi;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by alphrye on 17-8-7.
 */

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.FriendListViewHolder> {
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    public FriendListAdapter(Context context) {
        this.mContext =context;
    }

    @Override
    public FriendListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_friends,parent,false);
        return new FriendListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FriendListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 15;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public class FriendListViewHolder extends RecyclerView.ViewHolder {
        public FriendListViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick();
                }
            });
        }
    }
    public interface OnItemClickListener{
        void onItemClick();
    }
}

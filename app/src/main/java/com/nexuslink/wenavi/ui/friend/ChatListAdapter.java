package com.nexuslink.wenavi.ui.friend;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nexuslink.wenavi.R;
import com.nexuslink.wenavi.ui.main.HeaderViewHolder;

/**
 * Created by alphrye on 17-8-7.
 */

public class ChatListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_BODY = 1;
    private String userName;
    private Context mContext;

    public ChatListAdapter(Context context,String userName) {
        this.mContext = context;
        this.userName = userName;
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
                view = LayoutInflater.from(mContext).inflate(R.layout.item_chat, parent, false);
                return new ChatListAdapter.ChatListViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ChatListViewHolder extends RecyclerView.ViewHolder {
        public ChatListViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
        }
    }
}

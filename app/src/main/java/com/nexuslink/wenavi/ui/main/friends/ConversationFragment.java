package com.nexuslink.wenavi.ui.main.friends;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nexuslink.wenavi.R;
import com.nexuslink.wenavi.callback.BaseOnFragmentInteractionListener;
import com.nexuslink.wenavi.common.Constant;
import com.nexuslink.wenavi.model.ConversationItem;
import com.nexuslink.wenavi.ui.adapter.ConversationListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 会话Fragment
 */
public class ConversationFragment extends Fragment {

    @BindView(R.id.list_friends)
    RecyclerView recyclerView;

    private ConversationListAdapter friendListAdapter;

    private static final String ARG_PARAM1 = "param1";

    private static final String ARG_PARAM2 = "param2";

    private String mParam1;

    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public static ConversationFragment newInstance(String param1, String param2) {
        ConversationFragment fragment = new ConversationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        ButterKnife.bind(this,view);
        initView();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     *  初始化
     */
    private void initView() {
        List<ConversationItem> conversationItemList = mListener.getConversationItemList();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Log.d("Conversation", "initView: 获得conversationItem大小 " + conversationItemList.size());
        friendListAdapter = new ConversationListAdapter(getContext(), conversationItemList);
        recyclerView.setAdapter(friendListAdapter);
        friendListAdapter.setOnItemClickListener(new ConversationListAdapter.FriendListViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(ConversationItem conversationItem) {
                Bundle bundle = new Bundle();
                // 获取头像，账号，昵称
                bundle.putString(Constant.USERNAME, conversationItem.getUserName());
                bundle.putString(Constant.NICKNAME,conversationItem.getNickName());
                bundle.putString(Constant.AVATAR,conversationItem.getAvatar());
                mListener.openActivityByActivity(ChatActivity.class, bundle);
            }
        });
    }

    /**
     * 跟新会话列表
     */
    public void updateConversation(List<ConversationItem> conversationItems) {
        friendListAdapter.refresh(conversationItems);
    }

    public interface OnFragmentInteractionListener extends BaseOnFragmentInteractionListener {
        List<ConversationItem> getConversationItemList();
    }
}

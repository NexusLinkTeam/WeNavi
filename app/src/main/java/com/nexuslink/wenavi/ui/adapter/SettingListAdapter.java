package com.nexuslink.wenavi.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nexuslink.wenavi.R;
import com.nexuslink.wenavi.model.SettingItem;

import java.util.List;

/**
 * Created by 18064 on 2018/1/31.
 */

public class SettingListAdapter extends BaseAdapter {

    private Context context;

    private List<SettingItem> settingItems;

    public SettingListAdapter(Context context, List<SettingItem> settingItems) {
        this.context = context;
        this.settingItems = settingItems;
    }

    @Override
    public int getCount() {
        return settingItems.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SettingItem settingItem = settingItems.get(position);
        @SuppressLint("ViewHolder") View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_setting,null);
        ImageView imageView = view.findViewById(R.id.icon);
        TextView textView = view.findViewById(R.id.title);
        imageView.setImageResource(settingItem.getIcon());
        textView.setText(settingItem.getSettingText());
        return view;
    }
}

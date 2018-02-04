package com.nexuslink.wenavi.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nexuslink.wenavi.R;
import com.nexuslink.wenavi.ui.weight.CircleCheckView;
import com.nexuslink.wenavi.ui.weight.RectButton;
import com.nexuslink.wenavi.util.ThemeManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 18064 on 2018/1/13.
 */

public class ChangerAdapter extends RecyclerView.Adapter<ChangerAdapter.ChangerViewHolder> {

    private Context mContext;

    private List<ThemeManager.Theme> themes;

    private OnItemClickListener onItemClickListener;

    private int position;

    public ChangerAdapter(Context context, List<ThemeManager.Theme> themes) {
        this.mContext = context;
        this.themes = themes;
        this.position = ThemeManager.getSavedPosition(context);
    }

    @Override
    public ChangerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_theme_choice,parent,false);
        return new ChangerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ChangerViewHolder holder, int position) {
        int color = themes.get(position).getmColor();
        int colorText = color;
        holder.themeName.setText(themes.get(position).getmData());
        holder.circleCheckView.setColor(color);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onItemClickListener) {
                    //当前点击的位置
                    int pos = holder.getAdapterPosition();
                    onItemClickListener.onItemClick(pos, themes.get(pos).getmStyle());
                }
            }
        });

        if (this.position != position){
            holder.rectButton.setText("使用");
            holder.circleCheckView.setChecked(false);
            holder.rectButton.setColor(R.color.colorGray);
        } else {
            holder.rectButton.setText("使用中");
            holder.circleCheckView.setChecked(true);
            // TODO: 2018/1/31 这里有点耦合
            if (color == R.color.colorPrimary_Dark) {
                color = R.color.colorPrimary_Default;
                colorText = R.color.colorGray;
            }
            holder.rectButton.setColor(color);
        }
        holder.themeName.setTextColor(mContext.getResources().getColor(colorText));
    }

    @Override
    public int getItemCount() {
        return themes.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

//    public void notify(int position) {
//        this.position = position;
//        notifyDataSetChanged();
//    }

    public class ChangerViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.theme_name)
        TextView themeName;

        @BindView(R.id.circle_checked_view)
        CircleCheckView circleCheckView;

        @BindView(R.id.rect_button)
        RectButton rectButton;

        public ChangerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int pos, int themeId);
    }
}

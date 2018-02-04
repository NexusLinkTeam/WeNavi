package com.nexuslink.wenavi.ui.setting;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


import com.nexuslink.wenavi.R;
import com.nexuslink.wenavi.base.BaseActivity;
import com.nexuslink.wenavi.base.BaseApp;
import com.nexuslink.wenavi.common.Constant;
import com.nexuslink.wenavi.ui.adapter.ChangerAdapter;
import com.nexuslink.wenavi.ui.decoration.SimpleDecoration;
import com.nexuslink.wenavi.util.SPUtil;
import com.nexuslink.wenavi.util.ThemeManager;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ThemeChangeActivity extends BaseActivity implements ChangerAdapter.OnItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recycler_view_home)
    RecyclerView mRecyclerView;

    private ChangerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_change);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        toolbar.setTitle(R.string.theme);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        adapter = new ChangerAdapter(this, ThemeManager.themes);
        adapter.setOnItemClickListener(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addItemDecoration(new SimpleDecoration(this));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int pos, int themeId) {
        ThemeManager.save(this, pos, themeId);
//        SharedPreferences sharedPreferences = getSharedPreferences("ThemeChanger",MODE_PRIVATE);
//        sharedPreferences.edit().putInt("theme",themeId).putInt("position",pos).apply();
//        adapter.notify(pos);
        recreate();
    }
}

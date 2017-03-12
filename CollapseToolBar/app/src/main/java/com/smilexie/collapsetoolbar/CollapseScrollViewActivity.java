package com.smilexie.collapsetoolbar;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.smilexie.collapsetoolbar.databinding.CollaplseScrollviewActivityBinding;


/**
 * 用fitsSystemWindows实现沉浸式状态栏，状态栏只能设置成相应的色值，headview不可延伸到状态栏
 * Created by SmileXie on 2017/3/12.
 */

public class CollapseScrollViewActivity extends AppCompatActivity {
    private CollaplseScrollviewActivityBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.collaplse_scrollview_activity);

        setSupportActionBar(binding.toolbar);
        binding.toolbar.setTitle(getString(R.string.collapse_with_fit_system_window));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回键
        //设置返回键的点击事件：
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();;
            }
        });
    }
}

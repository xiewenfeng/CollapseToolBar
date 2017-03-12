package com.smilexie.collapsetoolbar;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.smilexie.collapsetoolbar.databinding.CollapseWithoutFitsysemBinding;

/**
 * 不使用fitsSystemWindows，将状态栏设置成透明状，将headview延伸到状态栏
 * Created by SmileXie on 2017/3/12.
 */

public class CollapseWithOutFitSysWinActivitity extends BaseActivity {
    private CollapseWithoutFitsysemBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.collapse_without_fitsysem);

        setSupportActionBar(binding.toolbar);
        binding.toolbar.setTitle(getString(R.string.collapse_transparent_statusbar));
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

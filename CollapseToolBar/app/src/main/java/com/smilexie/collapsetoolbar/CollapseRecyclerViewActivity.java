package com.smilexie.collapsetoolbar;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.smilexie.collapsetoolbar.R;
import com.smilexie.collapsetoolbar.baseadapter.BaseRecyclerViewAdapter;
import com.smilexie.collapsetoolbar.databinding.ActivityCollapseRecyclerviewBinding;
import com.smilexie.collapsetoolbar.databinding.CollapseRecyclerviewItemBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * 带有recyclerview的沉浸式状态栏
 * Created by SmileXie on 2017/3/12.
 */

public class CollapseRecyclerViewActivity extends BaseActivity {
    private ActivityCollapseRecyclerviewBinding binding;
    private List<String> datas;
    private BaseRecyclerViewAdapter<String, CollapseRecyclerviewItemBinding> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_collapse_recyclerview);
        initRecyclerView();

        setSupportActionBar(binding.toolbar);
        binding.toolbar.setTitle(getString(R.string.collapse_recyclerView));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回键
        //设置返回键的点击事件：
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();;
            }
        });
    }

    private void initRecyclerView() {
        datas = new ArrayList<>();
        for (int i = 0; i< 10; i++) {
            datas.add( "第" + i + "项" );
        }
        adapter = new BaseRecyclerViewAdapter<String, CollapseRecyclerviewItemBinding>(R.layout.collapse_recyclerview_item) {
            @Override
            public void onNewBindViewHolder(String object, int position, CollapseRecyclerviewItemBinding binding) {
                binding.contentText.setText(object);
            }
        };
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
        adapter.addAll(datas);
    }
}

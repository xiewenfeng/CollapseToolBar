package com.smilexie.collapsetoolbar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


/**
 * 主界面
 * Created by SmileXie on 2017/3/12.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 将状态栏设为透明态，根据不同的sdk，设置不同高度的标题高度，将标题栏延伸到状态栏
     */
    public void transParentStatusBar(View view) {
        startActivity(new Intent(this, CollapseWithOutFitSysWinActivitity.class));
    }

    /**
     * 列表式沉浸式状态栏实现
     */
    public void collapseRecyclerView(View view) {
        startActivity(new Intent(this, CollapseRecyclerViewActivity.class));
    }

    /**
     * 采用fitsSystemWindows实现沉浸式状态栏
     */
    public void withFitSystem(View view) {
        startActivity(new Intent(this, CollapseScrollViewActivity.class));

    }


}

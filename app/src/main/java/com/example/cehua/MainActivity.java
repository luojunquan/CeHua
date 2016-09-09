package com.example.cehua;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Toolbar mTbHeadBar;
    private LinearLayout mLlMenu;
    private ListView mLvMenu;
    private ImageView mIvContent;
    private DrawerLayout mMyDrawable;

    ActionBarDrawerToggle mToggle;

    private List<String> lvMenuList = new ArrayList<String>() {{
        add("angry");
        add("happy");
        add("sad");
        add("embarrassed");
    }};

    private List<Integer> imageList = new ArrayList<Integer>() {{
        add(R.drawable.angry);
        add(R.drawable.happy);
        add(R.drawable.sad);
        add(R.drawable.embarrassed);
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTbHeadBar =  (Toolbar) findViewById(R.id.tbHeadBar);
        mLvMenu = (ListView) findViewById(R.id.lvMenu);
        mLlMenu = (LinearLayout) findViewById(R.id.llMenu);
        mIvContent = (ImageView) findViewById(R.id.ivContent);
        mMyDrawable = (DrawerLayout) findViewById(R.id.dlMenu);
        /*初始化Toolbar与DrawableLayout*/
        initToolBarAndDrawableLayout();

        mLvMenu.setAdapter(new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, lvMenuList));
        mLvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mIvContent.setImageResource(imageList.get(position));
                mMyDrawable.closeDrawers();/*收起抽屉*/
            }
        });
    }

    private void initToolBarAndDrawableLayout() {
        setSupportActionBar(mTbHeadBar);
        /*以下俩方法设置返回键可用*/
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*设置标题文字不可显示*/
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mToggle = new ActionBarDrawerToggle(this, mMyDrawable, mTbHeadBar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        /*mMyDrawable.setDrawerListener(mToggle);不推荐*/
        mMyDrawable.addDrawerListener(mToggle);
        mToggle.syncState();/*同步状态*/
    }
}
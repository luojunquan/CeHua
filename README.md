
# 打造原生侧滑菜单

> 第一步：你需要使用ToolBar与DrawableLayout两个比较新的控件

首先要写三个xml布局文件，我这里的布局文件是使用了include标签嵌入的，代码如下:

headbar_toolbar.xml

	<?xml version="1.0" encoding="utf-8"?>
	<android.support.v7.widget.Toolbar
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/tbHeadBar"
    android:layout_width="match_parent"
    android:layout_height="50dp"
    android:background="#EA417A">
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:text="emotion"
        android:textColor="#FFFFFF"
        android:textSize="16sp" />

	</android.support.v7.widget.Toolbar>

my_drawablelayout.xml

	<?xml version="1.0" encoding="utf-8"?>
	<android.support.v4.widget.DrawerLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/dlMenu"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <LinearLayout
        android:id="@+id/llContent"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="#FFFFFF"
        android:gravity="center"
        android:orientation="vertical">

        <ImageView
            android:id="@+id/ivContent"
            android:layout_width="100dp"
            android:layout_height="100dp"
            android:src="@drawable/sad" />

    </LinearLayout>

    <!--android:layout_gravity="start"属性使这部分作为侧滑部分-->
    <!--一定要放在下面！！！关于控件的层次性如果不知道的同学去百度！哦不去谷歌-->
    <LinearLayout
        android:id="@+id/llMenu"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_gravity="start"
        android:background="#FFFFFF"
        android:orientation="vertical">

        <!--用于设置菜单项-->
        <ListView
            android:id="@+id/lvMenu"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:divider="@null" />

    </LinearLayout>

	</android.support.v4.widget.DrawerLayout>

main_activity.xml

	<?xml version="1.0" encoding="utf-8"?>
	<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <!--头部-->
    <include layout="@layout/headbar_toolbar" />

    <!--主布局-->
    <include layout="@layout/my_drawablelayout" />

	</LinearLayout>
	
> 如何应用在java文件中【一个文件搞定】

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
                //Toast.makeText(MainActivity.this, R.string.open, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                //Toast.makeText(MainActivity.this, R.string.close, Toast.LENGTH_SHORT).show();
            }
        };
        /*mMyDrawable.setDrawerListener(mToggle);不推荐*/
        mMyDrawable.addDrawerListener(mToggle);
        mToggle.syncState();/*同步状态*/
  	  }
	}

style【关于返回键的颜色样式等在style文件中修改】

	<resources>

    <style name="AppTheme" parent="Theme.AppCompat.Light.NoActionBar">
        <item name="drawerArrowStyle">@style/AppTheme.DrawerArrowToggle</item>
    </style>

    <style name="AppTheme.DrawerArrowToggle" parent="Base.Widget.AppCompat.DrawerArrowToggle">
        <item name="color">@android:color/white</item>
    </style>

	</resources>
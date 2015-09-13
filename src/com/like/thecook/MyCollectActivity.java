package com.like.thecook;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.dcjd.cook.R;
import com.like.adapter.IndexPagerAdapter;
import com.like.customeview.BarViewPager;
import com.like.customeview.PagerSlidingTabStrip;

@SuppressLint("NewApi")
public class MyCollectActivity extends FragmentActivity{

	private BarViewPager mPager;
	private PagerSlidingTabStrip mTab;
	private IndexPagerAdapter mIndexPagerAdapter;
	
	private int mBackClickTimes = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_collect);

		mPager = (BarViewPager) findViewById(R.id.main_pager);
		mTab = (PagerSlidingTabStrip) findViewById(R.id.main_tab);

		// 初始化Tab
		initTab();
		// 初始化全局参数
		mBackClickTimes = 0;
		mTab.setShouldExpand(true);
		mTab.setFillViewport(true);
		mTab.setIndicatorColorResource(R.color.red);
		mTab.setTextColorStateResource(R.color.tab_text_color);
		mTab.setDividerColor(0x00000000);
		mTab.setTextSize(30);
		mTab.setViewPager(mPager);
	}

	/**
	 * 初始化tab
	 */
	private void initTab() {
		mIndexPagerAdapter = new IndexPagerAdapter(getSupportFragmentManager());
		mPager.setAdapter(mIndexPagerAdapter);
	}

	public void back(View v) {
		this.finish();
	}


}

package com.like.thecook;

import java.lang.reflect.Type;
import java.util.List;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.dcjd.cook.R;
import com.google.gson.reflect.TypeToken;
import com.like.adapter.MSAdapter;
import com.like.adapter.MSPagerAdapter;
import com.like.customeview.HackyViewPager;
import com.like.customeview.pulldown.PullToRefreshBase;
import com.like.customeview.pulldown.PullToRefreshBase.Mode;
import com.like.customeview.pulldown.PullToRefreshBase.OnRefreshListener2;
import com.like.customeview.pulldown.PullToRefreshListView;
import com.like.entity.ListResult;
import com.like.entity.Meishi;
import com.like.network.GsonUtil;

public class MeiShiActivity extends BaseActivity {
	
	private int mCurrentPage = 0;
	private MSAdapter mAdapter;
	private HackyViewPager mPager;
	private ViewGroup mDotContainer; 
	
	private PullToRefreshListView mMsList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.meishi);
		
		mMsList = (PullToRefreshListView) findViewById(R.id.ms_list);
		mMsList.setMode(Mode.BOTH);
		mDotContainer = (ViewGroup) findViewById(R.id.dot_container);
		mPager = (HackyViewPager) findViewById(R.id.viewpager);
		mPager.setAdapter(new MSPagerAdapter(mContext));
		mDotContainer.getChildAt(1).setSelected(true);
		mPager.addOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				for(int i=0; i<mDotContainer.getChildCount()-1; i++) {
					mDotContainer.getChildAt(i + 1).setSelected(false);
				}
				mDotContainer.getChildAt(position + 1).setSelected(true);
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) { }
			@Override
			public void onPageScrollStateChanged(int position) { }
		});
		
		mMsList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				position -= 1;
				Intent intent = new Intent(mContext, TaoCanDetailActivity.class);
				Meishi ms = (Meishi) mAdapter.getItem(position);
				intent.putExtra("msId", ms.meishiId);
				startActivity(intent);
			}
		});
		mMsList.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				updateMSList();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				mCurrentPage++;
				updateMSList();
			}
		});
		updateMSList();
	}
	
	public void back(View v) {
		this.finish();
	}
	
	private void updateMSList() {
		mDataFetcher.fetchMSList(mCurrentPage+"", new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				System.out.println(response);
				Type type = new TypeToken<ListResult<Meishi>>(){}.getType();
				ListResult<Meishi> meishis = GsonUtil.gson.fromJson(response.toString(), type);
				List<Meishi> mss = meishis.list;
				if(mAdapter == null) {
					mAdapter = new MSAdapter(mContext, mss);
					mMsList.setAdapter(mAdapter);
				} else {
					mAdapter.updateList(mss);
				}
				if(mMsList.isRefreshing())
					mMsList.onRefreshComplete();
			}
		} , new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(mContext, "请检查网络", Toast.LENGTH_SHORT).show();
				if(mMsList.isRefreshing())
					mMsList.onRefreshComplete();
			}
		});
	}
	
	public void jumpToCar(View v) {
		Intent intent = new Intent(mContext, IndexActivity.class);
		intent.putExtra("tab_index", 2);
		startActivity(intent);
	}

}

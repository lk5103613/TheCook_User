package com.like.thecook;

import java.lang.reflect.Type;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.volley.Response.Listener;
import com.dcjd.cook.R;
import com.google.gson.reflect.TypeToken;
import com.like.adapter.ZXAdapter;
import com.like.customeview.pulldown.PullToRefreshBase;
import com.like.customeview.pulldown.PullToRefreshBase.Mode;
import com.like.customeview.pulldown.PullToRefreshBase.OnRefreshListener2;
import com.like.customeview.pulldown.PullToRefreshListView;
import com.like.entity.ListResult;
import com.like.entity.ZXEntity;
import com.like.network.GsonUtil;

public class ZxActivity extends BaseActivity {
	
	private PullToRefreshListView mList;
	private ZXAdapter mAdapter;
	private int mCurrentPage = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zx);
		mList = (PullToRefreshListView) findViewById(R.id.zx_list);
		mList.setMode(Mode.BOTH);
		mList.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				updateList();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				mCurrentPage++;
				updateList();
			}
		});
		mList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				Intent intent = new Intent(mContext, ZxInfoActivity.class);
				startActivity(intent);
			}
		});
		updateList();
	}
	
	public void back(View v) {
		this.finish();
	}
	
	private void updateList() {
		mDataFetcher.fetchZXList(mCurrentPage+"", new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				Type type = new TypeToken<ListResult<ZXEntity>>(){}.getType();
				ListResult<ZXEntity> zxs = GsonUtil.gson.fromJson(response.toString(), type);
				if(mAdapter == null) {
					mAdapter = new ZXAdapter(mContext, zxs.list);
					mList.setAdapter(mAdapter);
				} else {
					mAdapter.updateList(zxs.list);
				}
				if(mList.isRefreshing())
					mList.onRefreshComplete();
			}
		}, mErrorListener);
	}

}

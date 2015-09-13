package com.like.thecook;

import java.lang.reflect.Type;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dcjd.cook.R;
import com.google.gson.reflect.TypeToken;
import com.like.adapter.MenuListAdapter;
import com.like.customeview.pulldown.PullToRefreshBase;
import com.like.customeview.pulldown.PullToRefreshListView;
import com.like.entity.ListResult;
import com.like.entity.MenuEntity;
import com.like.entity.TaoCanEntity;
import com.like.network.GsonUtil;

public class TaoCanInfoActivity extends BaseActivity {

	private PullToRefreshListView mListView;
	private MenuListAdapter mAdapter;
	private Context mContext;
	private List<TaoCanEntity> mTaoCanList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.taocan_info);

		mContext = this;
		mListView = (PullToRefreshListView) findViewById(R.id.taocan_listview);
		mListView.setMode(PullToRefreshBase.Mode.BOTH);
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				position = position - 1;
				MenuEntity menu = mAdapter.getItem(position);
				mContext.startActivity(new Intent(mContext, TaoCanDetailActivity.class).putExtra("pacId", menu.packid));
			}
		});
		mListView
				.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						if (mListView.isShownHeader()) {
							updateMenu();
						} else if (mListView.isShownFooter()) {
							updateMenu();
						}
					}
				});
		updateMenu();
	}

	private void updateMenu() {
		mDataFetcher.fetchMenu(0, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				System.out.println(response);
				Type type = new TypeToken<ListResult<MenuEntity>>() {}.getType();
				ListResult<MenuEntity> menuList = GsonUtil.gson.fromJson(
						response.toString(), type);
				if (mAdapter == null) {
					mAdapter = new MenuListAdapter(mContext,
							mContext, menuList.resultList);
					mListView.setAdapter(mAdapter);
				} else {
					mAdapter.setList(menuList.resultList);
					mAdapter.notifyDataSetChanged();
				}
				if (mListView.isRefreshing())
					mListView.onRefreshComplete();
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(mContext, "请检查网络", Toast.LENGTH_LONG).show();
			}
		});
	}
	
	public void back(View v) {
		this.finish();
	}

}

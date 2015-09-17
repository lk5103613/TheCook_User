package com.like.thecook;

import java.lang.reflect.Type;
import java.util.List;

import org.json.JSONArray;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.volley.Response.Listener;
import com.dcjd.cook.R;
import com.google.gson.reflect.TypeToken;
import com.like.adapter.AddressListAdapter;
import com.like.customeview.pulldown.PullToRefreshBase;
import com.like.customeview.pulldown.PullToRefreshBase.OnRefreshListener;
import com.like.customeview.pulldown.PullToRefreshListView;
import com.like.entity.Address;
import com.like.network.GsonUtil;

public class MyAddressActivity extends BaseActivity {
	
	private AddressListAdapter mAdapter;
	
	private PullToRefreshListView mList;
	private String mUID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_address);
		
		mUID = mLoginSharef.getString(UID, "");
		mList = (PullToRefreshListView) findViewById(R.id.add_lst);
		mList.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				updateList();
			}
		});
		mList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				System.out.println("on item click");
				Address address = (Address) mAdapter.getItem(position);
				String json = GsonUtil.gson.toJson(address);
				Intent intent = new Intent(mContext, EditAddressActivity.class);
				intent.putExtra("address", json);
				startActivity(intent);
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		updateList();
	}

	public void back(View v) {
		this.finish();
	}
	
	public void editAddress(View v) {
		Intent intent = new Intent(mContext, EditAddressActivity.class);
		startActivity(intent);
	}
	
	public void addAddress(View v) {
		Intent intent = new Intent(mContext, EditAddressActivity.class);
		startActivity(intent);
	}
	
	private void updateList() {
		showLoading(true);
		mDataFetcher.fetchAddress(mUID, new Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray response) {
				showLoading(false);
				Type type = new TypeToken<List<Address>>(){}.getType();
				List<Address> addresses = GsonUtil.gson.fromJson(response.toString(), type);
				if(mAdapter == null) {
					mAdapter = new AddressListAdapter(mContext, addresses);
					mList.setAdapter(mAdapter);
				} else {
					mAdapter.updateList(addresses);
				}
				if(mList.isRefreshing())
					mList.onRefreshComplete();
			}
		}, mErrorListener);
	}
	
}

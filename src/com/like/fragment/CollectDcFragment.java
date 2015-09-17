package com.like.fragment;

import java.lang.reflect.Type;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dcjd.cook.R;
import com.google.gson.reflect.TypeToken;
import com.like.adapter.DCAdapter;
import com.like.customeview.pulldown.PullToRefreshBase;
import com.like.customeview.pulldown.PullToRefreshListView;
import com.like.entity.DCEntity;
import com.like.entity.ListResult;
import com.like.network.DataFetcher;
import com.like.network.GsonUtil;

public class CollectDcFragment extends BaseFragment {

	private Context mContext;
	private Context mApplicationContext;
	private List<DCEntity> mDCEntities;
	private PullToRefreshListView mDCList;
	private DCAdapter mAdapter;
	private DataFetcher mDataFetcher;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		mContext = getActivity();
		mDataFetcher = DataFetcher.getInstance(mContext);
		View layout = inflater.inflate(R.layout.chushi_fragment, container,
				false);
		mDCList = (PullToRefreshListView) layout.findViewById(R.id.dc_list);
		mDCList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				updateDC();
			}
		});
		updateDC();
		return layout;
	}

	private void updateDC() {
		showLoading(true);
		mDataFetcher.fetchCollectedDC(mUID, 
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						showLoading(false);
						Type type = new TypeToken<ListResult<DCEntity>>(){}.getType();
						ListResult<DCEntity> dcList = GsonUtil.gson.fromJson(
								response.toString(), type);
						mAdapter = new DCAdapter(mContext,
								mApplicationContext, dcList.list);
						mDCList.setAdapter(mAdapter);
						if (mDCList.isRefreshing())
							mDCList.onRefreshComplete();
					}
				}, mErrorListener);

	}

}

package com.like.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.dcjd.cook.R;
import com.like.adapter.OrderListApdapter;
import com.like.customeview.pulldown.PullToRefreshBase;
import com.like.customeview.pulldown.PullToRefreshBase.Mode;
import com.like.customeview.pulldown.PullToRefreshListView;
import com.like.entity.Order;
import com.like.entity.OrderResult;
import com.like.network.DataFetcher;
import com.like.network.GsonUtil;
import com.like.thecook.BaseActivity;

public class OrderFragment extends Fragment {
	private PullToRefreshListView mOrderListView;
	private OrderListApdapter mApdapter;
	private DataFetcher mDataFetcher;
	private Context mContext;
	private SharedPreferences mSharePref;
	
	List<Order> mOrders;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.mContext = getActivity();
		
		View layout = inflater.inflate(R.layout.order_fragment, container, false);
		mOrderListView = (PullToRefreshListView) layout.findViewById(R.id.order_list);
		mOrderListView.setMode(Mode.BOTH);
		mDataFetcher = DataFetcher.getInstance(mContext);
		mSharePref = getActivity().getSharedPreferences(BaseActivity.USER_PROPERTIES, Context.MODE_PRIVATE);
		layout.findViewById(R.id.call_num).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+ BaseActivity.PHONE_NUMBER));  
		        startActivity(intent);  
			}
		});
		mOrderListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				if(mOrderListView.isShownHeader()) {
					refreshData();
				} else if(mOrderListView.isShownFooter()) {
					refreshData();
				}
			}
		});
		refreshData();
		
		mApdapter = new OrderListApdapter(getActivity(), mOrders);
		mOrderListView.setAdapter(mApdapter);
		
		return layout;
	}

	private void refreshData(){
		mOrders = new ArrayList<Order>();
		String uid = mSharePref.getString(BaseActivity.UID, "");
		mDataFetcher.fetchOrder(uid, new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				OrderResult orderResult = GsonUtil.gson.fromJson(response.toString(), OrderResult.class);
				mOrders.clear();
				mOrders.addAll(orderResult.list);
				mApdapter.notifyDataSetChanged();
				mOrderListView.onRefreshComplete();
			}
		},new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				mOrderListView.onRefreshComplete();
			}
			
		});
		
	}
}

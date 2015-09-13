package com.like.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dcjd.cook.R;
import com.like.adapter.OrderListApdapter;
import com.like.entity.Order;

public class AddOrderFragment extends Fragment {
	private ListView mOrderListView;
	private List<Order> mOrders;
	private OrderListApdapter mApdapter;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.order_fragment, container, false);
		mOrderListView = (ListView) layout.findViewById(R.id.order_list);
		initData();
		
		mApdapter = new OrderListApdapter(getActivity(), mOrders);
		mOrderListView.setAdapter(mApdapter);
		
		
		return layout;
	}

	private void initData(){
		mOrders = new ArrayList<Order>();
		
		
	}
}

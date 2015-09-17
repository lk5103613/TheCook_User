package com.like.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.dcjd.cook.R;
import com.like.customeview.OrderStateView;
import com.like.customeview.SelectedLinearLayout;
import com.like.entity.Order;
import com.like.thecook.MyOrderActivity;
import com.like.thecook.OrderInfoActivity;

public class OrderListApdapter extends SimpleAdapter<Order>{
	
	private List<Order> mOrders;
	private Context mContext;

	public OrderListApdapter(Context context, List<Order> orders) {
		super(context, orders);
		this.mOrders = orders;
		this.mContext = context;
	}

	@Override
	public int getItemResourceId() {
		return R.layout.order_item;
	}

	@Override
	public void bindData(int position, View convertView,ViewHolder holder) {
		final Order order = mOrders.get(position);
		TextView number = holder.findView(R.id.number);
		TextView name = holder.findView(R.id.name);
		TextView money = holder.findView(R.id.money);
		TextView orderDetail = holder.findView(R.id.order_detail);
		TextView serviceTime = holder.findView(R.id.service_time);
		OrderStateView orderStateView = holder.findView(R.id.state_list);
		orderStateView.removeAllViews();
		System.out.println("state list" + order.stateList.size());
		orderStateView.setOrderStates(order.stateList);
		
//		List<SelectedLinearLayout> selecteds = new ArrayList<SelectedLinearLayout>();
//		selecteds.add((SelectedLinearLayout)holder.findView(R.id.status_0));
//		selecteds.add((SelectedLinearLayout)holder.findView(R.id.status_1));
//		selecteds.add((SelectedLinearLayout)holder.findView(R.id.status_2));
//		selecteds.add((SelectedLinearLayout)holder.findView(R.id.status_3));
//		selecteds.add((SelectedLinearLayout)holder.findView(R.id.status_4));
//		selecteds.add((SelectedLinearLayout)holder.findView(R.id.status_5));
		
		
//		for (int i = 0; i < 6; i++) {
//			if ( i == order.status) {
//				selecteds.get(i).setSelected(true);
//			} else {
//				selecteds.get(i).setSelected(false);
//			}
//		}
		
		number.setText("订单号: "+order.order_no);
		name.setText("套餐名称: " + "");
		money.setText("￥："+ order.all_money);
		serviceTime.setText("服务时间: " + order.dining_time);

		orderDetail.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent =null;
				if (order.status == 0) {
					intent = new Intent(mContext, MyOrderActivity.class);
				} else {
					intent = new Intent(mContext, OrderInfoActivity.class);
				}
				
				Bundle bundle = new Bundle();
				bundle.putString("orderId", order.orderid+"");
				
				intent.putExtras(bundle);
				mContext.startActivity(intent);
			}
		});
		
	}

}

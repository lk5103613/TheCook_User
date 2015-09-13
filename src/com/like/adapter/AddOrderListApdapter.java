package com.like.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.dcjd.cook.R;
import com.like.entity.OrderEntity;
import com.like.thecook.OrderInfoActivity;

public class AddOrderListApdapter extends SimpleAdapter<OrderEntity>{
	
	private List<OrderEntity> mOrderEntities;
	private Context mContext;
	

	public AddOrderListApdapter(Context context, List<OrderEntity> datas) {
		super(context, datas);
		this.mOrderEntities = datas;
		this.mContext = context;
	}

	@Override
	public int getItemResourceId() {
		return R.layout.add_order_item;
	}

	@Override
	public void bindData(int position, View convertView,ViewHolder holder) {
		OrderEntity orderEntity = mOrderEntities.get(position);
		TextView number = holder.findView(R.id.number);
		TextView name = holder.findView(R.id.name);
		TextView money = holder.findView(R.id.money);
		TextView orderDetail = holder.findView(R.id.order_detail);
		
		number.setText(orderEntity.getNumber());
		name.setText("套餐名称: " + orderEntity.getName());
		money.setText("￥："+ orderEntity.getMoney());
		
		orderDetail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mContext.startActivity(new Intent(mContext,OrderInfoActivity.class));
				
			}
		});
		
	}

}

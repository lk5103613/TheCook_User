package com.like.customeview;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dcjd.cook.R;
import com.like.entity.OrderState;

public class OrderStateView extends LinearLayout{
	private List<OrderState> mOrderStates;
	private LayoutInflater mInflater;
	
	public OrderStateView(Context context) {
		super(context);
		init(context);
	}
	public OrderStateView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	@SuppressLint("NewApi")
	public OrderStateView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}
	
	private void init(Context mContext){
		setOrientation(VERTICAL);
		mInflater = LayoutInflater.from(mContext);
	}
	
	public void setOrderStates(List<OrderState> orderStates) {
		this.mOrderStates = orderStates;
		for (int i = 0; i < orderStates.size(); i++) {
			
			View view = mInflater.inflate(R.layout.order_state_item, this, false);
			TextView name = (TextView) view.findViewById(R.id.state_name);
			TextView time = (TextView) view.findViewById(R.id.state_time);
			
			name.setText(orderStates.get(i).stateName);
			time.setText(orderStates.get(i).addTime);
			addView(view);
		}
		if(getChildCount() != 0)
			getChildAt(0).setSelected(true);
		
	}

}

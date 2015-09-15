package com.like.thecook;

import org.json.JSONObject;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.dcjd.cook.R;
import com.like.entity.OperationResult;
import com.like.entity.Order;
import com.like.network.DataFetcher;
import com.like.network.GsonUtil;

public class MyOrderActivity extends BaseActivity {
	private DataFetcher mDataFetcher;
	private Context mContext;

	private String orderId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_order);
		this.mContext = this;
		((TextView)findViewById(R.id.tel)).getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG );
		mDataFetcher = DataFetcher.getInstance(mContext);
		if (getIntent().getExtras() != null) {
			orderId = getIntent().getExtras().getString("orderId");
		}
		initData();
	}
	
	public void confirmOrder(View v) {
		mDataFetcher.fetchConfirmOrder(orderId, new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				OperationResult operationResult = GsonUtil.gson.fromJson(response.toString(), OperationResult.class);
				if (operationResult.code ==1) {
					Toast.makeText(mContext, "确认订单成功", Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(mContext, "确认订单失败", Toast.LENGTH_LONG).show();
				}
			}
		},new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(mContext, "确认订单失败", Toast.LENGTH_LONG).show();
			}
		});
	}
	
	private void initData(){
		mDataFetcher.fetchOrderDetail(orderId, new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				Order order = GsonUtil.gson.fromJson(response.toString(), Order.class);
			}
		}, mErrorListener);
	}
}

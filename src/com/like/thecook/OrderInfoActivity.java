package com.like.thecook;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.dcjd.cook.R;
import com.like.entity.Order;
import com.like.network.DataFetcher;
import com.like.network.GsonUtil;

public class OrderInfoActivity extends Activity {
	private DataFetcher mDataFetcher;
	private String orderId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_info);
		mDataFetcher = DataFetcher.getInstance(this);
		
		orderId = getIntent().getExtras().getString("orderId");
		System.out.println("order id " + orderId);
		
		((TextView)findViewById(R.id.tel)).getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG );
		
		findViewById(R.id.back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		findViewById(R.id.pl).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(OrderInfoActivity.this,CommentActivity.class));
			}
		});
		
		initData();
	}
	
	private void initData(){
		mDataFetcher.fetchOrderDetail(orderId, new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				Order order = GsonUtil.gson.fromJson(response.toString(), Order.class);
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
			}
		});
	}

}

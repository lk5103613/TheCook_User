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
	
	private TextView mLblKitchenSize;
	private TextView mLblPrice;
	private TextView mLblTotalPrice;
	private TextView mLblPayInfo;
	private TextView mLblDinnerTime;
	private TextView mLblServerCnt;
	private TextView mLblSpecialComment;
	private TextView mLblCJType;
	private TextView mLblPayMethod;

	private String orderId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_order);
		this.mContext = this;
		((TextView)findViewById(R.id.tel)).getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG );
		
		mLblKitchenSize = (TextView) findViewById(R.id.kitchen_size); 
		mLblPrice = (TextView) findViewById(R.id.total_price);
		mLblTotalPrice = (TextView) findViewById(R.id.total_price);
		mLblPayInfo = (TextView) findViewById(R.id.pay_detail_info);
		mLblDinnerTime = (TextView) findViewById(R.id.dinner_time);
		mLblServerCnt = (TextView) findViewById(R.id.server_cnt);
		mLblSpecialComment = (TextView) findViewById(R.id.special_comment);
		mLblCJType = (TextView) findViewById(R.id.canju_type);
		mLblPayMethod = (TextView) findViewById(R.id.pay_method);
		
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
	
	public void callNum(View v) {
		callNumber();
	}
	
	private void initData(){
		mDataFetcher.fetchOrderDetail(orderId, new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				Order order = GsonUtil.gson.fromJson(response.toString(), Order.class);
				mLblKitchenSize.setText("厨房面积："+order.kitchen_size+"平");
				mLblPrice.setText("订单总额：￥"+order.all_money);
				float alreadyPay = Float.valueOf(order.already_pay);
				float notPay = Float.valueOf(order.all_money) - alreadyPay;
				mLblPayInfo.setText("（已付"+alreadyPay+"元，待付"+notPay+"元）");
				mLblDinnerTime.setText("用餐时间：" + order.add_time);
				mLblServerCnt.setText("服务人数：" + order.servicer_usercnt);
				mLblSpecialComment.setText("特殊要求：" + order.special_memo);
				if(order.pay_type.equals("0")) {
					mLblPayMethod.setText("支付方式：支付宝");
				} else {
					mLblPayMethod.setText("支付方式：上门支付");
				}
				if(order.zidai_canju.equals("0")) {
					mLblCJType.setText("需要带餐厨具：中式");
				} else {
					mLblCJType.setText("需要带餐厨具：西式");
				}
			}
		}, mErrorListener);
	}
}

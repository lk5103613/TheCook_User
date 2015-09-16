package com.like.thecook;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response.Listener;
import com.dcjd.cook.R;
import com.like.entity.CommonResult;
import com.like.network.GsonUtil;

public class ForgetPwd2Activity extends BaseActivity {
	
	private EditText mTxtNewPwd;
	private EditText mTxtRepeatPwd;
	private String mPhoneNum;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_pwd2);
		
		mTxtNewPwd = (EditText) findViewById(R.id.txt_new_pwd);
		mTxtRepeatPwd = (EditText) findViewById(R.id.txt_repeat_pwd);
		mPhoneNum = getIntent().getStringExtra("phone_num");
	}
	
	public void changePwd(View v) {
		String newPwd = mTxtNewPwd.getText().toString();
		String repeatPwd = mTxtRepeatPwd.getText().toString();
		if(!newPwd.equals(repeatPwd)) {
			Toast.makeText(mContext, "两次密码不一致", Toast.LENGTH_SHORT).show();
			return;
		}
		mDataFetcher.fetchForgetPwd(mPhoneNum, newPwd, new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				CommonResult result = GsonUtil.gson.fromJson(response.toString(), CommonResult.class);
				if(result.code == 1) {
					Toast.makeText(mContext, "修改密码成功", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(mContext, LoginActivity.class);
					ForgetPwd2Activity.this.finish();
					startActivity(intent);
				} else {
					Toast.makeText(mContext, "修改密码失败", Toast.LENGTH_SHORT).show();
					return;
				}
			}
		}, mErrorListener);
	}
	
	public void toLogin(View v) {
		Intent intent = new Intent(mContext, LoginActivity.class);
		startActivity(intent);
	}

}

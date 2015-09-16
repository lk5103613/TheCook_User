package com.like.thecook;

import org.json.JSONObject;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response.Listener;
import com.dcjd.cook.R;
import com.like.entity.LoginResult;
import com.like.network.GsonUtil;
import com.like.util.ValidateCodeUtil;
import com.like.util.ValidateUtil;

public class ChangePhoneActivity extends BaseActivity {
	
	private EditText mTxtPhone;
	private String mPhoneNum;
	private String mVerifyCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_phone);
		mTxtPhone = (EditText) findViewById(R.id.edit_num);
		mVerifyCode = ValidateCodeUtil.getValidateCode();
	}

	public void back(View v) {
		this.finish();
	}
	
	public void changePhone(View v) {
		String phone = mTxtPhone.getText().toString();
		if(TextUtils.isEmpty(phone)) {
			Toast.makeText(mContext, "请输入手机号", Toast.LENGTH_SHORT).show();
			return;
		} else if(!ValidateUtil.validatePhoneNum(phone)) {
			Toast.makeText(mContext, "您输入的手机号不符合要求", Toast.LENGTH_SHORT).show();
			return;
		}
		mDataFetcher.fetchUpdatePhone(mUID, phone, new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				LoginResult result = GsonUtil.gson.fromJson(response.toString(), LoginResult.class);
				if(result.code == 1) {
					Toast.makeText(mContext, "修改成功", Toast.LENGTH_SHORT).show();
					ChangePhoneActivity.this.finish();
				}
			}
		}, mErrorListener);
	}
	
	public void getCode(View v) {
		String phone = mTxtPhone.getText().toString();
		if(TextUtils.isEmpty(phone)) {
			Toast.makeText(mContext, "请输入手机号", Toast.LENGTH_SHORT).show();
			return;
		} else if(!ValidateUtil.validatePhoneNum(phone)) {
			Toast.makeText(mContext, "您输入的手机号不符合要求", Toast.LENGTH_SHORT).show();
			return;
		}
		mDataFetcher.fetchSendCode(phone, mVerifyCode, new Listener<String>() {
			@Override
			public void onResponse(String response) {
			}
		}, mErrorListener);
	}
	
}

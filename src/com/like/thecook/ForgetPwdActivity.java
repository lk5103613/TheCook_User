package com.like.thecook;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response.Listener;
import com.dcjd.cook.R;
import com.like.util.ValidateCodeUtil;
import com.like.util.ValidateUtil;

public class ForgetPwdActivity extends BaseActivity {
	
	private EditText mTxtPhone;
	private EditText mTxtVerCode;
	private String mVerCode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_pwd);
		
		mTxtPhone = (EditText) findViewById(R.id.txt_phone);
		mTxtVerCode = (EditText) findViewById(R.id.txt_verify_code);
		mVerCode = ValidateCodeUtil.getValidateCode();
		Intent intent = getIntent();
		String phone = intent.getStringExtra("mp");
		mTxtPhone.setText(phone);
	}
	
	public void sendCode(View v) {
		String phone = mTxtPhone.getText().toString();
		if(TextUtils.isEmpty(phone)) {
			Toast.makeText(mContext, "请输入手机号码", Toast.LENGTH_SHORT).show();
			return;
		}
		if(!ValidateUtil.isMobileNO(phone)) {
			Toast.makeText(mContext, "输入的手机号格式错误", Toast.LENGTH_SHORT).show();
			return;
		}
		showLoading(true);
		mDataFetcher.fetchSendCode(phone, mVerCode, new Listener<String>() {
			@Override
			public void onResponse(String response) {
				showLoading(false);
				Toast.makeText(mContext, "发送成功", Toast.LENGTH_SHORT).show();
			}
		}, mErrorListener);
	}
	
	public void next(View v) {
		String verCode = mTxtVerCode.getText().toString();
		String phone = mTxtPhone.getText().toString();
		if(!verCode.equals(mVerCode)) {
			Toast.makeText(mContext, "输入的验证码错误", Toast.LENGTH_SHORT).show();
			return;
		}
		Intent intent = new Intent(mContext, ForgetPwd2Activity.class);
		intent.putExtra("phone_num", phone);
		startActivity(intent);
		this.finish();
	}
	
	public void toLogin(View v) {
		Intent intent = new Intent(mContext, LoginActivity.class);
		startActivity(intent);
	}

}

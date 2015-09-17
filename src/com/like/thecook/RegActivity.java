package com.like.thecook;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.Listener;
import com.dcjd.cook.R;
import com.like.entity.LoginResult;
import com.like.network.GsonUtil;
import com.like.util.ValidateCodeUtil;
import com.like.util.ValidateUtil;

public class RegActivity extends BaseActivity {
	
	private TextView mTxtPhone;
	private TextView mTxtValidateCode;
	private TextView mTxtPwd;
	private TextView mTxtRepeatPwd;
	private CheckBox mCheckAgree;
	private boolean mIsAgree;
	private String mValidateCode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reg);
		mTxtPhone = (TextView) findViewById(R.id.txt_phone);
		mTxtValidateCode = (TextView) findViewById(R.id.verify_code);
		mTxtPwd = (TextView) findViewById(R.id.txt_pwd);
		mTxtRepeatPwd = (TextView) findViewById(R.id.txt_repeat_pwd);
		mCheckAgree = (CheckBox) findViewById(R.id.check_agree);
		mCheckAgree.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mIsAgree = isChecked;
			}
		});
		mValidateCode = ValidateCodeUtil.getValidateCode();
	}
	
	public void back(View v) {
		this.finish();
	}
	
	public void toLogin(View v) {
		Intent intent = new Intent(mContext, LoginActivity.class);
		startActivity(intent);
		this.finish();
	}
	
	public void sendCode(final View v) {
		final String phone = mTxtPhone.getText().toString();
		if(TextUtils.isEmpty(phone)) {
			Toast.makeText(mContext, "请输入手机号", Toast.LENGTH_SHORT).show();
			return;
		}
		if(!ValidateUtil.isMobileNO(phone)) {
			Toast.makeText(mContext, "手机号码格式错误", Toast.LENGTH_SHORT).show();
			return;
		}
		showLoading(true);
		mDataFetcher.fetchSendCode(phone, mValidateCode, new Listener<String>() {
			@Override
			public void onResponse(String response) {
				showLoading(false);
				Toast.makeText(mContext, "短信发送成功", Toast.LENGTH_SHORT).show();
				v.setEnabled(false);
			}
		}, mErrorListener);
	}
	
	public void reg(View v)	{
		final String phone = mTxtPhone.getText().toString();
		String verifyCode = mTxtValidateCode.getText().toString();
		final String pwd = mTxtPwd.getText().toString();
		String repeatPwd = mTxtRepeatPwd.getText().toString();
		if(TextUtils.isEmpty(phone) || TextUtils.isEmpty(verifyCode) || TextUtils.isEmpty(pwd) || TextUtils.isEmpty(repeatPwd)) {
			Toast.makeText(mContext, "请输入完整信息", Toast.LENGTH_SHORT).show();
			return;
		}
		if(!ValidateUtil.validatePhoneNum(phone)) {
			Toast.makeText(mContext, "手机号码格式错误", Toast.LENGTH_SHORT).show();
			return;
		}
		if(!mValidateCode.equals(verifyCode)) {
			Toast.makeText(mContext, "输入的验证码有误", Toast.LENGTH_SHORT).show();
			return;
		}
		if(pwd.length() < 6 || pwd.length() > 16) {
			Toast.makeText(mContext, "密码必须在6到16位之间", Toast.LENGTH_SHORT).show();
			return;
		}
		if(!pwd.equals(repeatPwd)) {
			Toast.makeText(mContext, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
			return;
		}
		if(!mIsAgree) {
			Toast.makeText(mContext, "请阅读并同意安全协议", Toast.LENGTH_SHORT).show();
			return;
		}
		showLoading(true);
		mDataFetcher.fetchRegData(phone, pwd, new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				LoginResult result = GsonUtil.gson.fromJson(response.toString(), LoginResult.class);
				if(result != null && result.code == 1) {
					Toast.makeText(mContext, "注册成功", Toast.LENGTH_LONG).show();
					RegActivity.this.finish();
					mDataFetcher.fetchLoginData(phone, pwd, new Listener<JSONObject>() {
						@Override
						public void onResponse(JSONObject response) {
							showLoading(false);
							Intent intent = new Intent(mContext, IndexActivity.class);
							startActivity(intent);
						}
					}, mErrorListener);
				} else if(result != null && result.code == 7) {
					showLoading(false);
					Toast.makeText(mContext, "用户名已存在", Toast.LENGTH_SHORT).show();
					return;
				} else {
					showLoading(false);
					Toast.makeText(mContext, "注册失败", Toast.LENGTH_SHORT).show();
					return;
				}
			}
		}, mErrorListener);
	}

}

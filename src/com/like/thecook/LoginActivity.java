package com.like.thecook;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.dcjd.cook.R;
import com.like.entity.LoginResult;
import com.like.network.DataFetcher;
import com.like.network.GsonUtil;

public class LoginActivity extends BaseActivity {
	
	
	private Context mContext;
	private EditText mLblAccount;
	private EditText mLblPwd;
	private DataFetcher mDataFetcher;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		mContext = this;
		
		if(mLoginSharef.getBoolean(IS_LOGIN, false)) {
			Intent intent = new Intent(mContext, IndexActivity.class);
			startActivity(intent);
			LoginActivity.this.finish();
		}
		
		mLblAccount = (EditText) findViewById(R.id.lbl_account);
		mLblPwd = (EditText) findViewById(R.id.lbl_pwd);
		mDataFetcher = DataFetcher.getInstance(mContext);
	}
	
	public void login(View v) {
		String account = mLblAccount.getText().toString();
		String pwd = mLblPwd.getText().toString();
		if(TextUtils.isEmpty(account) || TextUtils.isEmpty(pwd)) {
			Toast.makeText(mContext, "请输入用户名或密码", Toast.LENGTH_SHORT).show();
			return;
		}
		showLoading(true);
//		if(pwd.trim().length() < 6) {
//			Toast.makeText(mContext, "密码必须长于6位", Toast.LENGTH_SHORT).show();
//			return;
//		}
		mDataFetcher.fetchLoginData(account, pwd, new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				showLoading(false);
				final LoginResult result = GsonUtil.gson.fromJson(response.toString(), LoginResult.class);
				if(result.code != 1) {
					Toast.makeText(mContext, "用户名或密码不正确", Toast.LENGTH_LONG).show();
					return;
				} else {
					
					Toast.makeText(mContext, "登陆成功", Toast.LENGTH_LONG).show();
					
					new Thread(new Runnable() {
						@Override
						public void run() {
							Editor editor = mLoginSharef.edit();
							editor.putBoolean(IS_LOGIN, true);
							String  uid=result.uid;
							String  mp=result.mp;
							editor.putString(UID, uid);
							editor.putString(PHONE_NUM, mp);
							editor.commit();
						}
					}).start();
					Intent intent = new Intent(mContext, IndexActivity.class);
					startActivity(intent);
					LoginActivity.this.finish();
				}
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(mContext, "请检查网络", Toast.LENGTH_LONG).show();
			}
		});
	}
	
	public void toReg(View v) {
		Intent intent = new Intent(mContext, RegActivity.class);
		startActivity(intent);
	}

}

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

public class ChangePwdActivity extends BaseActivity {
	
	private EditText mTxtOldPwd;
	private EditText mTxtNewPwd;
	private EditText mTxtRepeatNewPwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_pwd);
		
		mTxtOldPwd = (EditText) findViewById(R.id.edit_yuan);
		mTxtNewPwd = (EditText) findViewById(R.id.edit_new);
		mTxtRepeatNewPwd = (EditText) findViewById(R.id.edit_confirm);
	}

	public void back(View v) {
		this.finish();
	}
	
	public void changePwd(View v) {
		String oldPwd = mTxtOldPwd.getText().toString();
		String newPwd = mTxtNewPwd.getText().toString();
		String repeatNewPwd = mTxtRepeatNewPwd.getText().toString();
		if(TextUtils.isEmpty(oldPwd) || TextUtils.isEmpty(newPwd) || TextUtils.isEmpty(repeatNewPwd)) {
			Toast.makeText(mContext, "请输入完整信息", Toast.LENGTH_SHORT).show();
			return;
		} else if(!newPwd.equals(repeatNewPwd)) {
			Toast.makeText(mContext, "两次密码不一致", Toast.LENGTH_SHORT).show();
			return;
		}
		mDataFetcher.fetchChangePwd(oldPwd, newPwd, new Listener<JSONObject>(){
			@Override
			public void onResponse(JSONObject response) {
				LoginResult result = GsonUtil.gson.fromJson(response.toString(), LoginResult.class);
				if(result.code == 1) {
					Toast.makeText(mContext, "修改成功", Toast.LENGTH_LONG).show();
					ChangePwdActivity.this.finish();
				}
			}}, mErrorListener);
	}

}

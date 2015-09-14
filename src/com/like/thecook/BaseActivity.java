package com.like.thecook;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.dcjd.cook.R;
import com.like.network.DataFetcher;
import com.like.network.MyNetworkUtil;
import com.like.storage.ShoppingCartManager;

public class BaseActivity extends Activity {
	
	public final static String PHONE_NUMBER = "40067654634";
	
	public final static String USER_PROPERTIES = "login_properties";
	public final static String IS_LOGIN = "is_login";
	public final static String UID = "uid";
	public final static String PHONE_NUM = "mp";
	
	protected Context mContext;
	protected DataFetcher mDataFetcher;
	protected ErrorListener mErrorListener;
	protected ProgressDialog mLoadingDialog;
	protected ShoppingCartManager mShoppingCartManager;
	protected ImageLoader mImgLoader;
	
	protected SharedPreferences mLoginSharef;
	
	protected String mUID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		mDataFetcher = DataFetcher.getInstance(mContext);
		mShoppingCartManager = ShoppingCartManager.getInstance(mContext);
		mLoginSharef = getSharedPreferences(USER_PROPERTIES, Context.MODE_PRIVATE);
		mImgLoader = MyNetworkUtil.getInstance(mContext).getImageLoader();
		mErrorListener = new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(mContext, "请检查网络", Toast.LENGTH_LONG).show();
			}
		};
		mUID = mLoginSharef.getString(UID, "");
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		JPushInterface.onPause(mContext);
		if(isFinishing()) {
			overridePendingTransition(R.anim.back_from_right,
					R.anim.remove_to_right);
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		JPushInterface.onResume(mContext);
	}
	
	protected void showLoading(boolean show) {
		if(show) {
			if(mLoadingDialog == null) {
				mLoadingDialog = new ProgressDialog(this);
				mLoadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				mLoadingDialog.setMessage("请等待");
			}
			mLoadingDialog.show();
		} else {
			if(mLoadingDialog == null)
				return;
			if(mLoadingDialog.isShowing()) 
				mLoadingDialog.dismiss();
		}
	}
	
	protected void callNumber() {
		Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+ PHONE_NUMBER));  
        startActivity(intent);  
	}
	
	public void back(View v) {
		this.finish();
	}

}

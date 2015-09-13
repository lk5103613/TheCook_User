package com.like.fragment;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.ImageLoader;
import com.like.network.DataFetcher;
import com.like.network.MyNetworkUtil;
import com.like.storage.ShoppingCartManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class BaseFragment extends Fragment {
	
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext = getActivity();
		mDataFetcher = DataFetcher.getInstance(mContext);
		mShoppingCartManager = ShoppingCartManager.getInstance(mContext);
		mLoginSharef = getActivity().getSharedPreferences(USER_PROPERTIES, Context.MODE_PRIVATE);
		mImgLoader = MyNetworkUtil.getInstance(mContext).getImageLoader();
		mErrorListener = new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(mContext, "请检查网络", Toast.LENGTH_LONG).show();
			}
		};
		mUID = mLoginSharef.getString(UID, "");
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
}

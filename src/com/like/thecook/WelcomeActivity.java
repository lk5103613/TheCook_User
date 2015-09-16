package com.like.thecook;

import java.lang.reflect.Type;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;

import com.dcjd.cook.R;
import com.google.gson.reflect.TypeToken;
import com.like.entity.City;
import com.like.entity.Districts;
import com.like.entity.Provience;
import com.like.network.GsonUtil;
import com.like.storage.CityManager;
import com.like.storage.DistrictsManager;
import com.like.storage.ProvienceManager;
import com.like.util.LocationUtil;

public class WelcomeActivity extends BaseActivity {
	
	private final static int NOT_LOGIN = 0;
	private final static int LOGIN = 1;
	
	private Handler mHandler;
	private ProvienceManager mProvienceManager;
	private CityManager mCityManager;
	private DistrictsManager mDistrictsManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		mProvienceManager = ProvienceManager.getInstance(mContext);
		mCityManager = CityManager.getInstance(mContext);
		mDistrictsManager = DistrictsManager.getInstance(mContext);
		
		mHandler = new Handler(new Callback() {
			@Override
			public boolean handleMessage(Message msg) {
				Intent intent;
				switch (msg.what) {
				case NOT_LOGIN:
					intent = new Intent(mContext, LoginActivity.class);
					break;
				case LOGIN:
					intent = new Intent(mContext, IndexActivity.class);
					break;
				default:
					return false;
				}
				startActivity(intent);
				WelcomeActivity.this.finish();
				return false;
			}
		});
		
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				Message msg = Message.obtain();
				if(mLoginSharef.getBoolean(IS_LOGIN, false)) {
					msg.what = LOGIN;
				} else {
					msg.what = NOT_LOGIN;
				}
				initLocationDB();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				mHandler.sendMessage(msg);
			}
		}).start();
	}
	
	private void initLocationDB() {
		List<Provience> pros = mProvienceManager.getAll();
		if(pros != null && pros.size() > 0) 
			return;
		mProvienceManager.addProvience(getAllPro());
		mCityManager.addCity(getAllCity());
		mDistrictsManager.addDistrict(getAllDistrict());
	}
	
	private List<Provience> getAllPro() {
		Type type = new TypeToken<List<Provience>>(){}.getType();
		List<Provience> pros = GsonUtil.gson.fromJson(new LocationUtil().provienceJson, type);
		return pros;
	}
	
	private List<City> getAllCity() {
		Type type = new TypeToken<List<City>>(){}.getType();
		List<City> cities = GsonUtil.gson.fromJson(new LocationUtil().cityJson, type);
		return cities;
	}
	
	private List<Districts> getAllDistrict() {
		Type type = new TypeToken<List<Districts>>(){}.getType();
		List<Districts> districts = GsonUtil.gson.fromJson(new LocationUtil().districtJson, type);
		return districts;
	}

}

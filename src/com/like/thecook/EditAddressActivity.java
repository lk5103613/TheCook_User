package com.like.thecook;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.Listener;
import com.dcjd.cook.R;
import com.like.entity.Address;
import com.like.entity.LoginResult;
import com.like.network.GsonUtil;
import com.like.util.ValidateUtil;

public class EditAddressActivity extends BaseActivity {
	
	private EditText mTxtName;
	private EditText mTxtPhoneNum;
	private EditText mTxtDetail;
	private TextView mLblProvience;
	private TextView mLblDis;
	private TextView mLblCity;
	private String mUID;
	private Address mAddress;
	private PopupWindow mCityWindow, mProWindow, mDisWindow;
	private ViewGroup mCityDropdown, mProDropdown, mDisDropdown;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_address);
		
		mUID = mLoginSharef.getString(UID, "");
		
		Intent intent = getIntent();
		String json = intent.getStringExtra("address");
		if(!TextUtils.isEmpty(json)) {
			mAddress = GsonUtil.gson.fromJson(json, Address.class);
		}
		mTxtName = (EditText) findViewById(R.id.name);
		mTxtPhoneNum = (EditText) findViewById(R.id.phone_num);
		mTxtDetail = (EditText) findViewById(R.id.detail_address);
		mLblProvience = (TextView) findViewById(R.id.provience);
		mLblDis = (TextView) findViewById(R.id.district);
		mLblCity = (TextView) findViewById(R.id.city);
		mCityDropdown = (ViewGroup) findViewById(R.id.city_drop_btn);
		mProDropdown = (ViewGroup) findViewById(R.id.pro_drop_btn);
		mDisDropdown = (ViewGroup) findViewById(R.id.district_drop_btn);
		initView();
	}
	
	public void back(View v) {
		this.finish();
	}
	
	private void initView() {
		if(mAddress == null)
			return;
		mTxtName.setText(mAddress.linkman);
		mTxtPhoneNum.setText(mAddress.mp);
		mTxtDetail.setText(mAddress.detail_address);
		mLblProvience.setText(mAddress.province);
		mLblDis.setText(mAddress.district);
		mLblCity.setText(mAddress.city);
	}
	
	public void selectCity(View v) {
		if (mCityWindow == null) {
			LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = layoutInflater.inflate(R.layout.dropdown_dialog, null);
			ListView optionList = (ListView) view
					.findViewById(R.id.option_list);
			final List<String> options = getCitys();
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
					R.layout.dropdown_option_item, options);
			optionList.setAdapter(adapter);
			optionList.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					mLblCity.setText(options.get(position));
					if (mCityWindow.isShowing())
						mCityWindow.dismiss();
				}
			});
			mCityWindow = new PopupWindow(view, 250, 300);
		}
		mCityWindow.setOutsideTouchable(true);
		mCityWindow.setBackgroundDrawable(new BitmapDrawable());
		backgroundAlpha(1);
		mCityWindow.showAsDropDown(mCityDropdown, -150, 0);
	}
	
	public void selectPro(View v) {
		if (mProWindow == null) {
			LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = layoutInflater.inflate(R.layout.dropdown_dialog, null);
			ListView optionList = (ListView) view
					.findViewById(R.id.option_list);
			final List<String> options = getPros();
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
					R.layout.dropdown_option_item, options);
			optionList.setAdapter(adapter);
			optionList.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					mLblProvience.setText(options.get(position));
					if (mProWindow.isShowing())
						mProWindow.dismiss();
				}
			});
			mProWindow = new PopupWindow(view, 250, 300);
		}
		mProWindow.setOutsideTouchable(true);
		mProWindow.setBackgroundDrawable(new BitmapDrawable());
		backgroundAlpha(1);
		mProWindow.showAsDropDown(mProDropdown, -150, 0);
	}
	public void selectDis(View v) {
		if (mDisWindow == null) {
			LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = layoutInflater.inflate(R.layout.dropdown_dialog, null);
			ListView optionList = (ListView) view
					.findViewById(R.id.option_list);
			final List<String> options = getDis();
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
					R.layout.dropdown_option_item, options);
			optionList.setAdapter(adapter);
			optionList.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					mLblDis.setText(options.get(position));
					if (mDisWindow.isShowing())
						mDisWindow.dismiss();
				}
			});
			mDisWindow = new PopupWindow(view, 250, 300);
		}
		mDisWindow.setOutsideTouchable(true);
		mDisWindow.setBackgroundDrawable(new BitmapDrawable());
		backgroundAlpha(1);
		mDisWindow.showAsDropDown(mDisDropdown, -150, 0);
	}
	
	private List<String> getCitys() {
		List<String> citys = new ArrayList<String>();
		citys.add("城市1");
		citys.add("城市2");
		citys.add("城市3");
		citys.add("城市4");
		return citys;
	}
	
	private List<String> getPros(){
		List<String> pros = new ArrayList<String>();
		pros.add("省份1");
		pros.add("省份2");
		pros.add("省份3");
		pros.add("省份4");
		return pros;
	}
	
	private List<String> getDis(){
		List<String> dis = new ArrayList<String>();
		dis.add("地区1");
		dis.add("地区2");
		dis.add("地区3");
		dis.add("地区4");
		return dis;
	}
	public void commit(View v) {
		String name = mTxtName.getText().toString();
		String phoneNum = mTxtPhoneNum.getText().toString();
		String detail = mTxtDetail.getText().toString();
		String provience = mLblProvience.getText().toString();
		String district = mLblDis.getText().toString();
		String city = mLblCity.getText().toString();
		if(TextUtils.isEmpty(name) || TextUtils.isEmpty(phoneNum) || TextUtils.isEmpty(detail) || 
				TextUtils.isEmpty(provience) || TextUtils.isEmpty(district) || TextUtils.isEmpty(city)) {
			Toast.makeText(mContext, "请填写完整信息", Toast.LENGTH_LONG).show();
			return;
		}
		if(ValidateUtil.validatePhoneNum(phoneNum)) {
			Toast.makeText(mContext, "手机格式不正确", Toast.LENGTH_SHORT).show();
			return;
		}
		mDataFetcher.fetchAddAddress(mUID, name, phoneNum, district, provience, city, detail, 
				new Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						LoginResult result = GsonUtil.gson.fromJson(response.toString(), LoginResult.class);
						if(result.code == 1) {
							Toast.makeText(mContext, "添加成功", Toast.LENGTH_LONG).show();
							EditAddressActivity.this.finish();
						} else {
							Toast.makeText(mContext, "添加失败", Toast.LENGTH_LONG).show();
						}
					}
				}, mErrorListener);
	}
	
	public void backgroundAlpha(float bgAlpha) {
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = bgAlpha;
		getWindow().setAttributes(lp);
	}
	
}

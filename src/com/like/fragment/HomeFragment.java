package com.like.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.dcjd.cook.R;
import com.like.adapter.IndexBannerAdapter;
import com.like.customeview.HackyViewPager;
import com.like.thecook.BaseActivity;
import com.like.thecook.CountryChoiseActivity;
import com.like.thecook.DcActivity;
import com.like.thecook.MeiShiActivity;
import com.like.thecook.QRActivity;
import com.like.thecook.TaoCanInfoActivity;
import com.like.thecook.ZxActivity;

public class HomeFragment extends Fragment {
	
	private RelativeLayout callLayout;
	private ViewGroup mLocation;
	private HackyViewPager mViewPager;
	private ViewGroup mScan;
	private Handler mHandler;
	private boolean mNeedScroll = true;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.home_fragment, container, false);
		
		callLayout = (RelativeLayout) layout.findViewById(R.id.callLayout);
		mLocation = (ViewGroup) layout.findViewById(R.id.location);
		mViewPager = (HackyViewPager) layout.findViewById(R.id.view_pager);
		mViewPager.setAdapter(new IndexBannerAdapter(getActivity()));
		mScan = (ViewGroup) layout.findViewById(R.id.scan);
		mScan.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), QRActivity.class);
				startActivity(intent);
			}
		});
		mHandler = new Handler(new Callback() {
			int i = 0;
			@Override
			public boolean handleMessage(Message msg) {
				i++;
				if(i % 4 == 0) {
					mViewPager.setCurrentItem(i % 4, false);
				} else {
					mViewPager.setCurrentItem(i % 4, true);
				}
				return false;
			}
		});
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(mNeedScroll) {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					mHandler.sendEmptyMessage(1);
				}
			}
		}).start();
		mLocation.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), CountryChoiseActivity.class);
				startActivity(intent);
			}
		});
		
		layout.findViewById(R.id.call).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				callLayout.setVisibility(callLayout.getVisibility()==View.GONE?View.VISIBLE:View.GONE);
			}
		});
//		layout.findViewById(R.id.callNull).setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				callLayout.setVisibility(View.GONE);
//			}
//		});
		layout.findViewById(R.id.zixun).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivity().startActivity(new Intent(getActivity(),ZxActivity.class));
			}
		});
		layout.findViewById(R.id.jiushui).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), MeiShiActivity.class);
				startActivity(intent);
			}
		});
		layout.findViewById(R.id.taocan).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivity().startActivity(new Intent(getActivity(),TaoCanInfoActivity.class));
			}
		});
		layout.findViewById(R.id.daimai).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivity().startActivity(new Intent(getActivity(),MeiShiActivity.class));
			}
		});
		layout.findViewById(R.id.chushi).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivity().startActivity(new Intent(getActivity(), DcActivity.class));
			}
		});
		callLayout.findViewById(R.id.call_num).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.println("call");
				Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+ BaseActivity.PHONE_NUMBER));
		        startActivity(intent);
			}
		});
		return layout;
	}
	
	@Override
	public void onPause() {
		super.onPause();
		mNeedScroll = false;
	}
	

}

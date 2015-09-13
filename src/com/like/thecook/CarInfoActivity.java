package com.like.thecook;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.dcjd.cook.R;

public class CarInfoActivity extends BaseActivity {
	
	private Dialog mDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.car_info);
		
		findViewById(R.id.back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	public void pay(View v) {
		showDialog();
	}
	
	private void showDialog() {
		if(mDialog == null) {
			mDialog = new Dialog(mContext, R.style.Theme_dialog);
			View view = LayoutInflater.from(mContext).inflate(R.layout.car_dialog, null);
			ViewGroup addMore = (ViewGroup) view.findViewById(R.id.add_more);
			addMore.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mDialog.dismiss();
					Intent intent = new Intent(mContext, CarInfoAddActivity.class);
					startActivity(intent);
				}
			});
			mDialog.setContentView(view);
		}
		mDialog.show();
	}

}

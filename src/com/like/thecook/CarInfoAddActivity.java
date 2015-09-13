package com.like.thecook;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.dcjd.cook.R;

public class CarInfoAddActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.car_info_add);
		
		findViewById(R.id.back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

}

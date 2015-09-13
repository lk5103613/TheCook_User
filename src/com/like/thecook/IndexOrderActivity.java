package com.like.thecook;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;

import com.dcjd.cook.R;

public class IndexOrderActivity extends BaseActivity {
	
	private DrawerLayout mDrawerLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index_order);
		
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		
	}
	
	public void toggleMenu(View v) {
		if(mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
			mDrawerLayout.closeDrawer(Gravity.LEFT);
		} else {
			mDrawerLayout.openDrawer(Gravity.LEFT);
		}
	}
	

}

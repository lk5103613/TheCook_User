package com.like.thecook;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.dcjd.cook.R;
import com.like.fragment.CarFragment;
import com.like.fragment.CarFragment.CarFragmentListener;
import com.like.fragment.HomeFragment;
import com.like.fragment.MenuFragment;
import com.like.fragment.MyFragment;
import com.like.fragment.OrderFragment;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class IndexActivity extends FragmentActivity implements CarFragmentListener {

	private FragmentManager fragmentManager;

	private Map<String, Fragment> fragmentMap = new HashMap<String, Fragment>();

	private LinearLayout tab1, tab2, tab3, tab4, tab5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index);

		tab1 = (LinearLayout) findViewById(R.id.tab1);
		tab2 = (LinearLayout) findViewById(R.id.tab2);
		tab3 = (LinearLayout) findViewById(R.id.tab3);
		tab4 = (LinearLayout) findViewById(R.id.tab4);
		tab5 = (LinearLayout) findViewById(R.id.tab5);

		tab1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setTabSelection(0);
			}
		});
		tab2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setTabSelection(1);
			}
		});
		tab3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setTabSelection(2);
			}
		});
		tab4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setTabSelection(3);
			}
		});
		tab5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setTabSelection(4);
			}
		});

		fragmentManager = getSupportFragmentManager();
		setTabSelection(0);

	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		if (intent != null) {
			int index = intent.getIntExtra("tab_index", 0);
			setTabSelection(index);
		}
	}

	private void setTabSelection(int index) {
		tab1.setSelected(false);
		tab2.setSelected(false);
		tab3.setSelected(false);
		tab4.setSelected(false);
		tab5.setSelected(false);

		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hiddenFragment(transaction);

		Fragment fragment = null;
		switch (index) {
		case 0:
			fragment = new HomeFragment();
			tab1.setSelected(true);
			break;
		case 1:
			fragment = new MenuFragment();
			tab2.setSelected(true);
			break;
		case 2:
			fragment = new CarFragment();
			tab3.setSelected(true);
			break;
		case 3:
			fragment = new OrderFragment();
			tab4.setSelected(true);
			break;
		case 4:
			fragment = new MyFragment();
			tab5.setSelected(true);
			break;
		}
		transaction.add(R.id.frameLayout, fragment);
		fragmentMap.put(String.valueOf(index), fragment);
		transaction.commit();
	}

	private void hiddenFragment(FragmentTransaction transaction) {
		Iterator<Entry<String, Fragment>> iterator = fragmentMap.entrySet()
				.iterator();
		while (iterator.hasNext()) {
			Entry<String, Fragment> entry = iterator.next();
			transaction.hide(entry.getValue());
		}
	}

	@Override
	public void changeTab(int index) {
		setTabSelection(index);
	}

}

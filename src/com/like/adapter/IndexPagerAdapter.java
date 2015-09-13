package com.like.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.like.fragment.CollectDcFragment;
import com.like.fragment.CollectMsFragment;
import com.like.fragment.CollectTcFragment;

public class IndexPagerAdapter extends FragmentStatePagerAdapter{

	private CollectTcFragment mIndexFragment = new CollectTcFragment();
	private CollectMsFragment mDeviceFragment = new CollectMsFragment();
	private CollectDcFragment mSettingFragment = new CollectDcFragment();

	public IndexPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	private Fragment[] mFragments = new Fragment[] { mIndexFragment,
			mDeviceFragment, mSettingFragment };
	private String[] mTitles = new String[] { "套餐", "美食", "大厨" };

	@Override
	public Fragment getItem(int index) {
		return mFragments[index];
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return mTitles[position];
	}

	@Override
	public int getCount() {
		return 3;
	}

}

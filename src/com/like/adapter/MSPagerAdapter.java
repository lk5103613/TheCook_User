package com.like.adapter;

import java.util.HashMap;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.dcjd.cook.R;

public class MSPagerAdapter extends PagerAdapter {
	
	private int[] mDrawables = new int[]{R.drawable.meishi_banner, R.drawable.meishi_banner, R.drawable.meishi_banner};
	private HashMap<Integer, ImageView> mImgs = new HashMap<Integer, ImageView>();
	private Context mContext;
	
	public MSPagerAdapter(Context context) {
		this.mContext = context;
	}
	
	@Override
	public int getCount() {
		return 3;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		ImageView img;
		if(mImgs.get(mDrawables[position]) == null) {
			img = new ImageView(mContext);
			img.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			img.setScaleType(ScaleType.FIT_XY);
			img.setImageResource(mDrawables[position]);
		} else {
			img = mImgs.get(mDrawables[position]);
		}
		container.addView(img);
		return img;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

}

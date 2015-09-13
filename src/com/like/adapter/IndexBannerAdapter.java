package com.like.adapter;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.dcjd.cook.R;
import com.like.thecook.DcActivity;
import com.like.thecook.MeiShiActivity;
import com.like.thecook.ZxInfoActivity;

public class IndexBannerAdapter extends PagerAdapter {
	
	private int[] mDrawables = new int[]{R.drawable.banner, R.drawable.daimai, R.drawable.chushi, R.drawable.jiushui, R.drawable.zixun, R.drawable.home_title_bg};
	private Context mContext;
	
	private HashMap<Integer, ImageView> mImgs = new HashMap<Integer, ImageView>();
	
	public IndexBannerAdapter(Context context) {
		this.mContext = context;
	}

	@Override
	public int getCount() {
		return mDrawables.length;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		ImageView img;
		if(mImgs.get(mDrawables[position]) == null) {
			img = new ImageView(mContext);
			img.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			img.setScaleType(ScaleType.FIT_XY);
			img.setImageResource(mDrawables[position]);
			img.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = null;
					switch (mDrawables[position]) {
					case R.id.daimai:
						intent = new Intent(mContext, MeiShiActivity.class); 
						break;
					case R.drawable.chushi:
						intent = new Intent(mContext, DcActivity.class); 
						break;
					case R.drawable.jiushui:
						intent = new Intent(mContext, MeiShiActivity.class);
						break;
					case R.drawable.zixun:
						intent = new Intent(mContext, ZxInfoActivity.class);
						break;
					case R.drawable.home_title_bg:
						intent = new Intent(mContext, MeiShiActivity.class);
						break;
					default:
						return;
					}
					mContext.startActivity(intent);
				}
			});
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

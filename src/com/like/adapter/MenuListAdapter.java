package com.like.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.dcjd.cook.R;
import com.like.entity.MenuEntity;
import com.like.network.APIS;
import com.like.network.MyNetworkUtil;
import com.like.thecook.TaoCanDetailActivity;

public class MenuListAdapter extends SimpleAdapter<MenuEntity> {

	private List<MenuEntity> mMenuEntities;
	private Context mContext;
	private ImageLoader mImageLoader;

	public MenuListAdapter(Context context, Context applicationContext,  List<MenuEntity> datas) {
		super(context, datas);
		this.mMenuEntities = datas;
		this.mContext = context;
		this.mImageLoader = MyNetworkUtil.getInstance(applicationContext).getImageLoader();
	}

	@Override
	public int getItemResourceId() {
		return R.layout.menu_item;
	}

	public void setList(List<MenuEntity> entities) {
		this.mMenuEntities = entities;
	}

	@Override
	public void bindData(int position, View convertView, ViewHolder holder) {
		final MenuEntity menu = mMenuEntities.get(position);
		
		ImageView img = holder.findView(R.id.img);
		TextView name = holder.findView(R.id.name);
		TextView count = holder.findView(R.id.count);
		TextView money = holder.findView(R.id.money);
		TextView orderNow = holder.findView(R.id.order_now);
		
		mImageLoader.get(APIS.BASE_URL + menu.img_path, ImageLoader.getImageListener(img,
				R.color.white, R.color.white));
		name.setText(menu.packname);
		count.setText(menu.sold_cnt+"");
		money.setText(menu.price+"");
		
		orderNow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mContext.startActivity(new Intent(mContext, TaoCanDetailActivity.class).putExtra("pacId", menu.packid));	
			}
		});
	}

}

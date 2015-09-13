package com.like.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.dcjd.cook.R;
import com.like.entity.ShoppingCartEntity;
import com.like.network.MyNetworkUtil;

public class CarOrderListAdapter extends SimpleAdapter<ShoppingCartEntity>{
	
	private ImageLoader mImageLoader;
	
	public CarOrderListAdapter(Context context, List<ShoppingCartEntity> datas) {
		super(context, datas);
		mImageLoader = MyNetworkUtil.getInstance(context).getImageLoader();
	}

	@Override
	public int getItemResourceId() {
		return R.layout.car_order_list_item;
	}

	@Override
	public void bindData(int position, View convertView, ViewHolder holder) {
		ShoppingCartEntity entity = (ShoppingCartEntity) getItem(position);
		ImageView image = (ImageView) holder.findView(R.id.image);
		TextView name = (TextView) holder.findView(R.id.name);
		TextView price = (TextView) holder.findView(R.id.price);
		TextView count = (TextView) holder.findView(R.id.count);
		mImageLoader.get(entity.img, ImageLoader.getImageListener(image, R.color.white, R.color.white));
		name.setText(entity.name);
		count.setText(entity.cnt+"");
		price.setText(entity.price+"");
	}

}

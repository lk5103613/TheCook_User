package com.like.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.dcjd.cook.R;
import com.like.entity.Meishi;
import com.like.entity.ShoppingCartEntity;
import com.like.network.APIS;
import com.like.network.GsonUtil;
import com.like.network.MyNetworkUtil;
import com.like.thecook.CarOrderActivity;

public class MSAdapter extends BaseAdapter {
	
	private List<Meishi> mMeishis;
	private LayoutInflater mInflater;
	private ImageLoader mImgLoader;
	private Context mContext;
	
	public MSAdapter(Context context, List<Meishi> meishis) {
		this.mMeishis = meishis;
		this.mInflater = LayoutInflater.from(context);
		mImgLoader = MyNetworkUtil.getInstance(context).getImageLoader();
		this.mContext = context;
	}
	

	@Override
	public int getCount() {
		return mMeishis.size();
	}

	@Override
	public Object getItem(int position) {
		return mMeishis.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Meishi ms = mMeishis.get(position);
		ViewHolder vh;
		if(convertView == null) {
			convertView = mInflater.inflate(R.layout.ms_list_item, parent, false);
			vh = new ViewHolder(convertView);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		mImgLoader.get(APIS.BASE_URL + ms.avatar, ImageLoader.getImageListener(vh.sImg, R.color.white, R.color.white));
		vh.sPackName.setText(ms.name);
		vh.sPrice.setText(ms.price);
		vh.sSoldCnt.setText(ms.sold_cnt);
		vh.sAddToCart.setTag(position);
		return convertView;
	}
	
	public void updateList(List<Meishi> mss) {
		this.mMeishis = mss;
		notifyDataSetChanged();
	}
	
	public class ViewHolder {
		public ImageView sImg;
		public TextView sPackName;
		public TextView sPrice;
		public TextView sSoldCnt;
		public ViewGroup sAddToCart;
		
		public ViewHolder(View convertView) {
			sImg = (ImageView) convertView.findViewById(R.id.img);
			sPackName = (TextView) convertView.findViewById(R.id.pack_name);
			sPrice = (TextView) convertView.findViewById(R.id.price);
			sSoldCnt = (TextView) convertView.findViewById(R.id.sold_cnt);
			sAddToCart = (ViewGroup) convertView.findViewById(R.id.add_to_cart);
			sAddToCart.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					int position = (Integer) v.getTag();
					Meishi ms = mMeishis.get(position);
					List<ShoppingCartEntity> cars = new ArrayList<ShoppingCartEntity>();
					cars.add(ms.toShoppingCartEntity());
					Intent intent = new Intent(mContext, CarOrderActivity.class);
					String json = GsonUtil.gson.toJson(cars);
					intent.putExtra("selected_values", json);
					mContext.startActivity(intent);
				}
			});
		}
	}

}

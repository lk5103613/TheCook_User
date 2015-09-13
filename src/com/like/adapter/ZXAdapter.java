package com.like.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.dcjd.cook.R;
import com.like.entity.ZXEntity;
import com.like.network.APIS;
import com.like.network.MyNetworkUtil;

public class ZXAdapter extends BaseAdapter {
	
	private List<ZXEntity> mZXs;
	private LayoutInflater mInflater;
	private ImageLoader mImgLoader;
	
	public ZXAdapter(Context context, List<ZXEntity> zxs) {
		mZXs = zxs;
		mInflater = LayoutInflater.from(context);
		mImgLoader = MyNetworkUtil.getInstance(context).getImageLoader();
	}
	
	public void updateList(List<ZXEntity> zxs) {
		this.mZXs = zxs;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mZXs.size();
	}

	@Override
	public Object getItem(int position) {
		return mZXs.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh;
		ZXEntity entity = mZXs.get(position);
		if(convertView == null) {
			convertView = mInflater.inflate(R.layout.zx_item, parent, false);
			vh = new ViewHolder(convertView);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		mImgLoader.get(APIS.BASE_URL + entity.path, ImageLoader.getImageListener(vh.sZXImg, R.color.white, R.color.white));
		vh.sTitle.setText(entity.title);
		return convertView;
	}
	
	public class ViewHolder {
		public ImageView sZXImg;
		public TextView sTitle;
		
		public ViewHolder(View convertView) {
			sZXImg = (ImageView) convertView.findViewById(R.id.zx_img);
			sTitle = (TextView) convertView.findViewById(R.id.zx_title);
		}
	}

}

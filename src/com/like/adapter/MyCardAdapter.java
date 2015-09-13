package com.like.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dcjd.cook.R;
import com.like.entity.MyCardEntity;

public class MyCardAdapter extends BaseAdapter {
	
	private List<MyCardEntity> mCards;
	private LayoutInflater mInflater;
	
	public MyCardAdapter(Context context, List<MyCardEntity> cards) {
		this.mCards = cards;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return mCards.size();
	}

	@Override
	public Object getItem(int position) {
		return mCards.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MyCardEntity card = mCards.get(position);
		ViewHolder vh;
		if(convertView == null) {
			convertView = mInflater.inflate(R.layout.card_list_item, parent, false);
			vh = new ViewHolder(convertView);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.sLblName.setText(card.name);
		vh.sLblDeadTime.setText(card.deadTime);
		vh.sLblNo.setText(card.couponNo);
		return convertView;
	}
	
	public void updateList(List<MyCardEntity> cards) {
		this.mCards = cards;
		notifyDataSetChanged();
	}
	
	public class ViewHolder {
		
		public TextView sLblName;
		public TextView sLblDeadTime;
		public TextView sLblNo;
		
		public ViewHolder(View convertView) {
			sLblName = (TextView) convertView.findViewById(R.id.name);
			sLblDeadTime = (TextView) convertView.findViewById(R.id.dead_time);
			sLblNo = (TextView) convertView.findViewById(R.id.no);
		}
		
	}

}

package com.like.adapter;

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

import com.dcjd.cook.R;
import com.like.entity.Address;
import com.like.network.GsonUtil;
import com.like.thecook.EditAddressActivity;

public class AddressListAdapter extends BaseAdapter {
	
	private List<Address> mAddresses;
	private LayoutInflater mInflater;
	private Context mContext;
	
	public AddressListAdapter(Context context, List<Address> addresses) {
		this.mAddresses = addresses;
		mInflater = LayoutInflater.from(context);
		mContext = context;
	}
	
	@Override
	public int getCount() {
		return mAddresses.size();
	}

	@Override
	public Object getItem(int position) {
		return mAddresses.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public void updateList(List<Address> addresses) {
		this.mAddresses = addresses;
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Address address = mAddresses.get(position);
		ViewHolder vh;
		if(convertView == null) {
			convertView = mInflater.inflate(R.layout.address_list_item, parent, false);
			vh = new ViewHolder(convertView);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.sName.setText(address.linkman);
		vh.sPhoneNum.setText(address.mp);
		vh.sDetail.setText(address.detail_address);
		vh.sBtnEdit.setTag(position);
		return convertView;
	}
	
	public class ViewHolder {
		public TextView sIndicator;
		public TextView sName;
		public TextView sPhoneNum;
		public TextView sDetail;
		public ImageView sBtnEdit;
		
		public ViewHolder(View convertView) {
			sIndicator = (TextView) convertView.findViewById(R.id.is_select);
			sName = (TextView) convertView.findViewById(R.id.name);
			sPhoneNum = (TextView) convertView.findViewById(R.id.phone);
			sDetail = (TextView) convertView.findViewById(R.id.detail);
			sBtnEdit = (ImageView) convertView.findViewById(R.id.btn_edit);
			sBtnEdit.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					int position = (Integer) v.getTag();
					Address address = (Address) getItem(position);
					String json = GsonUtil.gson.toJson(address);
					System.out.println(json);
					Intent intent = new Intent(mContext, EditAddressActivity.class);
					intent.putExtra("address", json);
					mContext.startActivity(intent);
				}
			});
		}
		
	}

}

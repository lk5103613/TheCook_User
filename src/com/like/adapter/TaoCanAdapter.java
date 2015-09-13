package com.like.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dcjd.cook.R;
import com.like.customeview.CornerImage;
import com.like.entity.TaoCanEntity;
import com.like.thecook.TaoCanDetailActivity;

public class TaoCanAdapter extends SimpleAdapter<TaoCanEntity> implements OnClickListener{
	
	private List<TaoCanEntity> mtaoCanList;
	private Context mContext;

	public TaoCanAdapter(Context context, List<TaoCanEntity> taoCanList) {
		super(context, taoCanList);
		this.mContext = context;
		this.mtaoCanList = taoCanList;
	}

	@Override
	public int getItemResourceId() {
		return R.layout.taocan_item_layout;
	}

	@Override
	public void bindData(int position, View convertView, ViewHolder holder) {
		
		TaoCanEntity taoCanEntity = mtaoCanList.get(position);
		RelativeLayout itemContainer = holder.findView(R.id.tc_item);
		CornerImage img = holder.findView(R.id.taocan_img);
		TextView name = holder.findView(R.id.taocan_name);
		TextView comment = holder.findView(R.id.taocan_comment);
		TextView des = holder.findView(R.id.taocan_des);
		
		img.setImageResource(taoCanEntity.getImg());
		name.setText(taoCanEntity.getName());
		comment.setText(taoCanEntity.getComment());
		des.setText(taoCanEntity.getDescription());
		
		itemContainer.setOnClickListener(this);
		

	}

	@Override
	public void onClick(View v) {
		System.out.println("click");
		Intent intent = new Intent(mContext, TaoCanDetailActivity.class);
		context.startActivity(intent);
		
	}

}

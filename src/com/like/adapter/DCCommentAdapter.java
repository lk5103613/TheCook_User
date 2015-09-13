package com.like.adapter;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.dcjd.cook.R;
import com.like.customeview.RoundImageView;
import com.like.entity.DCCommentEntity;
import com.like.network.APIS;
import com.like.network.MyNetworkUtil;

public class DCCommentAdapter extends ArrayAdapter<DCCommentEntity>{
	
	private List<DCCommentEntity> mComments;
	private LayoutInflater inflater;
	private ImageLoader mImageLoader;

	public DCCommentAdapter(Context context, Context applicationContext, List<DCCommentEntity> commentEntities) {
		super(context, 0);
		this.mComments = commentEntities;
		inflater = LayoutInflater.from(context);
		mImageLoader = MyNetworkUtil.getInstance(applicationContext).getImageLoader();
	}

	public void setList(List<DCCommentEntity> commentEntities) {
		this.mComments = commentEntities;
	}

	public List<DCCommentEntity> getList() {
		return this.mComments;
	}
	
	
	@Override
	public int getCount() {
		return mComments.size();
	}
	
	@Override
	public DCCommentEntity getItem(int position) {
		return mComments.get(position);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		DCCommentEntity comment = mComments.get(position);
		CommentHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.dc_comment_list_item, parent, false);
			holder = new CommentHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (CommentHolder) convertView.getTag();
		}
		holder.sTxtComment.setText(comment.content);
		holder.sTxtDCName.setText(comment.nick_name);
		if(!TextUtils.isEmpty(comment.add_time))
            holder.sTxtDate.setText(comment.add_time);
		if(!TextUtils.isEmpty(comment.avatar)) {
			mImageLoader.get(APIS.BASE_URL + comment.avatar, ImageLoader.getImageListener(
					holder.sDCIcon, R.drawable.car_uncheck, R.drawable.car_uncheck));
		}
		if(!TextUtils.isEmpty(comment.img0_path)) {
			mImageLoader.get(APIS.BASE_URL + comment.img0_path, ImageLoader.getImageListener(
					holder.sImg1, R.color.white, R.color.white));
		}
		if(!TextUtils.isEmpty(comment.img1_path)) {
			mImageLoader.get(APIS.BASE_URL + comment.img1_path, ImageLoader.getImageListener(
					holder.sImg2, R.color.white, R.color.white));
		}
		if(!TextUtils.isEmpty(comment.img2_path)) {
			mImageLoader.get(APIS.BASE_URL + comment.img2_path, ImageLoader.getImageListener(
					holder.sImg3, R.color.white, R.color.white));
		}
		return convertView;
	}
	
	class CommentHolder{

		public TextView sTxtDCName;
		public RoundImageView sDCIcon;
		public TextView sTxtComment;
		public TextView sTxtDate;
		public ImageView sImg1;
		public ImageView sImg2;
		public ImageView sImg3;

		public CommentHolder(View convertView){
			sTxtDCName = (TextView) convertView.findViewById(R.id.name);
			sDCIcon = (RoundImageView) convertView.findViewById(R.id.header);
			sTxtComment = (TextView) convertView.findViewById(R.id.comment);
			sTxtDate = (TextView) convertView.findViewById(R.id.date);
			sImg1 = (ImageView) convertView.findViewById(R.id.img1);
			sImg2 = (ImageView) convertView.findViewById(R.id.img2);
			sImg3 = (ImageView) convertView.findViewById(R.id.img3);
		}
		
	}

}

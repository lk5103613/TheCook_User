package com.like.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.dcjd.cook.R;
import com.like.customeview.RoundImageView;
import com.like.entity.TCDetail.Comment;
import com.like.network.APIS;
import com.like.network.MyNetworkUtil;

public class CommentAdapter extends BaseAdapter {
	
	private List<Comment> mComments;
	private LayoutInflater mInflater;
	private ImageLoader mImageLoader;
	
	public CommentAdapter(Context context, List<Comment> comments) {
		mComments = comments;
		mInflater = LayoutInflater.from(context);
		mImageLoader = MyNetworkUtil.getInstance(context).getImageLoader();
	}

	@Override
	public int getCount() {
		return mComments.size();
	}

	@Override
	public Object getItem(int position) {
		return mComments.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Comment comment = mComments.get(position);
		ViewHolder holder = null;
		if(convertView == null) {
			convertView = mInflater.inflate(R.layout.comment_item, parent, false);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
			mImageLoader.get(APIS.BASE_URL + comment.user_avatar, ImageLoader.getImageListener(holder.sUserIcon, 
					R.drawable.car_uncheck, R.drawable.car_uncheck));
			holder.sTxtName.setText(comment.nickname);
			holder.sTxtContent.setText(comment.content);
			holder.sTxtTime.setText(comment.add_time);
			List<String> imgPaths = new ArrayList<String>();
			if(!TextUtils.isEmpty(comment.img0_path))
				imgPaths.add(comment.img0_path);
			if(!TextUtils.isEmpty(comment.img1_path))
				imgPaths.add(comment.img1_path);
			if(!TextUtils.isEmpty(comment.img2_path))
				imgPaths.add(comment.img2_path);
			for(int i=0; i<imgPaths.size(); i++) {
				holder.sImgs[i].setVisibility(View.VISIBLE);
				mImageLoader.get(APIS.BASE_URL + imgPaths.get(i), ImageLoader.getImageListener(holder.sImgs[i], 
						R.color.white, R.color.white));
			}
			for(int i=imgPaths.size(); i<3; i++) {
				holder.sImgs[i].setVisibility(View.GONE);
			}
		}
		return convertView;
	}
	
	public class ViewHolder {
		public RoundImageView sUserIcon;
		public TextView sTxtName;
		public TextView sTxtContent;
		public TextView sTxtTime;
		private ImageView[] sImgs = new ImageView[3];
		
		public ViewHolder(View convertView) {
			this.sUserIcon = (RoundImageView) convertView.findViewById(R.id.header_avatar);
			this.sTxtName = (TextView) convertView.findViewById(R.id.name);
			this.sTxtContent = (TextView) convertView.findViewById(R.id.reply_content);
			this.sTxtTime = (TextView) convertView.findViewById(R.id.time);
			this.sImgs[0] = (ImageView) convertView.findViewById(R.id.img1);
			this.sImgs[1] = (ImageView) convertView.findViewById(R.id.img2);
			this.sImgs[2] = (ImageView) convertView.findViewById(R.id.img3);
		}
	}

}

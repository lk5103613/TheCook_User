package com.like.adapter;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.dcjd.cook.R;
import com.like.customeview.RoundImageView;
import com.like.entity.DCEntity;
import com.like.network.APIS;
import com.like.network.MyNetworkUtil;

public class DCAdapter extends BaseAdapter {

    private List<DCEntity> mDcs;
    private LayoutInflater mInflater;
    private ImageLoader mImageLoader;

    public DCAdapter(Context context, Context applicationContext, List<DCEntity> dcs) {
        this.mDcs = dcs;
        mInflater = LayoutInflater.from(context);
        mImageLoader = MyNetworkUtil.getInstance(applicationContext).getImageLoader();
    }

    @Override
    public int getCount() {
        return mDcs.size();
    }

    @Override
    public Object getItem(int position) {
        return mDcs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        DCEntity entity = mDcs.get(position);
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.dc_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        mImageLoader.get(APIS.BASE_URL + entity.avatar, ImageLoader.getImageListener(
                viewHolder.sDCIcon, R.drawable.car_uncheck, R.drawable.car_uncheck));
        viewHolder.sLikeCount.setText(String.valueOf(entity.zan_cnt));
        String[] caixi = entity.caixi_style.split(",");
        viewHolder.sGoodAt1.setText(caixi[0]);
        viewHolder.sGoodAt2.setText(caixi[1]);
        viewHolder.sDCName.setText(entity.truename);
        viewHolder.sDCServerCount.setText(Html.fromHtml("已经服务<font color=\"#b6341e\">"+
                entity.service_cnt+"</font>家"));
        return convertView;
    }

    public void setList(List<DCEntity> dcs) {
        this.mDcs = dcs;
    }

    public List<DCEntity> getList() {
        return this.mDcs;
    }

    public class ViewHolder {

        public RoundImageView sDCIcon;
        public TextView sDCName;
        public TextView sDCServerCount;
        public TextView sGoodAt1;
        public TextView sGoodAt2;
        public TextView sLikeCount;

        public ViewHolder(View v) {
            sDCIcon = (RoundImageView) v.findViewById(R.id.dc_icon);
            sDCName = (TextView) v.findViewById(R.id.dc_name);
            sDCServerCount = (TextView) v.findViewById(R.id.server_count);
            sGoodAt1 = (TextView) v.findViewById(R.id.good_at_1);
            sGoodAt2 = (TextView) v.findViewById(R.id.good_at_2);
            sLikeCount = (TextView) v.findViewById(R.id.like_count);
        }

    }
}

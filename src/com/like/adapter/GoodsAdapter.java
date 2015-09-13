package com.like.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Paint;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.dcjd.cook.R;
import com.like.entity.GoodEntity;
import com.like.entity.MyScoreGood;
import com.like.entity.MyScoreGood.GoodInfo;
import com.like.network.APIS;
import com.like.network.MyNetworkUtil;

/**
 * Created by Administrator on 2015/8/19.
 */
public class GoodsAdapter extends BaseAdapter {

    private List<GoodInfo> mGoods;
    private LayoutInflater mInflater;
    private ImageLoader mImgLoader;

    public GoodsAdapter(Context context, List<GoodInfo> goods) {
        this.mGoods = goods;
        mInflater = LayoutInflater.from(context);
        mImgLoader = MyNetworkUtil.getInstance(context).getImageLoader();
    }
    
    public void updateList(List<GoodInfo> goods) {
    	this.mGoods = goods;
    	notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mGoods.size();
    }

    @Override
    public Object getItem(int position) {
        return mGoods.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.goods_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();
        GoodInfo good = mGoods.get(position);
//        viewHolder.sImage1.setImageResource(good.drawableId);
        mImgLoader.get(APIS.BASE_URL + good.img, ImageLoader.getImageListener(viewHolder.sImage1, R.color.white, R.color.white));
        viewHolder.sTxtName1.setText(good.name);
        viewHolder.sGoodMoney1.setText(Html.fromHtml("会员价：  <font color='#b64616'>"+good.price+"</font> 积分"));
        viewHolder.sBadMoney1.setText("市场价：  ￥" + good.score);
        return convertView;
    }

    public class ViewHolder {
        public ImageView sImage1;
        public TextView sTxtName1;
        public TextView sGoodMoney1;
        public TextView sBadMoney1;

        public ViewHolder(View v) {
            sImage1 = (ImageView) v.findViewById(R.id.goods_img_1);
            sTxtName1 = (TextView) v.findViewById(R.id.txt_name_1);
            sGoodMoney1 = (TextView) v.findViewById(R.id.txt_good_money_1);
            sBadMoney1 = (TextView) v.findViewById(R.id.txt_bad_money_1);
            sBadMoney1.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        }
    }
}

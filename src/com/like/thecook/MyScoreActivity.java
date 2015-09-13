package com.like.thecook;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.Response.Listener;
import com.dcjd.cook.R;
import com.google.gson.reflect.TypeToken;
import com.like.adapter.GoodsAdapter;
import com.like.customeview.pulldown.PullToRefreshListView;
import com.like.entity.GoodEntity;
import com.like.entity.ListResult;
import com.like.entity.MyScoreGood;
import com.like.entity.MyScoreGood.GoodInfo;
import com.like.network.GsonUtil;

public class MyScoreActivity extends BaseActivity {

    private List<GoodEntity> mGoods;

    private PullToRefreshListView mList;
    private TextView mCurrentScore;
    private GridView mGrid;
    private int mCurrentPage = 0;
    private GoodsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_score);


        mCurrentScore = (TextView) findViewById(R.id.current_score);
        initDataSource();
//        mList = (PullToRefreshListView) findViewById(R.id.goods_list);
        mGrid = (GridView) findViewById(R.id.gridview);
//        mList.setAdapter(new GoodsAdapter(this, mGoods));
//        mList.setMode(PullToRefreshBase.Mode.BOTH);
//        mList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
//            @Override
//            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
//                if(mList.isShownHeader()) {
//                } else if (mList.isShownFooter()) {
//                }
//            }
//        });
//        mGrid.setAdapter(new GoodsAdapter(this, mGoods));
        updateList();
    }

    private void initDataSource() {
        mGoods = new ArrayList<GoodEntity>();
        GoodEntity good1 = new GoodEntity(R.drawable.goods_icon_3, "快乐鱼--优质无纺布收纳盒",  780, 38);
        GoodEntity good2 = new GoodEntity(R.drawable.goods_icon_2, "新款加高加厚双人浴巾", 500,18);
        GoodEntity good3 = new GoodEntity(R.drawable.goods_icon_3, "快乐鱼--优质无纺布收纳盒", 780, 38);
        GoodEntity good4 = new GoodEntity(R.drawable.goods_icon_2, "新款加高加厚双人浴巾", 500, 18);
        mGoods.add(good1);
        mGoods.add(good2);
        mGoods.add(good3);
        mGoods.add(good4);

        String text = "当前可用积分：  ";
        mCurrentScore.setText(Html.fromHtml(text + "<font color='#bf4722'>345</font>"));
    }
    
    private void updateList() {
    	mDataFetcher.fetchGetMyScore(mCurrentPage+"", new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				Type type = new TypeToken<ListResult<MyScoreGood>>(){}.getType();
				ListResult<MyScoreGood> result = GsonUtil.gson.fromJson(response.toString(), type);
				List<GoodInfo> goods = new ArrayList<MyScoreGood.GoodInfo>();
				for(int i=0; i<result.list.size(); i++) {
					goods.add(result.list.get(i).left);
					goods.add(result.list.get(i).right);
				}
				if(mAdapter == null) {
					mAdapter = new GoodsAdapter(mContext, goods);
					mGrid.setAdapter(mAdapter);
				} else {
					mAdapter.updateList(goods);
				}
					
			}
		}, mErrorListener);
    }

    public void back(View v) {
        this.finish();
    }


    public void toScoreRecord(View v) {
        Intent intent = new Intent(this, ScoreListActivity.class);
        startActivity(intent);
    }

}

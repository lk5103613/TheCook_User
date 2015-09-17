package com.like.thecook;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.dcjd.cook.R;
import com.like.adapter.DCCommentAdapter;
import com.like.customeview.RoundImageView;
import com.like.customeview.pulldown.PullToRefreshBase;
import com.like.customeview.pulldown.PullToRefreshListView;
import com.like.entity.DCCommentEntity;
import com.like.entity.DCEntity;
import com.like.entity.LoginResult;
import com.like.network.APIS;
import com.like.network.GsonUtil;
import com.like.network.MyNetworkUtil;

public class DcInfoActivity extends BaseActivity {

	private Context mContext;
	private Context mApplicationContext;
	private PullToRefreshListView mCommentListView;
	private DCCommentAdapter mAdapter;
	private int mCId;
	private TextView mLblCommentSize;


	// UI
	private ImageLoader mImageLoader;
	private RoundImageView mDCIcon;
	private TextView mDCName;
	private TextView mGoodAt1;
	private TextView mGoodAt2;
	private TextView mTxtServiceCount;

	private List<DCCommentEntity> mComments = new ArrayList<DCCommentEntity>();

	private void initUIViews() {
		mImageLoader = MyNetworkUtil.getInstance(mApplicationContext).getImageLoader();
		mDCIcon = (RoundImageView) findViewById(R.id.dc_icon);
		mDCName = (TextView) findViewById(R.id.txt_dc_name);
		mGoodAt1 = (TextView) findViewById(R.id.good_at_1);
		mGoodAt2 = (TextView) findViewById(R.id.good_at_2);
		mTxtServiceCount = (TextView) findViewById(R.id.txt_service_count);
		mLblCommentSize = (TextView) findViewById(R.id.comment_size);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dc_info);

		mContext = this;
		mApplicationContext = this.getApplicationContext();
		initUIViews();
		mCommentListView = (PullToRefreshListView) findViewById(R.id.dc_comment_listview);
		mCommentListView.setMode(PullToRefreshBase.Mode.BOTH);
		mCommentListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				if(mCommentListView.isShownHeader()) {
					updateDCDetail();
				} else if(mCommentListView.isShownFooter()) {
					updateDCDetail();
				}
			}
		});

		Intent intent = getIntent();
		if(intent != null) {
			mCId = intent.getIntExtra("cid", -1);
		}
		findViewById(R.id.back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
        updateDCDetail();
	}

	private void updateDCDetail() {
		showLoading(true);
		mDataFetcher.fetchDCDetail(mCId, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				 showLoading(false);
				DCEntity dcEntity = GsonUtil.gson.fromJson(response.toString(), DCEntity.class);
				updateUI(dcEntity);
				updateList(dcEntity);
                if(mCommentListView.isRefreshing())
                    mCommentListView.onRefreshComplete();
                
               
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				showLoading(false);
				Toast.makeText(mContext, "请检查网络", Toast.LENGTH_LONG).show();
				if(mCommentListView.isRefreshing())
					mCommentListView.onRefreshComplete();
				
				
			}
		});
	}

	private void updateList(DCEntity dcEntity) {
		if(mAdapter == null) {
			mAdapter = new DCCommentAdapter(mContext, mApplicationContext, dcEntity.commentList);
			mCommentListView.setAdapter(mAdapter);
			mLblCommentSize.setText("("+dcEntity.commentList.size() + ")");
		} else {
			mAdapter.setList(dcEntity.commentList);
			mAdapter.notifyDataSetChanged();
		}
	}

	private void updateUI(DCEntity dc) {
		mTxtServiceCount.setText(dc.service_cnt + "");
		String[] caixi = dc.caixi_style.split(",");
		mGoodAt1.setText(caixi[0]);
		mGoodAt2.setText(caixi[1]);
		mDCName.setText(dc.truename);
		mImageLoader.get(APIS.BASE_URL + dc.avatar, ImageLoader.getImageListener(mDCIcon,
				R.drawable.car_uncheck, R.drawable.car_uncheck));
	}
	
	public void collect(View v) {
		showLoading(true);
		mDataFetcher.fetchCollectDC(mUID, mCId+"", new Listener<JSONObject>(){
			@Override
			public void onResponse(JSONObject response) {
				showLoading(false);
				LoginResult result = GsonUtil.gson.fromJson(response.toString(), LoginResult.class);
				if(result.code != 1) {
					Toast.makeText(mContext, "收藏失败", Toast.LENGTH_LONG).show();
					return;
				} else {
					Toast.makeText(mContext, "收藏成功", Toast.LENGTH_LONG).show();
				}
			}} , mErrorListener);
	}

}

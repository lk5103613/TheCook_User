package com.like.thecook;

import java.lang.reflect.Type;
import java.util.List;

import org.json.JSONArray;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Response.Listener;
import com.dcjd.cook.R;
import com.google.gson.reflect.TypeToken;
import com.like.adapter.MyCardAdapter;
import com.like.customeview.pulldown.PullToRefreshBase;
import com.like.customeview.pulldown.PullToRefreshBase.OnRefreshListener;
import com.like.customeview.pulldown.PullToRefreshListView;
import com.like.entity.MyCardEntity;
import com.like.network.GsonUtil;

public class MyCardActivity extends BaseActivity {
	
	private MyCardAdapter mAdapter;
	private PullToRefreshListView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_card);
        
        mList = (PullToRefreshListView) findViewById(R.id.card_list);
        
        mList.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				updateList();
			}
		});
        
        updateList();
    }

    public void back(View v) {
        this.finish();
    }

    public void toCardUsage(View v) {
        Intent intent = new Intent(this, CardUsageActivity.class);
        startActivity(intent);
    }
    
    private void updateList() {
    	showLoading(true);
    	mDataFetcher.fetchGetMyCard(mUID, new Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray response) {
				showLoading(false);
				Type type = new TypeToken<List<MyCardEntity>>(){}.getType();
				List<MyCardEntity> entities = GsonUtil.gson.fromJson(response.toString(), type);
				if(mAdapter == null) {
					mAdapter = new MyCardAdapter(mContext, entities);
					mList.setAdapter(mAdapter);
				} else {
					mAdapter.updateList(entities);
				}
				if(mList.isRefreshing())
					mList.onRefreshComplete();
			}
		}, mErrorListener);
    }

}


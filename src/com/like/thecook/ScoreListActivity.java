package com.like.thecook;

import java.lang.reflect.Type;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Response.Listener;
import com.dcjd.cook.R;
import com.google.gson.reflect.TypeToken;
import com.like.adapter.ScoreRecordAdapter;
import com.like.customeview.pulldown.PullToRefreshBase;
import com.like.customeview.pulldown.PullToRefreshListView;
import com.like.entity.ListResult;
import com.like.entity.ScoreRecord;
import com.like.network.GsonUtil;

public class ScoreListActivity extends BaseActivity {

    private Context mContext;
    private PullToRefreshListView mList;
    private int mCurrentPage = 0;
    private ScoreRecordAdapter mAdapter;

    private List<ScoreRecord> mScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_list);

        mContext = this;
        mList = (PullToRefreshListView) findViewById(R.id.score_list);
        mList.setMode(PullToRefreshBase.Mode.BOTH);
        mList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if(mList.isShownHeader()) {
                	updateList();
                } else if(mList.isShownFooter()) {
                	mCurrentPage++;
                	updateList();
                }
            }
        });
        updateList();
    }

    public void back(View v) {
        this.finish();
    }
    
    private void updateList() {
    	showLoading(true);
    	mDataFetcher.fetchScoreList(""+mCurrentPage, mUID, new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				showLoading(false);
				System.out.println(response);
				Type type = new TypeToken<ListResult<ScoreRecord>>(){}.getType();
				ListResult<ScoreRecord> results = GsonUtil.gson.fromJson(response.toString(), type);
				if(mAdapter == null) {
					mAdapter = new ScoreRecordAdapter(mContext, results.list);
					mList.setAdapter(mAdapter);
				} else {
					mAdapter.updateList(results.list);
				}
				if(mList.isRefreshing())
					mList.onRefreshComplete();
			}
		}, mErrorListener);
    }

}

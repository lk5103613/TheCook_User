package com.like.fragment;

import java.lang.reflect.Type;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dcjd.cook.R;
import com.google.gson.reflect.TypeToken;
import com.like.adapter.MenuListAdapter;
import com.like.customeview.pulldown.PullToRefreshBase;
import com.like.customeview.pulldown.PullToRefreshListView;
import com.like.entity.ListResult;
import com.like.entity.MenuEntity;
import com.like.network.DataFetcher;
import com.like.network.GsonUtil;
import com.like.thecook.BaseActivity;

public class MenuFragment extends BaseFragment {

    private Context mContext;
    private Context mApplicationContext;
	private PullToRefreshListView mMenuListView;
	private List<MenuEntity> mMenuEntities;
	private TextView mBtnCall;
	private MenuListAdapter mAdapter;
	private Handler mHandler;
    private DataFetcher mDataFetcher;
    private ViewGroup mCall;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.menu_fragment, container, false);
		mMenuListView = (PullToRefreshListView) layout.findViewById(R.id.menu_list);
		mCall = (ViewGroup) layout.findViewById(R.id.call);
		mBtnCall = (TextView) layout.findViewById(R.id.call_now);
		mBtnCall.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+ BaseActivity.PHONE_NUMBER)); 
                startActivity(intent);  
			}
		});
		mCall.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+ BaseActivity.PHONE_NUMBER)); 
                startActivity(intent);  
			}
		});
		mHandler = new Handler();
        mContext = getActivity();
        mApplicationContext = getActivity().getApplicationContext();
        mDataFetcher = DataFetcher.getInstance(mApplicationContext);
		mMenuListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                updateMenu();
			}
		});
        updateMenu();

		return layout;
	}

    private void updateMenu() {
    	showLoading(true);
        mDataFetcher.fetchMenu(0, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
            	showLoading(false);
                Type type = new TypeToken<ListResult<MenuEntity>>(){}.getType();
                ListResult<MenuEntity> menuList = GsonUtil.gson.fromJson(response.toString(), type);
                if(mAdapter == null) {
                    mAdapter = new MenuListAdapter(mContext, mApplicationContext, menuList.resultList);
                    mMenuListView.setAdapter(mAdapter);
                } else {
                    mAdapter.setList(menuList.resultList);
                    mAdapter.notifyDataSetChanged();
                }
                if(mMenuListView.isRefreshing())
                    mMenuListView.onRefreshComplete();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	showLoading(false);
                Toast.makeText(mContext, "请检查网络", Toast.LENGTH_LONG).show();
            }
        });
    }


}

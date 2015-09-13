package com.like.fragment;

import java.lang.reflect.Type;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dcjd.cook.R;
import com.google.gson.reflect.TypeToken;
import com.like.adapter.MenuListAdapter;
import com.like.customeview.pulldown.PullToRefreshBase;
import com.like.customeview.pulldown.PullToRefreshBase.Mode;
import com.like.customeview.pulldown.PullToRefreshBase.OnRefreshListener2;
import com.like.customeview.pulldown.PullToRefreshListView;
import com.like.entity.ListResult;
import com.like.entity.MenuEntity;
import com.like.network.DataFetcher;
import com.like.network.GsonUtil;

public class CollectTcFragment extends Fragment {

	private PullToRefreshListView mMenuListView;
	private LinearLayout mFooterView;
	private List<MenuEntity> mMenuEntities;
	private MenuListAdapter mAdapter;
	private DataFetcher mDataFetcher;
	private Context mContext;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.tc_fragment, container, false);
		
		mContext = getActivity();
		mDataFetcher = DataFetcher.getInstance(mContext);

		mMenuListView = (PullToRefreshListView) layout.findViewById(R.id.menu_list);
		mMenuListView.setMode(Mode.BOTH);
		mMenuListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				updateMenu();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				updateMenu();
			}

		});
		mFooterView = (LinearLayout) LayoutInflater.from(getActivity())
				.inflate(R.layout.menu_list_footer, null);
//		mMenuListView.addFooterView(mFooterView);
		updateMenu();
		return layout;
	}

	private void updateMenu() {
		mDataFetcher.fetchMenu(0, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				Type type = new TypeToken<ListResult<MenuEntity>>() {
				}.getType();
				ListResult<MenuEntity> menuList = GsonUtil.gson.fromJson(
						response.toString(), type);
				mAdapter = new MenuListAdapter(mContext,
						mContext, menuList.resultList);
				mMenuListView.setAdapter(mAdapter);
			if (mMenuListView.isRefreshing())
					mMenuListView.onRefreshComplete();
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(mContext, "请检查网络", Toast.LENGTH_LONG).show();
			}
		});
	}	

}

package com.like.thecook;

import java.lang.reflect.Type;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.tv.TvContract.Programs;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dcjd.cook.R;
import com.google.gson.reflect.TypeToken;
import com.like.adapter.DCAdapter;
import com.like.customeview.pulldown.PullToRefreshBase;
import com.like.customeview.pulldown.PullToRefreshListView;
import com.like.entity.DCEntity;
import com.like.entity.ListResult;
import com.like.network.DataFetcher;
import com.like.network.GsonUtil;
import com.nineoldandroids.animation.ValueAnimator;

public class DcActivity extends BaseActivity {

	private Context mContext;
    private Context mApplicationContext;
	private View mCurrentSelectedTopMenu = null;
	private PullToRefreshListView mDCList;
    private DCAdapter mAdapter;

    private DataFetcher mDataFetcher;

	private ViewGroup mGoodAtContainer;
	private ViewGroup mGoodAtContent;
	private ViewGroup mServerCountContainer;
	private ViewGroup mServerCountContent;
	private ViewGroup mLocationContainer;
	private ViewGroup mLocationContent;

    private int mCurrentPage = 0;
    private String mCurrentCaixi = "川";
    private String mCurrentLocation = "";
    private String mCurrentCnt = "";

	private View mCover;

	private PopupWindow mPopupWindow;

	private Handler mHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dc);
		mContext = this;
        mApplicationContext = getApplicationContext();
        mDataFetcher = DataFetcher.getInstance(mApplicationContext);
		mHandler = new Handler();
		mDCList = (PullToRefreshListView) findViewById(R.id.dc_list);
		mDCList.setMode(PullToRefreshBase.Mode.BOTH);
		mGoodAtContainer = (ViewGroup) findViewById(R.id.good_at_container);
		mGoodAtContent = (ViewGroup) findViewById(R.id.good_at_content);
		mServerCountContainer = (ViewGroup) findViewById(R.id.server_count_container);
		mServerCountContent = (ViewGroup) findViewById(R.id.server_count_content);
		mLocationContainer = (ViewGroup) findViewById(R.id.location_container);
		mLocationContent = (ViewGroup) findViewById(R.id.location_content);
		mCover =  findViewById(R.id.cover);
		mDCList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if (mDCList.isShownHeader()) {
                    // 下拉刷新
                    updateDC();
                } else if (mDCList.isShownFooter()) {
                    // 加载更多
                    
                    mCurrentPage++;
                    updateDC();
                }
            }
        });
        updateDC();
		mDCList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				DCEntity dc = mAdapter.getList().get(position-1);
                Intent intent = new Intent(mContext, DcInfoActivity.class);
				intent.putExtra("cid", dc.cid);
                startActivity(intent);
            }
        });

	}

    private void updateDC() {
    	showLoading(true);
        mDataFetcher.fetchDCInfo(Request.Method.POST, mCurrentPage, mCurrentCaixi, mCurrentLocation, mCurrentCnt,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                    	showLoading(false);
                        Type type = new TypeToken<ListResult<DCEntity>>(){}.getType();
                        ListResult<DCEntity> dcList = GsonUtil.gson.fromJson(response.toString(), type);
                        if(mAdapter == null){
                            mAdapter = new DCAdapter(mContext, mApplicationContext, dcList.resultList);
                            mDCList.setAdapter(mAdapter);
                        } else {
                            mAdapter.setList(dcList.resultList);
                            mAdapter.notifyDataSetChanged();
                        }
                        if(mDCList.isRefreshing())
                            mDCList.onRefreshComplete();
                        
                    }
                }, mErrorListener);
    }


	private void setMarginTop(View v, int marginTop) {
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)v.getLayoutParams();
		lp.setMargins(lp.leftMargin, marginTop, lp.rightMargin, lp.bottomMargin);
		v.setLayoutParams(lp);
	}

	private ValueAnimator generateValueAnimator(final View rootView, final View target, int duration, final int height, final int direction) {
		ValueAnimator valueAnimator = null;
		if(direction > 0) {
			valueAnimator = ValueAnimator.ofInt(-height, 0);
		} else {
			valueAnimator = ValueAnimator.ofInt(0, -height);
		}
		valueAnimator.setTarget(target);
		valueAnimator.setDuration(duration);
		valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (Integer) valueAnimator.getAnimatedValue();
                setMarginTop(target, value);
                if (direction < 0) {
                    if (value == -height)
                        rootView.setVisibility(View.GONE);
                }
            }
        });
		return valueAnimator;
	}

	private void showView(final View rootView, final View target, final int duration, boolean show) {
		if(show) {
			if (rootView.getVisibility() == View.VISIBLE)
				return;
			rootView.setVisibility(View.VISIBLE);
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					int height = target.getHeight();
					generateValueAnimator(rootView, target, duration, height, 1).start();
				}
			}, 0);
		} else {
			if(rootView.getVisibility() == View.GONE || rootView.getVisibility() == View.INVISIBLE)
				return;
			int height = target.getHeight();
			generateValueAnimator(rootView, target, duration, height, -1).start();
		}
	}


	private void setCurrentSelectedTopMenu(View v) {
		if(mCurrentSelectedTopMenu != null) {
			mCurrentSelectedTopMenu.setSelected(false);
			if(mCurrentSelectedTopMenu == v) {
				mCurrentSelectedTopMenu = null;
				return;
			}
		}
		if(v == null) {
			mCurrentSelectedTopMenu = null;
			return;
		}
		v.setSelected(true);
		mCurrentSelectedTopMenu = v;
	}

	public void selectGoodAt(View v) {
		if(mCurrentSelectedTopMenu == v) {
			showView(mGoodAtContainer, mGoodAtContent, 300, false);
		} else {
			showView(mGoodAtContainer, mGoodAtContent, 300, true);
			showView(mLocationContainer, mLocationContent, 0, false);
			showView(mServerCountContainer, mServerCountContent, 0, false);
		}
		setCurrentSelectedTopMenu(v);
	}

	public void selectServerCount(View v) {
		if(mCurrentSelectedTopMenu == v)
			showView(mServerCountContainer, mServerCountContent, 300, false);
		else {
			showView(mServerCountContainer, mServerCountContent, 300, true);
			showView(mLocationContainer, mLocationContent, 0, false);
			showView(mGoodAtContainer, mGoodAtContent, 0, false);
		}
		setCurrentSelectedTopMenu(v);
	}

	public void selectLocation(View v) {
		if(mCurrentSelectedTopMenu == v)
			showView(mLocationContainer, mLocationContent, 300, false);
		else {
			showView(mLocationContainer, mLocationContent, 300, true);
			showView(mServerCountContainer, mServerCountContent, 0, false);
			showView(mGoodAtContainer, mGoodAtContent, 0, false);
		}
		setCurrentSelectedTopMenu(v);
	}

	public void hideAll(View v) {
		setCurrentSelectedTopMenu(null);
		showView(mLocationContainer, mLocationContent, 300, false);
		showView(mServerCountContainer, mServerCountContent, 300, false);
		showView(mGoodAtContainer, mGoodAtContent, 300, false);
	}

	public void back(View v) {
		this.finish();
	}

	public void openMenu(View v) {
		showWindow(v);
	}

	private void showWindow(View parent) {
		mCover.setVisibility(View.VISIBLE);
		if (mPopupWindow == null) {
			LayoutInflater layoutInflater = (LayoutInflater) getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			View view = layoutInflater.inflate(R.layout.dc_popup, null);
			mPopupWindow = new PopupWindow(view, 300, 310, true);
			mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
				@Override
				public void onDismiss() {
					mCover.setVisibility(View.GONE);
				}
			});
		}
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
		WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		// 显示的位置为:屏幕的宽度的一半-PopupWindow的高度的一半
//		int xPos = windowManager.getDefaultDisplay().getWidth() / 2
//				- mPopupWindow.getWidth() / 2;
		mPopupWindow.showAsDropDown(parent, -230, 0);
	}

    public void choiceGoodAt(View v) {
        switch (v.getId()) {
            case R.id.xiang:
                mCurrentCaixi = "湘";
                break;
            case R.id.lu:
                mCurrentCaixi = "鲁";
                break;
            case R.id.chuan:
                mCurrentCaixi = "川";
                break;
            case R.id.ben:
                mCurrentCaixi = "本";
                break;
            case R.id.bei:
                mCurrentCaixi = "北";
                break;
            case R.id.yue:
                mCurrentCaixi = "粤";
                break;
        }
        updateDC();
        hideAll(v);
    }
    
    public void choiceCnt(View v) {
    	switch (v.getId()) {
		case R.id.cnt1:
			mCurrentCnt = "1";
			break;
		case R.id.cnt2:
			mCurrentCnt = "2";
			break;
		case R.id.cnt3:
			mCurrentCnt = "3";
			break;
		case R.id.cnt4:
			mCurrentCnt = "4";
			break;
		default:
			break;
		}
        updateDC();
        hideAll(v);
    }
    
    public void choiceLocation(View v) {
    	switch(v.getId()) {
    	case R.id.location1:
    		mCurrentLocation = "海曙区";
    		break;
    	case R.id.location2:
    		mCurrentLocation = "肃州区";
    		break;
    	case R.id.location3:
    		mCurrentLocation = "奉化区";
    		break;
    	case R.id.location4:
    		mCurrentLocation = "镇海区";
    		break;
    	}
    }

}

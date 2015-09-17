package com.like.thecook;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.dcjd.cook.R;
import com.like.adapter.CommentAdapter;
import com.like.entity.Meishi;
import com.like.entity.ShoppingCartEntity;
import com.like.entity.TCDetail;
import com.like.network.APIS;
import com.like.network.DataFetcher;
import com.like.network.GsonUtil;
import com.like.network.MyNetworkUtil;
import com.like.util.DensityUtil;

public class TaoCanDetailActivity extends BaseActivity {

	private Context mContext;

    private WebView mWebView;
    private ImageView mTCImg;
    private TextView mPackageName;
    private TextView mPrice;
    private TextView mSoldCount;
    private Button mBtnStyle0;
    private Button mBtnStyle1;
    private ListView mList;
    private DataFetcher mDataFetcher;
    private TCDetail mDetail;
    private CommentAdapter mAdapter;
    private View mNeedKnownSelect;
    private View mDCSelect;
    private View mDetailsSelect;
    private View mReplySelect;
    private View mCurrentTab;
    private PopupWindow mDialog;
    private ViewGroup mAddCar;
    private String mMsId;
    private Meishi mMS;
//    private TextView mTxtReplyCount;
    
    private int mPackId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tao_can_detail2);
		mContext = this;
		mDataFetcher = DataFetcher.getInstance(mContext);

        mWebView = (WebView) findViewById(R.id.webview);
        mTCImg = (ImageView) findViewById(R.id.img);
        mPackageName = (TextView) findViewById(R.id.package_name);
        mPrice = (TextView) findViewById(R.id.price);
        mSoldCount = (TextView) findViewById(R.id.sold_cnt);
        mBtnStyle0 = (Button) findViewById(R.id.style0);
        mBtnStyle1 = (Button) findViewById(R.id.style1);
        mList = (ListView) findViewById(R.id.comment_list);
        mNeedKnownSelect = findViewById(R.id.need_known_select);
        mDCSelect = findViewById(R.id.dc_select);
        mDetailsSelect = findViewById(R.id.details_select);
        mReplySelect = findViewById(R.id.reply_select);
        mAddCar = (ViewGroup) findViewById(R.id.add_car);
        View popupView = LayoutInflater.from(mContext).inflate(R.layout.tc_detail_popup, null);
        mBtnStyle0.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mBtnStyle1.setBackgroundResource(R.drawable.canju_option_bg);
				mBtnStyle0.setBackgroundResource(R.drawable.canju_option_selected_bg);
			}
		});
        mBtnStyle1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mBtnStyle0.setBackgroundResource(R.drawable.canju_option_bg);
				mBtnStyle1.setBackgroundResource(R.drawable.canju_option_selected_bg);
			}
		});
        popupView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mDialog.dismiss();
			}
		});
        mDialog = new PopupWindow(popupView, 
        		LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//		mDialog.setFocusable(true);
		mDialog.setOutsideTouchable(true);
//        mTxtReplyCount = (TextView) findViewById(R.id.txt_reply_count);
        
        Intent intent = getIntent();
        if(intent != null) {
        	mPackId = intent.getIntExtra("pacId", -1);
        	mMsId = intent.getStringExtra("msId");
        }
        if(mPackId != -1)
	        mDataFetcher.fetchTCDetail(mPackId, new Listener<JSONObject>() {
				@Override
				public void onResponse(JSONObject response) {
					mDetail = GsonUtil.gson.fromJson(response.toString(), TCDetail.class);
					setupView(mDetail);
				}
			}, mErrorListener);
        if(!TextUtils.isEmpty(mMsId)) {
        	mDataFetcher.fetchMSDetail(mMsId, new Listener<JSONObject>() {
				@Override
				public void onResponse(JSONObject response) {
					Meishi ms = GsonUtil.gson.fromJson(response.toString(), Meishi.class);
					setupView(ms);
				}
			}, mErrorListener);
        }
        
	}
	
	private void setupView(Meishi ms) {
		MyNetworkUtil.getInstance(mContext).getImageLoader().get(APIS.BASE_URL + ms.avatar, 
				ImageLoader.getImageListener(mTCImg, R.color.white, R.color.white));
		mPackageName.setText(ms.name);
		mPrice.setText(ms.price);
		mSoldCount.setText(ms.sold_cnt);
//		mAdapter = new CommentAdapter(mContext, detail.commentList);
//		mList.setAdapter(mAdapter);
	}
	
	private void setupView(TCDetail detail) {
		MyNetworkUtil.getInstance(mContext).getImageLoader().get(APIS.BASE_URL + detail.img_path, 
				ImageLoader.getImageListener(mTCImg, R.color.white, R.color.white));
		mPackageName.setText(detail.packname);
		mPrice.setText(detail.price);
		mSoldCount.setText(detail.sold_cnt);
		mAdapter = new CommentAdapter(mContext, detail.commentList);
		mList.setAdapter(mAdapter);
		if(detail.style == 0) {
			mBtnStyle1.setBackgroundResource(R.drawable.canju_option_bg);
			mBtnStyle0.setBackgroundResource(R.drawable.canju_option_selected_bg);
		} else {
			mBtnStyle0.setBackgroundResource(R.drawable.canju_option_bg);
			mBtnStyle1.setBackgroundResource(R.drawable.canju_option_selected_bg);
		}
	}
	
	private void showPopup() {
		if(mDialog.isShowing()) {
			mDialog.dismiss();
		} else {
			int offsetY = DensityUtil.dip2px(mContext, 85);
			mDialog.showAsDropDown(mAddCar, 0, -offsetY);
		}
	}
	
	public void toCar(View view){
		showPopup();
		if(mDetail == null)
			return;
        final ShoppingCartEntity entity = new ShoppingCartEntity(mDetail.packname, Float.valueOf(mDetail.price), 1, 1, APIS.BASE_URL + mDetail.img_path);
        new Thread(new Runnable() {
            @Override
            public void run() {
                mShoppingCartManager.addToShoppingCart(entity);
            }
        }).start();
        Toast.makeText(mContext, "添加成功", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if(mDialog.isShowing())
			mDialog.dismiss();
	}

	public void selectTab(View v) {
		if(mCurrentTab != null) 
			mCurrentTab.setVisibility(View.GONE);
		switch (v.getId()) {
		case R.id.need_known:
			mNeedKnownSelect.setVisibility(View.VISIBLE);
			mCurrentTab = mNeedKnownSelect;
			break;
		case R.id.details:
			mDetailsSelect.setVisibility(View.VISIBLE);
			mCurrentTab = mDetailsSelect;
			break;
		case R.id.dc:
			mDCSelect.setVisibility(View.VISIBLE);
			mCurrentTab = mDCSelect;
			break;
		case R.id.reply:
			mReplySelect.setVisibility(View.VISIBLE);
			mCurrentTab = mReplySelect;
			break;
		default:
			break;
		}
	}
	
	public void jumpToCar(View v) {
		this.finish();
		Intent intent = new Intent(mContext, IndexActivity.class);
		intent.putExtra("tab_index", 2);
		startActivity(intent);
	}
	
	public void toOrderInfo(View v) {
		Intent intent = new Intent(mContext, CarOrderActivity.class);
		String json = "";
		if(mDetail != null) {
			json = GsonUtil.gson.toJson(mDetail);
			intent.putExtra("tc", json);
		} else if(mMS != null) {
			json = GsonUtil.gson.toJson(mMS);
			intent.putExtra("ms", json);
		}
		startActivity(intent);
	}
	
	public void back(View v) {
		this.finish();
	}
	
	public void call(View v) {
		callNumber();
	}
}

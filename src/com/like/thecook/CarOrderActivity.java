package com.like.thecook;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.Listener;
import com.dcjd.cook.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.like.adapter.CarOrderListAdapter;
import com.like.entity.LoginResult;
import com.like.entity.Meishi;
import com.like.entity.ShoppingCartEntity;
import com.like.entity.TCDetail;
import com.like.network.GsonUtil;
import com.like.storage.ShoppingCartManager;
import com.like.util.DateUtil;
import com.like.wheel.widget.adapters.ArrayWheelAdapter;
import com.like.wheel.widget.views.OnWheelChangedListener;
import com.like.wheel.widget.views.WheelView;

public class CarOrderActivity extends BaseActivity {

	private LinearLayout layout1, layout2;
	private PopupWindow mTimeWindow;
	private PopupWindow mNumberWindow;
	private PopupWindow mAreaWindow;
	private PopupWindow mPayWindow;
	private TextView mLblTime;
	private int mScreenWidth;
	private int mScreenHeight;
	private TextView mLblNumber;
	private ViewGroup mDropdownNumber;
	private ViewGroup mDropdownArea;
	private ViewGroup mDropdownPay;
	private TextView mTelTextView;
	private TextView mLblPay;
	private TextView mArea;
	private WheelView mWheel1, mWheel3;
//	private WheelView mWheel2;
	private ArrayWheelAdapter mAdapter1, mAdapter3;
//	private WheelView mAdapter2;
	private String mMonth;
//	private String mWeek;
	private String mTime;
	private CarOrderListAdapter mCarOrderAdapter;
	private ListView mListView;

	private EditText special_memoEt;

	private ShoppingCartManager mManager;
	private List<ShoppingCartEntity> mCarEntities;
	private TextView mPrice;
	
	private Meishi mMS;
	private TCDetail mTC;
		

	public static Gson gson = new Gson();
	private Handler mHandler = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.car_order);

		special_memoEt = (EditText) findViewById(R.id.special_memoEt);

		mListView = (ListView) findViewById(R.id.order_list);
		mHandler = new Handler();
		layout1 = (LinearLayout) findViewById(R.id.layout1);
		layout2 = (LinearLayout) findViewById(R.id.layout2);
		mTelTextView = (TextView) findViewById(R.id.tel);
		mTelTextView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		mDropdownNumber = (ViewGroup) findViewById(R.id.dropdown_number);
		mDropdownArea = (ViewGroup) findViewById(R.id.dropdown_area);
		mDropdownPay = (ViewGroup) findViewById(R.id.select_pay_method);
		mLblNumber = (TextView) findViewById(R.id.lbl_number);
		mArea = (TextView) findViewById(R.id.area);
		mLblPay = (TextView) findViewById(R.id.lbl_pay);
		mLblTime = (TextView) findViewById(R.id.lbl_time);
		mPrice = (TextView) findViewById(R.id.price);

		// test
		layout1.setVisibility(View.VISIBLE);
		layout2.setVisibility(View.GONE);

		// 获取屏幕和PopupWindow的width和height
		mScreenWidth = getWindowManager().getDefaultDisplay().getWidth();
		mScreenHeight = getWindowManager().getDefaultDisplay().getHeight();
		Intent intent = getIntent();
		if(intent != null) {
			String tcJson = intent.getStringExtra("tc");
			String msJson = intent.getStringExtra("ms");
			if(tcJson != null) {
				mTC = GsonUtil.gson.fromJson(tcJson, TCDetail.class);
				mPrice.setText("订单总额：￥" + mTC.price);
			} else if(msJson != null) {
				mMS = GsonUtil.gson.fromJson(msJson, Meishi.class);
				mPrice.setText("订单总额：￥"+mMS.price);
			}
			if(intent.getStringExtra("selected_values") != null) {
				String jsonArray = intent.getStringExtra("selected_values");
				Type type = new TypeToken<List<ShoppingCartEntity>>(){}.getType();
				mCarEntities = GsonUtil.gson.fromJson(jsonArray, type);
			} else {
				mCarEntities = new ArrayList<ShoppingCartEntity>();
			}
		}
		// test end

		findViewById(R.id.back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		findViewById(R.id.sb).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String uid = mLoginSharef.getString(UID, "");
//				String diningTime = mMonth + " " + mWeek + " " + mTime;
				String diningTime = mMonth + " " + mTime;
				String serviceCnt = mLblNumber.getText().toString();
				String specialComment = special_memoEt.getText().toString();
				String kitCnt = mArea.getText().toString();
				String json = "";
				if (mCarEntities != null && mCarEntities.size() != 0) {
					json = GsonUtil.gson.toJson(mCarEntities);
				}
				mDataFetcher.fetchSaveOrder(uid, diningTime, serviceCnt,
						kitCnt, specialComment, json,
						new Listener<JSONObject>() {
							@Override
							public void onResponse(JSONObject response) {
								LoginResult result = GsonUtil.gson.fromJson(
										response.toString(), LoginResult.class);
								if (result.code == 1) {
									Toast toast = Toast.makeText(
											getApplicationContext(),
											"转向支付宝...", Toast.LENGTH_LONG);
									toast.setGravity(Gravity.CENTER, 0, 0);
									toast.show();
									
									Intent intent = new Intent(mContext, AliPayActivity.class);
									startActivity(intent);
								} else {
									Toast toast = Toast.makeText(
											getApplicationContext(), "提交失败",
											Toast.LENGTH_LONG);
									toast.setGravity(Gravity.CENTER, 0, 0);
									toast.show();
								}
							}
						}, mErrorListener);

			}
		});

		findViewById(R.id.cancel).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				layout1.setVisibility(View.VISIBLE);
				layout2.setVisibility(View.GONE);
			}
		});

		mCarOrderAdapter = new CarOrderListAdapter(this, mCarEntities);
		mListView.setAdapter(mCarOrderAdapter);
		setListViewHeightBasedOnChildren(mListView);
	}

	public void selectTime(View parent) {
		int[] location = new int[2];
		parent.getLocationOnScreen(location);

		int popwidth = mScreenWidth / 6 * 2;
		if (mTimeWindow == null) {
			LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = layoutInflater.inflate(R.layout.order_time_popup, null);
			initWheel(view);
			mTimeWindow = new PopupWindow(view, popwidth, 300, true);
			mTimeWindow
					.setOnDismissListener(new PopupWindow.OnDismissListener() {
						@Override
						public void onDismiss() {
							backgroundAlpha(1);
							mLblTime.setText(mMonth + "  " + mTime);
						}
					});
		}
		mTimeWindow.setOutsideTouchable(true);
		mTimeWindow.setBackgroundDrawable(new BitmapDrawable());
		backgroundAlpha(1);
		mTimeWindow.showAtLocation(parent, Gravity.NO_GRAVITY, location[0]
				- popwidth + parent.getWidth(),
				location[1] + parent.getHeight() - 10);
	}

	public void selectPay(View parent) {
		if (mPayWindow == null) {
			LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = layoutInflater.inflate(R.layout.dropdown_dialog, null);
			ListView optionList = (ListView) view
					.findViewById(R.id.option_list);
			final List<String> options = getPays();
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
					R.layout.dropdown_option_item, options);
			optionList.setAdapter(adapter);
			mPayWindow = new PopupWindow(view, 300, 200);
			optionList.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					mLblPay.setText(options.get(position));
					if (mPayWindow.isShowing())
						mPayWindow.dismiss();
				}
			});
		}
		mPayWindow.setOutsideTouchable(true);
		mPayWindow.setBackgroundDrawable(new BitmapDrawable());
		backgroundAlpha(1);
		mPayWindow.showAsDropDown(mDropdownPay, -200, 0);
	}

	public List<String> getNumbers() {
		List<String> numbers = new ArrayList<String>();
		numbers.add("1");
		numbers.add("2");
		numbers.add("3");
		numbers.add("4");
		numbers.add("5");
		return numbers;
	}

	public List<String> getPays() {
		List<String> pays = new ArrayList<String>();
		pays.add("支付宝");
		pays.add("银行卡");
		return pays;
	}

	public List<String> getAreas() {
		List<String> areas = new ArrayList<String>();
		areas.add("30");
		areas.add("60");
		areas.add("100");
		areas.add("150");
		areas.add("200");
		return areas;
	}

	public void selectNum(View v) {
		if (mNumberWindow == null) {
			LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = layoutInflater.inflate(R.layout.dropdown_dialog, null);
			ListView optionList = (ListView) view
					.findViewById(R.id.option_list);
			final List<String> options = getNumbers();
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
					R.layout.dropdown_option_item, options);
			optionList.setAdapter(adapter);
			optionList.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					String selectContent = options.get(position);
					mLblNumber.setText(selectContent);
					if (mNumberWindow.isShowing())
						mNumberWindow.dismiss();
				}
			});
			mNumberWindow = new PopupWindow(view, 200, 300);
		}
		mNumberWindow.setOutsideTouchable(true);
		mNumberWindow.setBackgroundDrawable(new BitmapDrawable());
		backgroundAlpha(1);
		mNumberWindow.showAsDropDown(mDropdownNumber, -100, 0);
	}

	public void selectArea(View v) {
		if (mAreaWindow == null) {
			LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = layoutInflater.inflate(R.layout.dropdown_dialog, null);
			ListView optionList = (ListView) view
					.findViewById(R.id.option_list);
			final List<String> options = getAreas();
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
					R.layout.dropdown_option_item, options);
			optionList.setAdapter(adapter);
			optionList.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					mArea.setText(options.get(position));
					if (mAreaWindow.isShowing())
						mAreaWindow.dismiss();
				}
			});
			mAreaWindow = new PopupWindow(view, 250, 300);
		}
		mAreaWindow.setOutsideTouchable(true);
		mAreaWindow.setBackgroundDrawable(new BitmapDrawable());
		backgroundAlpha(1);
		mAreaWindow.showAsDropDown(mDropdownArea, -150, 0);
	}

	public void backgroundAlpha(float bgAlpha) {
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = bgAlpha;
		getWindow().setAttributes(lp);
	}

	private int[] colors = new int[] { R.color.order_pop_color_1,
			R.color.order_pop_color_2, R.color.order_pop_color_3 };

	private void setCurrentItem(int position, ArrayWheelAdapter adapter,
			int gravity) {
		int totalCount = adapter.getItemsCount();
		int minPosition = position - 2 < 0 ? 0 : position - 2;
		int maxPosition = position + 2 > totalCount - 1 ? totalCount - 1
				: position + 2;
		for (int i = minPosition; i <= maxPosition; i++) {
			int offset = Math.abs(position - i);
			List<TextView> vs = adapter.getTextViews();
			TextView v = vs.get(i);
			if (v == null) {
				continue;
			}
			v.setTextSize(12 - 2 * offset);
			v.setGravity(gravity);
			v.setTextColor(getResources().getColor(colors[offset]));
		}
	}

	private void initWheel(View parent) {
		mWheel1 = (WheelView) parent.findViewById(R.id.wheel1);
//		mWheel2 = (WheelView) parent.findViewById(R.id.wheel2);
		mWheel3 = (WheelView) parent.findViewById(R.id.wheel3);
		mWheel1.setDrawShadow(false);
//		mWheel2.setDrawShadow(false);
		mWheel3.setDrawShadow(false);

		Calendar currentC = DateUtil.toCalendar(new Date());
		final String[] months = new String[10];
		final String[] weeks = new String[10];
		final String[] times = new String[] { "08:00", "09:00", "10:00",
				"11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00",
				"18: 00" };
		mTime = times[0];
		for (int i = 0; i < 10; i++) {
			String month = DateUtil.getMonth(currentC);
			String day = DateUtil.getDay(currentC);
			months[i] = month + "月" + day + "日";
			String week = DateUtil.getWeek(currentC);
			weeks[i] = week;
			if (i == 0) {
				mMonth = months[0];
//				mWeek = weeks[0];
			}
			currentC = DateUtil.getNextDay(currentC);
		}

		mAdapter1 = new ArrayWheelAdapter<String>(this, months, 50);
//		mAdapter2 = new ArrayWheelAdapter<String>(this, weeks, 50);
		mAdapter3 = new ArrayWheelAdapter<String>(this, times, 50);
		mWheel1.setViewAdapter(mAdapter1);
//		mWheel2.setViewAdapter(mAdapter2);
		mWheel3.setViewAdapter(mAdapter3);

		setCurrentItem(mWheel1.getCurrentItem(), mAdapter1, Gravity.RIGHT);

//		setCurrentItem(mWheel2.getCurrentItem(), mAdapter2, Gravity.CENTER);
		setCurrentItem(mWheel3.getCurrentItem(), mAdapter3, Gravity.LEFT);

		mWheel1.addChangingListener(new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int position = wheel.getCurrentItem();
				setCurrentItem(position, mAdapter1, Gravity.RIGHT
						| Gravity.CENTER_VERTICAL);
				mMonth = months[position];
			}
		});
//		mWheel2.addChangingListener(new OnWheelChangedListener() {
//			@Override
//			public void onChanged(WheelView wheel, int oldValue, int newValue) {
//				int position = wheel.getCurrentItem();
//				setCurrentItem(position, mAdapter2, Gravity.CENTER);
//				mWeek = weeks[position];
//			}
//		});
		mWheel3.addChangingListener(new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int position = mWheel3.getCurrentItem();
				setCurrentItem(position, mAdapter3, Gravity.LEFT
						| Gravity.CENTER_VERTICAL);
				mTime = times[position];
			}
		});
	}

	public void pay(View v) {
		//to alipay
		Intent intent = new Intent(this, AliPayActivity.class);
		startActivity(intent);
	}

	public void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

}

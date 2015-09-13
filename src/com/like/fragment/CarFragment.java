package com.like.fragment;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dcjd.cook.R;
import com.like.adapter.CarListAdapter;
import com.like.adapter.CarListAdapter.CheckListener;
import com.like.customeview.pulldown.PullToRefreshBase;
import com.like.customeview.pulldown.PullToRefreshListView;
import com.like.entity.ShoppingCartEntity;
import com.like.network.GsonUtil;
import com.like.storage.ShoppingCartManager;
import com.like.thecook.CarInfoActivity;
import com.like.thecook.CarOrderActivity;

public class CarFragment extends BaseFragment implements CheckListener {

	private Context mContext;
	private List<ShoppingCartEntity> mCarEntities;
	private PullToRefreshListView mCarListView;
	private CarListAdapter mAdapter;
	private CheckBox mCheckAll;
	private LinearLayout mAddMoreView;
	private TextView mSettle;
	private TextView mDelete;
	private ShoppingCartManager mManager;
	private ViewGroup mXiaDan;
	private CarFragmentListener mListener;
	private TextView mPrice;
	private int mSettleCnt = 0;
	
	private void settle() {
		if(mSettleCnt == 0) {
			Toast.makeText(mContext, "请选择购物车中的商品", Toast.LENGTH_SHORT).show();
			return;
		}
		Intent intent = new Intent(getActivity(), CarOrderActivity.class);
    	List<ShoppingCartEntity> entities = mAdapter.getSelectedEntities();
    	if(entities.size() != 0) {
    		String json = GsonUtil.gson.toJson(entities);
    		intent.putExtra("selected_values", json);
    	}
		startActivity(intent);
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View layout = inflater.inflate(R.layout.car_fragment, container, false);
		mContext = getActivity();
		mCarListView = (PullToRefreshListView) layout.findViewById(R.id.car_list);
		mXiaDan = (ViewGroup) layout.findViewById(R.id.xd);
		mSettle = (TextView) layout.findViewById(R.id.settle);
		mCheckAll = (CheckBox) layout.findViewById(R.id.check_all);
		mDelete = (TextView) layout.findViewById(R.id.delete);
		mPrice = (TextView) layout.findViewById(R.id.price);
		mSettle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				settle();
			}
		});
		mDelete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				List<ShoppingCartEntity> cars = mAdapter.getSelectedEntities();
				for(int i=0; i<cars.size(); i++) {
					check(cars.get(i), false);
					mShoppingCartManager.remove(cars.get(i));
				}
				cars = mShoppingCartManager.getAll();
				mAdapter.updateList(cars);
			}
		});
		layout.findViewById(R.id.add_more).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mListener != null)
					mListener.changeTab(1);
			}
		});
		mXiaDan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				settle();
			}
		});
		mCheckAll.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mAdapter.selectAll(isChecked);
			}
		});
		mManager = ShoppingCartManager.getInstance(mContext);
		update();

		mCarListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                startActivity(new Intent(getActivity(), CarInfoActivity.class));
            }
        });

        mCarListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                mCarListView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mCarListView.onRefreshComplete();
                    }
                }, 1000);
            }
        });
		return layout;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mListener = (CarFragmentListener) activity;
	}
	
	public interface CarFragmentListener {
		void changeTab(int index);
	}

	private void update() {
		mCarEntities = mManager.getAll();
        if(mAdapter == null) {
            mAdapter = new CarListAdapter(getActivity(), mCarEntities, this);
            mCarListView.setAdapter(mAdapter);
        } else {
            mAdapter.setList(mCarEntities);
            mAdapter.notifyDataSetChanged();
        }
	}

	@Override
	public void check(ShoppingCartEntity entity, boolean isChecked) {
		float prePrice = Float.valueOf(mPrice.getText().toString().replace("￥", ""));
		float price = 0;
		if(isChecked) {
			mSettleCnt += entity.cnt;
			price = entity.price * entity.cnt + prePrice;
		} else {
			mSettleCnt -= entity.cnt;
			price = prePrice - (entity.price * entity.cnt);
		}
		mPrice.setText("￥" + price);
		mSettle.setText("结算("+mSettleCnt+")");
	}
	
	

}

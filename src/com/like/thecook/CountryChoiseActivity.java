package com.like.thecook;

import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dcjd.cook.R;
import com.like.entity.City;
import com.like.entity.Districts;
import com.like.entity.Provience;
import com.like.storage.CityManager;
import com.like.storage.DistrictsManager;
import com.like.storage.ProvienceManager;
import com.like.wheel.widget.adapters.ArrayWheelAdapter;
import com.like.wheel.widget.views.OnWheelChangedListener;
import com.like.wheel.widget.views.WheelView;

public class CountryChoiseActivity extends BaseActivity {

    private RelativeLayout mContainer;
    private WheelView mWheel1, mWheel2, mWheel3;
    private ArrayWheelAdapter mAdapter1, mAdapter2, mAdapter3;
    private ProvienceManager mProvienceManager;
    private CityManager mCityManager;
    private DistrictsManager mDistrictsManager;
    private List<Provience> mPros;
    private List<City> mCities;
    private List<Districts> mDistricts;

    private int defaultTextSize = 18;
    private Handler mHandler;
    
    private int mProId = -1;
    private int mCityId = -1;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_choise);
        
        mProvienceManager = ProvienceManager.getInstance(mContext);
        mPros = mProvienceManager.getAll();
        mCityManager = CityManager.getInstance(mContext);
        mDistrictsManager = DistrictsManager.getInstance(mContext);

        mContainer = (RelativeLayout) findViewById(R.id.container);
        mWheel1 = (WheelView) findViewById(R.id.wheel1);
        mWheel2 = (WheelView) findViewById(R.id.wheel2);
        mWheel3 = (WheelView) findViewById(R.id.wheel3);
        
        mWheel1.setVisibleItems(7);
        mWheel2.setVisibleItems(7);
        mWheel3.setVisibleItems(7);
        mHandler = new Handler();

//        mAdapter1 = new ArrayWheelAdapter<String>(this, new String[]{"浙江"},80);
//        mAdapter2 = new ArrayWheelAdapter<String>(this, new String[]{"宁波"},80);
//        mAdapter3 = new ArrayWheelAdapter<String>(this, new String[]{"海曙区", "江东区", "江北区", "北仑区", "镇海区", "鄞州区", "余姚市", "慈溪市", "奉化市", "象山县", "宁海县"},80);
//        mWheel1.setViewAdapter(mAdapter1);
//        mWheel2.setViewAdapter(mAdapter2);
//        mWheel3.setViewAdapter(mAdapter3);
        initWheels(mProId, mCityId);
        mWheel1.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                int position = wheel.getCurrentItem();
                mProId = mPros.get(position).id;
                mCityId = -1;
                initWheels(mProId, mCityId);
                setCurrentItem(position, mAdapter1);
            }
        });
        mWheel2.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                int position = wheel.getCurrentItem();
                mCityId = mCities.get(position).id;
                initWheels(mProId, mCityId);
                setCurrentItem(position, mAdapter2);
            }
        });
        mWheel3.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                int position = mWheel3.getCurrentItem();
                setCurrentItem(position, mAdapter3);
            }
        });
    }
    
    private void initWheels(int proId, int cityId) {
    	String[] prosArr = new String[mPros.size()];
    	for(int i=0; i<mPros.size(); i++) {
    		prosArr[i] = mPros.get(i).name;
    	}
    	if(proId == -1)
	    	mProId = mPros.get(0).id;
    	else
    		mProId = proId;
    	mAdapter1 = new ArrayWheelAdapter<String>(this, prosArr ,90);
    	mWheel1.setViewAdapter(mAdapter1);
        setCurrentItem(mWheel1.getCurrentItem(), mAdapter1);
    	
    	mCities = mCityManager.getCityByProId(mProId);
    	String[] cityArr = new String[mCities.size()];
    	for(int i=0; i<mCities.size(); i++) {
    		cityArr[i] = mCities.get(i).name;
    	}
    	if(cityId == -1)
	    	mCityId = mCities.get(0).id;
    	else
    		mCityId = cityId;
    	mAdapter2 =  new ArrayWheelAdapter<String>(this, cityArr ,90);
    	mWheel2.setViewAdapter(mAdapter2);
        setCurrentItem(mWheel2.getCurrentItem(), mAdapter2);
    	
    	List<Districts> dis = mDistrictsManager.getDistrictsByCityId(mCityId);
    	String[] disArr = new String[dis.size()];
    	for(int i=0; i<dis.size(); i++) {
    		disArr[i] = dis.get(i).name;
    	}
    	mAdapter3 =   new ArrayWheelAdapter<String>(this, disArr ,90);
    	mWheel3.setViewAdapter(mAdapter3);
        setCurrentItem(mWheel3.getCurrentItem(), mAdapter3);
    }

    private int[] colors = new int[]{R.color.choise_color_4, R.color.choise_color_3, R.color.choise_color_2, R.color.choise_color_1};

    private void setCurrentItem(int position, ArrayWheelAdapter adapter) {
        int totalCount = adapter.getItemsCount();
        int minPosition = position - 3 < 0 ? 0 : position - 3;
        int maxPosition = position + 3 > totalCount - 1 ? totalCount - 1 : position + 3;
        for(int i=minPosition; i<=maxPosition; i++) {
            int offset = Math.abs(position - i);
            List<TextView> vs = adapter.getTextViews();
            TextView v = vs.get(i);
            if(v == null) {
                continue;
            }
            v.setTextSize(defaultTextSize + 6 - 5 * offset);
            v.setTextColor(getResources().getColor(colors[offset]));
        }
    }
    
    public void back(View v) {
    	this.finish();
    }

}

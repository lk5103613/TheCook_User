package com.like.thecook;

import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dcjd.cook.R;
import com.like.wheel.widget.adapters.ArrayWheelAdapter;
import com.like.wheel.widget.views.OnWheelChangedListener;
import com.like.wheel.widget.views.WheelView;

public class CountryChoiseActivity extends BaseActivity {

    private RelativeLayout mContainer;
    private WheelView mWheel1, mWheel2, mWheel3;
    private ArrayWheelAdapter mAdapter1, mAdapter2, mAdapter3;

    private int defaultTextSize = 18;
    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_choise);

        mContainer = (RelativeLayout) findViewById(R.id.container);
        mWheel1 = (WheelView) findViewById(R.id.wheel1);
        mWheel2 = (WheelView) findViewById(R.id.wheel2);
        mWheel3 = (WheelView) findViewById(R.id.wheel3);
        mWheel1.setVisibleItems(7);
        mWheel2.setVisibleItems(7);
        mWheel3.setVisibleItems(7);
        mHandler = new Handler();

        mAdapter1 = new ArrayWheelAdapter<String>(this, new String[]{"浙江"},80);
        mAdapter2 = new ArrayWheelAdapter<String>(this, new String[]{"宁波"},80);
        mAdapter3 = new ArrayWheelAdapter<String>(this, new String[]{"海曙区", "江东区", "江北区", "北仑区", "镇海区", "鄞州区", "余姚市", "慈溪市", "奉化市", "象山县", "宁海县"},80);
        mWheel1.setViewAdapter(mAdapter1);
        mWheel2.setViewAdapter(mAdapter2);
        mWheel3.setViewAdapter(mAdapter3);
        setCurrentItem(mWheel1.getCurrentItem(), mAdapter1);
        setCurrentItem(mWheel2.getCurrentItem(), mAdapter2);
        setCurrentItem(mWheel3.getCurrentItem(), mAdapter3);
        mWheel1.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                int position = wheel.getCurrentItem();
                setCurrentItem(position, mAdapter1);
            }
        });
        mWheel2.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                int position = wheel.getCurrentItem();
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

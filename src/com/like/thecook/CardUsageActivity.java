package com.like.thecook;

import android.os.Bundle;
import android.view.View;

import com.dcjd.cook.R;

public class CardUsageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_usage);
    }

    public void back(View v) {
        this.finish();
    }

}

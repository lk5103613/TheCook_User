package com.like.thecook;

import android.os.Bundle;
import android.view.View;

import com.dcjd.cook.R;

public class InviteActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
    }

    public void back(View v) {
        this.finish();
    }

}

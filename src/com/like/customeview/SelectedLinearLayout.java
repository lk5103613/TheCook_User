package com.like.customeview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class SelectedLinearLayout extends LinearLayout{
	
	public SelectedLinearLayout(Context context) {
		super(context);
	}
	public SelectedLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SelectedLinearLayout(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	
	@Override
	public void setSelected(boolean selected) {
		int count = getChildCount();
		for (int i = 0; i < count; i++) {
			getChildAt(i).setSelected(selected);
		}
		super.setSelected(selected);
	}

}

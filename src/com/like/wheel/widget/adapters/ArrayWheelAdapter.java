/*
 *  Copyright 2011 Yuri Kanivets
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.like.wheel.widget.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * The simple Array wheel adapter
 * 
 * @param <T>
 *            the element type
 */
public class ArrayWheelAdapter<T> extends AbstractWheelTextAdapter {

	// items
	private T items[];
	private List<TextView> views;
	private int itemHeight;

	/**
	 * Constructor
	 * 
	 * @param context
	 *            the current context
	 * @param items
	 *            the items
	 */
	public ArrayWheelAdapter(Context context, T items[], int itemHeihgt) {
		super(context);
		this.itemHeight = itemHeihgt;

		views = new ArrayList<TextView>();
		// setEmptyItemResource(TEXT_VIEW_ITEM_RESOURCE);
		this.items = items;
		for(int i=0; i<items.length; i++) {
			TextView textView = new TextView(context);
			configureTextView(textView);
			views.add(textView);
		}
	}

	public List<TextView> getTextViews() {
		return views;
	}

	@Override
	public CharSequence getItemText(int index) {
		if (index >= 0 && index < items.length) {
			T item = items[index];
			if (item instanceof CharSequence) {
				return (CharSequence) item;
			}
			return item.toString();
		}
		return null;
	}


	@Override
	public View getItem(int index, View convertView, ViewGroup parent) {
		if (index >= 0 && index < getItemsCount()) {
			TextView textView = views.get(index);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, itemHeight);
            lp.gravity = Gravity.CENTER;
            textView.setLayoutParams(lp);
			CharSequence text = getItemText(index);
			if (text == null) {
				text = "";
			}
            textView.setText(text);
			return textView;
        }
        return null;
	}

	@Override
	public int getItemsCount() {
		return items.length;
	}
}

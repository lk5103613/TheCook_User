package com.like.fragment;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.dcjd.cook.R;
import com.like.customeview.RoundImageView;
import com.like.entity.PersonInfo;
import com.like.network.APIS;
import com.like.network.GsonUtil;
import com.like.thecook.AboutUsActivity;
import com.like.thecook.InviteActivity;
import com.like.thecook.MyAddressActivity;
import com.like.thecook.MyCardActivity;
import com.like.thecook.MyCollectActivity;
import com.like.thecook.MyScoreActivity;
import com.like.thecook.PerInfoActivity;

public class MyFragment extends BaseFragment {

	private Context mContext;
	private ViewGroup mPersonInfo;
	
	private RoundImageView mIcon;
	private TextView mLblName;
	private TextView mLblPhone;

	private ViewGroup mMyScore;
	private ViewGroup mMyCard;
	private ViewGroup mInvite;
	private ViewGroup mAboutUs;
	private ViewGroup mMyCollect;
	private ViewGroup mMyLocation;
	
	private PersonInfo mPerson;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View layout = inflater.inflate(R.layout.my_fragment, container, false);
		mContext = getActivity();

		mMyScore = (ViewGroup) layout.findViewById(R.id.my_score);
		mMyCard = (ViewGroup) layout.findViewById(R.id.my_card);
		mInvite = (ViewGroup) layout.findViewById(R.id.invite_friends);
		mAboutUs = (ViewGroup) layout.findViewById(R.id.about_us);
		mMyCollect = (ViewGroup) layout.findViewById(R.id.my_collect);
		mMyLocation = (ViewGroup) layout.findViewById(R.id.my_location);
		mIcon = (RoundImageView) layout.findViewById(R.id.my_icon);
		mLblName = (TextView) layout.findViewById(R.id.my_name);
		mLblPhone = (TextView) layout.findViewById(R.id.phone);

		mMyScore.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), MyScoreActivity.class);
				startActivity(intent);
			}
		});

		mMyCard.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, MyCardActivity.class);
				startActivity(intent);
			}
		});
		mInvite.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, InviteActivity.class);
				startActivity(intent);
			}
		});
		mMyLocation.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, MyAddressActivity.class);
				startActivity(intent);
			}
		});
		mAboutUs.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, AboutUsActivity.class);
				startActivity(intent);
			}
		});
		mMyCollect.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, MyCollectActivity.class);
				startActivity(intent);
			}
		});

		mPersonInfo = (ViewGroup) layout.findViewById(R.id.person_info);
		mPersonInfo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mPerson == null)
					return;
				Intent intent = new Intent(getActivity(), PerInfoActivity.class);
				String json = GsonUtil.gson.toJson(mPerson);
				intent.putExtra("person_info", json);
				startActivity(intent);
			}
		});
		return layout;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		getPerInfo();
	}
	
	private void getPerInfo() {
		showLoading(true);
		mDataFetcher.fetchPerInfo(mUID, new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				showLoading(false);
				mPerson = GsonUtil.gson.fromJson(response.toString(), PersonInfo.class);
				setupView(mPerson);
			}
		}, mErrorListener);
	}
	
	private void setupView(PersonInfo person) {
		mImgLoader.get(APIS.BASE_URL + "/" + person.avatar, ImageLoader.getImageListener(mIcon, R.drawable.car_uncheck, R.drawable.car_uncheck));
		mLblName.setText(person.nickname);
		mLblPhone.setText(person.mp);
	}

}

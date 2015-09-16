package com.like.thecook;

import java.io.File;

import org.json.JSONObject;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.dcjd.cook.R;
import com.like.customeview.RoundImageView;
import com.like.entity.LoginResult;
import com.like.entity.PersonInfo;
import com.like.network.APIS;
import com.like.network.GsonUtil;
import com.like.util.DateUtil;
import com.like.util.UploadUtil;

public class PerInfoActivity extends BaseActivity implements OnClickListener {
	private RelativeLayout mPhotoContainer;
	private TextView mTakePhoto;
	private TextView mFromPhone;
	private TextView mCancel;
	private RoundImageView mHeaderImg;
	private String mImgPath;
	private PersonInfo mPerson;
	private TextView mLblName;
	private TextView mLblPhoneNum;

	private static final int REQUEST_TAKE_PHOTO = 1;
	private static final int REQUEST_FROM_FILE = REQUEST_TAKE_PHOTO + 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_per_info);
		
		Intent intent = getIntent();
		String json = intent.getStringExtra("person_info");
		mPerson = GsonUtil.gson.fromJson(json, PersonInfo.class);
		
		mPhotoContainer = (RelativeLayout) findViewById(R.id.photo_container);
		mTakePhoto = (TextView) findViewById(R.id.take_photo);
		mFromPhone = (TextView) findViewById(R.id.from_phone);
		mCancel = (TextView) findViewById(R.id.cancel);
		mHeaderImg = (RoundImageView) findViewById(R.id.header);
		mLblName = (TextView) findViewById(R.id.name);
		mLblPhoneNum = (TextView) findViewById(R.id.phone_num);

		mPhotoContainer.setOnClickListener(this);
		mTakePhoto.setOnClickListener(this);
		mFromPhone.setOnClickListener(this);
		mCancel.setOnClickListener(this);
		
		setupView();
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_TAKE_PHOTO:
			if (data != null) {
				Uri uri;
				Bitmap bitmap = null;
				Bundle extras = data.getExtras();
			    bitmap = (Bitmap) extras.get("data");
				if (data.getData() != null) { 
		            uri = data.getData();
		        } 
		        else { 
		            uri  = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null,null));      
		        } 
				String[] filePathColumn = { MediaStore.Images.Media.DATA };
				final Cursor cursor = getContentResolver().query(uri,
						filePathColumn, null, null, null);
				cursor.moveToFirst();
				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				String picturePath = cursor.getString(columnIndex);
				final File file = new File(picturePath);
				mHeaderImg.setImageBitmap(bitmap);
				new AsyncTask<File, Void, String>() {
					@Override
					protected String doInBackground(File... params) {
						File uploadFile = params[0];
						String fileName=uploadFile.getName();
					    String prefix=fileName.substring(fileName.lastIndexOf("."));
						final String serverImgName = DateUtil.getImgName();
						try {
							UploadUtil.post(uploadFile, APIS.UPLOAD_AVATAR,
									serverImgName);
							mImgPath = "upload/" + serverImgName + prefix;
							mPerson.avatar = mImgPath;
						} catch(Exception e) {
							e.printStackTrace();
						}
						cursor.close();
						return null;
					}
					
					@Override
					protected void onPostExecute(String result) {
						mDataFetcher.fetchUpdateAvatar(mImgPath, mUID, new Listener<JSONObject>() {
							@Override
							public void onResponse(JSONObject response) {
								LoginResult result = GsonUtil.gson.fromJson(response.toString(), LoginResult.class);
								if(result.code == 1) {
									Toast.makeText(mContext, "更新头像成功", Toast.LENGTH_LONG).show();
									return;
								}
							}
						}, mErrorListener);
					}
				}.execute(file);
			}

			break;
		case REQUEST_FROM_FILE:
			if (data != null) {
				Uri uri = data.getData();
				Bitmap bitmap = null;
				try {
					bitmap = MediaStore.Images.Media.getBitmap(
							this.getContentResolver(), uri);
					String[] filePathColumn = { MediaStore.Images.Media.DATA };

					final Cursor cursor = getContentResolver().query(uri,
							filePathColumn, null, null, null);
					cursor.moveToFirst();
					int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
					String picturePath = cursor.getString(columnIndex);
					final File file = new File(picturePath);
					mHeaderImg.setImageBitmap(bitmap);
					new AsyncTask<File, Void, String>() {
						@Override
						protected String doInBackground(File... params) {
							File uploadFile = params[0];
							String fileName=uploadFile.getName();
						    String prefix=fileName.substring(fileName.lastIndexOf("."));
							final String serverImgName = DateUtil.getImgName();
							try {
								UploadUtil.post(uploadFile, APIS.UPLOAD_AVATAR,
										serverImgName);
								mImgPath = "upload/" + serverImgName + prefix;
								mPerson.avatar = mImgPath;
							} catch(Exception e) {
								e.printStackTrace();
							}
							cursor.close();
							return null;
						}
						
						@Override
						protected void onPostExecute(String result) {
							mDataFetcher.fetchUpdateAvatar(mImgPath, mUID, new Listener<JSONObject>() {
								@Override
								public void onResponse(JSONObject response) {
									LoginResult result = GsonUtil.gson.fromJson(response.toString(), LoginResult.class);
									if(result.code == 1) {
										Toast.makeText(mContext, "更新头像成功", Toast.LENGTH_LONG).show();
										return;
									}
								}
							}, mErrorListener);
						}
					}.execute(file);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			break;
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private void setupView() {
		mImgLoader.get(APIS.BASE_URL + "/" + mPerson.avatar, ImageLoader.getImageListener(mHeaderImg, R.drawable.car_uncheck, R.drawable.car_uncheck));
		mLblName.setText(mPerson.truename);
		mLblPhoneNum.setText(mPerson.mp);
	}
	
	public void takePhoto(View v) {
		mPhotoContainer.setVisibility(View.VISIBLE);
	}

	public void back(View v) {
		this.finish();
	}

	public void changePwd(View v) {
		Intent intent = new Intent(this, ChangePwdActivity.class);
		startActivity(intent);
	}

	public void changeNumber(View v) {
		Intent intent = new Intent(this, ChangePhoneActivity.class);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.photo_container:
			mPhotoContainer.setVisibility(View.GONE);
			break;
		case R.id.take_photo:
			mPhotoContainer.setVisibility(View.GONE);
			takePhoto();
			break;
		case R.id.from_phone:
			mPhotoContainer.setVisibility(View.GONE);
			fromPhone();
			break;
		case R.id.cancel:
			mPhotoContainer.setVisibility(View.GONE);
			break;

		default:
			break;
		}
	}

	private void takePhoto() {
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		startActivityForResult(intent, REQUEST_TAKE_PHOTO);
	}

	private void fromPhone() {
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");// 相片类型
		startActivityForResult(intent, REQUEST_FROM_FILE);
	}

}

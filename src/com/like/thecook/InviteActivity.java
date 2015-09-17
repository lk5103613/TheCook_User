package com.like.thecook;

import android.os.Bundle;
import android.view.View;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

import com.dcjd.cook.R;

public class InviteActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invite);

		ShareSDK.initSDK(this);
	}

	public void back(View v) {
		this.finish();
	}

	public void showOnekeyshare(String platform, boolean silent) {
		OnekeyShare oks = new OnekeyShare();
		oks.setAddress("12345678901");
		oks.setTitle("大厨家到");
		oks.setTitleUrl("http://sharesdk.cn");
		oks.setText("大厨家到很不错哦");
		oks.setUrl("http://sharesdk.cn");
		oks.setComment("很好");
		oks.setSilent(silent);
		// 指定分享平台，和slient一起使用可以直接分享到指定的平台
		if (platform != null) {
			oks.setPlatform(platform);
		}
		// 去除注释可通过OneKeyShareCallback来捕获快捷分享的处理结果
		// oks.setCallback(new OneKeyShareCallback());
		// 通过OneKeyShareCallback来修改不同平台分享的内容
		oks.show(mContext);
	}

	public void share(View v) {
		switch (v.getId()) {
		case R.id.share_to_qq:
			showOnekeyshare(QQ.NAME, false);
			break;
		case R.id.share_to_wechat:
			showOnekeyshare(Wechat.NAME, false);
			break;
		case R.id.share_to_weibo:
			showOnekeyshare(SinaWeibo.NAME, true);
			break;
		default:
			break;
		}
	}

}

package com.like.thecook;

import cn.jpush.android.api.JPushInterface;
import android.app.Application;

public class MyApplication extends Application {
	
	 @Override
	    public void onCreate() {    	     
			 System.out.println("oncreate");
	         super.onCreate();
	         
	         JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
	         JPushInterface.init(this);     		// 初始化 JPush
	    }

}

package com.like.network;

public class UrlParamGenerator {
	
	public static String getPath(String url, String ...params) {
		String result = url;
		for(int i=1; i<=params.length; i++) {
			result = result.replace("%" + i, params[i - 1]);
		}
		return result;
	}

}

package com.like.network;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.like.util.DateUtil;

public class DataFetcher {

    private static DataFetcher mDataFetcher;
    private Context mApplicationContext;
    private String mChartName = "utf-8";

    private DataFetcher(Context applicationContext) {
        this.mApplicationContext = applicationContext;
    }

    public static DataFetcher getInstance(Context applicationContext) {
        if(mDataFetcher == null) {
            mDataFetcher = new DataFetcher(applicationContext);
        }
        return mDataFetcher;
    }

    public void fetchDCInfo(int method, int page, String caixi_style, String location, String cnt,
                            Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
    	try {
			caixi_style = URLEncoder.encode(caixi_style, mChartName);
			location = URLEncoder.encode(location, mChartName);
		} catch (UnsupportedEncodingException e1) {
		}
    	String url = UrlParamGenerator.getPath(APIS.GET_DC, page+"", caixi_style, location, cnt);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, listener, errorListener);
        MyNetworkUtil.getInstance(mApplicationContext).addToRequstQueue(request);
    }

    public void fetchMenu(int page, Response.Listener<JSONObject> listener,
                          Response.ErrorListener errorListener) {
        String url = APIS.GET_MENU + page;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, listener,
                errorListener);
        MyNetworkUtil.getInstance(mApplicationContext).addToRequstQueue(request);
    }

    public void fetchDCDetail(int cid, Response.Listener<JSONObject> listener,
                              Response.ErrorListener errorListener) {
        String url = APIS.GET_DC_DETAIL + cid;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, listener,
                errorListener);
        MyNetworkUtil.getInstance(mApplicationContext).addToRequstQueue(request);
    }
    
    public void fetchTCDetail(int packageId, Listener<JSONObject> listener, ErrorListener errorListener) {
    	String url = APIS.GET_TC_DETAIL + packageId;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, listener,
                errorListener);
        MyNetworkUtil.getInstance(mApplicationContext).addToRequstQueue(request);
    }
    
    public void fetchLoginData(String mp, String pwd, Listener<JSONObject> listener, ErrorListener errorListener) { 
    	String url = UrlParamGenerator.getPath(APIS.LOGIN, mp, pwd);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, listener,
                errorListener);
        MyNetworkUtil.getInstance(mApplicationContext).addToRequstQueue(request);
    }
    
    public void fetchOrder(String uid, Listener<JSONObject> listener, ErrorListener errorListener) {
    	String url = UrlParamGenerator.getPath(APIS.GET_ORDER, uid);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, listener,
                errorListener);
        MyNetworkUtil.getInstance(mApplicationContext).addToRequstQueue(request);
    }
    
    public void fetchOrderDetail(String orderId, Listener<JSONObject> listener, ErrorListener errorListener) {
    	String url = UrlParamGenerator.getPath(APIS.GET_ORDER_DETAIL, orderId);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, listener,
                errorListener);
        MyNetworkUtil.getInstance(mApplicationContext).addToRequstQueue(request);
    }
    
    public void fetchConfirmOrder(String orderId,  Listener<JSONObject> listener, ErrorListener errorListener) {
    	String url = UrlParamGenerator.getPath(APIS.CONFIRM_ORDER, orderId);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, listener,
                errorListener);
        MyNetworkUtil.getInstance(mApplicationContext).addToRequstQueue(request);
    }
    
    public void fetchRegData(final String mp, final String pwd, Listener<JSONObject> listener, ErrorListener errorListener) {
    	String url = UrlParamGenerator.getPath(APIS.REG, mp, pwd);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, listener,
                errorListener);
        MyNetworkUtil.getInstance(mApplicationContext).addToRequstQueue(request);
    }
    
    public void fetchSaveOrder(String uid, String diningTime, String serviceCnt, String kitCnt, String specialComment, String json, 
    		Listener<JSONObject> listener, ErrorListener errorListener) {
    	try {
			json =  URLEncoder.encode(json, mChartName);
			diningTime = URLEncoder.encode(diningTime, mChartName).replace("%3A", ":");
			specialComment = URLEncoder.encode(specialComment, mChartName);
			kitCnt = URLEncoder.encode(kitCnt, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	String url = UrlParamGenerator.getPath(APIS.SAVE_ORDER, uid, diningTime, serviceCnt, kitCnt, specialComment, json);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, listener,
                errorListener);
        MyNetworkUtil.getInstance(mApplicationContext).addToRequstQueue(request);
    }
    
    public void fetchAddAddress(String uid, String name, String mp, String district, String province, String city, 
    		String detailAddress, Listener<JSONObject> listener, ErrorListener errorListener) {
    	try {
			name = URLEncoder.encode(name, mChartName);
			province = URLEncoder.encode(province, mChartName);
			city = URLEncoder.encode(city, mChartName);
			district = URLEncoder.encode(district, mChartName);
			detailAddress = URLEncoder.encode(detailAddress, mChartName);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	String url = UrlParamGenerator.getPath(APIS.ADD_ADDRESS, uid, name, mp, district, province, city, detailAddress);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, listener,
                errorListener);
        MyNetworkUtil.getInstance(mApplicationContext).addToRequstQueue(request);
    }
    
    public void fetchAddress(String uid, Listener<JSONArray> listener, ErrorListener errorListener) {
    	String url = UrlParamGenerator.getPath(APIS.GET_ALL_ADDRESS, uid);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, url, null, listener,
                errorListener);
        MyNetworkUtil.getInstance(mApplicationContext).addToRequstQueue(request);
    }
    
    public void fetchCollectDC(String uid, String cookId, Listener<JSONObject> listener, ErrorListener errorListener) {
    	String url = UrlParamGenerator.getPath(APIS.COLLECT_DC, uid, cookId);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, listener,
                errorListener);
        MyNetworkUtil.getInstance(mApplicationContext).addToRequstQueue(request);
    }
    
    public void fetchCollectedDC(String uid, Listener<JSONObject> listener, ErrorListener errorListener) {
    	String url = UrlParamGenerator.getPath(APIS.GET_COLLECTED_DC, uid);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, listener,
                errorListener);
        MyNetworkUtil.getInstance(mApplicationContext).addToRequstQueue(request);
    }
    
    public void fetchCollectedMS(String uid, Listener<JSONObject> listener, ErrorListener errorListener) {
    	String url = UrlParamGenerator.getPath(APIS.GET_COLLECTED_MS, uid);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, listener,
                errorListener);
        MyNetworkUtil.getInstance(mApplicationContext).addToRequstQueue(request);
    }
    
    public void fetchUploadAvatar(final File file, Listener<String> listener, ErrorListener errorListener) {
    	RequestQueue mSingleQueue = Volley.newRequestQueue(mApplicationContext, new MultiPartStack());
    	MultiPartStringRequest multiPartRequest = new MultiPartStringRequest(
                Request.Method.PUT, APIS.UPLOAD_AVATAR, listener, errorListener) {
            @Override
            public Map<String, File> getFileUploads() {
            	Map<String, File> params = new HashMap<String, File>();
            	params.put("filename", file);
                return params;
            }

            @Override
            public Map<String, String> getStringUploads() {
            	String name=DateUtil.getImgName();
            	Map<String, String> params = new HashMap<String, String>();
            	params.put("serverImgName", name);
                return params;
            }

        };
        mSingleQueue.add(multiPartRequest);
    }
    
    public void fetchUpdateAvatar(String avatar, String uid, Listener<JSONObject> listener, ErrorListener errorListener) {
    	String url = UrlParamGenerator.getPath(APIS.UPDATE_AVATAR, avatar, uid);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, listener,
                errorListener);
        MyNetworkUtil.getInstance(mApplicationContext).addToRequstQueue(request);
    }
    
    public void fetchChangePwd(String oldPwd, String newPwd, Listener<JSONObject> listener, ErrorListener errorListener) {
    	String url = UrlParamGenerator.getPath(APIS.CHANGE_PWD, oldPwd, newPwd);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, listener,
                errorListener);
        MyNetworkUtil.getInstance(mApplicationContext).addToRequstQueue(request);
    }
    
    public void fetchMSList(String currentPage, Listener<JSONObject> listener, ErrorListener errorListener) {
    	String url = UrlParamGenerator.getPath(APIS.GET_MS_LIST, currentPage);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, listener,
                errorListener);
        MyNetworkUtil.getInstance(mApplicationContext).addToRequstQueue(request);
    }
    
    public void fetchZXList(String currentPage, Listener<JSONObject> listener, ErrorListener errorListener) {
    	String url = UrlParamGenerator.getPath(APIS.GET_ZX, currentPage);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, listener,
                errorListener);
        MyNetworkUtil.getInstance(mApplicationContext).addToRequstQueue(request);
    }
    
    public void fetchScoreList(String currentPage, String uid, Listener<JSONObject> listener, ErrorListener errorListener) {
    	String url = UrlParamGenerator.getPath(APIS.GET_SCORE_LIST, currentPage, uid);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, listener,
                errorListener);
        MyNetworkUtil.getInstance(mApplicationContext).addToRequstQueue(request);
    }
    
    public void fetchGetMyScore(String currentPage,  Listener<JSONObject> listener, ErrorListener errorListener) {
    	String url = UrlParamGenerator.getPath(APIS.GET_MY_SCORE, currentPage);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, listener,
                errorListener);
        MyNetworkUtil.getInstance(mApplicationContext).addToRequstQueue(request);
    }
    
    public void fetchGetMyCard(String uid, Listener<JSONArray> listener, ErrorListener errorListener) {
    	String url = UrlParamGenerator.getPath(APIS.GET_MY_CARD, uid);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, url, null, listener,
                errorListener);
        MyNetworkUtil.getInstance(mApplicationContext).addToRequstQueue(request);
    }
    
    public void fetchPerInfo(String uid, Listener<JSONObject> listener, ErrorListener errorListener) {
    	String url = UrlParamGenerator.getPath(APIS.GET_PER_INFO, uid);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, listener,
                errorListener);
        MyNetworkUtil.getInstance(mApplicationContext).addToRequstQueue(request);
    }
    
    public void fetchUpdatePhone(String uid, String phone, Listener<JSONObject> listener, ErrorListener errorListener) {
    	String url = UrlParamGenerator.getPath(APIS.UPDATE_PHONE, uid, phone);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, listener,
                errorListener);
        MyNetworkUtil.getInstance(mApplicationContext).addToRequstQueue(request);
    }
    
    public void fetchSendCode(String phone, String code, Listener<String> listener, ErrorListener errorListener) {
    	String msg = "【大厨家到】尊敬的用户您好,本次验证码是:" + code;
    	try {
			msg = URLEncoder.encode(msg, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	String url = UrlParamGenerator.getPath(APIS.SEND_CODE, phone, msg);
    	System.out.println(url);
        StringRequest request = new StringRequest(Request.Method.GET, url, listener,
                errorListener);
        MyNetworkUtil.getInstance(mApplicationContext).addToRequstQueue(request);
    }
    
    public void fetchMSDetail(String meishiId, Listener<JSONObject> listener, ErrorListener errorListener) {
    	String url = UrlParamGenerator.getPath(APIS.GET_MS_DETAIL, meishiId);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, listener,
                errorListener);
        MyNetworkUtil.getInstance(mApplicationContext).addToRequstQueue(request);
    }

}

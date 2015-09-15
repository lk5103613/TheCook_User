package com.like.network;

public class APIS {

    public static final String BASE_URL = "http://120.26.67.29";
    public static final String GET_DC = BASE_URL + "/index.php/cook/findPageList?currentPage=%1&caixi_style=%2&location=%3&cnt=%4";
    public static final String GET_MENU = BASE_URL + "/index.php/appPack/findPageList?currentPage=";
    public static final String GET_DC_DETAIL = BASE_URL + "/index.php/cook/cookDetail?cid="; 
    public static final String GET_TC_DETAIL = BASE_URL + "/index.php/AppPack/detail?packId=";
    public static final String LOGIN = BASE_URL + "/index.php/appUser/login?mp=%1&pwd=%2";
    public static final String REG = BASE_URL + "/index.php/user/reg?mp=%1&pwd=%2";
//    public static final String REG = BASE_URL + "/index.php/user/reg";
    
    public static final String GET_ORDER = BASE_URL + "/index.php/appI/orderPage?uid=%1";
    public static final String GET_ORDER_DETAIL = BASE_URL + " /index.php/AppI/orderDetail?orderId=%1";
    public static final String CONFIRM_ORDER = BASE_URL + "/index.php/AppI/confirmOrder?orderId=%1";
    public static final String SAVE_ORDER = BASE_URL + "/index.php/appOrder/saveOrder?uid=%1&dining_time=%2&servicer_usercnt=%3&kitchen_size=%4&special_memo=%5&json=%6";
    
    public static final String ADD_ADDRESS = BASE_URL + "/index.php/UserAddress/saveAddressFn?uid=%1&linkman=%2&mp=%3&district=%4&province=%5&city=%6&detail_address=%7";
    public static final String GET_ALL_ADDRESS = BASE_URL + "/index.php/UserAddress/findUserAddressList?uid=%1";
    
    public static final String COLLECT_DC = BASE_URL + "/index.php/appFav/saveFavCook?uid=%1&cook_id=%2";
    public static final String GET_COLLECTED_DC = BASE_URL + "/index.php/appFav/findFavCookList?uid=%1";
    
    public static final String GET_COLLECTED_MS = BASE_URL + "/index.php/appFav/findFavMeiShiList?uid=%1";
    
//    public static final String UPLOAD_AVATAR = BASE_URL + "/yw_uploadify.php?filename=%1&serverImgName=%2";
    public static final String UPLOAD_AVATAR = BASE_URL + "/yw_uploadify.php";
    
    public static final String UPDATE_AVATAR = BASE_URL + "/index.php/appUser/updateAvatar?avatar=%1&uid=%2";
    
    public static final String CHANGE_PWD = BASE_URL + "/index.php/AppUser/updatePwd?oldpwd=%1&newpwd=%2";
    
    public static final String GET_MS_LIST = BASE_URL + "/index.php/Meishi/findListPage?currentPage=%1";
    
    public static final String GET_ZX = BASE_URL + "/index.php/cms/findListPage?currentPage=%1";
    
    public static final String GET_SCORE_LIST = BASE_URL + "/index.php/AppScoreLog/findListPage?currentPage=%1&uid=%2";
    
    public static final String GET_MY_SCORE = BASE_URL + "/index.php/scoreGoods/findMultiPageList?currentPage=%1";
    public static final String GET_MY_CARD = BASE_URL + "/index.php/appCoupon/findCouponList?uid=%1";
    
    public static final String GET_PER_INFO = BASE_URL + "/index.php/appUser/loadUserInfo?uid=%1";
    
    public static final String UPDATE_PHONE = BASE_URL + "/index.php/AppUser/updateMpFn?uid=%1&newMp=%2";
    
    public static final String SEND_CODE = "http://222.73.117.158/msg/HttpBatchSendSM?account=vip_lb_dcjd&pswd=vip_lb_dcjd001&mobile=%1&msg=%2&needstatus=true";
    
    public static final String GET_MS_DETAIL = BASE_URL + "/index.php/meishi/loadMeishi?meishiid=%1";

}

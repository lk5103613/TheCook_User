package com.like.storage;

import android.provider.BaseColumns;

public class DBContract {

    public static final String DB_NAME = "the_cook.db";
    public static final int DB_VERSION = 1;

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String PRIMARY_KEY = " INTEGER PRIMARY KEY AUTOINCREMENT, ";

    public class ShoppingCartContract implements BaseColumns {
        public static final String TABLE_NAME = "shooping_cart";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_PRICE = "price";
        public static final String COLUMN_NAME_CNT = "cnt";
        public static final String COLUMN_NAME_PACKAGE_ID = "packageId";
        public static final String COLUMN_NAME_IMG = "img";
        public static final String COLUMN_NAME_NULLABLE = "null";
        public static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_NAME_ID + PRIMARY_KEY +
                COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_PRICE + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_CNT + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_IMG + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_PACKAGE_ID + TEXT_TYPE + ")" ;
        public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public class UserInfoContract implements BaseColumns {
        public static final String TABLE_NAME = "user_info";
        public static final String COLUMN_NAME_NULLABLE = "null";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_FACE_URL = "face_url";
        public static final String COLUMN_NAME_COVER = "cover";
        public static final String COLUMN_NAME_GENDER = "gender";
        public static final String COLUMN_NAME_PHONE = "phone";
        public static final String COLUMN_NAME_SIGN = "sign";
        public static final String COLUMN_NAME_BIRTHDAY = "birthday";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_RVRC = "rvrc";
        public static final String COLUMN_NAME_MONEY = "money";
        public static final String COLUMN_NAME_LEVEL = "level";
        public static final String COLUMN_NAME_SHORTCUT = "shortcut";
        public static final String COLUMN_NAME_EASEMOB = "easemob";
        public static final String COLUMN_NAME_FOLLOWS = "follows";
        public static final String COLUMN_NAME_FANS = "fans";
        public static final String COLUMN_NAME_VIPID = "vipid";
        public static final String COLUMN_NAME_MONEY_NAME = "money_name";
        public static final String COLUMN_NAME_MONEY_URL = "money_url";
        public static final String COLUMN_NAME_RVRC_NAME = "rvrc_name";
        public static final String COLUMN_NAME_RVRC_URL = "rvrc_url";
        public static final String COLUMN_NAME_LUCKY_URL = "lucky_url";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_IS_ATTENTION = "is_attention";
        public static final String COLUMN_NAME_SHARE_TITLE = "share_title";
        public static final String COLUMN_NAME_SHARE_URL = "share_url";
        public static final String COLUMN_NAME_SHARE_CONTENT = "share_content";
        public static final String COLUMN_NAME_SHARE_PIC = "share_pic";
        public static final String COLUMN_NAME_BBS_COOKIE_NAME = "bbs_cookie_name";
        public static final String COLUMN_NAME_BBS_COOKIE_VALUE = "bbs_cookie_value";
        public static final String COLUMN_NAME_ALLOW_PUSH = "allow_push";
        public static final String COLUMN_NAME_LOGIN_TOKEN = "login_token";
        public static final String COLUMN_NAME_UID = "uid";
        public static final String COLUMN_NAME_DEFAULT_FID = "default_fid";
        public static final String COLUMN_NAME_DEFAULT_FNAME = "default_fnamel";
        public static final String COLUMN_NAME_DEFAULT_FEEDBACK_FID = "default_feedback_fid";
        public static final String COLUMN_NAME_DEFAULT_FEEDBACK_FNAME = "default_feedback_fname";
        public static final String COLUMN_NAME_SHAKE_REWARD_NAME = "shake_reward_name";
        public static final String COLUMN_NAME_SHAKE_REWARD_URL = "shake_reward_url";
        public static final String COLUMN_NAME_SEARCH_URL = "search_url";
        public static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + PRIMARY_KEY +
                COLUMN_NAME_USERNAME + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_FACE_URL + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_COVER + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_GENDER + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_PHONE + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_SIGN + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_BIRTHDAY + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_EMAIL + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_RVRC + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_MONEY + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_LEVEL + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_SHORTCUT + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_EASEMOB + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_FOLLOWS + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_FANS + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_VIPID + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_MONEY_NAME + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_MONEY_URL + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_RVRC_NAME + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_RVRC_URL + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_LUCKY_URL + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_TYPE + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_IS_ATTENTION + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_SHARE_TITLE + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_SHARE_URL + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_SHARE_CONTENT + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_SHARE_PIC + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_BBS_COOKIE_NAME + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_BBS_COOKIE_VALUE + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_ALLOW_PUSH + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_LOGIN_TOKEN + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_UID + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_DEFAULT_FID + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_DEFAULT_FNAME + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_DEFAULT_FEEDBACK_FID + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_DEFAULT_FEEDBACK_FNAME + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_SHAKE_REWARD_NAME + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_SHAKE_REWARD_URL + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_SEARCH_URL + TEXT_TYPE + ")";
        public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }

    public class ProvienceContract implements BaseColumns {
    	public static final String TABLE_NAME = "provience";
    	public static final String COLUMN_NAME_ID = "id";
    	public static final String COLUMN_NAME_NAME = "name";
    	public static final String COLUMN_NAME_NULLABLE = "null";
    	public static final String SQL_CREATE_ENTRIES= "CREATE TABLE " + TABLE_NAME + " (" +
		    	COLUMN_NAME_ID + " INTEGER PRIMARY KEY " + COMMA_SEP +
		    	COLUMN_NAME_NAME + TEXT_TYPE + " )";
        public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
    
    public class CityContract implements BaseColumns {
    	public static final String TABLE_NAME = "city";
    	public static final String COLUMN_NAME_ID = "id";
    	public static final String COLUMN_NAME_NAME = "name";
    	public static final String COLUMN_NAME_PROVIENCE_ID = "pro_id";
    	public static final String COLUMN_NAME_NULLABLE = "null";
    	public static final String SQL_CREATE_ENTRIES= "CREATE TABLE " + TABLE_NAME + " (" +
		    	COLUMN_NAME_ID + " INTEGER PRIMARY KEY " + COMMA_SEP + 
		    	COLUMN_NAME_NAME + TEXT_TYPE +COMMA_SEP +
		    	COLUMN_NAME_PROVIENCE_ID + TEXT_TYPE + " )";
        public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
    
    public class DistrictsContract implements BaseColumns {
    	public static final String TABLE_NAME = "districts";
    	public static final String COLUMN_NAME_ID = "id";
    	public static final String COLUMN_NAME_NAME = "name";
    	public static final String COLUMN_NAME_CITY_ID = "city_id";
    	public static final String COLUMN_NAME_NULLABLE = "null";
    	public static final String SQL_CREATE_ENTRIES= "CREATE TABLE " + TABLE_NAME + " (" +
		    	COLUMN_NAME_ID + " INTEGER PRIMARY KEY " + COMMA_SEP +
		    	COLUMN_NAME_NAME + TEXT_TYPE +COMMA_SEP +
		    	COLUMN_NAME_CITY_ID + " )";
        public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
    
}

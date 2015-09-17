package com.like.storage;

import java.util.ArrayList;
import java.util.List;

import com.like.entity.City;
import com.like.entity.Districts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DistrictsManager {
	
	private static DistrictsManager mManager;
    private DBHelper mDBHelper;
    
    private DistrictsManager(Context context) {
        mDBHelper = DBHelper.getInstance(context);
        mDBHelper.getWritableDatabase();
    }
    
    public static DistrictsManager getInstance(Context context) {
        if(mManager == null) {
            mManager = new DistrictsManager(context);
        }
        return mManager;
    }
    
    public long addDistrict(Districts district, SQLiteDatabase db) {
        long id;
//        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBContract.DistrictsContract.COLUMN_NAME_ID, district.id);
        values.put(DBContract.DistrictsContract.COLUMN_NAME_NAME, district.name);
        values.put(DBContract.DistrictsContract.COLUMN_NAME_CITY_ID, district.cityId+"");
        id = db.insert(DBContract.DistrictsContract.TABLE_NAME,
                DBContract.DistrictsContract.COLUMN_NAME_NULLABLE, values);
        return id;
    }
    
    public void addDistrict(List<Districts> districts) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.beginTransaction();
        for(int i=0; i<districts.size(); i++) {
        	addDistrict(districts.get(i), db);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }
    
    public List<Districts> getDistrictsByCityId(int cityId)  {
    	List<Districts> districts = new ArrayList<Districts>();
    	SQLiteDatabase db = mDBHelper.getReadableDatabase();
    	String[] columns = new String[]{
    			DBContract.DistrictsContract.COLUMN_NAME_ID,
    			DBContract.DistrictsContract.COLUMN_NAME_NAME,
    			DBContract.DistrictsContract.COLUMN_NAME_CITY_ID
    	};
    	String selection = DBContract.DistrictsContract.COLUMN_NAME_CITY_ID + "=?";
    	String[] selectionArgs = new String[]{cityId+""};
    	Cursor cursor = db.query(DBContract.DistrictsContract.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
    	while (cursor.moveToNext()) {
            Districts dis = new Districts();
            dis.id = cursor.getInt(cursor.getColumnIndex(DBContract.DistrictsContract.COLUMN_NAME_ID));
            dis.name = cursor.getString(cursor.getColumnIndex(DBContract.DistrictsContract.COLUMN_NAME_NAME));
            dis.cityId = cursor.getInt(cursor.getColumnIndex(DBContract.DistrictsContract.COLUMN_NAME_CITY_ID));
            districts.add(dis);
        }
    	return districts;
    }
    
}

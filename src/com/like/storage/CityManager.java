package com.like.storage;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.like.entity.City;

public class CityManager {
	
	private static CityManager mManager;
    private DBHelper mDBHelper;
    
    private CityManager(Context context) {
        mDBHelper = DBHelper.getInstance(context);
        mDBHelper.getWritableDatabase();
    }
    
    public static CityManager getInstance(Context context) {
        if(mManager == null) {
            mManager = new CityManager(context);
        }
        return mManager;
    }
    
    public long addCity(City city, SQLiteDatabase db) {
        long id;
//        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBContract.CityContract.COLUMN_NAME_ID, city.id);
        values.put(DBContract.CityContract.COLUMN_NAME_NAME, city.name);
        values.put(DBContract.CityContract.COLUMN_NAME_PROVIENCE_ID, city.provicenceId+"");
        id = db.insert(DBContract.CityContract.TABLE_NAME,
                DBContract.CityContract.COLUMN_NAME_NULLABLE, values);
        return id;
    }
    
    public void addCity(List<City> cities) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.beginTransaction();
        for(int i=0; i<cities.size(); i++) {
        	addCity(cities.get(i), db);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }
    
    public List<City> getCityByProId(int provienceId)  {
    	List<City> cities = new ArrayList<City>();
    	SQLiteDatabase db = mDBHelper.getReadableDatabase();
    	String[] columns = new String[]{
    			DBContract.CityContract.COLUMN_NAME_ID,
    			DBContract.CityContract.COLUMN_NAME_NAME,
    			DBContract.CityContract.COLUMN_NAME_PROVIENCE_ID
    	};
    	String selection = DBContract.CityContract.COLUMN_NAME_PROVIENCE_ID + "=?";
    	String[] selectionArgs = new String[]{provienceId+""};
    	Cursor cursor = db.query(DBContract.CityContract.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
    	while (cursor.moveToNext()) {
            City city = new City();
            city.id = cursor.getInt(cursor.getColumnIndex(DBContract.CityContract.COLUMN_NAME_ID));
            city.name = cursor.getString(cursor.getColumnIndex(DBContract.CityContract.COLUMN_NAME_NAME));
            city.provicenceId = cursor.getInt(cursor.getColumnIndex(DBContract.CityContract.COLUMN_NAME_PROVIENCE_ID));
            cities.add(city);
        }
    	return cities;
    }
	
}

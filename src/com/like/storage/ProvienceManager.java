package com.like.storage;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.like.entity.Provience;

public class ProvienceManager {
	
	private static ProvienceManager mManager;
    private DBHelper mDBHelper;
    
    private ProvienceManager(Context context) {
        mDBHelper = DBHelper.getInstance(context);
        mDBHelper.getWritableDatabase();
    }
    
    public static ProvienceManager getInstance(Context context) {
        if(mManager == null) {
            mManager = new ProvienceManager(context);
        }
        return mManager;
    }
    
    public long addProvience(Provience provience, SQLiteDatabase db) {
        long id;
//        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBContract.ProvienceContract.COLUMN_NAME_ID, provience.id);
        values.put(DBContract.ProvienceContract.COLUMN_NAME_NAME, provience.name);
        id = db.insert(DBContract.ProvienceContract.TABLE_NAME,
                DBContract.ProvienceContract.COLUMN_NAME_NULLABLE, values);
        return id;
    }
    
    public void addProvience(List<Provience> pros) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.beginTransaction();
        for(int i=0; i<pros.size(); i++) {
        	addProvience(pros.get(i), db);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }
    
    public List<Provience> getAll() {
    	List<Provience> results = new ArrayList<Provience>();
    	SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.query(DBContract.ProvienceContract.TABLE_NAME, new String[]{
                DBContract.ProvienceContract.COLUMN_NAME_ID,
                DBContract.ProvienceContract.COLUMN_NAME_NAME,
        }, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Provience entity = new Provience();
            entity.id = cursor.getInt(cursor.getColumnIndex(DBContract.ProvienceContract.COLUMN_NAME_ID));
            entity.name = cursor.getString(cursor.getColumnIndex(DBContract.ProvienceContract.COLUMN_NAME_NAME));
            results.add(entity);
        }
    	return results;
    }

}

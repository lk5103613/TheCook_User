package com.like.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.like.entity.ShoppingCartEntity;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartManager {

    private static ShoppingCartManager mManager;
    private DBHelper mDBHelper;

    private ShoppingCartManager(Context context) {
        mDBHelper = DBHelper.getInstance(context);
        mDBHelper.getWritableDatabase();
    }

    public static ShoppingCartManager getInstance(Context context) {
        if(mManager == null) {
            mManager = new ShoppingCartManager(context);
        }
        return mManager;
    }

    public long addToShoppingCart(ShoppingCartEntity entity) {
        long id;
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBContract.ShoppingCartContract.COLUMN_NAME_NAME, entity.name);
        values.put(DBContract.ShoppingCartContract.COLUMN_NAME_PRICE, entity.price);
        values.put(DBContract.ShoppingCartContract.COLUMN_NAME_CNT, entity.cnt);
        values.put(DBContract.ShoppingCartContract.COLUMN_NAME_PACKAGE_ID, entity.packageId);
        values.put(DBContract.ShoppingCartContract.COLUMN_NAME_IMG, entity.img);
        id = db.insert(DBContract.ShoppingCartContract.TABLE_NAME,
                DBContract.ShoppingCartContract.COLUMN_NAME_NULLABLE, values);
        return id;
    }

    public List<ShoppingCartEntity> getAll() {
        List<ShoppingCartEntity> entities = new ArrayList<ShoppingCartEntity>();
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.query(DBContract.ShoppingCartContract.TABLE_NAME, new String[]{
                DBContract.ShoppingCartContract.COLUMN_NAME_ID,
                DBContract.ShoppingCartContract.COLUMN_NAME_NAME,
                DBContract.ShoppingCartContract.COLUMN_NAME_PRICE,
                DBContract.ShoppingCartContract.COLUMN_NAME_CNT,
                DBContract.ShoppingCartContract.COLUMN_NAME_PACKAGE_ID,
                DBContract.ShoppingCartContract.COLUMN_NAME_IMG
        }, null, null, null, null, null);
//        if(!cursor.moveToFirst())
//            return entities;
        while (cursor.moveToNext()) {
            ShoppingCartEntity entity = new ShoppingCartEntity();
            entity.id = cursor.getInt(cursor.getColumnIndex(DBContract.ShoppingCartContract.COLUMN_NAME_ID));
            entity.name = cursor.getString(cursor.getColumnIndex(DBContract.ShoppingCartContract.COLUMN_NAME_NAME));
            entity.cnt = cursor.getInt(cursor.getColumnIndex(DBContract.ShoppingCartContract.COLUMN_NAME_CNT));
            entity.price = cursor.getFloat(cursor.getColumnIndex(DBContract.ShoppingCartContract.COLUMN_NAME_PRICE));
            entity.packageId = cursor.getInt(cursor.getColumnIndex(DBContract.ShoppingCartContract.COLUMN_NAME_PACKAGE_ID));
            entity.img = cursor.getString(cursor.getColumnIndex(DBContract.ShoppingCartContract.COLUMN_NAME_IMG));
            entities.add(entity);
        }
        return entities;
    }

    public void update(ShoppingCartEntity entity) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBContract.ShoppingCartContract.COLUMN_NAME_NAME, entity.name);
        values.put(DBContract.ShoppingCartContract.COLUMN_NAME_PRICE, entity.price);
        values.put(DBContract.ShoppingCartContract.COLUMN_NAME_CNT, entity.cnt);
        values.put(DBContract.ShoppingCartContract.COLUMN_NAME_PACKAGE_ID, entity.packageId);
        values.put(DBContract.ShoppingCartContract.COLUMN_NAME_IMG, entity.img);
        String whereClause = DBContract.ShoppingCartContract.COLUMN_NAME_ID + " = ?";
        String[] whereArgs = new String[]{String.valueOf(entity.id)};
        db.update(DBContract.ShoppingCartContract.TABLE_NAME, values, whereClause, whereArgs);
    }

    public void remove(ShoppingCartEntity entity) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        String whereClause = DBContract.ShoppingCartContract.COLUMN_NAME_ID + " = ?";
        String[] whereArgs = new String[]{String.valueOf(entity.id)};
        db.delete(DBContract.ShoppingCartContract.TABLE_NAME, whereClause, whereArgs);
    }

}

package com.example.catapp.view;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.catapp.model.CatModel;

import java.util.ArrayList;

public class FavoriteDB extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;
    public static final String DATABASE_NAME = "CatDB";
    public static final String TABLE_NAME = "favoriteCatTable";
    public static final String KEY_ID = "id";
    public static final String ITEM_TITLE = "itemTitle";
    public static final String ITEM_IMAGE = "itemImage";
    public static final String FAVSTATUS = "fStatus";



    public FavoriteDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " TEXT," + ITEM_TITLE + " TEXT,"
                + ITEM_IMAGE + " TEXT," + FAVSTATUS + " TEXT)";

        sqLiteDatabase.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertEmpty() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        for (int i = 0; i < 15; i++) {
            cv.put(KEY_ID, i);
            cv.put(FAVSTATUS, 0);
            db.insert(TABLE_NAME, null, cv);
        }
    }

    public void insertIntoTheDatabase(String itemTitle, String itemImage, String id, String favStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ITEM_TITLE, itemTitle);
        cv.put(ITEM_IMAGE, itemImage);
        cv.put(KEY_ID, id);
        cv.put(FAVSTATUS, favStatus);
        db.insert(TABLE_NAME, null, cv);
        Log.d("FavDB", itemTitle);
    }

    public Cursor readAllData(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT*FROM " + TABLE_NAME + " WHERE " + KEY_ID + "=" + id + "";
        return db.rawQuery(sql, null, null);
    }

    public void remove_fav(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE " + TABLE_NAME + " SET " + FAVSTATUS + " ='0' WHERE " + KEY_ID + " = " + "'" + id + "'";
        db.execSQL(sql);
        Log.d("remove", id);
    }

    public Cursor selectALLFavoriteList() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT*FROM " + TABLE_NAME + " WHERE " + FAVSTATUS + " ='1'";
        return db.rawQuery(sql, null, null);
    }
}

package com.plusonesoftwares.plusonesoftwares.jokesworld.sqliteDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ashoksharma on 06/03/17.
 */

public class DBHelper  extends SQLiteOpenHelper {
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "crud.db";

    public DBHelper(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here

        String CREATE_TABLE_CATEGORY = "CREATE TABLE " + Category.TABLE  + "("
                + Category.KEY_ID  + " INTEGER ,"
                + Category.KEY_languageId + " INTEGER, "
                + Category.KEY_Category + " TEXT, "
                + Category.KEY_Image + " TEXT , "
                + Category.KEY_CreatedDate + " TEXT , "
                + Category.KEY_ContentCount + " INTEGER )";

        db.execSQL(CREATE_TABLE_CATEGORY);

        String CREATE_TABLE_LANGUAGE = "CREATE TABLE " + Language.TABLE  + "("
                + Language.KEY_ID  + " INTEGER ,"
                + Language.KEY_language + " TEXT )";

        db.execSQL(CREATE_TABLE_LANGUAGE);

        String CREATE_TABLE_CONTENT = "CREATE TABLE " + Content.TABLE  + "("
                + Content.KEY_ID  + " INTEGER ,"
                + Content.KEY_CategoryId + " INTEGER, "
                + Content.KEY_Content + " TEXT, "
                + Content.KEY_CreatedDate + " TEXT , "
                + Content.KEY_IsPopular + " INTEGER )";

        db.execSQL(CREATE_TABLE_CONTENT);

        String CREATE_TABLE_FAVOURITE_CONTENT = "CREATE TABLE " + FavouriteContent.TABLE  + "("
                + FavouriteContent.KEY_ID  + " INTEGER,"
                + FavouriteContent.KEY_CategoryId + " INTEGER, "
                + FavouriteContent.KEY_Content + " TEXT, "
                + FavouriteContent.KEY_CreatedDate + " TEXT , "
                + FavouriteContent.KEY_IsPopular + " INTEGER )";

        db.execSQL(CREATE_TABLE_FAVOURITE_CONTENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + Category.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Language.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Content.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + FavouriteContent.TABLE);
        // Create tables again
        onCreate(db);
    }
}
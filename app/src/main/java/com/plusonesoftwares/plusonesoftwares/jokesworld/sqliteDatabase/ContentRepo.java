package com.plusonesoftwares.plusonesoftwares.jokesworld.sqliteDatabase;

/**
 * Created by ashoksharma on 06/03/17.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContentRepo {

    private DBHelper dbHelper;

    public ContentRepo(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void insertLanguage(List<Language> list) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (Language language : list) {
                values.put(Language.KEY_language, language.Language);
                db.insert(Language.TABLE, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }
    public ArrayList<HashMap<String, String>>  getAllLanguage() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  " SELECT  " +
                Language.KEY_ID + "," +
                Language.KEY_language +
                " FROM " + Language.TABLE;

        ArrayList<HashMap<String, String>> languageList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> language = new HashMap<String, String>();
                language.put("id", cursor.getString(cursor.getColumnIndex(Language.KEY_ID)));
                language.put("name", cursor.getString(cursor.getColumnIndex(Language.KEY_language)));
                languageList.add(language);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return languageList;
    }

    public void insert_Categories(List<Category> list) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (Category category : list) {
                values.put(Category.KEY_ID, category.category_ID);
                values.put(Category.KEY_languageId, category.language_ID);
                values.put(Category.KEY_Category, category.Category);
                values.put(Category.KEY_Image, category.Image);
                values.put(Category.KEY_CreatedDate, category.CreatedDate);
                values.put(Category.KEY_ContentCount, category.ContentCount);
                db.insert(Category.TABLE, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public void delete_All_Categories() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(Category.TABLE,null,null);
        db.close(); // Closing database connection
    }

    public ArrayList<HashMap<String, String>>  getCategoriesList(String languageId) {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String categoryWhereClause = "";

        if(!languageId.equals("0"))
            categoryWhereClause = " WHERE " + Category.KEY_languageId + " = ? ";

        String selectQuery =  " SELECT  " +
                Category.KEY_ID + "," +
                Category.KEY_languageId + "," +
                Category.KEY_Category + "," +
                Category.KEY_Image + "," +
                Category.KEY_CreatedDate + "," +
                Category.KEY_ContentCount +
                " FROM " + Category.TABLE + categoryWhereClause  + " ORDER BY "  + Category.KEY_CreatedDate + " DESC ";

        Cursor cursor;

        if(!languageId.equals("0")) {
            String[] params = new String[]{languageId};
            cursor = db.rawQuery(selectQuery, params);
        }else {
            cursor = db.rawQuery(selectQuery, null);
        }

        //Student student = new Student();
        ArrayList<HashMap<String, String>> categoryList = new ArrayList<HashMap<String, String>>();


        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> category = new HashMap<String, String>();
                category.put("id", cursor.getString(cursor.getColumnIndex(Language.KEY_ID)));
                category.put("languageid", cursor.getString(cursor.getColumnIndex(Category.KEY_languageId)));
                category.put("category", cursor.getString(cursor.getColumnIndex(Category.KEY_Category)));
                category.put("image", cursor.getString(cursor.getColumnIndex(Category.KEY_Image)));
                category.put("creationdate", cursor.getString(cursor.getColumnIndex(Category.KEY_CreatedDate)));
                category.put("contentcount", cursor.getString(cursor.getColumnIndex(Category.KEY_ContentCount)));
                categoryList.add(category);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return categoryList;
    }

    public void insert_Content(List<Content> list) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (Content content : list) {
                values.put(Content.KEY_ID, content.content_ID);
                values.put(Content.KEY_CategoryId, content.category_ID);
                values.put(Content.KEY_Content, content.Content);
                values.put(Content.KEY_CreatedDate, content.CreatedDate);
                values.put(Content.KEY_IsPopular, content.IsPopular);
                db.insert(Content.TABLE, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }
    public void delete_All_Content() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(Content.TABLE,null,null);
        db.close(); // Closing database connection
    }

    public ArrayList<HashMap<String, String>>  getContentByCategoryId(String categoryId) {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String  categoryWhereClause = " WHERE " + Content.KEY_CategoryId + " = ? " + categoryId;

        String selectQuery =  "SELECT  " +
                Content.KEY_ID + "," +
                Content.KEY_CategoryId + "," +
                Content.KEY_Content + "," +
                Content.KEY_CreatedDate + "," +
                Content.KEY_IsPopular +
                " FROM " + Content.TABLE + " WHERE " + Content.KEY_CategoryId + " = ? " + " ORDER BY "  + Content.KEY_CreatedDate + " DESC ";

        String[] params = new String[]{ categoryId };
        Cursor cursor = db.rawQuery(selectQuery, params);
        ArrayList<HashMap<String, String>> contentList = new ArrayList<HashMap<String, String>>();

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> content = new HashMap<String, String>();
                content.put("id", cursor.getString(cursor.getColumnIndex(Content.KEY_ID)));
                content.put("categoryid", cursor.getString(cursor.getColumnIndex(Content.KEY_CategoryId)));
                content.put("content", cursor.getString(cursor.getColumnIndex(Content.KEY_Content)));
                content.put("creationdate", cursor.getString(cursor.getColumnIndex(Content.KEY_CreatedDate)));
                content.put("ispopular", cursor.getString(cursor.getColumnIndex(Content.KEY_IsPopular)));
                contentList.add(content);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return contentList;
    }

    public ArrayList<HashMap<String, String>>  getPopularContent() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery =  "SELECT  " +
                Content.KEY_ID + "," +
                Content.KEY_CategoryId + "," +
                Content.KEY_Content + "," +
                Content.KEY_CreatedDate + "," +
                Content.KEY_IsPopular +
                " FROM " + Content.TABLE + " WHERE " + Content.KEY_IsPopular + " = ? " + " ORDER BY "  + Content.KEY_CreatedDate + " DESC ";
        ArrayList<HashMap<String, String>> contentList = new ArrayList<HashMap<String, String>>();

        String[] params = new String[]{ "true" };
        Cursor cursor = db.rawQuery(selectQuery, params);

        //looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> content = new HashMap<String, String>();
                content.put("id", cursor.getString(cursor.getColumnIndex(Content.KEY_ID)));
                content.put("categoryid", cursor.getString(cursor.getColumnIndex(Content.KEY_CategoryId)));
                content.put("content", cursor.getString(cursor.getColumnIndex(Content.KEY_Content)));
                content.put("creationdate", cursor.getString(cursor.getColumnIndex(Content.KEY_CreatedDate)));
                content.put("ispopular", cursor.getString(cursor.getColumnIndex(Content.KEY_IsPopular)));
                contentList.add(content);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return contentList;
    }

    public ArrayList<HashMap<String, String>>  getLatestContent() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery =  "SELECT  " +
                Content.KEY_ID + "," +
                Content.KEY_CategoryId + "," +
                Content.KEY_Content + "," +
                Content.KEY_CreatedDate + "," +
                Content.KEY_IsPopular +
                " FROM " + Content.TABLE + " ORDER BY "  + Content.KEY_CreatedDate + " DESC LIMIT 30";

        ArrayList<HashMap<String, String>> contentList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> content = new HashMap<String, String>();
                content.put("id", cursor.getString(cursor.getColumnIndex(Content.KEY_ID)));
                content.put("categoryid", cursor.getString(cursor.getColumnIndex(Content.KEY_CategoryId)));
                content.put("content", cursor.getString(cursor.getColumnIndex(Content.KEY_Content)));
                content.put("creationdate", cursor.getString(cursor.getColumnIndex(Content.KEY_CreatedDate)));
                content.put("ispopular", cursor.getString(cursor.getColumnIndex(Content.KEY_IsPopular)));
                contentList.add(content);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return contentList;
    }

    public void insert_FavouriteContent(List<FavouriteContent> list) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (FavouriteContent favouritecontent : list) {
                values.put(FavouriteContent.KEY_ID, favouritecontent.content_ID);
                values.put(FavouriteContent.KEY_CategoryId, favouritecontent.category_ID);
                values.put(FavouriteContent.KEY_Content, favouritecontent.Content);
                values.put(FavouriteContent.KEY_CreatedDate, favouritecontent.CreatedDate);
                values.put(FavouriteContent.KEY_IsPopular, favouritecontent.IsPopular);
                db.insert(FavouriteContent.TABLE, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public void un_Favourite(int Id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.delete(FavouriteContent.TABLE, FavouriteContent.KEY_ID + " = ?", new String[] {String.valueOf(Id)});
    }
    public boolean isAlreadyFavourite(int Id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = null;
        String sql = " SELECT ID FROM "+ FavouriteContent.TABLE +" WHERE ID = " + Id;
        cursor = db.rawQuery(sql,null);

        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public ArrayList<HashMap<String, String>>  getFavouriteContent() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery =  " SELECT  " +
                FavouriteContent.KEY_ID + "," +
                FavouriteContent.KEY_CategoryId + "," +
                FavouriteContent.KEY_Content + "," +
                FavouriteContent.KEY_CreatedDate + "," +
                FavouriteContent.KEY_IsPopular +
                " FROM " + FavouriteContent.TABLE + " ORDER BY "  + Content.KEY_CreatedDate + " DESC ";

        ArrayList<HashMap<String, String>> contentList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> content = new HashMap<String, String>();
                content.put("id", cursor.getString(cursor.getColumnIndex(Content.KEY_ID)));
                content.put("categoryid", cursor.getString(cursor.getColumnIndex(Content.KEY_CategoryId)));
                content.put("content", cursor.getString(cursor.getColumnIndex(Content.KEY_Content)));
                content.put("creationdate", cursor.getString(cursor.getColumnIndex(Content.KEY_CreatedDate)));
                content.put("ispopular", cursor.getString(cursor.getColumnIndex(Content.KEY_IsPopular)));
                contentList.add(content);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return contentList;
    }

}

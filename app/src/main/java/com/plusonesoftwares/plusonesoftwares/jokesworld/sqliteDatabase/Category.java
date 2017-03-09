package com.plusonesoftwares.plusonesoftwares.jokesworld.sqliteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ashoksharma on 06/03/17.
 */

public class Category {
    // Labels table name
    public static final String TABLE = "tblCategory";

    // Labels Table Columns names
    public static final String KEY_ID = "ID";
    public static final String KEY_languageId = "LanguageID";
    public static final String KEY_Category = "Category";
    public static final String KEY_Image = "Image";
    public  static final String KEY_CreatedDate = "CreatedDate";
    public static final String KEY_ContentCount = "ContentCount";

    // property help us to keep data
    public String category_ID;
    public String language_ID;
    public String Category;
    public String Image;
    public String CreatedDate;
    public String ContentCount;

}

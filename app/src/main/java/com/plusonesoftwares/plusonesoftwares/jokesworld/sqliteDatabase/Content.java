package com.plusonesoftwares.plusonesoftwares.jokesworld.sqliteDatabase;

import java.util.Date;

/**
 * Created by ashoksharma on 06/03/17.
 */

public class Content {

    // Labels table name
    public static final String TABLE = "tblContent";

    // Labels Table Columns names
    public  static final String KEY_ID = "ID";
    public  static final String KEY_CategoryId = "CategoryID";
    public  static final String KEY_Content = "Content";
    public  static final String KEY_CreatedDate = "CreatedDate";
    public  static final String KEY_IsPopular = "IsPopular";

    //property help us to keep data
    public String content_ID;
    public String category_ID;
    public String Content;
    public String CreatedDate;
    public String IsPopular;
}

package com.imooc.daily.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by xhx12366 on 2017-08-18.
 */

public class DailyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "daily.db";
    private static final String SQL_CREATE = "create table if not exists cost_info(" +
            "_id integer primary key autoincrement, costTitle text, costData text," +
            " costMoney text)";
    private static final int VERSION = 1;

    public DailyDatabaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

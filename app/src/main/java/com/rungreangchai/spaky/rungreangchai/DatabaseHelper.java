package com.rungreangchai.spaky.rungreangchai;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by spaky on 24/7/2559.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static String databaseName = "Rungrengchai.db";
    public static int databaseVersion = 1;

    //    String weight, String type, String price, String amount
    public static final String SQL_CREATE_TABLE_RICE = "CREATE TABLE " + MySQLite.table_name + " ("
            + MySQLite.col_type + " TEXT,"
            + MySQLite.col_price + " TEXT)";

    public static final String SQL_CREATE_TABLE_STAT = "CREATE TABLE " + MySQLite.table_stat + " ("
            + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + MySQLite.col_date + " TEXT,"
            + MySQLite.col_weight + " TEXT,"
            + MySQLite.col_type_rice + " TEXT,"
            + MySQLite.col_result + " TEXT,"
            + MySQLite.col_amount + " TEXT" + ")";

    public DatabaseHelper(Context context) {
        super(context, databaseName, null, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_RICE);
        db.execSQL(SQL_CREATE_TABLE_STAT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

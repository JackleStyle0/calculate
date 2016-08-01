package com.rungreangchai.spaky.rungreangchai;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by spaky on 24/7/2559.
 */
public class MySQLite {

    private SQLiteDatabase sqLiteDatabase;
    private DatabaseHelper helper;

    String typeRice[] = {"ข้าวเก่า", "จ.มะลิ", "น.ใหม่", "น.เก่า"};
    String price[] = {"8", "10", "12", "9"};

    public final static String table_name = "Rice";
    public final static String col_type = "rice_type";
    public final static String col_price = "price_rice";


    //date, weight, type, price amount id
    public final static String table_stat = "Stat";
    public final static String col_date = "date";
    public final static String col_weight = "weight";
    public final static String col_type_rice = "type_rice";
    public final static String col_result = "result";
    public final static String col_amount = "amount";

    private String allColRice[] = {col_type, col_price};
    private String allColStat[] = {col_date, col_weight, col_type_rice, col_result, col_amount};
//    public final static String


    public MySQLite(Context context) {
        helper = new DatabaseHelper(context);
        sqLiteDatabase = helper.getWritableDatabase();
    }


    public long addToTableRice(String reiceType, String priceRice) {
        ContentValues values = new ContentValues();
        values.put(col_type, reiceType);
        values.put(col_price, priceRice);

        return sqLiteDatabase.insert(table_name, null, values);
    }

    public Cursor selectFromTableRice() {
        Cursor cursor = sqLiteDatabase.query(table_name, allColRice, null, null, null, null, null);
        return cursor;
    }


    public int updateTableRice(String tempName, String newName, String newPrice) {
        ContentValues values = new ContentValues();
        values.put(col_type, newName);
        values.put(col_price, newPrice);

        return sqLiteDatabase.update(table_name, values, col_type + "=?", new String[]{tempName});
    }


    public long addToTableStat(String weight, String type, String result, String amount) {
        ContentValues values = new ContentValues();
        values.put(col_date, getDate());
        values.put(col_weight, weight);
        values.put(col_type_rice, type);
        values.put(col_result, result);
        values.put(col_amount, amount);
        return sqLiteDatabase.insert(table_stat, null, values);
    }

    private String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault()
        );
        Date date = new Date();
        return dateFormat.format(date);
    }

    public Cursor selectStat() {
        Cursor cursor = sqLiteDatabase.query(table_stat, allColStat, null, null, null, null, null);
        return cursor;
    }

    public Cursor selByYear(String selValue) {
        Cursor cursor = sqLiteDatabase.query(table_stat, allColStat, col_date + " =?", new String[]{selValue}, null, null, null);
        return cursor;
    }


    public void closeSQLiteDatabase() {
        sqLiteDatabase.close();
    }

}
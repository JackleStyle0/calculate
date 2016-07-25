package com.rungreangchai.spaky.rungreangchai;

import android.content.ContentValues;
import android.content.Context;
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

}
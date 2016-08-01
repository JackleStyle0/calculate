package com.rungreangchai.spaky.rungreangchai;

import android.database.Cursor;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class layout_statitic extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    MySQLite mySQLite;
    Cursor mCursor;

    Spinner spYear, spMonth, spDay;
    ListView lvStat;
    List<String> lstYear, lstMonth, lstDay;
    TextView txtEmpty;

    String date[], dateInStat[], weight[], nameRice[], expend[], amount[];
    String searchDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_statitic);

        mySQLite = new MySQLite(layout_statitic.this);
        mCursor = mySQLite.selectStat();
        mCursor.moveToFirst();
        bindWidget();

        lstYear = new ArrayList<>();
        lstMonth = new ArrayList<>();
        lstDay = new ArrayList<>();

        dateInStat = new String[mCursor.getCount()];

        int i = 0;
        while (!mCursor.isAfterLast()) {
            dateInStat[i] = mCursor.getString(mCursor.getColumnIndex(MySQLite.col_date));
            date = dateInStat[i].split("-");

            if (!lstYear.contains(date[0])) {
                lstYear.add(date[0]);
            }

            if (!lstMonth.contains(date[1])) {
                lstMonth.add(date[1]);
            }

            if (!lstDay.contains(date[2])) {
                lstDay.add(date[2]);
            }

            i++;
            mCursor.moveToNext();
        }


        Collections.sort(lstDay);
        Collections.sort(lstMonth);
        Collections.sort(lstYear);

        ArrayAdapter<String> adapter_year = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lstYear);
        spYear.setAdapter(adapter_year);

        ArrayAdapter<String> adapter_month = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lstMonth);
        spMonth.setAdapter(adapter_month);

        ArrayAdapter<String> adapter_day = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lstDay);
        spDay.setAdapter(adapter_day);

        spYear.setOnItemSelectedListener(this);
        spMonth.setOnItemSelectedListener(this);
        spDay.setOnItemSelectedListener(this);


        mCursor.close();

    }

    public void bindWidget() {
        spYear = (Spinner) findViewById(R.id.spinner_year);
        spMonth = (Spinner) findViewById(R.id.spinner_month);
        spDay = (Spinner) findViewById(R.id.spinner_day);

        lvStat = (ListView) findViewById(R.id.list_statice);
        txtEmpty = (TextView) findViewById(R.id.empty_listview);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        mySQLite.closeSQLiteDatabase();
    }


    public void selFormStat(String strDate) {
//        Toast.makeText(getApplicationContext(), strDate, Toast.LENGTH_SHORT).show();
        mCursor = mySQLite.selByYear(strDate);
        mCursor.moveToFirst();

        dateInStat = new String[mCursor.getCount()];
        weight = new String[mCursor.getCount()];
        nameRice = new String[mCursor.getCount()];
        amount = new String[mCursor.getCount()];
        expend = new String[mCursor.getCount()];

        int i = 0;
        while (mCursor.isAfterLast()) {
            dateInStat[i] = mCursor.getString(mCursor.getColumnIndex(MySQLite.col_date));
            weight[i] = mCursor.getString(mCursor.getColumnIndex(MySQLite.col_weight));
            nameRice[i] = mCursor.getString(mCursor.getColumnIndex(MySQLite.col_type_rice));
            amount[i] = mCursor.getString(mCursor.getColumnIndex(MySQLite.col_amount));
            expend[i] = mCursor.getString(mCursor.getColumnIndex(MySQLite.col_result));

            i++;
            mCursor.moveToNext();
        }

        AdapterListStat adapterListStat = new AdapterListStat(this, dateInStat, weight, nameRice, expend, amount);
        lvStat.setAdapter(adapterListStat);

        mCursor.close();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinner_year:
                searchDate += parent.getItemAtPosition(position) + "-";
                break;
            case R.id.spinner_month:
                searchDate += parent.getItemAtPosition(position) + "-";
                break;
            case R.id.spinner_day:
                searchDate += parent.getItemAtPosition(position);
                selFormStat(searchDate);
                searchDate = "";
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

package com.rungreangchai.spaky.rungreangchai;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView priceRiceOld, stickyRiceNew, stickyRiceOld, jasmine, textResult;
    EditText edWeight, edAmount, edPrice;
    RadioButton raDeduct, raNoDeduct;
    Button btnSave, btnCal;
    Cursor mCursor;
    Spinner spiType;

    boolean chRadioButton = true;
    MySQLite mySQLite;
    SQLiteDatabase database;

    String typeRice[] = {"ข้าวเก่า", "จ.มะลิ", "น.ใหม่", "น.เก่า"};
    String price[] = {"8", "10", "12", "9"};
    String Amount, Weight;

    List<String> lstTypeRice;
    ArrayAdapter<String> adapter;

    int typePosition = 0;
    int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openActivity();

        lstTypeRice = new ArrayList<String>();
        mySQLite = new MySQLite(MainActivity.this);

        bindWidget();

        for (int i = 0; i < typeRice.length; i++) {
            mySQLite.addToTableRice(typeRice[i], price[i]);
            lstTypeRice.add(typeRice[i]);
        }

        selTableRice();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lstTypeRice);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiType.setAdapter(adapter);

        //onselect item in spiner
        selectSpinerRiceType();
        raDeduct.setChecked(true);

//        onclick button
        btnSave.setOnClickListener(this);
        btnCal.setOnClickListener(this);
        raDeduct.setOnClickListener(this);
        raNoDeduct.setOnClickListener(this);
    }

    public void selectSpinerRiceType() {
        spiType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCursor.moveToPosition(position);
                edPrice.setText(" " + mCursor.getString(mCursor.getColumnIndex(MySQLite.col_price)) + " บาท");
                typePosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void selTableRice() {
        mCursor = database.rawQuery("SELECT * FROM " + MySQLite.table_name, null);

        mCursor.moveToFirst();
        priceRiceOld.setText(mCursor.getString(mCursor.getColumnIndex(MySQLite.col_price)));
        mCursor.moveToNext();
        jasmine.setText(mCursor.getString(mCursor.getColumnIndex(MySQLite.col_price)));
        mCursor.moveToNext();
        stickyRiceNew.setText(mCursor.getString(mCursor.getColumnIndex(MySQLite.col_price)));
        mCursor.moveToNext();
        stickyRiceOld.setText(mCursor.getString(mCursor.getColumnIndex(MySQLite.col_price)));


    }

    public void openActivity() {
        database = openOrCreateDatabase(DatabaseHelper.databaseName, MODE_PRIVATE, null);
        database.delete(MySQLite.table_name, null, null);
    }


    public void bindWidget() {
        //text view
        priceRiceOld = (TextView) findViewById(R.id.price_rice_ole);
        stickyRiceNew = (TextView) findViewById(R.id.sticky_rice_new);
        jasmine = (TextView) findViewById(R.id.jasmine_rice);
        stickyRiceOld = (TextView) findViewById(R.id.sticky_rice_old);
        textResult = (TextView) findViewById(R.id.text_result);

        //Radio Button
        raNoDeduct = (RadioButton) findViewById(R.id.radio_no_deduct);
        raDeduct = (RadioButton) findViewById(R.id.radio_deduct);

        //Edit text
        edWeight = (EditText) findViewById(R.id.ed_weight);
        edAmount = (EditText) findViewById(R.id.ed_amount);
        edPrice = (EditText) findViewById(R.id.ed_price);

        //Button
        btnSave = (Button) findViewById(R.id.button_save);
        btnCal = (Button) findViewById(R.id.button_cal);

        //spiner
        spiType = (Spinner) findViewById(R.id.spiner_type_rice);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_save:
                Weight = edWeight.getText().toString();
                Amount = edAmount.getText().toString();
                if (Weight.isEmpty() || Amount.isEmpty()) {
                    if (Weight.isEmpty()) {
                        edWeight.requestFocus();
                        Toast.makeText(MainActivity.this, " กรุณากรอกน้ำหนัก", Toast.LENGTH_SHORT).show();
                    } else if (Amount.isEmpty()) {
                        edAmount.requestFocus();
                        Toast.makeText(MainActivity.this, " กรุณากรอกจำนวน", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    mCursor.moveToPosition(typePosition);
                    String type = mCursor.getString(mCursor.getColumnIndex(MySQLite.col_type));
                    String strResult = String.valueOf(result);
                    mySQLite.addToTableStat(Weight, type, strResult, Amount);
                }
                break;
            case R.id.button_cal:
                String strWeigth = edWeight.getText().toString();
                if (!strWeigth.matches("")) {
                    int weight = Integer.parseInt(edWeight.getText().toString());
                    if (chRadioButton) {
                        mCursor.moveToPosition(typePosition);
                        int price = mCursor.getInt(mCursor.getColumnIndex(MySQLite.col_price));
                        result = (weight - weight / 10) * price;
                        textResult.setText("" + result);
                    } else {
                        int price = mCursor.getInt(mCursor.getColumnIndex(MySQLite.col_price));
                        result = weight * price;
                        textResult.setText("" + result);
                    }
                    btnSave.setEnabled(true);
                } else {
                    Snackbar.make(v, "กรุณาใส่น้ำหนักก่อน", Snackbar.LENGTH_SHORT).show();
                    edWeight.requestFocus();
                }
                break;
            case R.id.radio_no_deduct:
                raDeduct.setChecked(false);
                chRadioButton = false;
                break;
            case R.id.radio_deduct:
                raNoDeduct.setChecked(false);
                chRadioButton = true;
                break;
            default:
                break;
        }
    }
}

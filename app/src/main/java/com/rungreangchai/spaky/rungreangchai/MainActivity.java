package com.rungreangchai.spaky.rungreangchai;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DialogTitle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textResult, col1, col2;
    EditText edWeight, edAmount, edPrice;
    RadioButton raDeduct, raNoDeduct;
    Button btnSave, btnCal;
    ImageButton navButton;
    Cursor mCursor;
    Spinner spiType;
    String strCol1, strCol2;

    boolean chRadioButton = true;
    MySQLite mySQLite;
    String Amount, Weight;

    List<String> lstTypeRice;
    List<String> lstPirce;
    ArrayAdapter<String> adapter;

    int typePosition = 0;
    int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstTypeRice = new ArrayList<String>();
        lstPirce = new ArrayList<String>();

        mySQLite = new MySQLite(MainActivity.this);
        mCursor = mySQLite.selectFromTableRice();
        mCursor.moveToFirst();

        while (!mCursor.isAfterLast()) {
            lstTypeRice.add(mCursor.getString(mCursor.getColumnIndex(MySQLite.col_type)));
            lstPirce.add(mCursor.getString(mCursor.getColumnIndex(MySQLite.col_price)));
            mCursor.moveToNext();
        }
        mCursor.close();

        bindWidget();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lstTypeRice);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiType.setAdapter(adapter);
        selectSpinerRiceType();

        raDeduct.setChecked(true);
//        onclick button
        btnSave.setOnClickListener(this);
        btnCal.setOnClickListener(this);
        raDeduct.setOnClickListener(this);
        raNoDeduct.setOnClickListener(this);
        navButton.setOnClickListener(this);
    }


    public void selectSpinerRiceType() {
        spiType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                edPrice.setText(lstPirce.get(position).toString());
                typePosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void bindWidget() {
        //text view
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

        navButton = (ImageButton) findViewById(R.id.nav_button);

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
                    } else if (Amount.isEmpty()) {
                        edAmount.requestFocus();
                    }
                } else {
                    String result = textResult.getText().toString();
                    String nameRice = lstPirce.get(typePosition).toString();
                    mySQLite.addToTableStat(Weight, nameRice, result, Amount);
                }

                break;
            case R.id.button_cal:
                String strWeigth = edWeight.getText().toString();
                if (!strWeigth.matches("")) {
                    int weight = Integer.parseInt(edWeight.getText().toString());
                    if (chRadioButton) {
                        int price = Integer.parseInt(lstPirce.get(typePosition));
                        result = (weight - weight / 10) * price;
                        textResult.setText("" + result);
                    } else {
                        int price = Integer.parseInt(lstPirce.get(typePosition));
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
            case R.id.nav_button:
                Intent intent = new Intent(MainActivity.this, activity_menu.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mySQLite.closeSQLiteDatabase();
    }
}

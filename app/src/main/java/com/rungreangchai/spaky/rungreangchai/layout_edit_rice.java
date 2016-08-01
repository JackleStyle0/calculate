package com.rungreangchai.spaky.rungreangchai;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class layout_edit_rice extends AppCompatActivity {

    private ListView lstItem;
    adapterCustom adapter;
    MySQLite mySQLite;
    Cursor mCursor;
    String[] prince;
    String[] typeRice;
    String strName;
    String strPrice;
    String tempName;
    FloatingActionButton fap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_edit_rice);

        mySQLite = new MySQLite(getApplicationContext());
        mCursor = mySQLite.selectFromTableRice();
        mCursor.moveToFirst();

        bindWidget();
        int i = 0;

        if (mCursor.getCount() > 0) {
            prince = new String[mCursor.getCount()];
            typeRice = new String[mCursor.getCount()];

            while (!mCursor.isAfterLast()) {
                prince[i] = mCursor.getString(mCursor.getColumnIndex(MySQLite.col_price));
                typeRice[i] = mCursor.getString(mCursor.getColumnIndex(MySQLite.col_type));
                i++;

                mCursor.moveToNext();
            }

            mCursor.close();

            adapter = new adapterCustom(getApplicationContext(), prince, typeRice);
            lstItem.setAdapter(adapter);

            lstItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    dialogEditDataInDatabase(typeRice[position], prince[position]);
                }
            });
        } else {
            String[] str = {"ยังไม่มีข้อมูล", "ยังไม่มีข้อมูล", "ยังไม่มีข้อมูล"
                    , "ยังไม่มีข้อมูล", "ยังไม่มีข้อมูล", "ยังไม่มีข้อมูล"};
            lstItem.setAdapter(new ArrayAdapter(this
                    , android.R.layout.simple_list_item_1, str));
            lstItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Snackbar.make(view, "ยังไม่มีข้อมูล", Snackbar.LENGTH_SHORT).show();
                }
            });
        }

        fap.setOnClickListener(new View.OnClickListener()

                               {
                                   @Override
                                   public void onClick(View v) {
                                       dialogInsertToDatabase();
                                   }
                               }

        );


    }

    public void bindWidget() {
        lstItem = (ListView) findViewById(R.id.listItem);
        fap = (FloatingActionButton) findViewById(R.id.fap);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mySQLite.closeSQLiteDatabase();
    }

    public void dialogInsertToDatabase() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(layout_edit_rice.this);
        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.insert_to_database, null);
        builder.setView(view);
        builder.setCancelable(true);

        final EditText edNameRice = (EditText) view.findViewById(R.id.ed_rice);
        final EditText edPriceRice = (EditText) view.findViewById(R.id.ed_price);
        Button btnSave = (Button) view.findViewById(R.id.button_save);
        Button btnCancel = (Button) view.findViewById(R.id.button_cancel);

        final AlertDialog dialog = builder.create();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edNameRice.getText().toString().matches("")) {
                    edNameRice.requestFocus();
                } else if (edPriceRice.getText().toString().matches("")) {
                    edPriceRice.requestFocus();
                } else {
                    String strNameRice = edNameRice.getText().toString();
                    String strPriceRice = edPriceRice.getText().toString();


                    mySQLite.addToTableRice(strNameRice, strPriceRice);
                    dialog.dismiss();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    public void dialogEditDataInDatabase(String name, String price) {
        AlertDialog.Builder builder = new AlertDialog.Builder(layout_edit_rice.this);
        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_edit_price, null);
        builder.setView(view);
        builder.setCancelable(true);

        final EditText edName = (EditText) view.findViewById(R.id.ed_name_rice);
        final EditText edPrice = (EditText) view.findViewById(R.id.ed_price);
        Button btnCancel = (Button) view.findViewById(R.id.button_cancel);
        Button btnSave = (Button) view.findViewById(R.id.button_save);

        tempName = name;
        edName.setText(name);
        edPrice.setText(price);

        final AlertDialog dialog = builder.create();
        final ReceiverThread thread = new ReceiverThread();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strName = edName.getText().toString();
                strPrice = edPrice.getText().toString();
                mySQLite.updateTableRice(tempName, strName, strPrice);
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private class ReceiverThread extends Thread {
        @Override
        public void run() {
            super.run();
            layout_edit_rice.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Cursor newCursor = mySQLite.selectFromTableRice();
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }

}

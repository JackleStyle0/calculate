package com.rungreangchai.spaky.rungreangchai;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


public class activity_menu extends Activity implements View.OnClickListener {
    ImageButton btnBack;

    LinearLayout layoutCal, layoutRang, layoutEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.side_menu);

        bindWidget();

        btnBack.setOnClickListener(this);
        layoutCal.setOnClickListener(this);
        layoutEdit.setOnClickListener(this);

    }

    public void bindWidget() {
        btnBack = (ImageButton) findViewById(R.id.back_button);
        layoutCal = (LinearLayout) findViewById(R.id.layout_id_calculate);
        layoutRang = (LinearLayout) findViewById(R.id.layout_id_change_range);
        layoutEdit = (LinearLayout) findViewById(R.id.layout_id_change_price);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.back_button:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
            case R.id.layout_id_calculate:
                intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                finish();
                break;
            case R.id.layout_id_change_price:
                intent = new Intent(getApplicationContext(), layout_edit_rice.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                finish();
                break;
            case R.id.layout_id_change_range:
        }
    }
}

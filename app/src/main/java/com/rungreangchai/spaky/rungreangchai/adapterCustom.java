package com.rungreangchai.spaky.rungreangchai;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.zip.Inflater;

/**
 * Created by spaky on 26/7/2559.
 */
public class adapterCustom extends BaseAdapter {

    private String[] prince;
    private String[] typeRice;
    private Context context;
    public LayoutInflater inflater;

    public adapterCustom(Context ctx, String[] price, String[] typeRice) {
        this.context = ctx;
        this.prince = price;
        this.typeRice = typeRice;
        inflater = LayoutInflater.from(context);
    }

    public void updateAdapter(String name, String price) {
        this.typeRice[0] = name;
        this.prince[0] = price;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return prince.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_edit_price, null);

            holder = new Holder();
            holder.txtPrince = (TextView) convertView.findViewById(R.id.txt_price_rice);
            holder.txtTypePrice = (TextView) convertView.findViewById(R.id.type_rice);


            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.txtPrince.setText(prince[position]);
        holder.txtTypePrice.setText(typeRice[position]);


        return convertView;
    }


    public class Holder {
        TextView txtPrince;
        TextView txtTypePrice;
    }
}

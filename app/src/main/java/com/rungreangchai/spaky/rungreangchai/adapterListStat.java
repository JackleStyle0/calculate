package com.rungreangchai.spaky.rungreangchai;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by spaky on 29/7/2559.
 */
public class AdapterListStat extends BaseAdapter {

    String[] date;
    String[] weight;
    String[] nameRice;
    String[] expend;
    String[] amount;
    Context context;

    LayoutInflater inflater;

    public AdapterListStat(Context ctx, String[] date, String[] weight, String[] nameRice, String[] expend, String[] amount) {
        this.context = ctx;
        this.date = date;
        this.weight = weight;
        this.nameRice = nameRice;
        this.expend = expend;
        this.amount = amount;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return date.length;
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
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.view_for_listview, null);
            holder = new ViewHolder();

            holder.txtDate = (TextView) convertView.findViewById(R.id.text_date);
            holder.txtWeigth = (TextView) convertView.findViewById(R.id.text_weight);
            holder.txtNameRice = (TextView) convertView.findViewById(R.id.text_name_rice);
            holder.txtExpend = (TextView) convertView.findViewById(R.id.text_result);
            holder.txtAmount = (TextView) convertView.findViewById(R.id.text_amount);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtDate.setText(date[position]);
        holder.txtWeigth.setText(weight[position]);
        holder.txtNameRice.setText(nameRice[position]);
        holder.txtExpend.setText(expend[position]);
        holder.txtAmount.setText(amount[position]+" กระสอบ");

        return convertView;
    }


    public class ViewHolder {
        TextView txtDate, txtWeigth, txtNameRice, txtExpend, txtAmount;
    }
}

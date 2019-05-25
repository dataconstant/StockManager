package com.example.softwareconstructionassign;

/**
 * Author - Abhishek Chetri (u6647717)
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class StockInfoAdapter extends BaseAdapter {

    Context context;
    ArrayList<stock> list;

    public StockInfoAdapter(Context context, ArrayList<stock> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        if (inflater!= null) convertView = inflater.inflate(R.layout.stockinfolayout, null);

        stock si = list.get(position);
        TextView symbol = convertView.findViewById(R.id.stockname);
        TextView volume = convertView.findViewById(R.id.stockvolume);

                symbol.setText("Symbol - "+si.getStock());
                volume.setText("Volume - "+si.getVolume());

                return convertView;

    }
}

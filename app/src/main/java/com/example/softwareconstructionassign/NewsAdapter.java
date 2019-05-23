package com.example.softwareconstructionassign;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends BaseAdapter {

    Context context;
    ArrayList<News> rowNewsItems;

    public NewsAdapter(Context context, ArrayList<News> newsItems) {
        this.context = context;
        this.rowNewsItems = newsItems;
    }

    @Override
    public int getCount() {
        return rowNewsItems.size();
    }

    @Override
    public Object getItem(int i) {
        return rowNewsItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return rowNewsItems.indexOf(getItem(i));
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        News newsObj = (News) getItem(i);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.newslistrowlayout, null);
        TextView textView = (TextView) view.findViewById(R.id.text1);
        ImageView imageView = (ImageView) view.findViewById(R.id.image1);
        textView.setText(newsObj.getNewsText());
        Picasso.get().load(newsObj.getNewImageURL()).into(imageView);
        return view;
    }

}

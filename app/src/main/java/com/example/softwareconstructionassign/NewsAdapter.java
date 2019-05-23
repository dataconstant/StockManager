/************
 * Project: Software Construction
 * Author: Mansoor Ali Halari
 */
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

/***
 * Class of news adapter
 */
public class NewsAdapter extends BaseAdapter {

    Context context;
    ArrayList<News> rowNewsItems;

    /***
     * Contructor of news adapter
     * @param context
     * @param newsItems
     */
    public NewsAdapter(Context context, ArrayList<News> newsItems) {
        this.context = context;
        this.rowNewsItems = newsItems;
    }

    /***
     * get count return size of news list
     * @return
     */
    @Override
    public int getCount() {
        return rowNewsItems.size();
    }

    /***
     * get the news item
     * @param i
     * @return
     */
    @Override
    public Object getItem(int i) {
        return rowNewsItems.get(i);
    }

    /***
     * get position in the list
     * @param i
     * @return
     */
    @Override
    public long getItemId(int i) {
        return rowNewsItems.indexOf(getItem(i));
    }

    /***
     * Overridden view method
     * @param i
     * @param view
     * @param viewGroup
     * @return
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        if (inflater!= null) view = inflater.inflate(R.layout.newslistrowlayout, null);

        News newsObj = (News) getItem(i);

        TextView textView = view.findViewById(R.id.text1);
        ImageView imageView = view.findViewById(R.id.image1);
        textView.setText(newsObj.getNewsText());
        Picasso.get().load(newsObj.getNewImageURL()).into(imageView);

        return view;
    }

}

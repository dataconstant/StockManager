/************
 * Project: Software Construction
 * Author: Mansoor Ali Halari
 */
package com.example.softwareconstructionassign;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;


public class stockNews extends AppCompatActivity {

    private ArrayList<String> arrList = new ArrayList<>();
    private ArrayList stockList;
    private int[] arrListSentiment = new int[3];
    private BarChart mchart;
    private String stockCode;
    private JSONObject stockNewsData;
    private HashMap<Integer,String> newsHashMap = new HashMap<>();
    private String email;
    private ArrayList<News> newsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stocknews);

        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        final Intent intent = getIntent();
        final BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        final Spinner selectStock = findViewById(R.id.stocksDropdown);
        final ListView listView = findViewById(R.id.liststockNews);

        stockList = intent.getStringArrayListExtra("stocklist");
        email = intent.getStringExtra("email");
        stockCode = stockList.get(0).toString();

        //get the stock data
        getJSON(stockCode,listView);

        //fill the dropdown list
        fillDropdownList(selectStock,stockList);

        //Listener to get spinner value
        selectStock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                stockCode = selectStock.getSelectedItem().toString();
                getJSON(stockCode,listView);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                }
        });

        //Listener to get list item value
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openLink(position);
            }
        });

        //Listener on bottom navigation
        bottomNavigationView.setSelectedItemId(R.id.action_news);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    selectNavigationMenu(item);
                return true;
            }
        });
    }

    /****
     * Function to getJSON object from news api website and fill the list view and chart
     * @param stock
     * @param listView
     */
    public void getJSON(String stock,final ListView listView){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url =getString(R.string.API_News_URL)+stockCode+"&items="+getString(R.string.API_News_Items)
                +"&fallback="+getString(R.string.API_News_Fallback)+"&token="+getString(R.string.API_News_key);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                stockNewsData = response;
                if (stockNewsData != null){
                    fillListView(listView,stockNewsData);
                    fillChart(arrListSentiment);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        }
        );
        queue.add(jsonObjectRequest);
    }

    /***
     * fill drop down list
     * @param selectStock
     * @param stockList
     * @return
     */
    public boolean fillDropdownList(Spinner selectStock,ArrayList stockList){
        if(selectStock == null || stockList == null) return false;
        if(stockList != null){
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),
                    android.R.layout.simple_list_item_1,stockList);
            selectStock.setAdapter(adapter);
        }
        return true;
    }

    /***
     * Function to fill the list with news items
     * @param listView
     * @param response
     * @return
     */
    public boolean fillListView(ListView listView,JSONObject response){
        if(listView == null || response == null) return false;
        try {

            JSONArray value = response.getJSONArray("data");

            arrListSentiment[0] = 0;
            arrListSentiment[1] = 0;
            arrListSentiment[2] = 0;

            arrList.clear();
            newsHashMap.clear();
            newsList.clear();

            for (int i=0;  i<value.length();i++){

                JSONObject result = value.getJSONObject(i);

                arrList.add(result.getString("text"));
                newsHashMap.put(i,result.getString("news_url"));

                News n = new News(result.getString("text"),result.getString("news_url"),result.getString("image_url"));
                newsList.add(n);

                switch (result.getString("sentiment")){
                    case "Positive": arrListSentiment[0] +=1; break;
                    case "Neutral": arrListSentiment[1] +=1; break;
                    case "Negative": arrListSentiment[2] +=1; break;
                }
            }
            NewsAdapter adapter = new NewsAdapter(stockNews.this,newsList);
            //ArrayAdapter<String> adapter = new ArrayAdapter<String>(stockNews.this,
             //       android.R.layout.simple_list_item_1, android.R.id.text1, arrList);
            listView.setAdapter(adapter);
        } catch (JSONException e) {
        }
        return true;
    }

    /***
     * function to fill the chart displaying the sentiments about the stocks based on news
     * @param arrListSentiment
     * @return
     */
    public boolean fillChart(int[] arrListSentiment){

        if (arrListSentiment == null) return false;

        String[] mBarLabel = new String[3];

        mBarLabel[0] = "Positive";
        mBarLabel[1] = "Neutral";
        mBarLabel[2] = "Negative";

        mchart =(BarChart) findViewById(R.id.barchart);
        mchart.setDrawBarShadow(false);
        mchart.setDrawValueAboveBar(false);
        mchart.setPinchZoom(true);
        mchart.getDescription().setEnabled(false);
        mchart.setDrawGridBackground(false);
        mchart.invalidate();
        mchart.animateY(500);
        mchart.setFitBars(true);

        ArrayList<BarEntry> yvals = new ArrayList<>();
        for(int i=0;i<arrListSentiment.length;i++)
        {
            int value = arrListSentiment[i];
            yvals.add(new BarEntry(i,value));
        }

        BarDataSet set=new BarDataSet(yvals,"Sentiment chart (Positive, Neutral, Negative) of "+stockCode);
        set.setColors(ColorTemplate.MATERIAL_COLORS);
        set.setDrawValues(true);

        BarData data=new BarData(set);
        mchart.setData(data);

        return true;
    }

    /****
     * Open the news link in external browser
     * @param position
     */
    public void openLink(int position){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsHashMap.get(position)));
        startActivity(browserIntent);
    }

    /***
     *     Code for Menu Creation and Selection
    */

    /****
     * Create Menu option
     * @param menu
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_mainscreen, menu);
        return true;
    }

    /****
     * Handle menu item selection
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_a:
                about();
                return true;
            case R.id.menu_h:
                help();
                return true;
            case R.id.menu_l:
            logOut();
            return  true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logOut() {
        Intent intent = new Intent(getBaseContext(),login.class);
        startActivity(intent);
    }

    /***
     * move to about activity
     */
    public void about(){
        Intent intent = new Intent(getBaseContext(),About.class);
        startActivity(intent);
    }

    /***
     * move to help
     */
    public void help(){
        Intent intent = new Intent(getBaseContext(),Help.class);
        startActivity(intent);
    }

    /***
     * select navigation options
     * @param item
     */
    public void selectNavigationMenu(MenuItem item){
        switch (item.getItemId()) {
            case R.id.action_dash:
                Intent intent = new Intent(getBaseContext(),mainscreen.class);
                intent.putExtra("stocklist",stockList);
                intent.putExtra("email",email);
                startActivity(intent);
                break;
            case R.id.action_analytics:
                Intent analyticsintent = new Intent(getBaseContext(),analytics.class);
                analyticsintent.putExtra("stocklist",stockList);
                analyticsintent.putExtra("email",email);
                startActivity(analyticsintent);
                break;
            case R.id.action_news:
                Intent newsintent = new Intent(getBaseContext(),stockNews.class);
                newsintent.putExtra("stocklist",stockList);
                newsintent.putExtra("email",email);
                startActivity(newsintent);
                break;

        }
    }
}

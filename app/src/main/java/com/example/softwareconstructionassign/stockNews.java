/************
 * Project: Software Construction
 * Author: Mansoor Ali Halari
 */
package com.example.softwareconstructionassign;

import android.content.Intent;
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

public class stockNews extends AppCompatActivity {

    private ArrayList<String> arrList = new ArrayList<>();
    private int[] arrListSentiment = new int[3];
    private BarChart mchart;
    private String stockCode;
    public JSONObject stockNewsData;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stocknews);

        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        final Intent intent = getIntent();
        final ListView listView = findViewById(R.id.liststockNews);
        final BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        final ArrayList stockList = intent.getStringArrayListExtra("stocklist");
        final String email = intent.getStringExtra("email");
        final Spinner selectStock = findViewById(R.id.stocksDropdown);

        //fill the dropdown list
        fillDropdownList(selectStock,stockList);

        //get the stock data
        stockCode = stockList.get(0).toString();
        //stockNewsData = getStocksNews(stockCode);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://stocknewsapi.com/api/v1?tickers="+stockCode+"&items=30&fallback=true&token=kfpcjr3gnmsrjof4ppekyudomxwoc8eickvsgkgn";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                stockNewsData = response;
                if (stockNewsData != null){
                    fillList(listView,stockNewsData);
                    fillChart(arrListSentiment);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        }
        );
        queue.add(jsonObjectRequest);

        //get spinner value
        selectStock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                stockCode = selectStock.getSelectedItem().toString();
                RequestQueue queue = Volley.newRequestQueue(stockNews.this);
                String url ="https://stocknewsapi.com/api/v1?tickers="+stockCode+"&items=30&fallback=true&token=kfpcjr3gnmsrjof4ppekyudomxwoc8eickvsgkgn";
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        stockNewsData = response;
                        if (stockNewsData != null){
                            adapter.clear();
                            fillList(listView,stockNewsData);
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

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
/*
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_recents:
                        Toast.makeText(stockNews.this, "Recents", Toast.LENGTH_SHORT).show();
                        //Intent intent = new Intent(stockNews.this,mainscreen.class);
                        //startActivity(intent);
                        break;
                    case R.id.action_favorites:
                        Toast.makeText(stockNews.this, "Favorites", Toast.LENGTH_SHORT).show();
                        break;

                }
                return true;
            }
        });




*/


        bottomNavigationView.setSelectedItemId(R.id.action_news);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
                return true;
            }
        });
    }

    public JSONObject getStocksNews(String stock){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://stocknewsapi.com/api/v1?tickers="+stockCode+"&items=30&fallback=true&token=kfpcjr3gnmsrjof4ppekyudomxwoc8eickvsgkgn";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) { stockNewsData = response; }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        });
        queue.add(jsonObjectRequest);
        return stockNewsData;
    }

    /****
     * fill dropdown list
     * @param selectStock
     * @param stockList
     */
    public void fillDropdownList(Spinner selectStock,ArrayList stockList){
        if(stockList != null){
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),
                    android.R.layout.simple_list_item_1,stockList);
            selectStock.setAdapter(adapter);
        }
    }

    /*******
     * Function to fill the list with news items
     * @param listView
     * @param response
     */
    public void fillList(ListView listView,JSONObject response){
        try {
            JSONArray value = response.getJSONArray("data");
            arrListSentiment[0] = 0;
            arrListSentiment[1] = 0;
            arrListSentiment[2] = 0;
            for (int i=0;  i<value.length();i++){
                JSONObject result = value.getJSONObject(i);
                arrList.add(result.getString("text"));
                System.out.println(result.getString("sentiment"));
                switch (result.getString("sentiment")){
                    case "Positive": arrListSentiment[0] +=1; break;
                    case "Neutral": arrListSentiment[1] +=1; break;
                    case "Negative": arrListSentiment[2] +=1; break;
                }
                }
            adapter = new ArrayAdapter<String>(stockNews.this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, arrList);
            listView.setAdapter(adapter);
        } catch (JSONException e) {
            // add code if needed
        }

    }

    /*****
     * function to fill the chart displaying the sentiments about the stocks based on news
     * @param arrListSentiment
     */
    public void fillChart(int[] arrListSentiment){

        String[] mBarLabel = new String[3];

        mBarLabel[0] = "Positive";
        mBarLabel[1] = "Positive";
        mBarLabel[2] = "Positive";

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
            System.out.println(arrListSentiment[i]);
            yvals.add(new BarEntry(i,value));
        }

        BarDataSet set=new BarDataSet(yvals,"Sentiment chart of the news of");
        set.setColors(ColorTemplate.MATERIAL_COLORS);
        set.setDrawValues(true);

        BarData data=new BarData(set);
        mchart.setData(data);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_mainscreen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_a:
                about();
                return true;
            case R.id.menu_h:
                help();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void about(){
        Intent intent = new Intent(getBaseContext(),mainscreen.class);
        startActivity(intent);
    }
    public void help(){
        Intent intent = new Intent(getBaseContext(),analytics.class);
        startActivity(intent);
    }

}

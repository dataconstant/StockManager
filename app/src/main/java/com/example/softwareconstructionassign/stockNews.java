package com.example.softwareconstructionassign;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jjoe64.graphview.series.DataPoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class stockNews extends AppCompatActivity {

    JSONObject stockData;
    public ArrayList<String> arrList = new ArrayList<>();
    public int[] arrListSentiment = new int[3];
    private BarChart mchart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stocknews);

        final ListView listView = (ListView) findViewById(R.id.liststockNews);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://stocknewsapi.com/api/v1?tickers=MSFT&items=30&fallback=true&token=kfpcjr3gnmsrjof4ppekyudomxwoc8eickvsgkgn";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                fillList(listView,response);
                fillChart(arrListSentiment);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        }
        );
        queue.add(jsonObjectRequest);


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_recents:
                        Toast.makeText(stockNews.this, "Recents", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(stockNews.this,mainscreen.class);
                        startActivity(intent);
                        break;
                    case R.id.action_favorites:
                        Toast.makeText(stockNews.this, "Favorites", Toast.LENGTH_SHORT).show();
                        break;

                }
                return true;
            }
        });

    }

    public void fillList(ListView listView,JSONObject response){
        try {
            JSONArray value = response.getJSONArray("data");
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
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(stockNews.this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, arrList);
            listView.setAdapter(adapter);
        } catch (JSONException e) {
            // add code if needed
        }

    }

    public void fillChart(int[] arrListSentiment){

        String[] mBarLabel = new String[3];

        mBarLabel[0] = "Positive";
        mBarLabel[0] = "Positive";
        mBarLabel[0] = "Positive";

        mchart =(BarChart) findViewById(R.id.barchart);
        mchart.setDrawBarShadow(false);
        mchart.setDrawValueAboveBar(false);
        mchart.setPinchZoom(true);
        mchart.getDescription().setEnabled(false);
        mchart.setDrawGridBackground(false);
        mchart.invalidate();
        mchart.animateY(500);
        mchart.setFitBars(true);

        //mchart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(mBarLabel));

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
            case R.id.menu_p:
                portfolio();
                return true;
            case R.id.menu_a:
                analytics();
                return true;
            case R.id.menu_l:
                livefeed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void portfolio(){
        Intent intent = new Intent(getBaseContext(),mainscreen.class);
        startActivity(intent);
    }
    public void analytics(){
        Intent intent = new Intent(getBaseContext(),analytics.class);
        startActivity(intent);
    }
    public void livefeed(){
        Intent intent = new Intent(getBaseContext(),livefeed.class);
        startActivity(intent);
    }



}

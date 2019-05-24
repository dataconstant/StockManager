/******
 * Project: Software Construction
 * Author: KRUTHI KRISHNA SENAPATHI (U6601532)
 */
package com.example.softwareconstructionassign;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

public class analytics extends AppCompatActivity {
    private BarChart mchart;
    String email;
    private String stockCode;

    /**
     * OnCreate of analytics activity.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);
        Intent intent = getIntent();
        email = (String) getIntent().getSerializableExtra("email");
        final RequestQueue queue = Volley.newRequestQueue(this);
        // TextView textView = findViewById(R.id.textView1);
        //textView.setText("Kruthi");
        mchart =(BarChart) findViewById(R.id.barchart); //creating the bar chart.
        mchart.getDescription().setEnabled(false);
        mchart.setFitBars(true);
        final ArrayList stock = getIntent().getStringArrayListExtra("stocklist");
        System.out.println("stocklist "+stock);
        final String s= (String) stock.get(0);
        final Spinner selectStock = findViewById(R.id.sp); // creating the drop down list with stocks
        fillDropdownList(selectStock,stock);
        stockCode = stock.get(0).toString();

        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        selectStock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                stockCode = selectStock.getSelectedItem().toString();
                // System.out.println(" "+stockCode);
                String url = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=" + stockCode + "&interval=5min&apikey=GF4EX3XKAFSY29GH";
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest // getting the required data by using the JSON file and parsing it
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONObject ts = response.getJSONObject("Time Series (5min)");
                                    Iterator<String> iter = ts.keys();
                                    String key = (iter.next());
                                    JSONObject result = ts.getJSONObject(key);
                                    System.out.println("Result" + ts);
                                    int open = result.getInt("1. open"); //get required data
                                    int high = result.getInt("2. high");
                                    int low = result.getInt("3. low");
                                    int close = result.getInt("4. close");
                                    int vol=result.getInt("5. volume");
                                    System.out.println(" " + open + " " + high + " " + close + " " + low);
                                    ArrayList<BarEntry> yvals = new ArrayList<>();
                                    yvals.add(new BarEntry(0, (float) open));
                                    yvals.add(new BarEntry(1, (float) high));   //set bar data
                                    yvals.add(new BarEntry(2, (float) low));
                                    yvals.add(new BarEntry(3, (float) close));
                                    BarDataSet set = new BarDataSet(yvals, " open,high,low,close of " + stockCode+" respectively ");
                                    set.setColors(ColorTemplate.MATERIAL_COLORS); //set colours to the graph
                                    set.setDrawValues(true);
                                    BarData data = new BarData(set);
                                    mchart.setData(data);
                                    mchart.invalidate();
                                    mchart.animateY(500); // animation while loading the barchart
                                    TextView tdate=(TextView) findViewById(R.id.date);
                                    Long date =System.currentTimeMillis();
                                    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy\nhh-mm-ss a");
                                    String dateString = sdf.format(date);
                                    tdate.setText(dateString);
                                    TextView n =(TextView) findViewById(R.id.name);
                                    n.setText(stockCode);
                                    TextView o=(TextView)findViewById(R.id.op);
                                    o.setText("OPEN STOCK:"+Integer.toString(open));
                                    TextView cc=(TextView)findViewById(R.id.cl);
                                    cc.setText("CLOSE STOCK:"+Integer.toString(close));
                                    TextView h=(TextView)findViewById(R.id.hi);
                                    h.setText("HIGH STOCK VALUE:"+Integer.toString(high)); //assign values to the respective textview
                                    TextView l=(TextView)findViewById(R.id.lo);
                                    l.setText("LOW STOCK VALUE:"+Integer.toString(low));
                                    TextView v=(TextView)findViewById(R.id.vol);
                                    v.setText("VOLUME"+Integer.toString(vol));
                                    TextView ch=(TextView)findViewById(R.id.change);
                                    ch.setText("STOCK CHANGE:"+Integer.toString(open-close));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });
                queue.add(jsonObjectRequest);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });
        String url = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=" + stockCode + "&interval=5min&apikey=GF4EX3XKAFSY29GH";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject ts = response.getJSONObject("Time Series (5min)");
                            Iterator<String> iter = ts.keys();
                            String key = (iter.next());
                            JSONObject result = ts.getJSONObject(key);
                            System.out.println("Result" + ts);
                            int open = result.getInt("1. open");
                            int high = result.getInt("2. high");
                            int low = result.getInt("3. low");
                            int close = result.getInt("4. close");
                            int vol=result.getInt("5. volume");
                            System.out.println(" " + open + " " + high + " " + close + " " + low);
                            ArrayList<BarEntry> yvals = new ArrayList<>();
                            yvals.add(new BarEntry(0, (float) open));
                            yvals.add(new BarEntry(1, (float) high));
                            yvals.add(new BarEntry(2, (float) low));
                            yvals.add(new BarEntry(3, (float) close));
                            BarDataSet set = new BarDataSet(yvals, " open,high,low,close of " + stockCode+" respectively ");
                            set.setColors(ColorTemplate.MATERIAL_COLORS);
                            set.setDrawValues(true);
                            BarData data = new BarData(set);
                            mchart.setData(data);
                            mchart.invalidate();
                            mchart.animateY(500);
                            TextView tdate=(TextView) findViewById(R.id.date);
                            Long date =System.currentTimeMillis();
                            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy\nhh-mm-ss a");
                            String dateString = sdf.format(date);
                            tdate.setText(dateString);
                            TextView n =(TextView) findViewById(R.id.name);
                            n.setText(stockCode);
                            TextView o=(TextView)findViewById(R.id.op);
                            o.setText("OPEN STOCK:"+Integer.toString(open));
                            TextView cc=(TextView)findViewById(R.id.cl);
                            cc.setText("CLOSE STOCK:"+Integer.toString(close));
                            TextView h=(TextView)findViewById(R.id.hi);
                            h.setText("HIGH STOCK VALUE:"+Integer.toString(high));
                            TextView l=(TextView)findViewById(R.id.lo);
                            l.setText("LOW STOCK VALUE:"+Integer.toString(low));
                            TextView v=(TextView)findViewById(R.id.vol);
                            v.setText("VOLUME"+Integer.toString(vol));
                            TextView ch=(TextView)findViewById(R.id.change);
                            ch.setText("STOCK CHANGE:"+Integer.toString(open-close));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        queue.add(jsonObjectRequest);


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.action_analytics);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_dash:
                        Intent intent = new Intent(getBaseContext(),mainscreen.class);
                        intent.putExtra("stocklist",stock);
                        intent.putExtra("email",email);
                        startActivity(intent);
                        break;
                    case R.id.action_analytics:
                        Intent analyticsintent = new Intent(getBaseContext(),analytics.class);
                        analyticsintent.putExtra("stocklist",stock);
                        analyticsintent.putExtra("email",email);
                        startActivity(analyticsintent);
                        break;
                    case R.id.action_news:
                        Intent newsintent = new Intent(getBaseContext(),stockNews.class);
                        newsintent.putExtra("stocklist",stock);
                        newsintent.putExtra("email",email);
                        startActivity(newsintent);
                        break;

                }
                return true;
            }
        });

    }
    //to fill the spinner with the stocks.
    public void fillDropdownList(Spinner selectStock,ArrayList stock){
        if(stock != null){
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),
                    android.R.layout.simple_list_item_1,stock);
            selectStock.setAdapter(adapter);
        }
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

    public void about(){
        Intent intent = new Intent(getBaseContext(),About.class);

        startActivity(intent);
    }
    public void help(){
        Intent intent = new Intent(getBaseContext(),Help.class);

        startActivity(intent);
    }

}

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
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class livefeed extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livefeed);
        Intent intent = getIntent();

        Spinner spinner = (Spinner) findViewById(R.id.planets_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.stocks_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        final TextView textView_date1 = findViewById(R.id.textView1);
        final TextView textView_date2 = findViewById(R.id.textView2);

        final LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();
        final GraphView graph = (GraphView) findViewById(R.id.graph);
        graph.setVisibility(View.INVISIBLE);
        // Instantiate the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=MSFT&outputsize=compact&apikey='GF4EX3XKAFSY29GH'";

        // Request json object response from the provided URL
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject value = response.getJSONObject("Time Series (Daily)");
                        Iterator<String> iter = value.keys();
                        double x=0,y=0;
                        String date1="",date2="";
                        
                        while (iter.hasNext()) {
                            String key = iter.next();
                            x += 1;
                            if (date1 == "") date1 = key;
                            try {
                                JSONObject value1 = value.getJSONObject(key);
                                 y = Double.parseDouble(value1.getString("4. close"));
                                series.appendData(new DataPoint(x,y),true,value.length());
                            } catch (JSONException e) {
                                // add code if needed
                            }
                            date2 = key;
                        }
                        textView_date1.setText(date2);
                        textView_date2.setText(date1);
                        graph.addSeries(series);
                        graph.setVisibility(View.VISIBLE);
                    } catch (JSONException e) {
                        // add code if needed
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            }
        );
        queue.add(jsonObjectRequest);


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_recents:
                        Toast.makeText(livefeed.this, "Recents", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(livefeed.this,mainscreen.class);
                        startActivity(intent);
                        break;
                    case R.id.action_favorites:
                        Toast.makeText(livefeed.this, "Favorites", Toast.LENGTH_SHORT).show();
                        break;

                }
                return true;
            }
        });
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

package com.example.softwareconstructionassign;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.series.DataPoint;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class stockNews extends AppCompatActivity {

    JSONObject stockData;
    public ArrayList<String> arrList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stocknews);

        final ListView listView = (ListView) findViewById(R.id.liststockNews);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://stocknewsapi.com/api/v1?tickers=MSFT&items=30&fallback=true&token=isto2i4cq7h369pzieouu5wxaw3n8bbjdgorkvaq";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    fillList(listView,response.getJSONObject("data"));
                    }
                catch (JSONException e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        }
        );
        queue.add(jsonObjectRequest);
    }

    public void fillList(ListView listView,JSONObject response){
        System.out.println("Hi");
        try {
            JSONObject value = response.getJSONObject("data");
            Iterator<String> iter = value.keys();
            while (iter.hasNext()) {
                String key = iter.next();
                try {
                    JSONObject value1 = value.getJSONObject(key);
                    arrList.add(value1.getString("text"));
                    } catch (JSONException e) {
                    // add code if needed
                }
            }
        } catch (JSONException e) {
            // add code if needed
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, arrList);
        listView.setAdapter(adapter);
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

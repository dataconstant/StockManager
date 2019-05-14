package com.example.softwareconstructionassign;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class mainscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        final EditText search = (EditText) findViewById(R.id.editText);
        final Button buttonsearch = (Button) findViewById(R.id.searchButton);
        final RequestQueue queue = Volley.newRequestQueue(this);

        buttonsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String term = search.getText().toString();
                String url = "https://www.alphavantage.co/query?function=SYMBOL_SEARCH&keywords="+term+"&apikey=GF4EX3XKAFSY29GH";
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray ts = response.getJSONArray("bestMatches");
                                    JSONObject result = ts.getJSONObject(0);
                                    Toast.makeText(getApplicationContext(),result.getString("2. name"),Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO: Handle error
                            }
                        });
                queue.add(jsonObjectRequest);

            }
        });

        Intent intent = getIntent();

    }

    @Override
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

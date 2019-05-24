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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

// Code by Abhishek Chetri (u6647717)

public class mainscreen extends AppCompatActivity {

    DatabaseReference dblogin;
    String stocklist = "";
    String email;
    ArrayList<String> list = new ArrayList<>();
    String deletedlist;
    boolean present;
    ArrayAdapter<String> adapter;
    ListView listview;
    Spinner selectStock;
    TextView search;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        dblogin = FirebaseDatabase.getInstance().getReference("users");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        // receiving the bundle information sent from the login activity
        email = (String) getIntent().getSerializableExtra("email");
        String name = email.split("@")[0];


        search = findViewById(R.id.editText);
        final Button buttonsearch = (Button) findViewById(R.id.searchButton);
        queue = Volley.newRequestQueue(this);
        listview = (ListView) findViewById(R.id.listview);
        final TextView deleteText = findViewById(R.id.deleteText);
        selectStock = findViewById(R.id.selectStock);
        final Button deleteButton = findViewById(R.id.deleteButton);
        final TextView textstocklist = findViewById(R.id.textstocklist);
        TextView textView7 = findViewById(R.id.textView7);
        textView7.setText("Welcome "+name.toUpperCase()+"!");

        //To disable back button
        getSupportActionBar().setHomeButtonEnabled(false);

        // Event Listener for getting the data from database and populating the listview and the spinner
        dblogin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setlist(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        // CLick listener for searching a string.
        buttonsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchbutton();
            }
        });

        // This click listener will delete the stock selected from the spinner.
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletebutton();
            }
        });

        // This is for the bottom navigation bar. The user will be able to select the next activities from the navigation bar.
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.action_dash);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_dash:
                        Intent intent = new Intent(getBaseContext(),mainscreen.class);
                        intent.putExtra("stocklist",list);
                        intent.putExtra("email",email);
                        startActivity(intent);
                        break;
                    case R.id.action_analytics:
                        if(list.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Please add a stock", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Intent analyticsintent = new Intent(getBaseContext(), analytics.class);
                            analyticsintent.putExtra("stocklist", list);
                            analyticsintent.putExtra("email", email);
                            startActivity(analyticsintent);
                        }
                        break;
                    case R.id.action_news:
                        if(list.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Please add a stock", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Intent newsintent = new Intent(getBaseContext(), stockNews.class);
                            newsintent.putExtra("stocklist", list);
                            newsintent.putExtra("email", email);
                            startActivity(newsintent);
                        }
                        break;

                }
                return true;
            }
        });



    }

    /***
     * This function will handle the delete operation. It will first select the stock from the arraylist and then delete the stock from the arraylist.
     * This will then replace the stock data on the database.
     */

    private void deletebutton(){
        if (!list.isEmpty()) {
            String selected = selectStock.getSelectedItem().toString();
            int i = 0;
            for (String s : list) {
                i++;
                if (s.equals(selected)) {
                    break;
                }
            }
            list.remove(i - 1);
            System.out.println(list.size());
            stocklist = "";

            for (String s : list) {

                if (list.size() >= 1) {
                    stocklist = stocklist + ";" + s;
                } else if (list.size() == 0) {
                    stocklist = "";
                }

            }

            // This event listener will replace the stock data present in the database.
            dblogin.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    emailclass eid = null;
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        eid = new emailclass(ds.getValue());
                        eid.setEmail(ds.child("email").getValue().toString());
                        eid.setstocks(ds.child("stocks").getValue().toString());
                        if (email.equals(eid.email)) {
                            // Updating the stock list on database.
                            if (list.size() == 0)
                                ds.getRef().child("stocks").setValue("");
                            else
                                ds.getRef().child("stocks").setValue(stocklist);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

            listview.setAdapter(adapter);
            selectStock.setAdapter(adapter);

        }
        else{
            Toast.makeText(getApplicationContext(), "Nothing to delete", Toast.LENGTH_SHORT).show();
        }
    }


    /***
     * This will take the input from the user and once the button is pressed, will call API and receive the stock names closest to the search term.
     * Once the stock symbol has been found. It will check if the symbol is already present in the database and will add it to the database if the symbol is not present.
     */

    private void searchbutton(){
        String term = search.getText().toString();
        String url = "https://www.alphavantage.co/query?function=SYMBOL_SEARCH&keywords=" + term + "&apikey=GF4EX3XKAFSY29GH";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray ts = response.getJSONArray("bestMatches");
                            JSONObject result = ts.getJSONObject(0);
                            final String newstock = result.getString("1. symbol");
                            present = false;

                            for(String s:list){
                                if(newstock.equals(s)){
                                    present=true;
                                }
                            }

                            if(present==false) {
                                if (stocklist != ";") {
                                    stocklist = stocklist + ";" + newstock;
                                } else {
                                    stocklist = newstock;
                                }
                            }

                            // This Event listener will check of the stock is already present in the database and will add it. if the stock is not present.
                            dblogin.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    emailclass eid = null;
                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                        eid = new emailclass(ds.getValue());
                                        eid.setEmail(ds.child("email").getValue().toString());
                                        eid.setstocks(ds.child("stocks").getValue().toString());
                                        if (email.equals(eid.email) && present==false) {
                                            ds.getRef().child("stocks").setValue(stocklist);
                                            Toast.makeText(getApplicationContext(), "Added " + newstock + " to your portfolio", Toast.LENGTH_SHORT).show();
                                        }
                                        else if(present==true){
                                            Toast.makeText(getApplicationContext(), "The stock is already added", Toast.LENGTH_SHORT).show();
                                            break;
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                }
                            });
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

    /***
     * This function will get the data for the user and will update the listview and spinner.
     * @param dataSnapshot
     */

    private void setlist(DataSnapshot dataSnapshot){
        emailclass eid = null;
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            eid = new emailclass(ds.getValue());
            eid.setEmail(ds.child("email").getValue().toString());
            eid.setstocks(ds.child("stocks").getValue().toString());
            if (email.equals(eid.email)) {

                // getting the stock data from database after checking the user email.
                stocklist = eid.getstocks();
                String[] separated = stocklist.split(";");

                for (int i = 1; i < separated.length; i++) {
                    list.add(separated[i]);
                    adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, list);
                    listview.setAdapter(adapter);
                    selectStock.setAdapter(adapter);
                }

            }
        }
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
        Intent intent = new Intent(getBaseContext(),About.class);
        startActivity(intent);
    }
    public void help(){
        Intent intent = new Intent(getBaseContext(),Help.class);
        startActivity(intent);
    }

}

package com.example.softwareconstructionassign;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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


public class mainscreen extends AppCompatActivity {

    DatabaseReference dblogin;
    String stocklist = "";
    String email;
    ArrayList<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;
    String deletedlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        dblogin = FirebaseDatabase.getInstance().getReference("users");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        email = (String) getIntent().getSerializableExtra("email");

        final TextView search = findViewById(R.id.editText);
        final Button buttonsearch = (Button) findViewById(R.id.searchButton);
        final RequestQueue queue = Volley.newRequestQueue(this);
        final ListView listview = (ListView) findViewById(R.id.listview);
        final TextView deleteText = findViewById(R.id.deleteText);
        final Spinner selectStock = findViewById(R.id.selectStock);
        final Button deleteButton = findViewById(R.id.deleteButton);



        dblogin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            private void showData(DataSnapshot dataSnapshot) {
                emailclass eid = null;
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    eid = new emailclass(ds.getValue());
                    eid.setEmail(ds.child("email").getValue().toString());
                    eid.setstocks(ds.child("stocks").getValue().toString());
                    if(email.equals(eid.email)) {
                        stocklist = eid.getstocks();
                        String[] separated= stocklist.split(";");

                        for(int i=1;i<separated.length;i++){
                            list.add(separated[i]);
                        }

                        adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, list);
                        listview.setAdapter(adapter);
                        selectStock.setAdapter(adapter);

                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


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
                                    final String newstock = result.getString("1. symbol");
                                    if(stocklist!=";") {
                                        stocklist = stocklist + ";" + newstock;
                                    }else{
                                        stocklist = newstock;
                                    }


                                    dblogin.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            showData(dataSnapshot);
                                        }
                                        private void showData(DataSnapshot dataSnapshot) {
                                            emailclass eid = null;
                                            for (DataSnapshot ds : dataSnapshot.getChildren()){
                                                eid = new emailclass(ds.getValue());
                                                eid.setEmail(ds.child("email").getValue().toString());
                                                eid.setstocks(ds.child("stocks").getValue().toString());
                                                if(email.equals(eid.email)) {
                                                    ds.getRef().child("stocks").setValue(stocklist);
                                                    Toast.makeText(getApplicationContext(), "Added "+ newstock+" to your portfolio", Toast.LENGTH_LONG).show();
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
                                // TODO: Handle error
                            }
                        });
                queue.add(jsonObjectRequest);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selected = selectStock.getSelectedItem().toString();
                int i=0;
                for(String s:list){
                    i++;
                    if(s.equals(selected)){
                        break;
                    }
                }
                list.remove(i-1);
                System.out.println(list.size());
                stocklist = "";

                for(String s : list){
                    
                    if(list.size()>=1){
                        stocklist = stocklist +";"+ s;
                    }
                    else if (list.size()==0){
                        stocklist = "";
                    }

                }
                System.out.println(stocklist);

                dblogin.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        showData(dataSnapshot);
                    }

                    private void showData(DataSnapshot dataSnapshot) {
                        emailclass eid = null;
                        for (DataSnapshot ds : dataSnapshot.getChildren()){
                            eid = new emailclass(ds.getValue());
                            eid.setEmail(ds.child("email").getValue().toString());
                            eid.setstocks(ds.child("stocks").getValue().toString());
                            if(email.equals(eid.email)) {
                                if(list.size()==0)
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
        });
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
        intent.putExtra("stocklist",list);
        intent.putExtra("email",email);
        startActivity(intent);
    }
    public void analytics(){
        Intent intent = new Intent(getBaseContext(),analytics.class);
        intent.putExtra("stocklist",list);
        intent.putExtra("email",email);
        startActivity(intent);
    }
    public void livefeed(){
        Intent intent = new Intent(getBaseContext(),livefeed.class);
        intent.putExtra("email",email);
        startActivity(intent);
    }
}

package com.example.softwareconstructionassign;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class mainscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        // Get the Intent that started this activity and extract the string
        String email = (String) getIntent().getSerializableExtra("email");
        System.out.println(email);

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView1);
        textView.setText("Abhishek");

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

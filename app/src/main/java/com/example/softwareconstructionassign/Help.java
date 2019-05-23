package com.example.softwareconstructionassign;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        this.setTitle("Help");
        TextView dash = findViewById(R.id.textView2);
        TextView analytics = findViewById(R.id.textView3);
        TextView news = findViewById(R.id.textView4);
        TextView txt1 = findViewById(R.id.textView6);
        TextView txt2 = findViewById(R.id.textView5);
        TextView txt3 = findViewById(R.id.textView8);

        txt1.setText("Dash Board: ");
        txt2.setText("Analytics: ");
        txt3.setText("News Feed: ");

        dash.setText("DashBoard contains all the stocks that have been added to your profile. You can add/delete/edit the stocks in realtime.");

        analytics.setText("You can analyse each and every stock of the stocks added in your profile. A visual overview of each stock with open, close, high, low volume of the stocks selected is displayed.");

        news.setText("A sentiment graph of positives, negatives or neutral news about the stock selected is displayed here. News are updated in real time.You can now choose to invest.");
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
        Intent intent = new Intent(getBaseContext(),About.class);

        startActivity(intent);
    }
    public void help(){
        Intent intent = new Intent(getBaseContext(),Help.class);

        startActivity(intent);
    }
}

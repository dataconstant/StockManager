/******
 * Project: Software Construction
 * Author: DILEEP VEMULA (U6631257) AND KRUTHI KRISHNA SENAPATHI (U6601532)
 */package com.example.softwareconstructionassign;

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
        TextView ques=findViewById(R.id.q1);
        TextView fa=findViewById(R.id.faq);
        TextView t1=findViewById(R.id.t1);
        TextView ans1= findViewById(R.id.ans);
        TextView q=findViewById(R.id.q2);
        TextView an=findViewById(R.id.ans1);
        TextView qq=findViewById(R.id.q3);
        TextView aa=findViewById(R.id.ans3);
        t1.setText("Overview");
        txt1.setText("Dash Board: ");
        txt2.setText("Analytics: ");
        txt3.setText("News Feed: ");

        dash.setText("DashBoard contains all the stocks that have been added to your profile. You can add/delete/edit the stocks in realtime.");
        analytics.setText("You can analyse each and every stock of the stocks added in your profile. A visual overview of each stock with open, close, high, low volume of the stocks selected is displayed.");
        news.setText("A sentiment graph of positives, negatives or neutral news about the stock selected is displayed here. News are updated in real time.You can now choose to invest.");
        fa.setText("Frequently Asked Questions:");
        ques.setText("Where are my stocks saved and how should i edit them?");
        ans1.setText("Please check your dashboard to view and edit your stocks, all the saved stocks can viewed in the dashboard section and can be edited. Select a particular stock and click on delete button at the bottom to delete that stock from your portfolio " +
                "To Add a particular stock please enter the stock name and click on search");
        q.setText("How do i know my stock range?");
        an.setText("Please direct your page to analytics and you will get all the information about the stocks added into your profile. " +
                " By selecting the stocks from the drop down menu which has all your stocks you can view information about each one of it." +
                " It also displays the last update of the stock values through date and time section.");
        qq.setText("What does news refer to?");
        aa.setText("It has all the updated news obtained from the internet ready for you to read and predict about your stock ");
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

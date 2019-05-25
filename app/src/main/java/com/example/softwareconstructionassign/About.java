/***
 *  Project: Software Construction
 *  Author: DILEEP VEMULA
 */
package com.example.softwareconstructionassign;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        this.setTitle("About Us");
        ImageView imageView = findViewById(R.id.imageView);
        TextView assign = findViewById(R.id.textView);
        TextView version = findViewById(R.id.textView9);
        TextView developers = findViewById(R.id.textView10);
        TextView app = findViewById(R.id.textView11);
        String src = "Developed as part of Assignment of "+ "<b>" + "COMP6442"+ "</b>";
        assign.setText(Html.fromHtml(src));

        version.setText("version 1.0");
        String src2 = "<b>"+"Developed by : "+"</b>"+"Abhishek Chetri, Mansoor Ali Halari, Kruthi Senapathi and Dileep Vemula.";
        developers.setText(Html.fromHtml(src2));

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

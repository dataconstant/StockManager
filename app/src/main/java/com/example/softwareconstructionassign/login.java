package com.example.softwareconstructionassign;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void buttonPress(View v){
        Intent intent = new Intent(getBaseContext(), mainscreen.class);
        EditText editText = (EditText) findViewById(R.id.textPassword);
        String message = editText.getText().toString();
        startActivity(intent);

    }
}

package com.example.softwareconstructionassign;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    Button button1;
    Button button2;
    EditText editText1, editText2;
    TextView textView1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.logo);
        button1 = findViewById(R.id.buttonLogin);
        button2 = findViewById(R.id.buttonSignUp);
        editText1 = findViewById(R.id.editTextEmail);
        editText2 = findViewById(R.id.editTextPassword);
        textView1 = findViewById(R.id.textView);
    }
}

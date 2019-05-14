package com.example.softwareconstructionassign;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class login extends AppCompatActivity {



    Button login;
    Button signUp;
    EditText email, password;

    DatabaseReference dblogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dblogin = FirebaseDatabase.getInstance().getReference("users");

        login = findViewById(R.id.buttonLogin);
        signUp = findViewById(R.id.buttonSignUp);
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.textPassword);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent =new Intent(getBaseContext(),mainscreen.class);
                startActivity(myIntent);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addemail();
            }
        });

    }



    private void addemail(){
        String emailaddress = email.getText().toString().trim();
        String pass = password.getText().toString().trim();

        if(!emailaddress.isEmpty()){

            String id = emailaddress.split("@")[0];

            emailclass useremail = new emailclass(id,emailaddress,pass);
            dblogin.child(id).setValue(useremail);
            Toast.makeText(this,"User Registered", Toast.LENGTH_LONG).show();

            email.setText("");
            password.setText("");

        }
        else {
            Toast.makeText(this,"Please enter a email address", Toast.LENGTH_LONG).show();
        }
    }
}

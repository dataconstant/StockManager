package com.example.softwareconstructionassign;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


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
                System.out.println("method running");
                //Toast.makeText(getApplicationContext(), "Please enter a valid email address and password", Toast.LENGTH_LONG).show();
                validDataCheck();
                /**/
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addemail();
            }
        });

    }

    private void validDataCheck(){

        dblogin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            private void showData(DataSnapshot dataSnapshot) {
                String emailaddress1 = email.getText().toString();
                String pass1 = password.getText().toString();

                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    emailclass eid = new emailclass(ds.getValue());
                    eid.setEmail(ds.child("email").getValue().toString());
                    eid.setPassword(ds.child("password").getValue().toString());
                    if (eid.email.equals(emailaddress1) && eid.password.equals(pass1)){
                        Intent myIntent = new Intent(getBaseContext(), mainscreen.class);
                        myIntent.putExtra("email",emailaddress1);
                        startActivity(myIntent);

                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void addemail() {
        String emailaddress = email.getText().toString().trim();
        String pass = password.getText().toString().trim();

        if (!emailaddress.isEmpty()) {

            String id = emailaddress.split("@")[0];

            emailclass useremail = new emailclass(id, emailaddress, pass);
            dblogin.child(id).setValue(useremail);
            Toast.makeText(this, "User Registered", Toast.LENGTH_LONG).show();

            email.setText("");
            password.setText("");

        } else {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_LONG).show();
        }
    }
}

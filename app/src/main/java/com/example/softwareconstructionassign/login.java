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
                validDataCheck();

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addemail();
            }
        });

    }


    private void validDataCheck() {

        dblogin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void showData(DataSnapshot dataSnapshot) {
        String emailaddress1 = email.getText().toString();
        String pass1 = password.getText().toString();
        int cond = 0,count=0;
        if (!(emailaddress1.isEmpty() || pass1.isEmpty())) {
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                emailclass eid = new emailclass(ds.getValue());
                eid.setEmail(ds.child("email").getValue().toString());
                eid.setPassword(ds.child("password").getValue().toString());
                if (eid.email.equals(emailaddress1) && eid.password.equals(pass1)) {
                    cond = 1;
                    break;
                } else if ((eid.email.equals(emailaddress1) && !eid.password.equals(pass1)) || (!eid.email.equals(emailaddress1) && eid.password.equals(pass1))) {
                    cond=2;
                    break;
                }
                else count++;
            }
            if (cond == 1) {
                Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(getBaseContext(), mainscreen.class);
                myIntent.putExtra("email", emailaddress1);
                startActivity(myIntent);
            }
            if (cond == 2) {
                Toast.makeText(getApplicationContext(), "Either email or password is incorrect", Toast.LENGTH_SHORT).show();
            }
            if (count == dataSnapshot.getChildrenCount()){
                Toast.makeText(getApplicationContext(),"User does not exist. Please Sign Up",Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Empty! Please enter email or password", Toast.LENGTH_LONG).show();
        }
    }


    private void addemail() {

        String emailaddress = email.getText().toString().trim();
        String pass = password.getText().toString().trim();

        if (!(emailaddress.isEmpty() || pass.isEmpty())) {
            String id = emailaddress.split("@")[0];
            emailclass useremail = new emailclass(id, emailaddress, pass, "");

            dblogin.child(id).setValue(useremail);
            Toast.makeText(this, "User Registered", Toast.LENGTH_LONG).show();


        } else {
            Toast.makeText(this, "Please enter a valid email address to sign up", Toast.LENGTH_LONG).show();
        }
    }
}

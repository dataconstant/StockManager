/***
 * Project: Software Construction
 * Author: DILEEP VEMULA (U6631257)
 */

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

    //DB refernece
    DatabaseReference dblogin;

    /**
     * OnCreate of login activity.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dblogin = FirebaseDatabase.getInstance().getReference("users");

        login = findViewById(R.id.buttonLogin);
        signUp = findViewById(R.id.buttonSignUp);
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.textPassword);

        //listener for login button press
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validDataCheck();
            }
        });
        //Listener for sign up button press
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUserExists();
            }
        });

    }

    /**
     * Method to validate entered credentials for login.
     */
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

    /**
     * Method to validate email and password with data in DB. Intent to next activity if both email and password matches with DB data.
     * Toasts appropriate message if the credentials do not match.
     *
     * @param dataSnapshot
     */
    private void showData(DataSnapshot dataSnapshot) {
        //get email address entered by user
        String emailaddress1 = email.getText().toString();
        //get password entered by user
        String pass1 = password.getText().toString();
        int cond = 0, count = 0;
        if (!(emailaddress1.isEmpty() || pass1.isEmpty())) {
            //Checking every child of users db with email and password entered.
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                User eid = new User(ds.getValue());
                eid.setEmail(ds.child("email").getValue().toString());
                eid.setPassword(ds.child("password").getValue().toString());
                if (eid.email.equals(emailaddress1) && eid.password.equals(pass1)) {
                    cond = 1; // useremail and password match with a child in db
                    break;
                } else if ((eid.email.equals(emailaddress1) && !eid.password.equals(pass1)) || (!eid.email.equals(emailaddress1) && eid.password.equals(pass1))) {
                    cond = 2;
                    break;
                } else count++;
            }
            if (cond == 1) { //if the match happens, Intent to main screen
                Intent myIntent = new Intent(getBaseContext(), mainscreen.class);
                myIntent.putExtra("email", emailaddress1);
                startActivity(myIntent);
            }
            if (cond == 2) { //if Either email or password is incorrect toast this message
                Toast.makeText(getApplicationContext(), "Either email or password is incorrect", Toast.LENGTH_SHORT).show();
            }
            if (count == dataSnapshot.getChildrenCount()) {
                //if useremail does not exist in db, ask user to sign up and then login.
                Toast.makeText(getApplicationContext(), "User does not exist. Please Sign Up", Toast.LENGTH_SHORT).show();
            }
        } else {
            //if email or password is empty and user tries to login, toast this message.
            Toast.makeText(getApplicationContext(), "Empty! Please enter email or password", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Method to check if user wanting to sign up exists or not by accessing db.
     */
    private void checkUserExists() {
        dblogin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData1(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    /**
     * Method to get all children of users from db and compare the email adress with entered email adress and check existing user.
     * @param dataSnapshot
     */
    private void showData1(DataSnapshot dataSnapshot) {
        boolean cond = false;
        String emailaddress2 = email.getText().toString();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            User eid = new User(ds.getValue());
            eid.setEmail(ds.child("email").getValue().toString());
            if (eid.email.equals(emailaddress2)) {
                    cond=true;
                    break;
            }
        }
       if (cond == false){
           //if user does not exist then go ahead with sign up
           addemail();
       }
       if (cond ==true){
           Toast.makeText(getApplicationContext(),"User already exists. Please login",Toast.LENGTH_SHORT).show();
       }

    }


    /**
     * Method to sign up if user does not exist already in db.
     */

    private void addemail() {
        //get email address entered by user.
        String emailaddress = email.getText().toString().trim();
        //get password entered by user.
        String pass = password.getText().toString().trim();
        //Create new child of user in db if both email and password is not empty.
        if (!(emailaddress.isEmpty() || pass.isEmpty())) {
            String id = emailaddress.split("@")[0];
            User useremail = new User(id, emailaddress, pass, "","");
            dblogin.child(id).setValue(useremail);
            Toast.makeText(this, "User Registered", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Please enter a valid email address to sign up", Toast.LENGTH_SHORT).show();
        }
    }
}

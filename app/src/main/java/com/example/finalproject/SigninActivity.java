package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SigninActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference usersRef = database.getReference("users");
    FirebaseAuth mAuth; //= FirebaseAuth.getInstance();


    EditText sign_ETX_confirmPassword;
    EditText sign_ETX_password;
    EditText sign_ETX_username;
    private ProgressBar progressbar;
    Button signin_BTN_signin;
    Intent intent;

    // creating a variable for our
    // Firebase Database.
    FirebaseDatabase firebaseDatabase;
    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference;

    UserDetails userInfo;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mAuth = FirebaseAuth.getInstance();//Getting instance of FirebaseAuth

        // below line is used to get the
        // instance of our FIrebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();
        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("UserInfo");
        userInfo = new UserDetails();

        signin_BTN_signin = findViewById(R.id.signin_BTN_signin);
        sign_ETX_confirmPassword = findViewById(R.id.sign_ETX_confirmPassword);
        sign_ETX_password = findViewById(R.id.sign_ETX_password);
        sign_ETX_username = findViewById(R.id.sign_ETX_username);
        progressbar = findViewById(R.id.progressbar);

        signin_BTN_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //saveUserData();
                registerNewUser();
            }
        });

    }

   /* private void saveUserData() {
        String userId = mAuth.getCurrentUser().getUid();
        usersRef.child(userId).child("username").setValue(sign_ETX_username.getText());
        usersRef.child(userId).child("password").setValue(sign_ETX_password.getText());
    }*/

    // Checking the current all state
    @Override
    public void onStart() {
        super.onStart();

        // if user logged in, go to sign-in screen
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(this, MenuActivity.class));
            finish();
        }
    }

    private void registerNewUser() {

        // show the visibility of progress bar to show loading
        progressbar.setVisibility(View.VISIBLE);

        // Take the value of two edit texts in Strings
        String email, password;
        email = sign_ETX_username.getText().toString();
        password = sign_ETX_password.getText().toString();

        // Validations for input email and password
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter email!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter password!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // create new user or register new user
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
                // hide the progress bar
                progressbar.setVisibility(View.GONE);
                // if the user created intent to login activity
                Intent intent = new Intent(SigninActivity.this, MenuActivity.class);
                startActivity(intent);
            } else {
                // Registration failed
                Toast.makeText(getApplicationContext(), "Registration failed!!" + " Please try again later", Toast.LENGTH_LONG).show();
                // hide the progress bar
                progressbar.setVisibility(View.GONE);
            }
        });
    }
}
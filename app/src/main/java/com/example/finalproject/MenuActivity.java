package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class MenuActivity extends AppCompatActivity {


    ImageButton manu_BTN_play;
    ImageButton manu_BTN_practice;
    ImageButton manu_BTN_records;
    ImageView logout;
    Intent intent;

    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions gso;

    SharedPreferences preferences;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        preferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);

        gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        findViews();
        initViews();
    }


    private void findViews() {

        manu_BTN_play = findViewById(R.id.manu_BTN_play);
        manu_BTN_practice= findViewById(R.id.manu_BTN_practice);
        manu_BTN_records= findViewById(R.id.manu_BTN_records);
        logout= findViewById(R.id.logout);

    }


    private void initViews() {

        manu_BTN_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                intent= new Intent(MenuActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });


        manu_BTN_practice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent= new Intent(MenuActivity.this, PracticeActivity.class);
                startActivity(intent);
            }
        });

        manu_BTN_records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent= new Intent(MenuActivity.this, RecordsActivity.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(view ->
        {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Are you sure you want to logout?");
            alert.setPositiveButton("Yes,Logout", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // continue with logout
                    FirebaseAuth.getInstance().signOut();
                    // Google sign out
                    mGoogleSignInClient.signOut().addOnCompleteListener(MenuActivity.this,
                            new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        intent= new Intent(MenuActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(getApplicationContext(),"Session not close",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            });
            alert.setNegativeButton("Not Now", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // close dialog
                    dialog.cancel();
                }
            });
            alert.show();
        });
    }



}
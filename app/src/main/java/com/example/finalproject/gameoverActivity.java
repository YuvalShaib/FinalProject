package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class gameoverActivity extends AppCompatActivity {
    TextView gameover_ETX_score;
    Button gameover_BTN_back;
    SharedPreferences preferences;
    String id;
    int score;
    Intent intent;
    //Bundle b = (Bundle) intent.getExtras();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);

        preferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        id = preferences.getString("id", "");

        intent = getIntent();
        score = intent.getIntExtra("score", 0);

        findViews();
        initViews();


        updateScore(score);
    }

    private void findViews() {
        gameover_ETX_score = findViewById(R.id.gameover_ETX_score);
        gameover_BTN_back= findViewById(R.id.gameover_BTN_back);

    }

    private void initViews() {
        gameover_ETX_score.setText(score + "");

        gameover_BTN_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewMenuActivity();
            }
        });

    }


    private void updateScore(int score) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("user_info").child(id);
        //here we get the score value from the database first
        mDatabase.child("score").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                int val = 0;
                val = Integer.parseInt(snapshot.getValue().toString());
                if (val < score) {
                    Map<String, Object> updates = new HashMap<>();
                    updates.put("score", score);
                    mDatabase.updateChildren(updates);
                    //Toast.makeText(gameoverActivity.this, "Value Updated into DB", Toast.LENGTH_SHORT).show();
                } else if (val > score) {
                    //Toast.makeText(gameoverActivity.this, "Current value of DB is already greater than this", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void openNewMenuActivity() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}
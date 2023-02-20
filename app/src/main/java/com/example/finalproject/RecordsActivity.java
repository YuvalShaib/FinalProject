package com.example.finalproject;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.security.cert.PolicyNode;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class RecordsActivity extends AppCompatActivity {
    private Button records_BTN_back;
    private ListView records_LAY_list;
    private AdapterRecord arrayAdapter;
    List<Player> players;

    public RecordsActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        players = new ArrayList<>();

        records_BTN_back= findViewById(R.id.records_BTN_back);
        records_BTN_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewMenuActivity();
            }
        });

        records_LAY_list= findViewById(R.id.records_LAY_list);

        updateList();
        //getSortedNamesAndScores();
        //Log.d("nameR" , ""+players.get(0).getScore());

        for (Player user : players) {
            Log.d("Player", ""+user.getScore());
        }

        // Create an ArrayAdapter to convert the list of users into views that can be displayed in the ListView
        AdapterRecord adapter = new AdapterRecord(this, players);


        records_LAY_list.setAdapter(adapter);

    }


    private void updateList() {


        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("user_info");
        Query query = usersRef.orderByChild("score").limitToLast(10);


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // Clear the existing list
                players.clear();
                // This method will be called every time the data changes
                // Loop through the children of the data snapshot to retrieve the sorted data
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    // Get the user data from the child snapshot
                    String name = child.child("name").getValue(String.class);
                    int score = child.child("score").getValue(Integer.class);
                    players.add(new Player(name, score));
                    // Do something with the user data
                }
                Collections.reverse(players);


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database errors
            }

        });
    }



    private void openNewMenuActivity() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}
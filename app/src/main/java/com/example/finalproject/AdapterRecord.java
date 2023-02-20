package com.example.finalproject;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.security.AccessControlContext;
import java.util.ArrayList;
import java.util.List;

public class AdapterRecord extends ArrayAdapter<Player> {

    public AdapterRecord(Context context, List<Player> players) {
        super(context, 0, players);
        //Log.d("nameC" , ""+players.get(0).getScore());

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.record_row_format, parent, false);
            }

            Player player = getItem(position);
//            Log.d("name1", ""+player.getUserName().toString());
//            Log.d("score1", ""+player.getScore());

            TextView nameTextView = convertView.findViewById(R.id.list_LBL_name);
            nameTextView.setText(player.getUserName());

            TextView scoreTextView = convertView.findViewById(R.id.list_LBL_score);
            scoreTextView.setText(String.valueOf(player.getScore()));

            return convertView;
    }
}

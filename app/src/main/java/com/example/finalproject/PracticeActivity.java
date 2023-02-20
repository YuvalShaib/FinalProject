package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PracticeActivity extends AppCompatActivity {
    private Button practice_BTN_back;

    private EditText[][] mulTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        mulTable= new EditText[11][11];

        findViews();
        initViews();
    }

    private void initViews() {
        practice_BTN_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewMenuActivity();
            }
        });

        // Assuming you have a table view with 10 rows and 10 columns
        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {
                    EditText answerEditText = mulTable[i][j];
                    int correctAnswer = i*j;
                    answerEditText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            // do nothing
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            // do nothing
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            // check the user's answer
                            try {
                                int userAnswer = Integer.parseInt(answerEditText.getText().toString());
                                if (userAnswer == correctAnswer) {
                                    Toast.makeText(PracticeActivity.this, "Correct", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(PracticeActivity.this, "InCorrect", Toast.LENGTH_SHORT).show();

                                }
                            } catch (NumberFormatException e) {
                                // handle exception (e.g. if user enters non-numeric value)
                            }
                        }
                    });

            }
        }


    }
    // Helper function to get the EditText view ID for a given row and column
    private int getEditTextId ( int row, int column){
        return getResources().getIdentifier("edit_text_" + row + "_" + column, "id", getPackageName());
    }

    // Helper function to get the TextView view ID for a given row and column
    private int getTextViewId ( int row, int column){
        return getResources().getIdentifier("feedback_text_view_" + row + "_" + column, "id", getPackageName());
    }



    private void findViews() {

        practice_BTN_back= findViewById(R.id.practice_BTN_back);

        for (int i = 1; i < mulTable.length; i++) {
            for (int j = 1; j < mulTable[0].length; j++) {
                String TableId = "practice_ETX_" + i + j;
                int resId = getResources().getIdentifier(TableId, "id", getPackageName());
                mulTable[i][j] = findViewById(resId);
            }
        }

    }

    private void openNewMenuActivity() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}
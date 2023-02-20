package com.example.finalproject;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private String strUsername;
    private String strPassword;
    EditText main_ETX_username;
    EditText main_ETX_password;
    Button main_BTN_login;
    Button main_BTN_signin;
    Intent intent;

    FirebaseAuth mAuth;
    private ProgressBar progressbar;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int Req_Code = 123;
    SharedPreferences preferences;
    CardView Signin;

    UserDetails userDetails;

    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        preferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        // below line is used to get reference for our database.
        databaseReference = FirebaseDatabase.getInstance().getReference("user_info");
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();


        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();

        findViews();

        Signin = findViewById(R.id.Signin);

        Signin.setOnClickListener(view ->
        {
            signInGoogle();
        });
    }


    // signInGoogle() function
    private void signInGoogle(){

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        someActivityResultLauncher.launch(signInIntent);
    };


    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {

                        Intent data = result.getData();
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        handleResult(task);
                    }
                }
            });

    private void handleResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {
                updateUI(account);
            }
        } catch (ApiException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    // updateUI() method - specify what UI updation are needed after google signin has taken place.
    private void updateUI(GoogleSignInAccount account) {
        // show the visibility of progress bar to show loading
        progressbar.setVisibility(View.VISIBLE);
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("name", account.getDisplayName());
                    editor.putString("email", account.getEmail());
                    editor.putString("id", account.getId());
                    System.out.println("-- google id " + preferences.getString("id" , ""));
                    editor.apply();
                    addDatatoFirebase(account.getDisplayName(),account.getEmail(),account.getId());
                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                    startActivity(intent);
                    //Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (GoogleSignIn.getLastSignedInAccount(this) != null) {
            startActivity(new Intent(this, MenuActivity.class));
            finish();
        }
    }

    private void findViews() {
        main_ETX_username = findViewById(R.id.main_ETX_username);
        main_ETX_password = findViewById(R.id.main_ETX_password);
        main_BTN_login = findViewById(R.id.main_BTN_login);
        main_BTN_signin = findViewById(R.id.main_BTN_signin);
        progressbar = findViewById(R.id.progressbar);
        Signin = findViewById(R.id.Signin);
    }

    private void addDatatoFirebase(String name, String email , String id) {
        // show the visibility of progress bar to show loading
        progressbar.setVisibility(View.VISIBLE);
        userDetails = new UserDetails(id , name , email  , 0);
        // use add value event listener method which is called with database reference.
        databaseReference.child(id).setValue(userDetails).addOnCompleteListener(task -> {
            progressbar.setVisibility(View.GONE);
            System.out.println("-- data added into database");

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("failed to add data into db");
            }
        });
    }
}
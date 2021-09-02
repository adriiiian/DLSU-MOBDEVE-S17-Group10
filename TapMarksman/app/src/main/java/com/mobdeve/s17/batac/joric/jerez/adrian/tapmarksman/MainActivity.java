package com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirebaseAuth mAuth;
    private MediaPlayer ringer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Listener to go to signup page
        binding.btnCreateaccount.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish();
        });

        // Listener to go to login page
        binding.btnOnline.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        // Starts the background music when main activity is started
//        ringer = MediaPlayer.create(MainActivity.this, R.raw.backgroundmusic);
//        ringer.start();

        // Checking if current user is successfully logged out
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() == null){
            System.out.println("SUCCESS");
        }
        else{
            System.out.println(mAuth.getCurrentUser().getEmail());
        }
    }

    // onPause method stops the background music
//    @Override
//    protected void onPause() {
//        super.onPause();
//        ringer.stop();
//    }
}
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
        ringer = MediaPlayer.create(MainActivity.this, R.raw.main_menu_bg_music);
        ringer.start();
        ringer.setLooping(true);
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            mAuth.signOut();
        }

        /*
         * Listener to go to offline game
         */
        binding.btnOffline.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, GameOfflineActivity.class);
            startActivity(intent);
            finish();
        });

        /*
         * Listener to go to sign up page
         */
        binding.btnCreateaccount.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish();
        });

        /*
         * Listener to go to login page
         */
        binding.btnOnline.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        /*
         * Listener to go to leaderboards page
         */
        binding.btnLeaderboards.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, LeaderboardsMenuActivity.class);
            startActivity(intent);
            finish();
        });
    }

    /**
     * Override method for onPause
     */
    @Override
    protected void onPause() {
        super.onPause();
        if(ringer != null){
            ringer.pause();
        }

    }

    /**
     * Override method for onDestroy
     */
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(ringer != null){
            ringer.stop();
            ringer.release();
        }
    }

    /**
     * Override method for onResume
     */
    @Override
    protected void onResume() {
        super.onResume();
        if(ringer != null){
            ringer.start();
        }
    }
}
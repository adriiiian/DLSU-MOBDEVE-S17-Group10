package com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.databinding.ActivityLeaderboardsMenuBinding;

public class LeaderboardsMenuActivity extends AppCompatActivity {

    private ActivityLeaderboardsMenuBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLeaderboardsMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.fabBack.setOnClickListener(view -> {
            Intent intent = new Intent(LeaderboardsMenuActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
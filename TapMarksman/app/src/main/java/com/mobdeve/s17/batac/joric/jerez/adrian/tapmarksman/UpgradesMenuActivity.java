package com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.databinding.ActivityUpgradesMenuBinding;

public class UpgradesMenuActivity extends AppCompatActivity {

    private ActivityUpgradesMenuBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpgradesMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnReturn.setOnClickListener(view -> {
            Intent intent = new Intent(UpgradesMenuActivity.this, GameOnlineActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
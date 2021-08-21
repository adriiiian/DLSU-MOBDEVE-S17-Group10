package com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.databinding.ActivityGameOnlineBinding;

public class GameOnlineActivity extends AppCompatActivity {

    private ActivityGameOnlineBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGameOnlineBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init(){
        mAuth = FirebaseAuth.getInstance();
        System.out.println(mAuth.getCurrentUser().getEmail());
    }
}

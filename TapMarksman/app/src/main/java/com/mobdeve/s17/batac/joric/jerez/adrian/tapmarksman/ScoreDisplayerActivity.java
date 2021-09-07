package com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.dao.UserDAO;
import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.dao.UserDAOFirebaseImpl;
import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.databinding.ActivityScoreDisplayerBinding;
import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.model.User;

public class ScoreDisplayerActivity extends AppCompatActivity {

    private ActivityScoreDisplayerBinding binding;
    private int points;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScoreDisplayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        points = 0;

        getWindow().setLayout((int)(width * 0.8), (int)(height * 0.6));

        Intent intent = getIntent();
        binding.tvTargetskilled.setText(Integer.toString(intent.getIntExtra("targets_killed", 0)));
        binding.tvDifficulty.setText(intent.getStringExtra("difficulty"));
        binding.tvMultiplier.setText(Integer.toString(intent.getIntExtra("multiplier", 1)));
        binding.tvTotalscore.setText(Integer.toString(intent.getIntExtra("targets_killed", 0) * intent.getIntExtra("multiplier", 1)));
        points = intent.getIntExtra("gamepoints", 0) + (intent.getIntExtra("targets_killed", 0) * intent.getIntExtra("multiplier", 1));

        binding.btnContinue.setOnClickListener(view -> {
            UserDAO userDAO = new UserDAOFirebaseImpl();
            userDAO.updateUserGamePoints(points, new FirebaseCallback(){
                @Override
                public void onCallBack(User user) {
                    finish();
                }
            });
        });
    }
}
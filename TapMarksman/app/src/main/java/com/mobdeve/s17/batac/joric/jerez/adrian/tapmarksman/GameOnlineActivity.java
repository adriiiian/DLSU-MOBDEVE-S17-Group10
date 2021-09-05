package com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.databinding.ActivityGameOnlineBinding;

import java.util.Random;

public class GameOnlineActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    private ActivityGameOnlineBinding binding;
    private FirebaseAuth mAuth;
    private SharedPreferences sp;
    private int miliSecTotal = 0, secTotal = 0, secDivider = 0;
    private Display display;
    private Point size;
    private float x, y;
    private ObjectAnimator animation, animation2;
    private CountDownTimer timer;
    private MediaPlayer ringer;
    private int scoreCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGameOnlineBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init(){
        mAuth = FirebaseAuth.getInstance();
        scoreCounter = 0; // Sets the score counter to 0

        // Setting video to video view
        Uri uri = Uri.parse("android.resource://com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman/" + R.raw.pistol);
        binding.vvGun.setVideoURI(uri);
        binding.vvGun.requestFocus();

        // Listener for the menu popup
        binding.btnMenu.setOnClickListener(view -> {
            PopupMenu popup = new PopupMenu(GameOnlineActivity.this, view);
            popup.setOnMenuItemClickListener(GameOnlineActivity.this);
            popup.inflate(R.menu.menu_online);
            popup.show();
        });

        // Listener for the upgrades menu button
        binding.btnUpgrades.setOnClickListener(view -> {
            Intent intent = new Intent(GameOnlineActivity.this, UpgradesMenuActivity.class);
            startActivity(intent);
            finish();
        });

        // Listener for when the user tap the target
        binding.ivTarget.setOnClickListener(view -> {
            binding.ivTarget.setVisibility(View.GONE);
            binding.vvGun.start();
            ringer = MediaPlayer.create(GameOnlineActivity.this, R.raw.pistolsound);
            ringer.start();
            scoreCounter++;
            binding.tvPtsctr.setText(Integer.toString(scoreCounter));
        });

        // Listener for the start game button
        binding.btnStartGame.setOnClickListener(view -> {
            binding.btnStartGame.setVisibility(View.GONE);
            display = getWindowManager().getDefaultDisplay();
            size = new Point();
            x = (float) (size.x * getX());
            y = (float) (size.y * getY());

            animation = ObjectAnimator.ofFloat(binding.ivTarget, "X", x);
            animation2 = ObjectAnimator.ofFloat(binding.ivTarget, "Y", y);
            animation.setDuration(0);
            animation2.setDuration(0);
            animation.start();
            animation2.start();

            binding.ivGun.setVisibility(View.GONE);
            binding.vvGun.setVisibility(View.VISIBLE);

            timer = new CountDownTimer(miliSecTotal, 1000){
                @Override
                public void onTick(long millisUntilFinished) {
                    if((secTotal - (millisUntilFinished/1000)) % secDivider == 0){
                        display = getWindowManager().getDefaultDisplay();
                        size = new Point();
                        display.getSize(size);
                        x = (float) (size.x * getX());
                        y = (float) (size.y * getY());

                        animation = ObjectAnimator.ofFloat(binding.ivTarget, "X", x);
                        animation2 = ObjectAnimator.ofFloat(binding.ivTarget, "Y", y);
                        animation.setDuration(0);
                        animation2.setDuration(0);
                        animation.start();
                        animation2.start();

                        binding.ivTarget.setVisibility(View.VISIBLE);
                        binding.tvTargetRemainingCtr.setText(millisUntilFinished/(secDivider*1000) + "");
                    }
                    else if((secTotal - (millisUntilFinished/500)) % 2 == 1){
                        ringer.release();
                    }
                }

                @Override
                public void onFinish() {
                    binding.ivTarget.setVisibility(View.GONE);
                    binding.btnStartGame.setVisibility(View.VISIBLE);
                    binding.ivGun.setVisibility(View.VISIBLE);
                    binding.vvGun.setVisibility(View.GONE);

                    Intent intent = new Intent(GameOnlineActivity.this, ScoreDisplayerActivity.class);
                    intent.putExtra("targets_killed", scoreCounter);
                    intent.putExtra("difficulty", Integer.toString(sp.getInt(SettingsOnlineActivity.SETTINGS_SELECTED_KEY, 1)));
                    startActivity(intent);

                    scoreCounter = 0; // Sets the score counter to 0 after finishing the round.
                }
            };

            timer.start();
        });

        // Shared preference
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        switch(sp.getInt(SettingsOnlineActivity.SETTINGS_SELECTED_KEY, 1)){
            case 1:
                miliSecTotal = 128000;
                secTotal = 128;
                secDivider = 4;
                break;

            case 2:
                miliSecTotal = 96000;
                secTotal = 96;
                secDivider = 3;
                break;

            case 3:
                miliSecTotal = 32000;
                secTotal = 32;
                secDivider = 1;
                break;

            default:
                break;
        }
    }

    // Computes the valid x multiplier for the x coordinate
    private float getX(){
        Random rand = new Random();

        float result = rand.nextFloat();
        while(result > 0.775){
            result = rand.nextFloat();
        }

        return result;
    }

    // Computes the valid y multiplier for the x coordinate
    private float getY(){
        Random rand = new Random();

        float result = rand.nextFloat();
        while(result > 0.5 || result < 0.1){
            result = rand.nextFloat();
        }

        return result;
    }

    // Method that handles the more options items list
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Intent intent;
        switch(item.getItemId()){
            case R.id.action_settings:
                intent = new Intent(GameOnlineActivity.this, SettingsOnlineActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.action_logout:
                intent = new Intent(GameOnlineActivity.this, LoginActivity.class);
                startActivity(intent);
                mAuth.signOut();
                finish();
                break;
        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
//        timer.cancel();
    }
}

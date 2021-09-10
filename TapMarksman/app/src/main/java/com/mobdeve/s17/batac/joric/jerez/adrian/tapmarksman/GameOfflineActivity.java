package com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman;

import androidx.appcompat.app.AppCompatActivity;

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

import com.google.firebase.auth.FirebaseAuth;
import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.databinding.ActivityGameOfflineBinding;

import java.util.Random;

public class GameOfflineActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private ActivityGameOfflineBinding binding;
    private SharedPreferences sp;
    private int miliSecTotal = 0, secTotal = 0, secDivider = 0;
    private Display display;
    private Point size;
    private float x, y;
    private ObjectAnimator animation, animation2;
    private CountDownTimer timer;
    private MediaPlayer ringer, ringerBG;
    private int scoreCounter;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameOfflineBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init(){
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            mAuth.signOut();
        }

        scoreCounter = 0;

        ringerBG = MediaPlayer.create(GameOfflineActivity.this, R.raw.ingame_bg_music);
        ringerBG.start();

        // Setting image and video to their specific views
        Uri uri = Uri.parse("android.resource://com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman/" + R.raw.pistol);
        binding.vvGun.setVideoURI(uri);
        binding.vvGun.requestFocus();
        ringer = MediaPlayer.create(GameOfflineActivity.this, R.raw.pistol_sound);

        // Listener for the menu popup
        binding.btnMenu.setOnClickListener(view -> {
            PopupMenu popup = new PopupMenu(GameOfflineActivity.this, view);
            popup.setOnMenuItemClickListener(GameOfflineActivity.this);
            popup.inflate(R.menu.menu_offline);
            popup.show();
        });

        // Listener for when the user tap the target
        binding.ivTarget.setOnClickListener(view -> {
            binding.ivTarget.setVisibility(View.GONE);
            binding.vvGun.start();
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

            binding.ivTarget.setVisibility(View.VISIBLE);
            binding.ivGun.setVisibility(View.GONE);
            binding.vvGun.setVisibility(View.VISIBLE);

            /*
                Timer for the round.

                Variables:
                miliSecTotal is the variable that specify how long the round will go.
                Easy: 120 seconds (4 secs each target, 30 targets)
                Medium: 90 seconds (3 secs each target, 30 targets)
                Hard: 30 seconds (1 sec each target, 30 targets)

                countDownInterval is always 1 seconds or 1000 milliseconds

                secDivider is the variable that specify if the target should change its position,
                depending on the remaining time in our timer.
             */
            timer = new CountDownTimer(miliSecTotal, 1000){
                @Override
                public void onTick(long millisUntilFinished) {
                    if((secTotal - (millisUntilFinished/1000)) % secDivider == 1){
                        binding.ivTarget.setVisibility(View.VISIBLE);
                        if(sp.getInt(SettingsOfflineActivity.SETTINGS_SELECTED_KEY, 1) == 3 && (millisUntilFinished/(secDivider*1000)) > 31){
                            binding.tvTargetRemainingCtr.setText("30");
                        }
                    }
                    else if((secTotal - (millisUntilFinished/1000)) % secDivider == 0){
                        binding.ivTarget.setVisibility(View.GONE);
                        display = getWindowManager().getDefaultDisplay();
                        binding.tvTargetRemainingCtr.setText(millisUntilFinished/(secDivider*1000) + "");
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
                    }
                }

                @Override
                public void onFinish() {
                    binding.ivTarget.setVisibility(View.GONE);
                    binding.btnStartGame.setVisibility(View.VISIBLE);
                    binding.ivGun.setVisibility(View.VISIBLE);
                    binding.vvGun.setVisibility(View.GONE);
                    binding.tvTargetRemainingCtr.setText(Integer.toString(0));

                    Intent intent = new Intent(GameOfflineActivity.this, ScoreDisplayerActivity.class);
                    intent.putExtra("targets_killed", scoreCounter);
                    intent.putExtra("difficulty", Integer.toString(sp.getInt(SettingsOfflineActivity.SETTINGS_SELECTED_KEY, 1)));
                    startActivity(intent);

                    scoreCounter = 0; // Sets the score counter to 0 after finishing the round.
                }
            };

            timer.start();
        });

        // Shared preference
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        switch(sp.getInt(SettingsOfflineActivity.SETTINGS_SELECTED_KEY, 1)){
            case 1:
                miliSecTotal = 180000;
                secTotal = 180;
                secDivider = 6;
                break;

            case 2:
                miliSecTotal = 120000;
                secTotal = 120;
                secDivider = 4;
                break;

            case 3:
                miliSecTotal = 60000;
                secTotal = 60;
                secDivider = 2;
                break;

            default:
                break;
        }
    }

    // Computes the valid x multiplier for the x coordinate
    private float getX(){
        Random rand = new Random();

        float result = rand.nextFloat();
        while(result > 0.75 || result < 0.05){
            result = rand.nextFloat();
        }

        return result;
    }

    // Computes the valid y multiplier for the x coordinate
    private float getY(){
        Random rand = new Random();

        float result = rand.nextFloat();
        while(result > 0.45 || result < 0.15){
            result = rand.nextFloat();
        }

        return result;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Intent intent;
        switch(item.getItemId()){
            case R.id.action_settings:
                intent = new Intent(GameOfflineActivity.this, SettingsOfflineActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.action_mainmenu:
                intent = new Intent(GameOfflineActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        ringerBG.stop();
//        timer.cancel();
    }
}
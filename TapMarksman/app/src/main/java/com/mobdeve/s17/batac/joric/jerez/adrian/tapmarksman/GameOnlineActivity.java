package com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.dao.UserDAO;
import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.dao.UserDAOFirebaseImpl;
import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.databinding.ActivityGameOnlineBinding;
import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.model.User;

import java.util.Random;

public class GameOnlineActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    private ActivityGameOnlineBinding binding;
    private FirebaseAuth mAuth;
    private int miliSecTotal = 0, secTotal = 0, secDivider = 0, secCopy, scoreCounter;
    private Display display;
    private Point size;
    private float x, y;
    private ObjectAnimator animation, animation2;
    private CountDownTimer timer;
    private MediaPlayer ringer, ringerBG;
    private User currentUser;
    private boolean isChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGameOnlineBinding.inflate(getLayoutInflater());
        UserDAO userDAO = new UserDAOFirebaseImpl();

        // Gets the current logged in user information from firebase database
        userDAO.getUser(new FirebaseCallback(){
            @Override
            public void onCallBack(User user) {
                currentUser = user;
                setContentView(binding.getRoot());
                init();
            }
        });

    }

    private void init(){
        mAuth = FirebaseAuth.getInstance();
        scoreCounter = 0; // Sets the score counter to 0

        ringerBG = MediaPlayer.create(GameOnlineActivity.this, R.raw.ingame_bg_music);
        if(ringerBG != null && !ringerBG.isPlaying()){
            ringerBG.start();
        }

        Uri uri = null;
        // Setting image and video to their specific views
        if(currentUser.getEquipedGun().equalsIgnoreCase("Pistol")){
            binding.ivGun.setImageResource(R.drawable.pistol);
            uri = Uri.parse("android.resource://com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman/" + R.raw.pistol);
            ringer = MediaPlayer.create(GameOnlineActivity.this, R.raw.pistol_sound);
        }
        else if(currentUser.getEquipedGun().equalsIgnoreCase("Revolver")){
            binding.ivGun.setImageResource(R.drawable.revolver);
            uri = Uri.parse("android.resource://com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman/" + R.raw.revolver);
            ringer = MediaPlayer.create(GameOnlineActivity.this, R.raw.revolver_sound);
        }
        else if(currentUser.getEquipedGun().equalsIgnoreCase("Desert Eagle")){
            binding.ivGun.setImageResource(R.drawable.deagle);
            uri = Uri.parse("android.resource://com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman/" + R.raw.deagle);
            ringer = MediaPlayer.create(GameOnlineActivity.this, R.raw.deagle_sound);
        }
        else if(currentUser.getEquipedGun().equalsIgnoreCase("Rifle")){
            binding.ivGun.setImageResource(R.drawable.rifle);
            uri = Uri.parse("android.resource://com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman/" + R.raw.rifle);
            ringer = MediaPlayer.create(GameOnlineActivity.this, R.raw.rifle_sound);
        }
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
            intent.putExtra("pistol", currentUser.getOwnedPistol());
            intent.putExtra("revolver", currentUser.getOwnedRevolver());
            intent.putExtra("deserteagle", currentUser.getOwnedDesertEagle());
            intent.putExtra("rifle", currentUser.getOwnedRifle());
            intent.putExtra("damagectr", currentUser.getDamageUpgradeCounter());
            intent.putExtra("powerctr", currentUser.getPowerUpgradeCounter());
            intent.putExtra("controlctr", currentUser.getControlUpgradeCounter());
            intent.putExtra("points", currentUser.getPoints());
            intent.putExtra("email", currentUser.getUserEmail());
            intent.putExtra("username", currentUser.getUserName());
            intent.putExtra("password", currentUser.getUserPassword());
            intent.putExtra("multiplier", currentUser.getMultiplier());
            intent.putExtra("difficulty", currentUser.getDifficulty());
            intent.putExtra("equipedGun", currentUser.getEquipedGun());
            intent.putExtra("highestScore", currentUser.getHighestScore());
            startActivity(intent);
            finish();
        });

        // Listener for when the user tap the target
        binding.ivTarget.setOnClickListener(view -> {
            binding.ivTarget.setVisibility(View.GONE);
            binding.vvGun.start();
            ringer.start();
            scoreCounter++;
            binding.tvPtsctr.setText(Integer.toString(scoreCounter));
            binding.tvTargetRemainingCtr.setText(Integer.toString(Integer.parseInt(binding.tvTargetRemainingCtr.getText().toString()) - 1));
            isChanged = true;
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
            binding.vvGun.seekTo(1);
            secCopy = secTotal;
            isChanged = false;

            timer = new CountDownTimer(miliSecTotal, 1000){
                @Override
                public void onTick(long millisUntilFinished) {
                    secCopy--;
                    if(secCopy % secDivider == secDivider - 1){
                        binding.ivTarget.setVisibility(View.VISIBLE);
                        if(!isChanged){
                            binding.tvTargetRemainingCtr.setText((secCopy + 1)/secDivider + "");
                        }
                        isChanged = false;
                    }
                    else if(secCopy % secDivider == 0){
                        binding.ivTarget.setVisibility(View.GONE);
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
                    }
                }

                @Override
                public void onFinish() {
                    binding.ivTarget.setVisibility(View.GONE);
                    binding.btnStartGame.setVisibility(View.VISIBLE);
                    binding.ivGun.setVisibility(View.VISIBLE);
                    binding.vvGun.setVisibility(View.GONE);
                    binding.tvTargetRemainingCtr.setText(Integer.toString(0));

                    Intent intent = new Intent(GameOnlineActivity.this, ScoreDisplayerActivity.class);
                    intent.putExtra("targets_killed", scoreCounter);
                    intent.putExtra("difficulty", currentUser.getDifficulty());
                    intent.putExtra("multiplier", currentUser.getMultiplier());
                    intent.putExtra("gamepoints", currentUser.getPoints());
                    intent.putExtra("highestScore", currentUser.getHighestScore());
                    startActivity(intent);

                    scoreCounter = 0; // Sets the score counter to 0 after finishing the round.
                }
            };

            timer.start();
        });

        switch(currentUser.getDifficulty()){
            case "Easy":
                miliSecTotal = 120000;
                secTotal = 120;
                secDivider = 4;
                break;

            case "Medium":
                miliSecTotal = 90000;
                secTotal = 90;
                secDivider = 3;
                break;

            case "Hard":
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

    // Method that handles the more options items list
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Intent intent;
        switch(item.getItemId()){
            case R.id.action_settings:
                intent = new Intent(GameOnlineActivity.this, SettingsOnlineActivity.class);
                intent.putExtra("difficulty", currentUser.getDifficulty());
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
        if(timer != null){
            timer.cancel();
        }

        if(ringerBG != null){
            ringerBG.pause();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(ringerBG != null){
            ringerBG.stop();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(ringerBG != null && !ringerBG.isPlaying()){
            ringerBG.start();
        }
    }
}

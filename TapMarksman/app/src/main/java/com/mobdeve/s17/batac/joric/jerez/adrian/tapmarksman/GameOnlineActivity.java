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
    private int miliSecTotal = 0, secTotal = 0, secDivider = 0;
    private Display display;
    private Point size;
    private float x, y;
    private ObjectAnimator animation, animation2;
    private CountDownTimer timer;
    private MediaPlayer ringer;
    private int scoreCounter;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGameOnlineBinding.inflate(getLayoutInflater());
        UserDAO userDAO = new UserDAOFirebaseImpl();

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
//            ringer = MediaPlayer.create(GameOnlineActivity.this, R.raw.pistolsound);
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
                        if(currentUser.getDifficulty().equalsIgnoreCase("Hard") && (millisUntilFinished/(secDivider*1000)) > 31){
                            binding.tvTargetRemainingCtr.setText("30");
                        }
                    }
//                    else if((secTotal - (millisUntilFinished/500)) % 2 == 1){
//                        ringer.release();
//                    }
                }

                @Override
                public void onFinish() {
                    binding.ivTarget.setVisibility(View.GONE);
                    binding.btnStartGame.setVisibility(View.VISIBLE);
                    binding.ivGun.setVisibility(View.VISIBLE);
                    binding.vvGun.setVisibility(View.GONE);

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
                miliSecTotal = 125000;
                secTotal = 125;
                secDivider = 4;
                break;

            case "Medium":
                miliSecTotal = 94000;
                secTotal = 94;
                secDivider = 3;
                break;

            case "Hard":
                miliSecTotal = 32000;
                secTotal = 32;
                secDivider = 1;
                break;

            default:
                break;

        }
        // Shared preference
//        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        switch(sp.getInt(SettingsOnlineActivity.SETTINGS_SELECTED_KEY, 1)){
//            case 1:
//                miliSecTotal = 124000;
//                secTotal = 124;
//                secDivider = 4;
//                break;
//
//            case 2:
//                miliSecTotal = 93000;
//                secTotal = 93;
//                secDivider = 3;
//                break;
//
//            case 3:
//                miliSecTotal = 31000;
//                secTotal = 31;
//                secDivider = 1;
//                break;
//
//            default:
//                break;
//        }
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
//        timer.cancel();
    }
}

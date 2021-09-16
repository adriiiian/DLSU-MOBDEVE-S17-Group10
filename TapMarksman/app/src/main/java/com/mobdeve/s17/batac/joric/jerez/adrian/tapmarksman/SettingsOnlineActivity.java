package com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.dao.UserDAO;
import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.dao.UserDAOFirebaseImpl;
import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.databinding.ActivitySettingsOnlineBinding;
import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.model.User;

public class    SettingsOnlineActivity extends AppCompatActivity {

    public static final String SETTINGS_KEY = "SETTINGS_KEY";
    public static final String SETTINGS_SELECTED_KEY = "SETTINGS_SELECTED_KEY";
    ActivitySettingsOnlineBinding binding;

    private SharedPreferences sp;
    private SharedPreferences.Editor spEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsOnlineBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

        binding.btnSave.setOnClickListener(view -> {
            UserDAO userDAO = new UserDAOFirebaseImpl();
            userDAO.updateUserDifficulty(getDifficulty(), new FirebaseCallback(){
                @Override
                public void onCallBack(User user) {
                    Intent intent = new Intent(SettingsOnlineActivity.this, GameOnlineActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        });
    }

    private void init(){
        Intent getIntent = getIntent();
        String difficulty = getIntent.getStringExtra("difficulty");
        switch(difficulty){
            case "Easy":
                binding.rgDifficulty.check(binding.rbEasy.getId());
                break;

            case "Medium":
                binding.rgDifficulty.check(binding.rbMedium.getId());
                break;

            case "Hard":
                binding.rgDifficulty.check(binding.rbHard.getId());
                break;

            default:
                break;
        }

    }

    private String getDifficulty(){
        String result = "";
        if(binding.rbEasy.isChecked()){
            result = "Easy";
        }
        else if(binding.rbMedium.isChecked()){
            result = "Medium";
        }
        else if(binding.rbHard.isChecked()){
            result = "Hard";
        }

        return result;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        UserDAO userDAO = new UserDAOFirebaseImpl();
//        userDAO.updateUserDifficulty(getDifficulty(), new FirebaseCallback(){
//            @Override
//            public void onCallBack(User user) {
//                Intent intent = new Intent(SettingsOnlineActivity.this, GameOnlineActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
    }
}
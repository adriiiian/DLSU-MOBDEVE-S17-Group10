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

//        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        init();

        binding.btnSave.setOnClickListener(view -> {
            Intent intent = new Intent(SettingsOnlineActivity.this, GameOnlineActivity.class);

            UserDAO userDAO = new UserDAOFirebaseImpl();
            userDAO.updateUserDifficulty(getDifficulty(), new FirebaseCallback(){
                @Override
                public void onCallBack(User user) {
                    startActivity(intent);
                    finish();
                }
            });
        });
    }

    private void init(){
        Intent getIntent = getIntent();
        String difficulty = getIntent.getStringExtra("difficulty");
        int checked = 0;
        switch(difficulty){
            case "Easy":
                checked = 1;
                binding.rgDifficulty.check(binding.rbEasy.getId());
                break;

            case "Medium":
                checked = 2;
                binding.rgDifficulty.check(binding.rbMedium.getId());
                break;

            case "Hard":
                checked = 3;
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
        Intent intent = new Intent(SettingsOnlineActivity.this, GameOnlineActivity.class);

        UserDAO userDAO = new UserDAOFirebaseImpl();
        userDAO.updateUserDifficulty(getDifficulty(), new FirebaseCallback(){
            @Override
            public void onCallBack(User user) {
                startActivity(intent);
                finish();
            }
        });
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        spEditor = sp.edit();
//
//        if(binding.rbEasy.isChecked()){
//            spEditor.putInt(SETTINGS_KEY, binding.rgDifficulty.getCheckedRadioButtonId());
//            spEditor.putInt(SETTINGS_SELECTED_KEY, 1);
//        }
//        else if(binding.rbMedium.isChecked()){
//            spEditor.putInt(SETTINGS_KEY, binding.rgDifficulty.getCheckedRadioButtonId());
//            spEditor.putInt(SETTINGS_SELECTED_KEY, 2);
//        }
//        else if(binding.rbHard.isChecked()){
//            spEditor.putInt(SETTINGS_KEY, binding.rgDifficulty.getCheckedRadioButtonId());
//            spEditor.putInt(SETTINGS_SELECTED_KEY, 3);
//        }
//
//        spEditor.apply();
//
//    }
}
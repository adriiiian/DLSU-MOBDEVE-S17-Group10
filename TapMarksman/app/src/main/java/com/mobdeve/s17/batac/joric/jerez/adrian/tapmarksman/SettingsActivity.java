package com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.databinding.ActivitySettingsBinding;

public class SettingsActivity extends AppCompatActivity {

    public static final String SETTINGS_KEY = "SETTINGS_KEY";
    public static final String SETTINGS_SELECTED_KEY = "SETTINGS_SELECTED_KEY";
    ActivitySettingsBinding binding;

    private SharedPreferences sp;
    private SharedPreferences.Editor spEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        binding.rgDifficulty.check(sp.getInt(SETTINGS_KEY, binding.rbEasy.getId()));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SettingsActivity.this, GameOnlineActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        spEditor = sp.edit();
//        spEditor.putInt(SETTINGS_KEY, binding.rgDifficulty.getCheckedRadioButtonId());
//        spEditor.apply();
        if(binding.rbEasy.isChecked()){
            spEditor.putInt(SETTINGS_KEY, binding.rgDifficulty.getCheckedRadioButtonId());
            spEditor.putInt(SETTINGS_SELECTED_KEY, 1);
        }
        else if(binding.rbMedium.isChecked()){
            spEditor.putInt(SETTINGS_KEY, binding.rgDifficulty.getCheckedRadioButtonId());
            spEditor.putInt(SETTINGS_SELECTED_KEY, 2);
        }
        else if(binding.rbHard.isChecked()){
            spEditor.putInt(SETTINGS_KEY, binding.rgDifficulty.getCheckedRadioButtonId());
            spEditor.putInt(SETTINGS_SELECTED_KEY, 3);
        }

        spEditor.apply();

    }
}
package com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.databinding.ActivitySettingsOfflineBinding;

public class SettingsOfflineActivity extends AppCompatActivity {

    public static final String SETTINGS_KEY = "SETTINGS_KEY_OFFLINE";
    public static final String SETTINGS_SELECTED_KEY = "SETTINGS_SELECTED_KEY_OFFLINE";
    private ActivitySettingsOfflineBinding binding;

    private SharedPreferences sp;
    private SharedPreferences.Editor spEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsOfflineBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        binding.rgDifficulty.check(sp.getInt(SETTINGS_KEY, binding.rbEasy.getId()));

        binding.btnSave.setOnClickListener(view -> {
            Intent intent = new Intent(SettingsOfflineActivity.this, GameOfflineActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SettingsOfflineActivity.this, GameOfflineActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        spEditor = sp.edit();

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
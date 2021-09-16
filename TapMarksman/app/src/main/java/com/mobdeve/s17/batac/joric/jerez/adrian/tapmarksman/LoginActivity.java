package com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    static public final String SAVE_DETAILS = "SAVE_DETAILS";
    static public final String SAVE_EMAIL = "SAVE_EMAIL";
    static public final String SAVE_PASSWORD = "SAVE_PASSWORD";

    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;
    private SharedPreferences sp;
    private SharedPreferences.Editor spEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setRequestFocus();
        init();
        binding.etEmail.setText(sp.getString(SAVE_EMAIL, ""));
        binding.etPassword.setText(sp.getString(SAVE_PASSWORD, ""));
        binding.cbSavepassword.setChecked(sp.getBoolean(SAVE_DETAILS, false));
    }

    /**
     * This method is used to initialize variables and views in LoginActivity
     */
    private void init(){
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mAuth = FirebaseAuth.getInstance();

        /*
         * Listener for back button
         */
        binding.fabBack.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        });

        /*
         * Listener for go to signup button
         */
        binding.tvGotosignup.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
            startActivity(intent);
            finish();
        });

        /*
         * Listener for login button
         */
        binding.btnLogin.setOnClickListener(view -> {
            /*
             * This part is used to check whether the fields submitted is empty
             *
             * If empty, it wont accept the submitted fields and will highlight all empty fields
             */
            if(checkNonEmptyFields()){
                /*
                 * This code is used for authentication using FirebaseAuth
                 *
                 * Task will not be successful if the submitted values is not valid
                 */
                mAuth.signInWithEmailAndPassword(binding.etEmail.getText().toString(), binding.etPassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(),"Successful Login", Toast.LENGTH_LONG).show();
                                    binding.llEmail.setBackgroundResource(R.drawable.signup_login_edit_texts);
                                    binding.llPassword.setBackgroundResource(R.drawable.signup_login_edit_texts);
                                    binding.tvError.setVisibility(View.INVISIBLE);
                                    if(binding.cbSavepassword.isChecked()){
                                        spEditor = sp.edit();
                                        spEditor.putString(SAVE_EMAIL, binding.etEmail.getText().toString());
                                        spEditor.putString(SAVE_PASSWORD, binding.etPassword.getText().toString());
                                        spEditor.apply();
                                    }
                                    else{
                                        spEditor = sp.edit();
                                        spEditor.putString(SAVE_EMAIL, "");
                                        spEditor.putString(SAVE_PASSWORD, "");
                                        spEditor.apply();
                                    }
                                    Intent intent = new Intent(LoginActivity.this, GameOnlineActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "Incorrect email and or password", Toast.LENGTH_LONG).show();
                                }

                            }
                        });
            }
        });
    }

    /**
     * This method will set the request focus for each image views and text views
     * to their respective views
     */
    private void setRequestFocus(){
        binding.ivEmail.setOnClickListener(view -> {
            binding.etEmail.requestFocus();
        });

        binding.tvEmail.setOnClickListener(view -> {
            binding.etEmail.requestFocus();
        });

        binding.ivPassword.setOnClickListener(view -> {
            binding.etPassword.requestFocus();
        });

        binding.tvPassword.setOnClickListener(view -> {
            binding.etPassword.requestFocus();
        });
    }


    /**
     * This method is used to check if the fields submitted by the user is empty
     * @return boolean
     * true: if field is not empty
     * false: if field is empty
     */
    private boolean checkNonEmptyFields(){
        binding.llEmail.setBackgroundResource(R.drawable.signup_login_edit_texts);
        binding.llPassword.setBackgroundResource(R.drawable.signup_login_edit_texts);
        boolean result = true;

        if(TextUtils.isEmpty(binding.etEmail.getText().toString())){
            binding.llEmail.setBackgroundResource(R.drawable.error_edit_texts);
            binding.tvError.setVisibility(View.VISIBLE);
            result = false;
        }
        else{
            binding.llEmail.setBackgroundResource(R.drawable.signup_login_edit_texts);
            binding.tvError.setVisibility(View.INVISIBLE);
        }

        if(TextUtils.isEmpty(binding.etPassword.getText().toString())){
            binding.llPassword.setBackgroundResource(R.drawable.error_edit_texts);
            binding.tvError.setVisibility(View.VISIBLE);
            result = false;
        }
        else{
            binding.llPassword.setBackgroundResource(R.drawable.signup_login_edit_texts);
            binding.tvError.setVisibility(View.INVISIBLE);
        }
        return result;
    }

    /**
     * Override method for onBackPressed
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Override method for onPause
     */
    @Override
    public void onPause(){
        super.onPause();
        spEditor = sp.edit();
        spEditor.putBoolean(SAVE_DETAILS, binding.cbSavepassword.isChecked());
        spEditor.apply();
    }
}
package com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setRequestFocus();

        // Listener to go back to main menu page
        binding.fabBack.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        });

        // Listener to go to sign up page
        binding.tvGotosignup.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
            startActivity(intent);
            finish();
        });

        binding.btnLogin.setOnClickListener(view -> {
            submit();
        });



    }

    // This method will set the request focus for each image views and text views
    // to their respective edit texts field
    private void setRequestFocus(){
        binding.ivUsername.setOnClickListener(view -> {
            binding.etUsername.requestFocus();
        });

        binding.tvUsername.setOnClickListener(view -> {
            binding.etUsername.requestFocus();
        });

        binding.ivPassword.setOnClickListener(view -> {
            binding.etPassword.requestFocus();
        });

        binding.tvPassword.setOnClickListener(view -> {
            binding.etPassword.requestFocus();
        });
    }

    private boolean submit(){
        boolean result = false;
        if(checkEmptyFields("username") && checkEmptyFields("password")){
            // code to check if username and password is correct
            result = true;
        }
        return result;
    }

    private boolean checkEmptyFields(String field){
        binding.llUsername.setBackgroundResource(R.drawable.signup_login_edit_texts);
        binding.llPassword.setBackgroundResource(R.drawable.signup_login_edit_texts);
        boolean result = false;
        switch(field){
            case "username":
                if(TextUtils.isEmpty(binding.etUsername.getText().toString())){
                    binding.llUsername.setBackgroundResource(R.drawable.error_edit_texts);
                    binding.tvError.setVisibility(View.VISIBLE);
                }
                else{
                    binding.llUsername.setBackgroundResource(R.drawable.signup_login_edit_texts);
                    binding.tvError.setVisibility(View.INVISIBLE);
                    result = true;
                }
                break;

            case "password":
                if(TextUtils.isEmpty(binding.etPassword.getText().toString())){
                    binding.llPassword.setBackgroundResource(R.drawable.error_edit_texts);
                    binding.tvError.setVisibility(View.VISIBLE);
                }
                else{
                    binding.llPassword.setBackgroundResource(R.drawable.signup_login_edit_texts);
                    binding.tvError.setVisibility(View.INVISIBLE);
                    result = true;
                }
                break;

            default:

                break;
        }
        return result;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
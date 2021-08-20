package com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.dao.UserDAO;
import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.dao.UserDAOFirebaseImpl;
import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.databinding.ActivityLoginBinding;
import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.model.User;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setRequestFocus();
        init();

    }

    private void init(){
        UserDAO userDAO = new UserDAOFirebaseImpl();
        users = new ArrayList<>();
        users = userDAO.getUsers();

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
            User loginUser = null;
            if(checkEmptyFields("username") && checkEmptyFields("password")){
                // code to check if username and password is correct
                for(int cnt = 0; cnt < users.size(); cnt++){
                    if(users.get(cnt).getUserName().equals(binding.etUsername.getText().toString()) &&
                    users.get(cnt).getUserPassword().equals(binding.etPassword.getText().toString())){
                        loginUser = users.get(cnt);
                        cnt = users.size();
                        binding.llUsername.setBackgroundResource(R.drawable.signup_login_edit_texts);
                        binding.llPassword.setBackgroundResource(R.drawable.signup_login_edit_texts);
                        binding.tvError.setVisibility(View.INVISIBLE);
                    }
                    else{
                        binding.llUsername.setBackgroundResource(R.drawable.error_edit_texts);
                        binding.llPassword.setBackgroundResource(R.drawable.error_edit_texts);
                        binding.tvError.setVisibility(View.VISIBLE);
                    }
                }
                if(loginUser != null){
                    Toast.makeText(this,"Successful Login", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "Incorrect username and or password", Toast.LENGTH_SHORT).show();
                }
            }
//            submit();
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
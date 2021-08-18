package com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.databinding.ActivitySignUpBinding;
import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.model.User;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");
        user = new User();

        setRequestFocus();

        // Listener to go back to main menu page
        binding.fabBack.setOnClickListener(view -> {
            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // Listener to go to login page
        binding.tvGotologin.setOnClickListener(view -> {
            Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        });

        binding.btnSignup.setOnClickListener(view -> {
            String username = binding.etUsername.getText().toString();
            String email = binding.etEmail.getText().toString();
            String password = binding.etPassword.getText().toString();
            String userid = "user1";
//            if(ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED){
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);
//            }

            submit(username, email, password, userid);
        });

    }

    private boolean submit(String username, String email, String password, String userid){
        if(checkEmptyFields("username") && checkEmptyFields("email") &&
                checkEmptyFields("password") && checkEmptyFields("cfpassword")){

            user.setUserId(userid);
            user.setUserName(username);
            user.setUserEmail(email);
            user.setUserPassword(password);

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    databaseReference.setValue(user);
                    Toast.makeText(SignUpActivity.this, "data added", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Toast.makeText(SignUpActivity.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
                }


            });
        }
        else{
            Toast.makeText(getApplicationContext(), "ELSE", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private boolean checkEmptyFields(String field){
        binding.llUsername.setBackgroundResource(R.drawable.signup_login_edit_texts);
        binding.llEmail.setBackgroundResource(R.drawable.signup_login_edit_texts);
        binding.llPassword.setBackgroundResource(R.drawable.signup_login_edit_texts);
        binding.llCfpassword.setBackgroundResource(R.drawable.signup_login_edit_texts);
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
            case "email":
                if(TextUtils.isEmpty(binding.etEmail.getText().toString())){
                    binding.llEmail.setBackgroundResource(R.drawable.error_edit_texts);
                    binding.tvError.setVisibility(View.VISIBLE);
                }
                else{
                    binding.llEmail.setBackgroundResource(R.drawable.signup_login_edit_texts);
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
                    if(binding.etPassword.getText().toString().equalsIgnoreCase(
                            binding.etCfpassword.getText().toString())){
                        binding.llPassword.setBackgroundResource(R.drawable.signup_login_edit_texts);
                        binding.llCfpassword.setBackgroundResource(R.drawable.signup_login_edit_texts);
                        binding.tvError.setVisibility(View.INVISIBLE);
                        result = true;
                    }
                    else{
                        binding.llPassword.setBackgroundResource(R.drawable.error_edit_texts);
                        binding.llCfpassword.setBackgroundResource(R.drawable.error_edit_texts);
                        binding.tvError.setVisibility(View.VISIBLE);
                    }

                }
                break;

            case "cfpassword":
                if(TextUtils.isEmpty(binding.etCfpassword.getText().toString())){
                    binding.llCfpassword.setBackgroundResource(R.drawable.error_edit_texts);
                    binding.tvError.setVisibility(View.VISIBLE);
                }
                else{
                    if(binding.etCfpassword.getText().toString().equalsIgnoreCase(
                            binding.etPassword.getText().toString())){
                        binding.llCfpassword.setBackgroundResource(R.drawable.signup_login_edit_texts);
                        binding.llPassword.setBackgroundResource(R.drawable.signup_login_edit_texts);
                        binding.tvError.setVisibility(View.INVISIBLE);
                        result = true;
                    }
                    else{
                        binding.llPassword.setBackgroundResource(R.drawable.error_edit_texts);
                        binding.llCfpassword.setBackgroundResource(R.drawable.error_edit_texts);
                        binding.tvError.setVisibility(View.VISIBLE);
                    }

                }
                break;

            default:

                break;
        }
        return result;
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

        binding.ivCfpassword.setOnClickListener(view -> {
            binding.etCfpassword.requestFocus();
        });

        binding.tvCfpassword.setOnClickListener(view -> {
            binding.etCfpassword.requestFocus();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
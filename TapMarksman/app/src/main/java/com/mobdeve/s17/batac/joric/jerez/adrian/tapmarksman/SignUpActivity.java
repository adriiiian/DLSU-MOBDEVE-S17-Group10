package com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.dao.UserDAO;
import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.dao.UserDAOFirebaseImpl;
import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.databinding.ActivitySignUpBinding;
import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.model.User;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setRequestFocus();
        init();
    }

    /**
     * This method is used to initialize variables and views in SignUpActivity
     */
    private void init(){
        UserDAO userDAO = new UserDAOFirebaseImpl();
        mAuth = FirebaseAuth.getInstance();

        /*
         * Listener for back button
         */
        binding.fabBack.setOnClickListener(view -> {
            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        /*
         * Listener for go to login button
         */
        binding.tvGotologin.setOnClickListener(view -> {
            Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        });

        /*
         * Listener for signup button
         */
        binding.btnSignup.setOnClickListener(view -> {
            /*
             * This part is used to check whether the fields submitted is empty
             *
             * If empty, it wont accept the submitted fields and will highlight all empty fields
             */
            if(checkNonEmptyFields()) {
                /*
                 * This part is used to check whether the password and confirm password fields
                 * has the same value
                 */
                if(checkValidPassword()){
                    User user = new User();
                    user.setUserName(binding.etUsername.getText().toString());
                    user.setUserEmail(binding.etEmail.getText().toString());
                    user.setUserPassword(binding.etPassword.getText().toString());
                    user.setOwnedPistol(true);
                    user.setOwnedRevolver(false);
                    user.setOwnedDesertEagle(false);
                    user.setOwnedRifle(false);
                    user.setDamageUpgradeCounter(0);
                    user.setPowerUpgradeCounter(0);
                    user.setControlUpgradeCounter(0);
                    user.setPoints(0);
                    user.setMultiplier(1);
                    user.setDifficulty("Easy");
                    user.setEquipedGun("Pistol");
                    user.setHighestScore(0);

                    /*
                     * This part is used for authentication using FirebaseAuth
                     *
                     * Task will not be successful if the submitted values is not valid
                     */
                    mAuth.createUserWithEmailAndPassword(user.getUserEmail(), user.getUserPassword()).addOnCompleteListener(
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(), "You registered successfully!", Toast.LENGTH_LONG).show();
                                        userDAO.addUser(user); // This line adds the user into the firebase database
                                        Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "Registration Failed! Please try again", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                    );
                }
            }
            else{
                Toast.makeText(this, "Please input correctly!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * This method is used to check if the fields submitted by the user is empty
     * @return boolean
     * true: if field is not empty
     * false: if field is empty
     */
    private boolean checkNonEmptyFields(){
        binding.llUsername.setBackgroundResource(R.drawable.signup_login_edit_texts);
        binding.llEmail.setBackgroundResource(R.drawable.signup_login_edit_texts);
        binding.llPassword.setBackgroundResource(R.drawable.signup_login_edit_texts);
        binding.llCfpassword.setBackgroundResource(R.drawable.signup_login_edit_texts);
        boolean result = true;

        if(TextUtils.isEmpty(binding.etUsername.getText().toString())){
            binding.llUsername.setBackgroundResource(R.drawable.error_edit_texts);
            binding.tvError.setVisibility(View.VISIBLE);
            result = false;
        }

        else{
            binding.llUsername.setBackgroundResource(R.drawable.signup_login_edit_texts);
            binding.tvError.setVisibility(View.INVISIBLE);
        }

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

        if(TextUtils.isEmpty(binding.etCfpassword.getText().toString())){
            binding.llCfpassword.setBackgroundResource(R.drawable.error_edit_texts);
            binding.tvError.setVisibility(View.VISIBLE);
            result = false;
        }
        else{
            binding.llCfpassword.setBackgroundResource(R.drawable.signup_login_edit_texts);
            binding.tvError.setVisibility(View.INVISIBLE);
        }
        return result;
    }

    /**
     * This method is used to check if the value of password matches the value of confirm password
     * @return boolean
     * true: if password and confirm password match
     * false: if password and confirm password do not match
     */
    private boolean checkValidPassword(){
        boolean result = true;
        if(binding.etPassword.getText().toString().equalsIgnoreCase(
                binding.etCfpassword.getText().toString())){
            binding.llPassword.setBackgroundResource(R.drawable.signup_login_edit_texts);
            binding.llCfpassword.setBackgroundResource(R.drawable.signup_login_edit_texts);
            binding.tvError.setVisibility(View.INVISIBLE);
        }
        else{
            binding.llPassword.setBackgroundResource(R.drawable.error_edit_texts);
            binding.llCfpassword.setBackgroundResource(R.drawable.error_edit_texts);
            binding.tvError.setVisibility(View.VISIBLE);
            result = false;
        }

        return result;
    }


    /**
     * This method will set the request focus for each image views and text views
     * to their respective views
     */
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

    /**
     * Override method for onBackPressed
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
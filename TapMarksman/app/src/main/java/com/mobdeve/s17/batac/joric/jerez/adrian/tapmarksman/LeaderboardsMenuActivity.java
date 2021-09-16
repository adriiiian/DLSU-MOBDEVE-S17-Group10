package com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.adapter.LeaderboardsAdapter;
import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.dao.UserDAO;
import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.dao.UserDAOFirebaseImpl;
import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.databinding.ActivityLeaderboardsMenuBinding;
import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.model.User;

import java.util.ArrayList;
import java.util.Collections;

public class LeaderboardsMenuActivity extends AppCompatActivity {

    private ActivityLeaderboardsMenuBinding binding;
    private ArrayList<User> usersTop10;
    private LeaderboardsAdapter leaderboardsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLeaderboardsMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        UserDAO userDAO = new UserDAOFirebaseImpl();
        usersTop10 = new ArrayList<>();

        /*
         * This part get all the users and sort them in descending order. (Highest to lowest)
         * This part also selects only up to 10 highest scorer users that will be displayed
         * in the leaderboards
         */
        userDAO.getUsers(new FirebaseLeaderboardCallback() {
            @Override
            public void onCallBack(ArrayList<User> users) {
                Collections.sort(users, User.UserComparator);

                if(users.size() > 10){
                    for(int i = 0; i < 10; i ++){
                        usersTop10.add(users.get(i));
                    }
                }
                else{
                    usersTop10.addAll(users);
                }

                leaderboardsAdapter = new LeaderboardsAdapter(getApplicationContext(), usersTop10);
                binding.rvData.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                binding.rvData.setAdapter(leaderboardsAdapter);

                /*
                 * Listener for back button
                 */
                binding.fabBack.setOnClickListener(view -> {
                    Intent intent = new Intent(LeaderboardsMenuActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                });
            }
        });
    }

    /**
     * Override method for onBackPressed
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(LeaderboardsMenuActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
package com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.databinding.ActivityGameOnlineBinding;

import java.util.Random;

public class GameOnlineActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    private ActivityGameOnlineBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGameOnlineBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();

        System.out.println(binding.ivTarget.getX());

        binding.ivTarget.setOnClickListener(view -> {
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            float x = (float) (size.x * getX());
            float y = (float) (size.y * getY());

            ObjectAnimator animation = ObjectAnimator.ofFloat(binding.ivTarget, "X", x);
            ObjectAnimator animation2 = ObjectAnimator.ofFloat(binding.ivTarget, "Y", y);
            animation.setDuration(1);
            animation2.setDuration(1);
            animation.start();
            animation2.start();
        });

    }

    private void init(){
        mAuth = FirebaseAuth.getInstance();
        System.out.println(mAuth.getCurrentUser().getEmail());



        binding.btnMenu.setOnClickListener(view -> {
            PopupMenu popup = new PopupMenu(GameOnlineActivity.this, view);
            popup.setOnMenuItemClickListener(GameOnlineActivity.this);
            popup.inflate(R.menu.main_menu);
            popup.show();
        });
    }

    private float getX(){
        Random rand = new Random();

        float result = rand.nextFloat();
        while(result > 0.775){
            result = rand.nextFloat();
        }

        return result;
    }

    private float getY(){
        Random rand = new Random();

        float result = rand.nextFloat();
        while(result > 0.5 || result < 0.1){
            result = rand.nextFloat();
        }

        return result;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_settings:
                Intent intent = new Intent(GameOnlineActivity.this, MainActivity.class);
                startActivity(intent);
                mAuth.signOut();
                finish();
        }
        return false;
    }
}

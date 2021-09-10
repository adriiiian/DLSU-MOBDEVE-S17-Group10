package com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.dao.UserDAO;
import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.dao.UserDAOFirebaseImpl;
import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.databinding.ActivityUpgradesMenuBinding;
import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.model.User;

public class UpgradesMenuActivity extends AppCompatActivity {

    private ActivityUpgradesMenuBinding binding;
    private int damageprice, powerprice, controlprice;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpgradesMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();

    }

    private void init(){

        Intent getIntent = getIntent();
        user = new User();
        user.setOwnedPistol(getIntent.getBooleanExtra("pistol", false));
        user.setOwnedRevolver(getIntent.getBooleanExtra("revolver", false));
        user.setOwnedDesertEagle(getIntent.getBooleanExtra("deserteagle", false));
        user.setOwnedRifle(getIntent.getBooleanExtra("rifle", false));
        user.setDamageUpgradeCounter(getIntent.getIntExtra("damagectr", 0));
        user.setPowerUpgradeCounter(getIntent.getIntExtra("powerctr", 0));
        user.setControlUpgradeCounter(getIntent.getIntExtra("controlctr", 0));
        user.setPoints(getIntent.getIntExtra("points", 0));
        user.setUserEmail(getIntent.getStringExtra("email"));
        user.setUserName(getIntent.getStringExtra("username"));
        user.setUserPassword(getIntent.getStringExtra("password"));
        user.setMultiplier(getIntent.getIntExtra("multiplier", 1));
        user.setDifficulty(getIntent.getStringExtra("difficulty"));
        user.setEquipedGun(getIntent.getStringExtra("equipedGun"));
        user.setHighestScore(getIntent.getIntExtra("highestScore", 0));

        setBtnGuns();
        setEquipedGun();
        setUpgrades();
        getMultiplier();
        binding.tvPtsctr2.setText(String.valueOf(user.getPoints()));

        binding.btnReturn.setOnClickListener(view -> {
            Intent intent = new Intent(UpgradesMenuActivity.this, GameOnlineActivity.class);
            UserDAO userDAO = new UserDAOFirebaseImpl();
            userDAO.updateUser(user, new FirebaseCallback(){
                @Override
                public void onCallBack(User user) {
                    startActivity(intent);
                    finish();
                }
            });
        });

        binding.btnPistol.setOnClickListener(view -> {
            if(user.getOwnedPistol()){
                binding.btnPistol.setText("EQUIPED");
                user.setEquipedGun("Pistol");
                setOwned(1);
            }
            else{

                if(user.getPoints() >= 100){
                    binding.btnPistol.setText("OWNED");
                    user.setPoints(user.getPoints() - 100);
                    binding.tvPtsctr2.setText(String.valueOf(user.getPoints()));
                    user.setOwnedRifle(true);
                    getMultiplier();
                }
            }
        });

        binding.btnRevolver.setOnClickListener(view -> {
            if(user.getOwnedRevolver()){
                binding.btnRevolver.setText("EQUIPED");
                user.setEquipedGun("Revolver");
                setOwned(2);
            }
            else{
                if(user.getPoints() >= 250){
                    binding.btnRevolver.setText("OWNED");
                    user.setPoints(user.getPoints() - 250);
                    binding.tvPtsctr2.setText(String.valueOf(user.getPoints()));
                    user.setOwnedRevolver(true);
                    getMultiplier();
                }
            }
        });

        binding.btnDeserteagle.setOnClickListener(view -> {
            if(user.getOwnedDesertEagle()){
                binding.btnDeserteagle.setText("EQUIPED");
                user.setEquipedGun("Desert Eagle");
                setOwned(3);
            }
            else{
                if(user.getPoints() >= 750){
                    binding.btnDeserteagle.setText("OWNED");
                    user.setPoints(user.getPoints() - 750);
                    binding.tvPtsctr2.setText(String.valueOf(user.getPoints()));
                    user.setOwnedDesertEagle(true);
                    getMultiplier();
                }
            }
        });

        binding.btnRifle.setOnClickListener(view -> {
            if(user.getOwnedRifle()){
                binding.btnRifle.setText("EQUIPED");
                user.setEquipedGun("Rifle");
                setOwned(4);
            }
            else{
                if(user.getPoints() >= 2000){
                    binding.btnRifle.setText("OWNED");
                    user.setPoints(user.getPoints() - 2000);
                    binding.tvPtsctr2.setText(String.valueOf(user.getPoints()));
                    user.setOwnedRifle(true);
                    getMultiplier();
                }
            }
        });

        binding.btnDamage.setOnClickListener(view -> {
            if(user.getPoints() >= Integer.parseInt(binding.btnDamage.getText().toString())){
                user.setPoints(user.getPoints() - Integer.parseInt(binding.btnDamage.getText().toString()));
                user.setDamageUpgradeCounter(user.getDamageUpgradeCounter() + 1);
                damageprice = damageprice * 2;
                binding.btnDamage.setText(Integer.toString(damageprice));
                binding.tvPtsctr2.setText(String.valueOf(user.getPoints()));
                getMultiplier();
            }
        });

        binding.btnPower.setOnClickListener(view -> {
            if(user.getPoints() >= Integer.parseInt(binding.btnPower.getText().toString())){
                user.setPoints(user.getPoints() - Integer.parseInt(binding.btnPower.getText().toString()));
                user.setPowerUpgradeCounter(user.getPowerUpgradeCounter() + 1);
                powerprice = powerprice * 2;
                binding.btnPower.setText(Integer.toString(powerprice));
                binding.tvPtsctr2.setText(String.valueOf(user.getPoints()));
                getMultiplier();
            }
        });

        binding.btnControl.setOnClickListener(view -> {
            if(user.getPoints() >= Integer.parseInt(binding.btnControl.getText().toString())){
                user.setPoints(user.getPoints() - Integer.parseInt(binding.btnControl.getText().toString()));
                user.setControlUpgradeCounter(user.getControlUpgradeCounter() + 1);
                controlprice = controlprice * 2;
                binding.btnControl.setText(Integer.toString(controlprice));
                binding.tvPtsctr2.setText(String.valueOf(user.getPoints()));
                getMultiplier();
            }
        });

    }

    // This method sets all owned guns when upgrade menu is opened.
    private void setBtnGuns(){

        if(user.getOwnedPistol()){
            binding.btnPistol.setText("Owned");
        }

        if(user.getOwnedRevolver()){
            binding.btnRevolver.setText("Owned");
        }

        if(user.getOwnedDesertEagle()){
            binding.btnDeserteagle.setText("Owned");
        }

        if(user.getOwnedRifle()){
            binding.btnRifle.setText("Owned");
        }

    }

    // This method sets the current equiped gun label when upgrade menu is opened.
    private void setEquipedGun(){
        if(user.getEquipedGun().equalsIgnoreCase("Pistol")){
            binding.btnPistol.setText("Equiped");
        }

        else if(user.getEquipedGun().equalsIgnoreCase("Revolver")){
            binding.btnRevolver.setText("Equiped");
        }

        else if(user.getEquipedGun().equalsIgnoreCase("Desert Eagle")){
            binding.btnDeserteagle.setText("Equiped");
        }

        else if(user.getEquipedGun().equalsIgnoreCase("Rifle")){
            binding.btnRifle.setText("Equiped");
        }
    }

    // This methods sets the upgrade price for each upgrades depending on the current upgrades the user has.
    private void setUpgrades(){
        if(user.getDamageUpgradeCounter() > 0){
            int ctr = 0;
            damageprice = 25;
            while(ctr < user.getDamageUpgradeCounter()){
                damageprice = damageprice * 2;
                ctr++;
            }
            binding.btnDamage.setText(Integer.toString(damageprice));
        }
        else{
            damageprice = 25;
        }

        if(user.getPowerUpgradeCounter() > 0){
            int ctr = 0;
            powerprice = 25;
            while(ctr < user.getPowerUpgradeCounter()){
                powerprice = powerprice * 2;
                ctr++;
            }
            binding.btnPower.setText(Integer.toString(powerprice));
        }
        else{
            powerprice = 25;
        }

        if(user.getControlUpgradeCounter() > 0){
            int ctr = 0;
            controlprice = 25;
            while(ctr < user.getControlUpgradeCounter()){
                controlprice = controlprice * 2;
                ctr++;
            }
            binding.btnControl.setText(Integer.toString(controlprice));
        }
        else{
            controlprice = 25;
        }
    }

    // This method sets the multiplier based on the guns and upgrades owned by the user.
    private void getMultiplier(){
        user.setMultiplier(0);
        if(user.getOwnedPistol()){
            System.out.println("1st: " + user.getMultiplier());
            user.setMultiplier(user.getMultiplier() + 1);
            System.out.println("2nd: " + user.getMultiplier());
        }

        if(user.getOwnedRevolver()){
            user.setMultiplier(user.getMultiplier() + 1);
        }

        if(user.getOwnedDesertEagle()){
            user.setMultiplier(user.getMultiplier() + 1);
        }

        if(user.getOwnedRifle()){
            user.setMultiplier(user.getMultiplier() + 1);
        }

        user.setMultiplier(user.getMultiplier() + user.getDamageUpgradeCounter() + user.getPowerUpgradeCounter() + user.getControlUpgradeCounter());
        binding.tvMultinum.setText(Integer.toString(user.getMultiplier()));
    }

    // This method sets the un-equiped guns owned of the user to "Owned".
    private void setOwned(int gunNum){
        switch(gunNum){
            case 1:
                if(user.getOwnedRevolver()){
                    binding.btnRevolver.setText("OWNED");
                }
                if(user.getOwnedDesertEagle()){
                    binding.btnDeserteagle.setText("OWNED");
                }
                if(user.getOwnedRifle()){
                    binding.btnRifle.setText("OWNED");
                }
                break;

            case 2:
                if(user.getOwnedPistol()){
                    binding.btnPistol.setText("OWNED");
                }
                if(user.getOwnedDesertEagle()){
                    binding.btnDeserteagle.setText("OWNED");
                }
                if(user.getOwnedRifle()){
                    binding.btnRifle.setText("OWNED");
                }
                break;

            case 3:
                if(user.getOwnedPistol()){
                    binding.btnPistol.setText("OWNED");
                }
                if(user.getOwnedRevolver()){
                    binding.btnRevolver.setText("OWNED");
                }
                if(user.getOwnedRifle()){
                    binding.btnRifle.setText("OWNED");
                }
                break;

            case 4:
                if(user.getOwnedPistol()){
                    binding.btnPistol.setText("OWNED");
                }
                if(user.getOwnedRevolver()){
                    binding.btnRevolver.setText("OWNED");
                }
                if(user.getOwnedDesertEagle()){
                    binding.btnDeserteagle.setText("OWNED");
                }
                break;

            default:

                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(UpgradesMenuActivity.this, GameOnlineActivity.class);
        UserDAO userDAO = new UserDAOFirebaseImpl();
        userDAO.updateUser(user, new FirebaseCallback(){
            @Override
            public void onCallBack(User user) {
                startActivity(intent);
                finish();
            }
        });
    }
}
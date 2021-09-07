package com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.dao;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.FirebaseCallback;
import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.model.User;

import java.util.ArrayList;

public class UserDAOFirebaseImpl implements UserDAO{

    private final String PATH = "users";
    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://tap-marksman-default-rtdb.asia-southeast1.firebasedatabase.app");
    private DatabaseReference myRef = database.getReference(PATH);
    private User result = new User();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public UserDAOFirebaseImpl(){
        final String TAG = "Listener";
    }
    @Override
    public long addUser(User user) {
        final long[] result = {-1};

        myRef.child(mAuth.getCurrentUser().getUid()).setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, DatabaseReference ref) {
                if(error != null){
                    Log.e("ERROR", "ERROR: " + error.getMessage());
                    result[0] = -2;
                }
                else{
                    Log.d("SUCCESS", "DATA INSERTED");
                    result[0] = 1L;
                }
            }
        });
        return result[0];
    }

    @Override
    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();

        myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    for(DataSnapshot user : task.getResult().getChildren()){
                        User temp = new User();
                        temp.setUserName(user.child("userName").getValue().toString());
                        temp.setUserPassword(user.child("userPassword").getValue().toString());
                        temp.setUserEmail(user.child("userEmail").getValue().toString());

                        users.add(temp);

                    }
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                }
            }
        });
        return users;
    }

    @Override
    public void getUser(FirebaseCallback firebaseCallback) {
        DatabaseReference temp = myRef.child(mAuth.getCurrentUser().getUid());
        temp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                User user = new User();
                user.setUserEmail(snapshot.child("userEmail").getValue().toString());
                user.setUserName(snapshot.child("userName").getValue().toString());
                user.setUserPassword(snapshot.child("userPassword").getValue().toString());
                user.setOwnedPistol(Boolean.valueOf(snapshot.child("ownedPistol").getValue().toString()));
                user.setOwnedRevolver(Boolean.valueOf(snapshot.child("ownedRevolver").getValue().toString()));
                user.setOwnedDesertEagle(Boolean.valueOf(snapshot.child("ownedDesertEagle").getValue().toString()));
                user.setOwnedRifle(Boolean.valueOf(snapshot.child("ownedRifle").getValue().toString()));
                user.setDamageUpgradeCounter(Integer.valueOf(snapshot.child("damageUpgradeCounter").getValue().toString()));
                user.setPowerUpgradeCounter(Integer.valueOf(snapshot.child("powerUpgradeCounter").getValue().toString()));
                user.setControlUpgradeCounter(Integer.valueOf(snapshot.child("controlUpgradeCounter").getValue().toString()));
                user.setPoints(Integer.valueOf(snapshot.child("points").getValue().toString()));
                user.setMultiplier(Integer.valueOf(snapshot.child("multiplier").getValue().toString()));
                user.setDifficulty(snapshot.child("difficulty").getValue().toString());
                user.setEquipedGun(snapshot.child("equipedGun").getValue().toString());
                firebaseCallback.onCallBack(user);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("firebase", "listener canceled", error.toException());
            }
        });
    }

    @Override
    public long updateUser(User user, FirebaseCallback firebaseCallback) {
        final long[] result = {-1};

        myRef.child(mAuth.getCurrentUser().getUid()).setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, DatabaseReference ref) {
                if(error != null){
                    Log.e("ERROR", "ERROR: " + error.getMessage());
                    result[0] = -2;
                }
                else{
                    Log.d("SUCCESS", "DATA UPDATED");
                    result[0] = 1L;
                    firebaseCallback.onCallBack(user);
                }
            }
        });
        return result[0];
    }

    @Override
    public void updateUserDifficulty(String difficulty, FirebaseCallback firebaseCallback) {
        myRef.child(mAuth.getCurrentUser().getUid()).child("difficulty").setValue(difficulty);
        User user = new User();
        firebaseCallback.onCallBack(user);
    }

    @Override
    public void updateUserGamePoints(int gamepoints, FirebaseCallback firebaseCallback) {
        myRef.child(mAuth.getCurrentUser().getUid()).child("points").setValue(gamepoints);
        User user = new User();
        firebaseCallback.onCallBack(user);
    }

    @Override
    public int deleteUser(int userid) {
        return 0;
    }
}

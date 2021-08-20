package com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.dao;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.model.User;

import java.util.ArrayList;

public class UserDAOFirebaseImpl implements UserDAO{

    private final String PATH = "users";
    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://tap-marksman-default-rtdb.asia-southeast1.firebasedatabase.app");
    private DatabaseReference myRef = database.getReference(PATH);
    private User result = new User();

    public UserDAOFirebaseImpl(){
        final String TAG = "Listner";
    }
    @Override
    public long addUser(User user) {
        final long[] result = {-1};
        myRef.push().setValue(user, new DatabaseReference.CompletionListener() {
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
    public User getUser(String userName, String userPassword) {
        return null;
    }

    @Override
    public int updateUser(User user) {
        return 0;
    }

    @Override
    public int deleteUser(int userid) {
        return 0;
    }
}

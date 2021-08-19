package com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.dao;

import android.util.Log;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.model.User;

import java.util.ArrayList;

public class UserDAOFirebaseImpl implements UserDAO{

    private final String PATH = "users";
    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://tap-marksman-default-rtdb.asia-southeast1.firebasedatabase.app");
    private DatabaseReference myRef = database.getReference(PATH);

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
                System.out.println(user.getUserName());
            }
        });


        return result[0];
    }

    @Override
    public ArrayList<User> getUsers() {
        return null;
    }

    @Override
    public User getUser(int userid) {
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

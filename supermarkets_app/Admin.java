package com.p17107.supermarkets_app;

import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.widget.Toast.*;

public class Admin {
    private  static String admin_uid;
    FirebaseDatabase database;
    DatabaseReference myRef;
    public Admin() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("admin");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                admin_uid = dataSnapshot.getValue(String.class);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });


    }

    private static String getAdmin_uid(){

        return  admin_uid;
    }

    public boolean check_admin_login(String user_uid){

        if(user_uid.equals(getAdmin_uid())){
            return  true;
        }
        return false;
    }


}

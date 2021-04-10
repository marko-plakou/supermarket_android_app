package com.p17107.supermarkets_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class view_supermarkets extends AppCompatActivity {
    ImageView supermarket1,supermarket2,supermarket3,supermarket4,supermarket5;
    TextView location1,location2,location3,location4,location5;
    Supermarkets supermarket=new Supermarkets();
    Supermarkets sp1=new Supermarkets();
    Supermarkets sp2=new Supermarkets();
    Supermarkets sp3=new Supermarkets();
    Supermarkets sp4=new Supermarkets();
    Supermarkets sp5=new Supermarkets();
    private StorageReference mStorageRef;
    DatabaseReference myRef1,myRef2;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_supermarkets);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        supermarket1=findViewById(R.id.imageView);
        supermarket2=findViewById(R.id.imageView2);
        supermarket3=findViewById(R.id.imageView3);
        supermarket4=findViewById(R.id.imageView4);
        supermarket5=findViewById(R.id.imageView5);
        location1=findViewById(R.id.textView14);
        location2=findViewById(R.id.textView12);
        location3=findViewById(R.id.textView11);
        location4=findViewById(R.id.textView13);
        location5=findViewById(R.id.textView6);

        sp1.setSupermarket_id("supermarket1");
        sp2.setSupermarket_id("supermarket2");
        sp3.setSupermarket_id("supermarket3");
        sp4.setSupermarket_id("supermarket4");
        sp5.setSupermarket_id("supermarket5");

        find_supermarket_photo_url(sp1,supermarket1);
        find_supermarket_photo_url(sp2,supermarket2);
        find_supermarket_photo_url(sp3,supermarket3);
        find_supermarket_photo_url(sp4,supermarket4);
        find_supermarket_photo_url(sp5,supermarket5);




        find_supermarket_location(sp1,location1);
        find_supermarket_location(sp2,location2);
        find_supermarket_location(sp3,location3);
        find_supermarket_location(sp4,location4);
        find_supermarket_location(sp5,location5);

    }

    public void set_supermarket_img(String photo_url,ImageView supermarket){
        try {
            String s = photo_url;
            File localFile = File.createTempFile("supermarkets","jpg");
            StorageReference imageRef = mStorageRef.child("supermarkets/"+s);
            imageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    supermarket.setImageBitmap(BitmapFactory.decodeFile(localFile.getAbsolutePath()));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public void find_supermarket_location(Supermarkets supermarket_id,TextView location_id){
        database = FirebaseDatabase.getInstance();
        SharedPreferences preferences=getApplicationContext().getSharedPreferences("Settings",getApplicationContext().MODE_PRIVATE);
        String language=preferences.getString("My_Lang","");
        if(language.equals("en")){
            myRef1 =database.getReference(supermarket_id.getSupermarket_id()+"/locationEn");
        }else{ myRef1 =database.getReference(supermarket_id.getSupermarket_id()+"/locationGr");}




        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                        supermarket.setLocation_en(dataSnapshot.getValue(String.class));
                        location_id.setText( supermarket.getLocation_en());



            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });

    }

    public void find_supermarket_photo_url(Supermarkets supermarket,ImageView supermarket_image_view){
        database = FirebaseDatabase.getInstance();
        myRef2 =database.getReference(supermarket.getSupermarket_id()+"/photo_url");
        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                supermarket.setPhoto_url( dataSnapshot.getValue(String.class));
                set_supermarket_img(supermarket.getPhoto_url(),supermarket_image_view);



            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
    }

    public void supermarket1(View view){
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }

}
package com.p17107.supermarkets_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class edit_stock extends AppCompatActivity {
    EditText supermarket_id,product_id,stock;
    TextView p_id,supermarket_location;
    ImageView product_image,supermarket_image;
    Products product=new Products();
    Supermarkets supermarket=new Supermarkets();
    DatabaseReference myRef1,myRef2;
    FirebaseDatabase database;
    private StorageReference mStorageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_stock);
        supermarket_id=findViewById(R.id.editTextNumberSigned);
        product_id=findViewById(R.id.editTextTextPersonName10);
        stock=findViewById(R.id.editTextNumberSigned2);
        p_id=findViewById(R.id.textView33);
        supermarket_location=findViewById(R.id.textView34);
        product_image=findViewById(R.id.imageView7);
        supermarket_image=findViewById(R.id.imageView8);
        mStorageRef = FirebaseStorage.getInstance().getReference();

    }

    public void find_product(View view){
        supermarket.setSupermarket_id(supermarket_id.getText().toString());
        supermarket.setProduct_id(product_id.getText().toString());
        product.setProduct_id(product_id.getText().toString());



        try{
            database = FirebaseDatabase.getInstance();

            myRef1 =database.getReference("supermarket"+supermarket.getSupermarket_id()+"/products/product"+supermarket.getProduct_id()+"/stock");





            myRef1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    if(dataSnapshot.exists()){
                        supermarket.setProduct_stock(dataSnapshot.getValue(long.class));
                        stock.setText(String.valueOf(supermarket.getProduct_stock()));


                        p_id.setText(p_id.getText().toString()+supermarket.getProduct_id());
                       set_img(find_supermarket_img(supermarket),find_product_img(product),supermarket_image,product_image);

                    }
                    else{
                        Toast.makeText(getApplicationContext(), getString(R.string.product_does_not_exist), Toast.LENGTH_SHORT).show();


                        stock.setText(getString(R.string.not_found));
                        p_id.setText(R.string.not_found);




                    }



                }






                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value

                }
            });}catch (Exception a){
            Toast.makeText(this, "exception occured", Toast.LENGTH_SHORT).show();
        }
        //stock.setText(supermarket.getProduct_stock());
        SharedPreferences preferences=getApplicationContext().getSharedPreferences("Settings",getApplicationContext().MODE_PRIVATE);
        String language=preferences.getString("My_Lang","");
        if(language.equals("en-US")){
            myRef1 =database.getReference("supermarket"+supermarket.getSupermarket_id()+"/locationEn");
        }else{ myRef1 =database.getReference("supermarket"+supermarket.getSupermarket_id()+"/locationGr");}




        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(dataSnapshot.exists()){
                    supermarket.setLocation_en(dataSnapshot.getValue(String.class));
                    supermarket_location.setText( supermarket.getLocation_en());

                }
                else{
                    Toast.makeText(getApplicationContext(), getString(R.string.supermarket_id_not_found), Toast.LENGTH_SHORT).show();

                    supermarket_location.setText(getString(R.string.not_found));
                }




            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
    }


    public void set_img(String  supermarket_url,String product_url,ImageView supermarket,ImageView product){
        try {
            String url1=supermarket_url;
            File localFile1 = File.createTempFile("supermarkets","jpg");
            StorageReference imageRef1 = mStorageRef.child("supermarkets/"+url1);
            imageRef1.getFile(localFile1).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    supermarket.setImageBitmap(BitmapFactory.decodeFile(localFile1.getAbsolutePath()));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String url2=product_url;
            File localFile2 = File.createTempFile("products","jpg");
            StorageReference imageRef2 = mStorageRef.child("products/"+url2);
            imageRef2.getFile(localFile2).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    product.setImageBitmap(BitmapFactory.decodeFile(localFile2.getAbsolutePath()));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String find_supermarket_img(Supermarkets supermarket){
        database = FirebaseDatabase.getInstance();
        myRef1 =database.getReference("supermarket"+supermarket.getSupermarket_id()+"/photo_url");
        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                supermarket.setPhoto_url(dataSnapshot.getValue(String.class));




            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        }); return supermarket.getPhoto_url();
    }
    public String find_product_img(Products product){
        database = FirebaseDatabase.getInstance();
        myRef1 =database.getReference("products/product"+product.getProduct_id()+"/photo_url");
        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                product.setPhoto_url(dataSnapshot.getValue(String.class));




            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        }); return product.getPhoto_url();
    }



    public void update_stock(View view){
       if(!stock.getText().toString().isEmpty()){
            supermarket.setSupermarket_id(supermarket_id.getText().toString());
            supermarket.setProduct_id(product_id.getText().toString());
            try {


            supermarket.setProduct_stock(Integer.parseInt(stock.getText().toString()));}catch(Exception b){}

            database = FirebaseDatabase.getInstance();
            myRef2 =database.getReference("supermarket"+supermarket.getSupermarket_id()+"/products/product"+supermarket.getProduct_id()+"/stock");
            myRef2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    if(dataSnapshot.exists()) {
                        myRef2.setValue(supermarket.getProduct_stock());

                        Toast.makeText(getApplicationContext(), getString(R.string.update_success), Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(getApplicationContext(), getString(R.string.product_does_not_exist), Toast.LENGTH_SHORT).show();
                    }
                }  @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value


                }
            });
        }


}}
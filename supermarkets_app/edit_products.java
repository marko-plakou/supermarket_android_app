package com.p17107.supermarkets_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class edit_products extends AppCompatActivity {
    EditText product_id,product_en,product_gr,price,photo_url;
    DatabaseReference myRef1;
    FirebaseDatabase database;
    Products product=new Products();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_products);
        product_id=findViewById(R.id.editTextTextPersonName);
        product_en=findViewById(R.id.editTextTextPersonName2);
        product_gr=findViewById(R.id.editTextTextPersonName3);
        photo_url=findViewById(R.id.editTextTextPersonName4);
        price=findViewById(R.id.editTextTextPersonName5);
    }

    public void select_product(View view){
        product.setProduct_id(product_id.getText().toString());
        try{
        database = FirebaseDatabase.getInstance();

        myRef1 =database.getReference("products/product"+product.getProduct_id());





        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(dataSnapshot.exists()){
                product= dataSnapshot.getValue(Products.class);


                }
            else{
                    Toast.makeText(getApplicationContext(), getString(R.string.product_does_not_exist), Toast.LENGTH_SHORT).show();
                    product.setNameEn(getString(R.string.not_found));
                    product.setNameGr(getString(R.string.not_found));
                    product.setPhoto_url(getString(R.string.not_found));
                    product.setPrice(0);
                }
                product_en.setText(product.getNameEn());
                product_gr.setText(product.getNameGr());
                photo_url.setText(product.getPhoto_url());
                price.setText(String.valueOf(product.getPrice()));

            }






            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });}catch (Exception a){
            Toast.makeText(this, "Error!.", Toast.LENGTH_SHORT).show();
        }






    }
    public void submit_edit(View view){
        product.setProduct_id(product_id.getText().toString());
        product.setNameEn(product_en.getText().toString());
        product.setNameGr(product_gr.getText().toString());
        product.setPhoto_url(photo_url.getText().toString());
        product.setPrice(Double.valueOf(price.getText().toString()));
        database = FirebaseDatabase.getInstance();
        myRef1 =database.getReference("products/product"+product.getProduct_id());
        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(dataSnapshot.exists()) {

                    myRef1.setValue(product);
                    Toast.makeText(getApplicationContext(), getString(R.string.update_success), Toast.LENGTH_SHORT).show();
                }
                else{Toast.makeText(getApplicationContext(), getString(R.string.update_fail), Toast.LENGTH_SHORT).show();}
    }  @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
}}
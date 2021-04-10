package com.p17107.supermarkets_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class add_products extends AppCompatActivity {
    EditText product_id,name_en,name_gr,img_name,price;
    Supermarkets supermarket=new Supermarkets();
    Products product=new Products();
    DatabaseReference myRef1,myRef2;
    FirebaseDatabase database;
    FirebaseStorage storage;
    StorageReference storageReference;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 22;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);
        product_id=findViewById(R.id.editTextTextPersonName6);
        name_en=findViewById(R.id.editTextTextPersonName7);
        name_gr=findViewById(R.id.editTextTextPersonName8);
        img_name=findViewById(R.id.editTextTextPersonName9);
        price=findViewById(R.id.editTextNumberDecimal);
        imageView = findViewById(R.id.imageView6);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }
    public void submit_addition(){



            product.setProduct_id(product_id.getText().toString());
            product.setNameEn(name_en.getText().toString());
            product.setNameGr(name_gr.getText().toString());
            product.setPhoto_url(img_name.getText().toString());
            product.setPrice(Double.valueOf(price.getText().toString()));


        database = FirebaseDatabase.getInstance();
        myRef1 =database.getReference("products/product"+product.getProduct_id());

        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(!dataSnapshot.exists()) {

                    myRef1.setValue(product);

                    supermarket.setSupermarket_id("1");
                    add_to_supermarkets(product,supermarket.getSupermarket_id());
                    supermarket.setSupermarket_id("2");
                    add_to_supermarkets(product,supermarket.getSupermarket_id());
                    supermarket.setSupermarket_id("3");
                    add_to_supermarkets(product,supermarket.getSupermarket_id());
                    supermarket.setSupermarket_id("4");
                    add_to_supermarkets(product,supermarket.getSupermarket_id());
                    supermarket.setSupermarket_id("5");
                    add_to_supermarkets(product,supermarket.getSupermarket_id());
                    Toast.makeText(getApplicationContext(), getString(R.string.product_added_success), Toast.LENGTH_SHORT).show();



                }
                else{

                    Toast.makeText(getApplicationContext(), getString(R.string.product_exists), Toast.LENGTH_SHORT).show();


                }
            }  @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value


            }
        });



    }
    public void select_image(View view){
        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("product/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        getString(R.string.select_image_from_here)),
                PICK_IMAGE_REQUEST);
    }

    // Override onActivityResult method
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data)
    {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                imageView.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }



    }
    public void upload_product(View view){
        if(product_id.getText().toString().isEmpty()||name_en.getText().toString().isEmpty()||name_gr.getText().toString().isEmpty()||img_name.getText().toString().isEmpty()
                ||price.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), getString(R.string.add_all_fields_message), Toast.LENGTH_SHORT).show();

        }
        else{
        if(img_name.getText().toString().contains(".png")||img_name.getText().toString().contains(".jpg")){
            if (filePath != null) {
                submit_addition();

                // Code for showing progressDialog while uploading
                ProgressDialog progressDialog
                        = new ProgressDialog(this);
                progressDialog.setTitle(getString(R.string.image_uploading));
                progressDialog.show();

                // Defining the child of storageReference
                StorageReference ref
                        = storageReference
                        .child(
                                "products/"
                                        + img_name.getText().toString());

                // adding listeners on upload
                // or failure of image
                ref.putFile(filePath)
                        .addOnSuccessListener(
                                new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                    @Override
                                    public void onSuccess(
                                            UploadTask.TaskSnapshot taskSnapshot)
                                    {

                                        // Image uploaded successfully
                                        // Dismiss dialog
                                        progressDialog.dismiss();
                                        Toast
                                                .makeText(getApplicationContext(),
                                                        getText(R.string.image_uploaded),
                                                        Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                })

                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {

                                // Error, Image not uploaded
                                progressDialog.dismiss();
                                Toast
                                        .makeText(getApplicationContext(),
                                                getText(R.string.image_failed) + e.getMessage(),
                                                Toast.LENGTH_SHORT)
                                        .show();
                            }
                        })
                        .addOnProgressListener(
                                new OnProgressListener<UploadTask.TaskSnapshot>() {

                                    // Progress Listener for loading
                                    // percentage on the dialog box
                                    @Override
                                    public void onProgress(
                                            UploadTask.TaskSnapshot taskSnapshot)
                                    {
                                        double progress
                                                = (100.0
                                                * taskSnapshot.getBytesTransferred()
                                                / taskSnapshot.getTotalByteCount());
                                        progressDialog.setMessage(
                                                getString(R.string.progress)
                                                        + (int)progress + "%");
                                    }
                                });
                //submit_addition();

        }}
        else{

                Toast
                        .makeText(getApplicationContext(),
                                getString(R.string.error_img_form),
                                Toast.LENGTH_SHORT)
                        .show();

        }}
    }

    public void add_to_supermarkets(Products product,String supermarket_id){
        database = FirebaseDatabase.getInstance();
        myRef2 = database.getReference("supermarket"+supermarket_id+ "/products/product" + product.getProduct_id());
        myRef2.child("stock").setValue(0);

    }


    }



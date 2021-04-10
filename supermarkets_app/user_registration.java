package com.p17107.supermarkets_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class user_registration extends AppCompatActivity {
    EditText email,username,password,confirm_password;
    FirebaseAuth mAuth;
    LanguageHandler languageHandler=new LanguageHandler(this);
    ImageView register_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        email=findViewById(R.id.editTextTextEmailAddress3);
        username=findViewById(R.id.editTextTextPersonName11);
        password=findViewById(R.id.editTextTextPassword3);
        confirm_password=findViewById(R.id.editTextTextPassword4);
        register_img=findViewById(R.id.imageView13);
        register_img.setImageResource(R.drawable.register);
        languageHandler.loadLocale();
        mAuth=FirebaseAuth.getInstance();
    }

    public void register (View view){
        if (!email.getText().toString().isEmpty() && !username.getText().toString().isEmpty() &&
                !password.getText().toString().isEmpty() && !confirm_password.getText().toString().isEmpty()){
            if (password.getText().toString().equals(confirm_password.getText().toString())){
                mAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),getString(R.string.register_success),Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getApplicationContext(),view_supermarkets.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getApplicationContext(),getString(R.string.register_failed),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else {
                Toast.makeText(this,getString(R.string.passwords_do_not_match),Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this,getString(R.string.add_all_fields_message),Toast.LENGTH_SHORT).show();
        }
    }

    }

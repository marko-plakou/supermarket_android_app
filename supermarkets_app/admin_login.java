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
import com.google.firebase.auth.FirebaseUser;

public class admin_login extends AppCompatActivity {
    EditText editText1,editText2;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    Admin admin=new Admin();
    ImageView login_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        editText1=findViewById(R.id.editTextTextEmailAddress);
        editText2=findViewById(R.id.editTextTextPassword);
        mAuth = FirebaseAuth.getInstance();
        login_img=findViewById(R.id.imageView9);
        login_img.setImageResource(R.drawable.signin);
    }

    public void admin_login(View view){


        mAuth.signInWithEmailAndPassword(editText1.getText().toString(),editText2.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            currentUser = mAuth.getCurrentUser();
                            if(admin.check_admin_login(currentUser.getUid())){
                                Toast.makeText(getApplicationContext(),getString(R.string.auth_success),Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(getApplicationContext(),admin_menu.class);
                                startActivity(intent);
                            }
                        }else{Toast.makeText(getApplicationContext(),getString(R.string.auth_failed),Toast.LENGTH_SHORT).show();}
                    }});






    }


}
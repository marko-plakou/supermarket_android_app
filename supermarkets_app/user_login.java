package com.p17107.supermarkets_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static java.util.Locale.US;

public class user_login extends AppCompatActivity {
    EditText email,password;
    FirebaseAuth mAuth;
    LanguageHandler languageHandler=new LanguageHandler(this);
    ImageView user_login_img,flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        languageHandler.loadLocale();
        email=findViewById(R.id.editTextTextEmailAddress2);
        password=findViewById(R.id.editTextTextPassword2);
        user_login_img=findViewById(R.id.imageView11);
        flag=findViewById(R.id.imageView12);
        flag.setImageResource(R.drawable.greek);
        user_login_img.setImageResource(R.drawable.user_login);
        mAuth=FirebaseAuth.getInstance();
    }
    @Override
    protected void onResume() {
        super.onResume();
        check_flag();
        languageHandler.loadLocale();
    }
    protected void on(){}


    public void user_login(View view){
        if (!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
            mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), getString(R.string.auth_success), Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(getApplicationContext(),view_supermarkets.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), getString(R.string.auth_failed), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else {
            Toast.makeText(getApplicationContext(),getString(R.string.add_all_fields_message),Toast.LENGTH_SHORT).show();
        }
    }
    public void user_registration(View view){
        startActivity(new Intent(this,user_registration.class));
    }
    public void admin_login(View view){
        startActivity(new Intent(this,admin_login.class));
    }

    public void change_language(View view){

        languageHandler.changeLocale();
        check_flag();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
    public void check_flag(){
        SharedPreferences preferences=getApplicationContext().getSharedPreferences("Settings",getApplicationContext().MODE_PRIVATE);
        String language=preferences.getString("My_Lang","");
        if(language.equals("en")){
            flag.setImageResource(R.drawable.english);
        }
        else{flag.setImageResource(R.drawable.greek);}
    }

}
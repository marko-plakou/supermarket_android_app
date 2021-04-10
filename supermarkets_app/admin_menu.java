package com.p17107.supermarkets_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class admin_menu extends AppCompatActivity {
    private static final int REC_RESULT = 653;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);
    }

    public void view_products(View view){
        Intent intent=new Intent(getApplicationContext(),view_supermarkets.class);
        startActivity(intent);
    }
    public void edit_stock(View view){
        Intent intent=new Intent(getApplicationContext(),edit_stock.class);
        startActivity(intent);
    }
    public void edit_products(View view){
        Intent intent=new Intent(getApplicationContext(),edit_products_menu.class);
        startActivity(intent);
    }



    public  void voice_command(View view){
        Toast.makeText(this, getString(R.string.voice_command_msg1), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,getString(R.string.voice_command_msg2));
        startActivityForResult(intent,REC_RESULT);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {//SEARCHING THE USER'S VOICE COMMANDS
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REC_RESULT && resultCode==RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            if (matches.contains("ένα")||matches.contains("1")||matches.contains("one")) {
                Intent intent=new Intent(getApplicationContext(),view_supermarkets.class);


                startActivity(intent);
            }
            else if(matches.contains("δύο")||matches.contains("2")||matches.contains("two")){Intent intent=new Intent(getApplicationContext(),edit_products_menu.class);


                startActivity(intent);}

            else if(matches.contains("τρία")||matches.contains("3")||matches.contains("three")){Intent intent=new Intent(getApplicationContext(),edit_stock.class);

            startActivity(intent);}

            else{
                Toast.makeText(this, getString(R.string.voice_command_error), Toast.LENGTH_SHORT).show();}

        }

    }

}
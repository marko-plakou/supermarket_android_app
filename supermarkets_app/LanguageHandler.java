package com.p17107.supermarkets_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

import java.util.Locale;

public class LanguageHandler {
    Context context;

    public LanguageHandler(Context context){
        this.context=context;
    }

    public void setLocale(String lang){
        Locale locale=new Locale(lang);
        Configuration configuration=context.getResources().getConfiguration();
        DisplayMetrics displayMetrics=context.getResources().getDisplayMetrics();
        configuration.setLocale(locale);
        //noinspection deprecation
        context.getResources().updateConfiguration(configuration,displayMetrics);
        SharedPreferences.Editor editor=context.getSharedPreferences("Settings",context.MODE_PRIVATE).edit();
        editor.putString("My_Lang",lang);
        editor.apply();
    }

    public void loadLocale(){
        SharedPreferences preferences=context.getSharedPreferences("Settings",context.MODE_PRIVATE);
        String language=preferences.getString("My_Lang","");
        setLocale(language);
    }

   public void changeLocale(){
       Locale current=context.getResources().getConfiguration().getLocales().get(0);
       if (current.equals(Locale.forLanguageTag("en"))){
           setLocale("gr");
       }
       else{
           setLocale("en");
       }
   }


}

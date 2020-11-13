package com.example.goworkapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    private static final String PREFS = "PREFS";
    private static final String PREFS_ISLOGGED = "Logged_PREF";
    Intent intent1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //l'objectif est de sauvgarder une var qui nous indique l'etat du user
        sharedPreferences = getBaseContext().getSharedPreferences(PREFS, MODE_PRIVATE);



        Thread th = new Thread(){
            public void run()
            {
                try{
                    sleep(3000);
                }catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally {
                    if (IsLogged())
                    {
                        intent1 = new Intent(SplashScreen.this,Accueil.class);

                    }
                    else
                    {

                        intent1 = new Intent(SplashScreen.this,MainActivity.class);

                    }

                    startActivity(intent1);
                    finish();
                }
            }
        };
        th.start();
    }

    public boolean IsLogged()
    {
        if (sharedPreferences.contains(PREFS_ISLOGGED)) {

            boolean islogged = sharedPreferences.getBoolean(PREFS_ISLOGGED, false);
            if (islogged == true)
            {
               return true;

            }
            else
            {
                return false;
            }

        } else {

            sharedPreferences
                    .edit()
                    .putBoolean(PREFS_ISLOGGED, false)
                    .apply();

           return false;
        }
    }
}

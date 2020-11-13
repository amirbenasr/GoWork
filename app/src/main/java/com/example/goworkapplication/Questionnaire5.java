package com.example.goworkapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.Random;

public class Questionnaire5 extends AppCompatActivity {

    Button oui,non;
    TextView txt;


    SharedPreferences sharedPreferences;
    private static final String PREFS = "PREFS";
    private static final String PREFS_IDUSER = "idUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire5);

        sharedPreferences = getBaseContext().getSharedPreferences(PREFS, MODE_PRIVATE);
        int idUser =   sharedPreferences.getInt(PREFS_IDUSER,0);
        String url ="http://sahajamaya.ovh/wsmobile/v2/updateCounter.php?user_id="+idUser;

       ///
        String background_url = "http://sahajamaya.ovh/wsmobile/img/";
        Random randomBg = new Random();
        String random_background = background_url+"i"+(randomBg.nextInt(10)+1)+".jpg";
        System.out.println(random_background);
        Context context = getBaseContext();
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearQ);

        Drawable mDrawable = new BitmapDrawable(getResources(), function_image.getBitmapFromURL(random_background));
        linearLayout.setBackground(mDrawable);
        ImageView imageView= findViewById(R.id.background_img5);
        Ion.with(context)
                .load(random_background)
                .withBitmap()
                .intoImageView(imageView);


        Ion.with(Questionnaire5.this)
                .load(url)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (e != null)
                        {
                            Toast.makeText(Questionnaire5.this, "Erreur ", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            boolean counterIncremented = result.get("reponse").getAsBoolean();
                            if(!counterIncremented){
                                Toast.makeText(Questionnaire5.this, "Erreur Compteur", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

        txt = findViewById(R.id.textquestionnaire5);
        oui = findViewById(R.id.oui5);
        non = findViewById(R.id.non5);

        GetAllQuestion(5);

        oui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Questionnaire5.this,EndActivity.class);
                startActivity(intent);
                finish();
            }
        });

        non.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Questionnaire5.this,EndActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }



    public  void GetAllQuestion(int it )
    {
        String url="http://sahajamaya.ovh/wsmobile/v2/getQuestion.php?idquestion="+it;
        Ion.with(Questionnaire5.this)
                .load(url)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (e != null)
                        {
                            Toast.makeText(Questionnaire5.this, "Erreur ", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {


                            boolean test = result.get("reponse").getAsBoolean();
                            if (test)
                            {
                                String question = result.get("question").getAsString();
                                txt.setText(question);
                            }
                            else
                            {
                                Toast.makeText(Questionnaire5.this, "Problème de connexion", Toast.LENGTH_SHORT).show();
                            }


                        }

                    }

                });  }

}

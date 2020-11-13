package com.example.goworkapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class Questionnaire2 extends AppCompatActivity {
    ArrayList<QuestionnaireClass> arrayList = new ArrayList<>();
    TextView txt;
    Button oui,non;

    SharedPreferences sharedPreferences;
    private static final String PREFS = "PREFS";
    private static final String PREFS_IDUSER = "idUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire2);
        GetAllQuestion(2);

        sharedPreferences = getBaseContext().getSharedPreferences(PREFS, MODE_PRIVATE);
        int idUser =   sharedPreferences.getInt(PREFS_IDUSER,0);

    ///function to load random image

        String background_url = "http://sahajamaya.ovh/wsmobile/img/";
        Random randomBg = new Random();
        String random_background = background_url+"i"+(randomBg.nextInt(10)+1)+".jpg";
        System.out.println(random_background);
        Context context = getBaseContext();
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearQ);


        Drawable mDrawable = new BitmapDrawable(getResources(), function_image.getBitmapFromURL(random_background));
        linearLayout.setBackground(mDrawable);
        ImageView imageView= findViewById(R.id.background_img2);
        Ion.with(context)
                .load(random_background)
                .withBitmap()
                .intoImageView(imageView);
        String url ="http://sahajamaya.ovh/wsmobile/v2/updateCounter.php?user_id="+idUser;
        Ion.with(Questionnaire2.this)
                .load(url)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (e != null)
                        {
                            Toast.makeText(Questionnaire2.this, "Erreur ", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            boolean counterIncremented = result.get("reponse").getAsBoolean();
                            if(!counterIncremented){
                                Toast.makeText(Questionnaire2.this, "Erreur Compteur", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

        txt = findViewById(R.id.textquestionnaire2);
        oui = findViewById(R.id.oui2);
        non = findViewById(R.id.non2);

        oui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Questionnaire2.this,Questionnaire3.class);
                startActivity(intent);
                finish();

            }
        });

        non.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Questionnaire2.this,Questionnaire3.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public Bitmap getBitmapFromURL(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



    public  void GetAllQuestion(int it)
    {
        String url="http://sahajamaya.ovh/wsmobile/v2/getQuestion.php?idquestion="+it;
        Ion.with(Questionnaire2.this)
            .load(url)
            .asJsonObject()
            .setCallback(new FutureCallback<JsonObject>() {
                @Override
                public void onCompleted(Exception e, JsonObject result) {
                    if (e != null)
                    {
                        Toast.makeText(Questionnaire2.this, "Erreur ", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(Questionnaire2.this, "Problème de connexion", Toast.LENGTH_SHORT).show();
                        }


                    }

                }

            });  }

}

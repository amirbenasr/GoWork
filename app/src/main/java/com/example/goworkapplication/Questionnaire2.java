package com.example.goworkapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
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
import java.util.concurrent.ExecutionException;

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

        //AsyncTask middleware pour les transactions background
        ImageAsync myDownloader = new ImageAsync();

        try {
            //executer asynctask et recuperer l'image
            Drawable mDrawable = new BitmapDrawable(getResources(),myDownloader.execute(random_background).get());
            //afficher l'image
            linearLayout.setBackground(mDrawable);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        };

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

    ///ImageAsync class pour executer les fonctionalités en background
    public class ImageAsync extends AsyncTask<String,Void,Bitmap>
    {
        @Override
        protected void onPreExecute() {
            // Show progress dialog
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            //Populate Ui
            super.onPostExecute(bitmap);
        }

        @Override
        protected Bitmap doInBackground(String... params) {

            return  function_image.getBitmapFromURL(params[0]);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            // Show progress update
            super.onProgressUpdate(values);
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

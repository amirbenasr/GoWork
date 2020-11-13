package com.example.goworkapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class Sondage extends AppCompatActivity {

    TextView txt;
    EditText edit;
    Button btn;



    SharedPreferences sharedPreferences;
    private static final String PREFS = "PREFS";
    private static final String PREFS_IDUSER = "idUser";

    int idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sondage);

        sharedPreferences = getBaseContext().getSharedPreferences(PREFS, MODE_PRIVATE);
        int idUser =   sharedPreferences.getInt(PREFS_IDUSER,0);
        String url ="http://sahajamaya.ovh/wsmobile/v2/updateCounter.php?user_id="+idUser;
        Ion.with(Sondage.this)
                .load(url)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (e != null)
                        {
                            Toast.makeText(Sondage.this, "Erreur ", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            boolean counterIncremented = result.get("reponse").getAsBoolean();
                            if(!counterIncremented){
                                Toast.makeText(Sondage.this, "Erreur Compteur", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

        btn = findViewById(R.id.btnSsubmit);
        txt = findViewById(R.id.textSondage);
        edit = findViewById(R.id.editsondage);

        sharedPreferences = getBaseContext().getSharedPreferences(PREFS, MODE_PRIVATE);
        idUser = sharedPreferences.getInt(PREFS_IDUSER, 0);

        GetAllQuestionSondage(1);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = edit.getText().toString();

                SaveReponse(str);

            }
        });




    }

    public void GetAllQuestionSondage(int id)
    {
        String url="http://sahajamaya.ovh/wsmobile/v2/getAllQuestionsSondage.php?idquestion_sondage="+id;

        Ion.with(Sondage.this)
                .load(url)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (e != null)
                        {
                            Toast.makeText(Sondage.this, "Erreur ", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {

                            boolean test = result.get("reponse").getAsBoolean();
                            if (test)
                            {
                                String question_sondage = result.get("question_sondage").getAsString();
                                txt.setText(question_sondage);
                            }
                            else
                            {
                                Toast.makeText(Sondage.this, "Problème de connexion", Toast.LENGTH_SHORT).show();
                            }



                        }

                    }

                });  }



                public void SaveReponse(String rep)
                {

                    String url="http://sahajamaya.ovh/wsmobile/v2/InsertReponse.php?idUser="+idUser+"&Userreponse="+rep.replace(" ","%20");
                    Ion.with(Sondage.this)
                            .load(url)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    if (e != null)
                                    {
                                        Toast.makeText(Sondage.this, "Error "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {

                                        boolean isInserted = result.get("reponse").getAsBoolean();
                                        if (isInserted)
                                        {
                                            Toast.makeText(Sondage.this, "Votre reponse a été envoyé", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(Sondage.this,Accueil.class);
                                            startActivity(intent);
                                        }
                                        else
                                        {
                                            Toast.makeText(Sondage.this, "une Erreur s'est produit", Toast.LENGTH_SHORT).show();
                                        }

                                    }

                                }

                            });


                }


}

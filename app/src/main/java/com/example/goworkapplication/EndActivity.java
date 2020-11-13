package com.example.goworkapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class EndActivity extends AppCompatActivity {

    Button end;
    TextView txt;

    SharedPreferences sharedPreferences;
    private static final String PREFS = "PREFS";
    private static final String PREFS_IDUSER = "idUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        sharedPreferences = getBaseContext().getSharedPreferences(PREFS, MODE_PRIVATE);
        int idUser =   sharedPreferences.getInt(PREFS_IDUSER,0);
        String url ="http://sahajamaya.ovh/wsmobile/v2/updateCounter.php?user_id="+idUser;
        Ion.with(EndActivity.this)
                .load(url)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (e != null)
                        {
                            Toast.makeText(EndActivity.this, "Erreur ", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            boolean counterIncremented = result.get("reponse").getAsBoolean();
                            if(!counterIncremented){
                                Toast.makeText(EndActivity.this, "Erreur Compteur", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

        end = findViewById(R.id.aurevoir);
        txt = findViewById(R.id.txtend);

        GetInstruction(2);

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
            }
        });
    }


    public  void GetInstruction(int it )
    {
        String url="http://sahajamaya.ovh/wsmobile/v2/getFirstInstruction.php?id="+it+"&type=end";
        Ion.with(EndActivity.this)
                .load(url)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (e != null)
                        {
                            Toast.makeText(EndActivity.this, "Erreur ", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {

                            boolean test = result.get("reponse").getAsBoolean();
                            if (test)
                            {
                                String instruction = result.get("instruction").getAsString();
                                txt.setText(instruction);
                            }
                            else
                            {
                                Toast.makeText(EndActivity.this, "Problème de connexion", Toast.LENGTH_SHORT).show();
                            }


                        }

                    }

                });  }
}

package com.example.goworkapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


public class Login extends AppCompatActivity {

    Button Lbtn;
    EditText Lemail, Lpass;

    SharedPreferences sharedPreferences;
    private static final String PREFS = "PREFS";
    private static final String PREFS_ISLOGGED = "Logged_PREF";
    private static final String PREFS_IDUSER = "idUser";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        sharedPreferences = getBaseContext().getSharedPreferences(PREFS, MODE_PRIVATE);

        Lbtn = findViewById(R.id.Lbtn);
        Lemail = findViewById(R.id.Lemail);
        Lpass = findViewById(R.id.Lpass);

        Lbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Lemail.getText().toString();
                String pass = Lpass.getText().toString();
                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass))
                {
                    Users user = new Users(email,pass);
                    UserLogin(user);

                }
                else
                {
                    Toast.makeText(Login.this, "Veuillez Remplir les champs ", Toast.LENGTH_SHORT).show();
                }
            }
        });


        
    }

    public void UserLogin(Users user)
    {
        String url ="http://sahajamaya.ovh/wsmobile/v2/login.php?email="+user.getEmail().replace(" ","%20")+"&password="+user.getPassword().replace(" ","%20");
        Ion.with(Login.this)
                .load(url)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (e != null)
                        {
                            Toast.makeText(Login.this, "Erreur ", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {

                            boolean isInserted = result.get("reponse").getAsBoolean();

                            if (isInserted) {
                                int idUser = result.get("idUser").getAsInt();
                                sharedPreferences
                                        .edit()
                                        .putBoolean(PREFS_ISLOGGED, true)
                                        .putInt(PREFS_IDUSER,idUser)
                                        .apply();

                                String url ="http://sahajamaya.ovh/wsmobile/v2/updateCounter.php?user_id="+idUser;
                                Ion.with(Login.this)
                                        .load(url)
                                        .asJsonObject()
                                        .setCallback(new FutureCallback<JsonObject>() {
                                            @Override
                                            public void onCompleted(Exception e, JsonObject result) {
                                                if (e != null)
                                                {
                                                    Toast.makeText(Login.this, "Erreur ", Toast.LENGTH_SHORT).show();
                                                }
                                                else {
                                                    boolean counterIncremented = result.get("reponse").getAsBoolean();
                                                    if(!counterIncremented){
                                                        Toast.makeText(Login.this, "Erreur Compteur", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }
                                        });


                                Intent i = new Intent(Login.this,Accueil.class);
                                startActivity(i);


                            }else{
                                Toast.makeText(Login.this, "verifier vos informations", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }

                });




    }
}

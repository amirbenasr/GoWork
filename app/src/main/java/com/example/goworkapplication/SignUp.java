package com.example.goworkapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    Button Sbtn;
    EditText Snom,Sprenom,Semail,Spass,Scnf,Sdate,Sphone;
    Spinner Setude,Slieux,Sgenre;
    String item,lieux,genre;
    Calendar c;


    SharedPreferences sharedPreferences;
    private static final String PREFS = "PREFS";
    private static final String PREFS_ISLOGGED = "Logged_PREF";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);



        Sbtn = findViewById(R.id.Sbtn);
        Snom = findViewById(R.id.Snom);
        Sprenom = findViewById(R.id.Sprenom);
        Semail = findViewById(R.id.Semail);
        Setude = findViewById(R.id.Setude);
        Sdate = findViewById(R.id.Sdate);
        Slieux = findViewById(R.id.Slieux);
        Sgenre = findViewById(R.id.Sgenre);
        Sphone = findViewById(R.id.Sphone);
        Spass = findViewById(R.id.Spass);
        Scnf = findViewById(R.id.Scnfpass);
        Sdate.setFocusable(false);

        sharedPreferences = getBaseContext().getSharedPreferences(PREFS, MODE_PRIVATE);


        //SpinnerEtude text
        Setude.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                item = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //SpinnerLieux text
        Slieux.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                lieux = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //SpinnerGenre text
        Sgenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                genre = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });







        //DATE PICKER
         c = Calendar.getInstance();
         final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, monthOfYear);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        Sdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(SignUp.this, date, c
                        .get(Calendar.YEAR), c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });




        Sbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String nom = Snom.getText().toString();
            String prenom = Sprenom.getText().toString();
            String email = Semail.getText().toString();
            //String lieux = Slieux.getText().toString();
            String date = Sdate.getText().toString();
            String phone = Sphone.getText().toString();
            String pass = Spass.getText().toString();
            String cnf = Scnf.getText().toString();

            if(!TextUtils.isEmpty(nom) && !TextUtils.isEmpty(prenom) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(cnf))
            {
                if (!isValidEmaillId(email) || pass.length() < 7 || !isValidPhone(phone) || !pass.equalsIgnoreCase(cnf))
                {
                    if(!isValidEmaillId(email))
                        Toast.makeText(SignUp.this, "L'email est incorrecte ", Toast.LENGTH_SHORT).show();
                    if(pass.length()<7)
                        Toast.makeText(SignUp.this, "le mot de passe doit contenir au moin 8 caractéres", Toast.LENGTH_SHORT).show();
                    if(!isValidPhone(phone))
                        Toast.makeText(SignUp.this, "verifier votre numero de téléphone", Toast.LENGTH_SHORT).show();
                    if(!pass.equalsIgnoreCase(cnf))
                        Toast.makeText(SignUp.this, "les mots de passe ne sont pas confondu !", Toast.LENGTH_SHORT).show();

                }
                else

                if(isValidEmaillId(email) && pass.equalsIgnoreCase(cnf) && isValidPhone(phone) && pass.length()>7 )
                {
                    Users user = new Users(genre,nom,prenom,email,item,lieux,phone,date,pass);
                    ADDUser(user);
                }
            }
            else
            {
                Toast.makeText(SignUp.this, "Veuillez completez le formulaire ", Toast.LENGTH_SHORT).show();
            }


            }
        });



    }

    public void ADDUser(Users user){

        String url ="http://sahajamaya.ovh/wsmobile/v2/register.php?genre="+user.getGenre()+"&nom="+user.getNom().replace(" ","%20")+"&prenom="+user.getPrenom().replace(" ","%20")+"&email="+user.getEmail().replace(" ","%20")+"&etude="+user.getEtude().replace(" ","%20")+"&lieux="+user.getLieux().replace(" ","%20")+"&phone="+user.getPhone()+"&date="+user.getDate()+"&password="+user.getPassword().replace(" ","%20");
        Ion.with(SignUp.this)
                .load(url)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (e != null)
                        {
                            Toast.makeText(SignUp.this, "Erreur ", Toast.LENGTH_SHORT).show();
                        }
                       else
                        {

                            boolean isInserted = result.get("reponse").getAsBoolean();
                            if (isInserted)
                            {
                                Toast.makeText(SignUp.this, "inscription avec succès", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(SignUp.this,Login.class);
                                startActivity(i);

                            }else{
                                Toast.makeText(SignUp.this, "cette email est deja utilisé", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }

                });

    }

    private void updateLabel()
    {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        Sdate.setText(sdf.format(c.getTime()));
    }

    private boolean isValidEmaillId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }
    public boolean isValidPhone(CharSequence phone) {
        if (phone.length()<8) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(phone).matches();
        }
    }

}

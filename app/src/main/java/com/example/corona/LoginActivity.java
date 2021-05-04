package com.example.corona;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {  //klasės pradžia

    @Override
    protected void onCreate(Bundle savedInstanceState) { //Funkcijos pradžia
        super.onCreate(savedInstanceState); //tuscio lango sukurimas
        setContentView(R.layout.activity_login); //suteik tusciam langui si vaizda (kodas susiejamas su vaizdu, vaizda is xml uzdedame ant tuscio lango), jei nebutu sitos eilutes, nereiketu ir pries tai esancios

        EditText usernameET = findViewById(R.id.username); //susiejamas elementas vaizde su kintamuoju kode; EditText yra tipas, o username - kintamojo pavadinimas; pagal id is to elemento issiimsim tai, ka vartotojas suvede; kabliataskis zymi sakinio pabaiga
        EditText passwordET = findViewById(R.id.password);
        Button kaipNoriuTaipVadinuBT = findViewById(R.id.login);
        Button RegisterBT = findViewById(R.id.register);

        CheckBox rememberMe = findViewById(R.id.remember_me);

        User user = new User(LoginActivity.this);   //turi duomenis

        rememberMe.setChecked(user.isRememberedForLogin()); //patikriname, ar paskutini karta vartotojas buvo pazymejes remember me (kokia paskutini karta buvo suteikta reiksme (true arba fase))

        if (rememberMe.isChecked()) {    //patikriname is karto uzkrovus langa
            usernameET.setText(user.getUsernameForLogin(), TextView.BufferType.EDITABLE);   //editText viduje pateiksime
            //bus galima redaguoti(pasikeisti) del EDITABLE
            passwordET.setText(user.getPasswordForLogin(), TextView.BufferType.EDITABLE);
        } else {     //jeigu vartotojas nepazymejo checkbox remember me
            usernameET.setText("", TextView.BufferType.EDITABLE);   //nes is SharedPreferences interf. galima paiimti is visur
            passwordET.setText("", TextView.BufferType.EDITABLE);
        }

        // Cia bus aprasomas kodas, susijes su mygtuko Login paspaudimu
        kaipNoriuTaipVadinuBT.setOnClickListener(new View.OnClickListener() {   //new - kuriamas naujas objektas
            @Override   //paspaudus mygtuka
            public void onClick(View v) {   // onClick pradzia paspaudus mygtuka

                // cia bus rasomas kodas, kuris bus vykdomas ant paspausto mygtuko
                String usernameStr = usernameET.getText().toString(); //String visuomet is didziosios raides
                String passwordStr = passwordET.getText().toString();

                usernameET.setError(null);    //issivalome klaidu zurnala (username)
                passwordET.setError(null);  //issivalome klaidu zurnala (password)

                if (Validation.isUsernameValid(usernameStr) && Validation.isPasswordValid(passwordStr)) {    //if zodziu prasideda salyga, turi buti visada skliausteliuose; jeigu bus validus duomenys, pereisim is vieno lango i kita
                    User user = new User(LoginActivity.this); //sukonstruotas naujas objektas

                    //issaugoti SharedPref. duomenis

                    user.setUsernameForLogin(usernameStr);
                    user.setPasswordForLogin(passwordStr);

                    if (rememberMe.isChecked()) { //ar pazymejome checkbox
                        user.setRemembermeKeyForLogin(true);    //norime ji issaugoti, kad irasytu i SharedPreferences
                    } else {
                        user.setRemembermeKeyForLogin(false);   //kad kita karta nebutu irasyta
                    }

                    Intent goToSearchActivity = new Intent(LoginActivity.this, SearchActivity.class);   // is kur (pirmas parametras) i kur (antras parametras); i new Intent() labai nesigilinti kol kas, taip tiesiog reikia. This reiskia, jog siame lange esu, gali buti net be zodziu LoginActivity. o class reiskia, jog i kuri eisime langa
                    startActivity(goToSearchActivity);
                } else {  //be skliausteliu () reiskia "visais kitais atvejais", nes mes tu visu atveju negalime nurodyti skliausteliuose; ir cia salyga, todel skliausteliu nereikia
                    usernameET.setError(getResources().getString(R.string.login_invalid_credentials));
                    usernameET.requestFocus();
                }

            }   // onClick pabaiga
        }); //mygtuko paspaudimo funkcijos pabaiga - visada bus sie trys simboliai su mygtuko paspaudimo funkcijos pabaiga

        // Cia bus aprasomas kodas, susijes su mygtuko Register paspaudimu

        RegisterBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent goToRegisterActivity = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(goToRegisterActivity);
            }
        });

    }   //Funkcijos pabaiga

}   //klasės pabaiga
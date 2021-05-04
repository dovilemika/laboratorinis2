package com.example.corona;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.corona.Validation.isEmailValid;

public class RegisterActivity extends AppCompatActivity {   //klases pradzia

    @Override
    protected void onCreate(Bundle savedInstanceState) {    //funkcijos pradzia
        super.onCreate(savedInstanceState); //tuscio lango sukurimas
        setContentView(R.layout.activity_register); //suteik tusciam langui si vaizda (kodas susiejamas su vaizdu),
        // vaizda is xml uzdedame ant tuscio lango, jei nebutu sitos eilutes, nereiketu ir pries tai esancios

        EditText usernameET = findViewById(R.id.username);  //susiejamas elementas vaizde su kintamuoju kode; EditText yra tipas, o username - kintamojo pavadinimas; pagal id is to elemento issiimsim tai, ka vartotojas suvede; kabliataskis zymi sakinio pabaiga
        EditText emailET = findViewById(R.id.email);
        EditText passwordET = findViewById(R.id.password);
        Button RegisterBT = findViewById(R.id.register);

        //cia bus aprasomas kodas, susijes su Register mygtuko paspaudimu
        RegisterBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   //onClick pradzia
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);   //kad pasleptu emuliatoriaus klaviatura paspaudus register mygtuka
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                // cia bus rasomas kodas, kuris bus vykdomas ant paspausto mygtuko
                String usernameStr = usernameET.getText().toString();   //visuomet is didziosios raides
                String emailStr = emailET.getText().toString();
                String passwordStr = passwordET.getText().toString();

                usernameET.setError(null);  //issivalome klaidu zurnala
                emailET.setError(null);
                passwordET.setError(null);

                if (Validation.isUsernameValid(usernameStr) && Validation.isEmailValid(emailStr) &&
                        Validation.isPasswordValid(passwordStr)) {  //if zodziu prasideda salyga, turi buti visada skliausteliuose; jeigu bus validus duomenys, pereisim is vieno lango i kita

                    Toast.makeText(RegisterActivity.this, "Prisijungimo vardas: " +
                            usernameStr + "\n" + "El.paštas: " + emailStr + "\n" + "Slaptažodis: " +
                            passwordStr, Toast.LENGTH_LONG).show();   //tai, kas yra skliausteliuose, vadinama funkcijos parametrais, siuo atveju 3 parametrai. jeigu sklaisutai tusti, funkcija be parametro
                    // pirmas parametras nusako, i kuria vieta spausdinti teksta (context - esamas langas), antras - koki teksta spausdinti (gali buti tik viena eilute, bet i ja galime suklijuoti daug eiluciu),
                    // trecias - kiek laiko ta teksta rodyti ekrane

                    Intent goToLoginActivity = new Intent(RegisterActivity.this, LoginActivity.class);  //is kurio parametro - i kuri
                    startActivity(goToLoginActivity);

                } else {  //jeigu nebus validus, ismesime i ekrana klaida. be skliausteliu () reiskia "visais kitais atvejais", nes mes tu visu atveju negalime nurodyti skliausteliuose;
                    if (!Validation.isUsernameValid(usernameStr)) {
                        usernameET.setError(getResources().getString(R.string.register_invalid_username));
                        usernameET.requestFocus();
                    }
                    if (!Validation.isEmailValid(emailStr)) {
                        emailET.setError(getResources().getString(R.string.register_invalid_email));
                        emailET.requestFocus();
                    }
                    if (!Validation.isPasswordValid(passwordStr)) {
                        passwordET.setError(getResources().getString(R.string.register_invalid_password));
                        passwordET.requestFocus();
                    }
                }
            }   //onClick pabaiga
        }); //mygtuko paspaudimo funkcijos pabaiga - visada bus sie trys simboliai su mygtuko paspaudimo funkcijos pabaiga

    }   //funkcijos pabaiga
}   //klases pabaiga

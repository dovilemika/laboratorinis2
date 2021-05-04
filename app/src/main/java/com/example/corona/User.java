package com.example.corona;

import android.content.Context;
import android.content.SharedPreferences;

public class User { //visada is didziosios, nes klase
    //1.klases kintamieji(dar vadinami argumentais, pozymiais)
    private String username;
    private String password;
    private String email;

//    toliau yra musu raktai
    private SharedPreferences sharedPreferences;
    private static final String PREFERENCES_PACKAGE_NAME = "com.example.corona";
    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";
    private static final String REMEMBERME_KEY = "rememberMe";


    //2.konstruktorius
    public User(){
//        bevardis konstruktorius (galima jo ir nekurti - sukuriamas automatiskai)
    }
    //konstruktorius (jau panaudotas prisijungimo lange LoginActivity)
    public User (Context context) {
        this.sharedPreferences = context.getSharedPreferences(User.PREFERENCES_PACKAGE_NAME, Context.MODE_PRIVATE);
    }

//    sis konstruktorius skirtas registracijos langui, perduosime tris parametrus:
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    //3.geteriai, seteriai

    //    geteris atitinka grazinancia funkcija be parametru:
    public String getUsernameForRegistration() {
        return username;
    }
    //    seteris atitinka negrazinancia funkcija su parametrais:
    public void setUsernameForRegistration(String username) { this.username=username; }

    public String getPasswordForRegistration() {
        return password;
    }

    public void setPasswordForRegistration(String password) {
        this.password=password;
    }

    public String getEmailForRegistration() {
        return email;
    }

    public void setEmailForRegistration(String email) {
        this.email = email;
    }

//    toliau viskas su shared preferences, nes login prisijungimo duomenis turi issaugoti musu narsykle. Registracijos atveju duomenys saugomi ne musu narsykleje, o nukeliauja i DB.



    public String getUsernameForLogin() {
        return this.sharedPreferences.getString(USERNAME_KEY, "");  //tarp kabuciu tuscia, toks formatas, bet grazins ne tuscia, o tai, ka irasys vartotojas (get atitinka grazinancia funkcija be parametru)
    }

    public void setUsernameForLogin(String username) {
        this.sharedPreferences.edit().putString(USERNAME_KEY, username).commit();
    }

    public String getPasswordForLogin() {
        return this.sharedPreferences.getString(PASSWORD_KEY, "");
    }

    public void setPasswordForLogin(String password) {  //set atititnka negrazinancia funkcija su parametrais
        this.sharedPreferences.edit().putString(PASSWORD_KEY, password).commit();
    }

//    toliau reiksme Boolean, o ne String, nes tai checkbox
    public boolean isRememberedForLogin() {
        return this.sharedPreferences.getBoolean(REMEMBERME_KEY, false);
    }

    public void setRemembermeKeyForLogin(boolean rememberMeKey) {
        this.sharedPreferences.edit().putBoolean(REMEMBERME_KEY, rememberMeKey).commit();
    }

}

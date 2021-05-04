package com.example.corona;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    public static final String USERNAME_REGEX_PATTERN = "^[a-zA-Z0-9]{3,20}$";  //per static prieina; public turi buti, o ne private; ir final turi buti - final galutinis, nebekeicia; tai globalus kintamasis, nes yra klases viduje, o ne funkcijoje, jis bus visur matomas
    public static final String PASSWORD_REGEX_PATTERN = "^[a-zA-Z0-9.!@_]{5,20}$";
//    "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
//    public static final String EMAIL_REGEX_PATTERN = "^[a-zA-Z0-9@._-]{10,50}$";
    public static final String EMAIL_REGEX_PATTERN =  "^[A-Za-z0-9._-]+@[A-Za-z0-9]+.[A-Za-z]{2,50}$";
    //"^([a-z0-9._-]+){10,50}@([a-z0-9._-]+){2,10}.[a-z]{2,}$" - simbolių skaičių reikia nurodyti trijose vietose: iki @, tarp @ ir taškelio, ir po taškelio.
    //reikalauja įvesti @, kadangi ji nėra laužtinių skliaustų rėmuose.


    public static boolean isUsernameValid(String username){ //arba true (1), arba false (0); paima parametrus, kur skliausteliuose. Siuo atveju metoda aprasome, todel tarp skliaustu rasome tipa ir kintamojo pavadinima. Si eilute yra funkcijos antraste (dar vadinama prototipu)
        Pattern pattern = Pattern.compile(USERNAME_REGEX_PATTERN);  //sukuriamos username validacijai taisyklės pagal nurodyta sablona; musu Stringas perduodamas bibliotekos tam tikram metodui, kuris pagal musu nurodytas tam stringui taisykles sukuria sablona
        Matcher matcher = pattern.matcher(username);    //pagal anksciau sukurtas taisykles palyginami vartotojo ivesti duomenys (username); matcher - atitikimas. Tai kaip svarstykles - susikurta sablona sveriame su tuo, ka vartotojas ivede
        boolean isUsernameValid = matcher.find(); //jeigu atitinka, grazins true, jeigu ne - false
        return isUsernameValid;
    }

    public static boolean isPasswordValid(String password){
        Pattern pattern = Pattern.compile(PASSWORD_REGEX_PATTERN);
        Matcher matcher = pattern.matcher(password);
        boolean isPasswordValid = matcher.find();
        return isPasswordValid;
    }

    public static boolean isEmailValid(String email){
        Pattern pattern = Pattern.compile(EMAIL_REGEX_PATTERN);
        Matcher matcher = pattern.matcher(email);
        boolean isEmailValid = matcher.find();
        return isEmailValid;
    }
}
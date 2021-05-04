package com.example.corona;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSON {

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));    //nuskaityti is kazkokiu srautu
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally { //uzdaro ivedimo srautus
            is.close();
        }
    }

    public static ArrayList<Margarita> getList(JSONArray jsonArray) throws JSONException{
        ArrayList<Margarita> margaritaList = new ArrayList<Margarita>();
        //isimti data is JSON ir issaugoti corona objektu sarase (coronaList)
        for (int i=0; i<jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Margarita margarita = new Margarita(
              //public Margarita(int id, String name, String tags, String category, String glass)
                    jsonObject.getString("idDrink"),
                    jsonObject.getString("strDrink"),
                    jsonObject.getString("strTags"),
                    jsonObject.getString("strCategory"),
                    jsonObject.getString("strGlass")
            );
            margaritaList.add(margarita);
        }

        return margaritaList;
    }

   // public static JSONArray getJSONArray(JSONObject jsonObject) throws JSONException{
        //pasalinama is JSON visa nereikalinga informacija (metaduomenys), paliekant tik covid19Stats masyva
      //  int jsonLength = jsonObject.toString().length();
       // String covid19Stats = "{" + jsonObject.toString().substring(96, jsonLength) + "}"; //substring iskerpa dali simboliu is eilutes, prades nuo 96 iki pacio galo

        // string i JSON object
       // JSONObject jsonObject1 = new JSONObject(covid19Stats);

        //JSON Object i JSON Array
     //   JSON json;
     //   JSONArray jsonArray = json.getJSONArray("drinks");

   //     return jsonArray;
  //  }

    public static ArrayList<Margarita> getMargaritaByQuery(ArrayList<Margarita> margaritaArrayList, String coctailName) {
        ArrayList<Margarita> margaritaListByCountry = new ArrayList<Margarita>();
        for (Margarita margarita : margaritaArrayList) { //kaireje sukuriamas tos klases objektas, per kurios sarasa itegruojame (desineje)
            if (margarita.getName().contains(coctailName)) { //contains vykdo paieska!!
                margaritaListByCountry.add(margarita);
            }
        }
        return margaritaListByCountry;
    }
}




//public static ArrayList<Margarita> getMargaritaByQuery(ArrayList<Margarita> margaritaList, String query) {
   // }
//}

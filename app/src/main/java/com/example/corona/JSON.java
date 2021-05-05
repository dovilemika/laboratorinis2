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

public class JSON { //is URL gaunamas json ir atspausdinamas

    private static String readAll(Reader rd) throws IOException { //vadinasi metodas
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

// ilgas tekstas is API NUSKAITOMAS I String o is ten konvertuojamas i json oblejta
    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException { //vadinasi metodas, kuriuo kreipiames i url ir mums grazina json
        InputStream is = new URL(url).openStream();
        try { //i try dedamas kodas kuriame gali buti klaida
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));    //nuskaityti is kazkokiu srautu (IS URL LINKO)
            String jsonText = readAll(rd); //grazinmas stringas
            JSONObject json = new JSONObject(jsonText); //formuojamas json objektas
            return json; //grazina json
        } finally { //uzdaro ivedimo srautus. net jei klaida - srautas uzdaromas
            is.close();
        }
    }
// sis metodas paims json objekta ir grazins masyva. istruakiame tik  mums reikiama info
    public static ArrayList<Margarita> getList(JSONArray jsonArray) throws JSONException{ //metodo antraste, grazins sarasa, ArrayaList o ne visa json
        ArrayList<Margarita> margaritaList = new ArrayList<Margarita>(); //sukureme klase, kur norime patalpintiklases Margarita objektus. margaritaList tai saraso pavadinimas
        //isimti data is JSON ir issaugoti corona objektu sarase (coronaList)
        for (int i=0; i<jsonArray.length(); i++) { //ciklas, kuris rodo tris salygas
            JSONObject jsonObject = jsonArray.getJSONObject(i); //cia rodome, kad eisime per visa sarasa
            Margarita margarita = new Margarita( //margarita konstruktorius susideda is 5 elementu, juos cia trauksime
              //public Margarita(int id, String name, String tags, String category, String glass)
                    jsonObject.getString("idDrink"), //idDrink rasome identiskai kaip json duomenyse parasyta
                    jsonObject.getString("strDrink"),
                    jsonObject.getString("strTags"),
                    jsonObject.getString("strCategory"),
                    jsonObject.getString("strGlass")
            );
            margaritaList.add(margarita); //eis per visus json sarase esancius objektus, juos paims ir konstruos Margaruta klases objekta
        }

        return margaritaList;
    }

    public static JSONArray getJSONArray(JSONObject json) throws JSONException {//metodas
        JSONArray jsonArray = json.getJSONArray("drinks");
        return jsonArray; //jsonArray paims getList
    }
    //is visi saraso istrauks tik mums reikiamus gerimus
    public static ArrayList<Margarita> getMargaritaListByQuery(ArrayList<Margarita> margaritaArrayList, String name) { //metodas, noresim gauti sarasa pagal kokteilio pavadinima. F-ja paims du parametrus - ArrayList, kokteil. pav. Grazins arrayList
        ArrayList<Margarita> margaritaListByQuery = new ArrayList<Margarita>();
        //eis per visa sarasa ir formuos reikiamus gerimus
        for (Margarita margarita : margaritaArrayList) {
            if (margarita.getName().contains(name)) { //contains metodas yra vienas is String metodu, kuris iesko zodzio dalies is ivesto getName
                margaritaListByQuery.add(margarita);
            }
        }

        return margaritaListByQuery;
    }
}







    // _________________________________________________________________________________________________________________________________________________________

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

  //  public static ArrayList<Margarita> getMargaritaByQuery(ArrayList<Margarita> margaritaArrayList, String coctailName) {
      //  ArrayList<Margarita> margaritaListByCountry = new ArrayList<Margarita>();
      //  for (Margarita margarita : margaritaArrayList) { //kaireje sukuriamas tos klases objektas, per kurios sarasa itegruojame (desineje)
       //     if (margarita.getName().contains(coctailName)) { //contains vykdo paieska!!
        //        margaritaListByCountry.add(margarita);
 //           }
//        }
 //       return margaritaListByCountry;
  //  }
//}




//public static ArrayList<Margarita> getMargaritaByQuery(ArrayList<Margarita> margaritaList, String query) {
   // }
//}

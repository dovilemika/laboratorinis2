package com.example.corona;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    public static final String MARGARITA_API = "https://www.thecocktaildb.com/api/json/v1/1/search.php?s=Margarita";
    private ArrayList<Margarita> margaritaList = new ArrayList<Margarita>();
    //pasirasysim globalius kintamuosius:
    private RecyclerView recyclerView; //korteliu vaizdas
    private Adapter adapter;  // tarpininkas tarp search activity ir xml
    private ArrayList<Margarita> margaritaList = new ArrayList<Margarita>();


    private SearchView searchView = null; // gal nereikia private

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //bus paleidziama nauja gija (thread) - JSONo nuskaitymui is API
        AsyncFetch asyncFetch = new AsyncFetch();   //kuriames klase
        asyncFetch.execute();
    }

    // cia ikopinom mokytojo koda =========================================================
    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //suteikiam vartotojui galimybe irasyti paieskoje
        // adds item to action bar
         getMenuInflater().inflate(R.menu.search, menu);
         //Get Search item from action bar and Get Search service
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) SearchActivity.this.getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(SearchActivity.this.getComponentName()));
            searchView.setIconified(false);
        }
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //paspaudus atsiranda paieskos simbolis
        return super.onOptionsItemSelected(item);
    }


    @Override

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY); //tai, ka vartyotojas suvede i meniu juosta, jei ives pvz Italija tai QUERY BUS Italija
            if (searchView != null) {
                searchView.clearFocus(); //isvalo blyksinti kursoriu
            }

            //is visu kokteiliu saraso sukuriamas sarasas pagal vertotoja ieskoma kokteili (QUERY)
            ArrayList<Margarita> margaritaListByCountry = JSON.getMargaritaByQuery(margaritaList, query);

            if (margaritaList.size() == 0) {
                Toast.makeText(this, getResources().getString(R.string.search_no_results) + query, Toast.LENGTH_SHORT).show();
            }

            //duomenu perdavimas adapteriui ir RecyclerView sukurimas
            recyclerView = (RecyclerView) findViewById(R.id.margarita_list);
            adapter = new Adapter(SearchActivity.this, margaritaList);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));

        }
    }



    private class AsyncFetch extends AsyncTask<String, String, JSONObject> {    //privati klase, ji yra vidine; lygiagrecios klases gali buti privacios
        ProgressDialog progressDialog = new ProgressDialog(SearchActivity.this);

        @Override
        protected void onPreExecute() { //sis metodas bus vykdomas pries do it background. Paprasysime vartotojo palaukti, kol gausime duomenis is API (bus besisukantis vaizdas)
            super.onPreExecute();
            progressDialog.setMessage(getResources().getString(R.string.search_loading_data));
            progressDialog.setCancelable(false);    //turi islaukti kol gris kazkokia informacija
            progressDialog.show();  //kad matytu vaizda
        }

        @Override   // Jis skirtas gavimui JSON is API
        protected JSONObject doInBackground(String... strings) {    //jis bus vykdomas tuo metu, kai vartotojas matys besisukanti (progress) dialoga.
            try {
                JSONObject jsonObject = JSON.readJsonFromUrl(MARGARITA_API); //sioje vietoje perduosime URL
                return jsonObject;
            } catch (IOException e) {
                Toast.makeText(
                        SearchActivity.this,
                        getResources().getString(R.string.search_error_reading_data) + e.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            } catch (JSONException e) {
                Toast.makeText(
                        SearchActivity.this,
                        getResources().getString(R.string.search_error_reading_data) + e.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
            return null;
        }
      //  }   //doInBackground pabaiga

      //  @Override
     //   protected void onPostExecute(JSONObject jsonObject) {    //execute atitinka doInBackgroundA
      //      progressDialog.dismiss();

          //  int statusCode = 0;
        //      try {
          //      statusCode = jsonObject.getInt("statusCode");
           // } catch (JSONException e) {
          //      Toast.makeText(
            //            SearchActivity.this,
         //               getResources().getString(R.string.search_error_reading_data) + e.getMessage(),
          //              Toast.LENGTH_LONG
            //    ).show();
          //  }
         //   if(statusCode == HttpURLConnection.HTTP_OK) {
                //System.err.println(jsonObject.toString());   //spausdina terminala, pasitikrinimui; bandys spausdinti kaip klaida, kita spalva
             //   JSONArray jsonArray = null;
               // try {
               //     jsonArray = JSON.getJSONArray(jsonObject);
                //    margaritaList = JSON.getList(jsonArray);

             //   } catch (JSONException e) {
              //      System.out.println(getResources().getString(R.string.search_error_reading_data) + e.getMessage());
             //   }

            // else {   // kazkas nepavyko (serveris negrazino 200 code)
          //    String message = null;
           //     try {
            //        message = jsonObject.getString("message");
           //     } catch (JSONException e) {
           //         Toast.makeText( //cia apdorojame JSON exceptiona
              //              SearchActivity.this,
             //               getResources().getString(R.string.search_error_reading_data) + e.getMessage(),
              //              Toast.LENGTH_LONG
              //      ).show();
             //   }
              //  Toast.makeText( //sioje vietoje jau perduodame zinute
                  //      SearchActivity.this,
                   //     getResources().getString(R.string.search_error_reading_data) + e.getMesssage,   //KAS CIA???
                //        Toast.LENGTH_LONG
             //   ).show();
         //   }   //baigiasi else
      //  }   //baigiasi onPostExecute
  //  }   //baigiasi AsyncFetch klase

    //sioje vietoje gales buti kazkokie metodai

//}   //baigiasi SearchActivity java class
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

public class SearchActivity extends AppCompatActivity { //globalus kintamieji, aprasomi klases virsuje
    public static final String MARGARITA_API = "https://www.thecocktaildb.com/api/json/v1/1/search.php?s=Margarita"; //kreikiames i url
    private ArrayList<Margarita> margaritaList = new ArrayList<Margarita>();
    //pasirasysim globalius kintamuosius:
    private RecyclerView recyclerView; //korteliu vaizdas
    private Adapter adapter;  // tarpininkas tarp search activity ir xml. apjungia dvi klases
    private SearchView searchView = null; // paieskos vaizdas

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //bus paleidziama nauja gija (thread) - JSONO nuskaitymui is API
        AsyncFetch asyncFetch = new AsyncFetch();   //kuriames klase- AsyncFetch
        asyncFetch.execute(); //execute iskviecia metotus, sukurtus toje AsyncFetch klaseje
    }

    // cia ikopinom mokytojo koda =========================================================
    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //suteikiam vartotojui galimybe irasyti paieskoje
        // adds item to action bar
        getMenuInflater().inflate(R.menu.search, menu); // search.xml
        //Get Search item from action bar and Get Search service
        MenuItem searchItem = menu.findItem(R.id.action_search); //vartotojas irasys paieskos zodi

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

    protected void onNewIntent(Intent intent) { //vykdoma paieskos funkcija
        super.onNewIntent(intent);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY); //tai, ka vartyotojas suvede i meniu juosta, jei ives pvz Italija tai QUERY BUS Italija
            if (searchView != null) {
                searchView.clearFocus(); //isvalo blyksinti kursoriu
            }

            //is visu kokteiliu saraso sukuriamas sarasas pagal vertotoja ieskoma kokteili (QUERY)
            ArrayList<Margarita> margaritaListByQuery = JSON.getMargaritaListByQuery(margaritaList, query);

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

    private class AsyncFetch extends AsyncTask<String, String, JSONObject> {    //privati klase, ji yra vidine; lygiagrecios klases gali buti privacios. naudojama ne sbu dvi lygiagrecios uzduotys
        ProgressDialog progressDialog = new ProgressDialog(SearchActivity.this); //nurodome kad jis bus , suksis, search activity lange

        @Override
        protected void onPreExecute() { //sis metodas bus vykdomas pries do it background. Paprasysime vartotojo palaukti, kol gausime duomenis is API (bus besisukantis vaizdas)
            super.onPreExecute();
            progressDialog.setMessage(getResources().getString(R.string.search_loading_data));
            progressDialog.setCancelable(false);    //turi islaukti kol gris kazkokia informacija
            progressDialog.show();  //kad matytu vaizda, bus rodomas besisukantis
        }

        @Override
        //kol sukasi rutuliukas, gauname duomenis is url
        protected JSONObject doInBackground(String... strings) { //vykdoma kai vartotojas mato besisukanti dialoga (progressDialog)
            try { //try yra blogas, kuriame gali buti klaidu, bet procesas nenutruks
                JSONObject jsonObject = JSON.readJsonFromUrl(MARGARITA_API); //pasikreipiame i klase Json.java ir  perduodame URL
                return jsonObject; //Jei viskas gerai, grazinsime jsonObject
            } catch (IOException e) { //input output exception tai ivedimo, isvedimo isimtys
                Toast.makeText(SearchActivity.this,
                        getResources().getString(R.string.search_error_reading_data) + e.getMessage(), //ismesime pranesima apie klaida, per getMessage  pateiksime papildomos informacijos
                        Toast.LENGTH_LONG
                ).show();
            } catch (JSONException e) {
                Toast.makeText(SearchActivity.this,
                        getResources().getString(R.string.search_error_reading_data) + e.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
            return null; //null - tai kia nieko nera
        } // baigiasi doInBackground metodas

        @Override
        protected void onPostExecute(JSONObject jsonObject) { //onPostExecute - padaryk kazka po. Execute atitinka doInBackground
            progressDialog.dismiss(); //gavome duomenis is background, todel panaikiname besisukanti dialoga


            JSONArray jsonArray = null;
            try {
                jsonArray = JSON.getJSONArray(jsonObject); //is JSON object suformuoja JSON Array
                margaritaList = JSON.getList(jsonArray);

            } //catch (JSONException e) {

            catch (JSONException e) { //toliau apdoroma zinute JSON exeption. Catch suranda klaida ir ja isspausdina
                Toast.makeText(SearchActivity.this,
                        getResources().getString(R.string.search_error_reading_data) + e.getMessage(), //ismesime pranesima apie error'a, getMessage - pateiksime papildomos info
                        Toast.LENGTH_LONG
                ).show();
            } //baigiasi catch
        } //baigiasi onpostexecute
    } //baigiasi AsyncFetch klase
}//baigiasi searchactivity klase


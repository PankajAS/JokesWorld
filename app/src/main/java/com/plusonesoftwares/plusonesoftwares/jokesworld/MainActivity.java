package com.plusonesoftwares.plusonesoftwares.jokesworld;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Spinner spinnerLang;
    List<String> string;
    View v;
    HttpConnection httpConnection;
    ListView data;
    CategoryListAdapter adapter;
    String getAllCategoryUrl = "http://ssmasti.com/api/Category/GetAll";
    String getCategoryByLangUrl = "http://ssmasti.com/api/Category/GetCategoryByLanguageId?id=";

    Utils utils;
    JSONArray array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerLang  = (Spinner)findViewById(R.id.spinnerLang);
        v = (View)findViewById(R.id.include_data);
        data = (ListView)v.findViewById(R.id.Stored_data);
        string = new ArrayList<String>();
        httpConnection = new HttpConnection();
        utils = new Utils();

        try {
            if(utils.haveNetworkConnection(getApplicationContext())) {
                new getCategoryList().execute(new URL(getAllCategoryUrl));//start async task to get all categories
            }
            else
            {
                utils.showNetworkConnectionMsg(MainActivity.this);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        spinnerLang.setSelection(0,false);
        spinnerLang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                try {
                    if(utils.haveNetworkConnection(getApplicationContext())) {
                        if(!spinnerLang.getSelectedItem().equals("All Language Categories")) {
                            String langId = spinnerLang.getSelectedItem().equals("Hindi") ? "1" : "2";
                            new getCategoryList().execute(new URL(getCategoryByLangUrl + langId));//sending request to fetch category data here by language
                        }
                        else if(spinnerLang.getSelectedItem().equals("All Language Categories"))
                        {
                            new getCategoryList().execute(new URL(getAllCategoryUrl));//start async task to get all categories
                        }
                    }
                    else {
                        utils.showNetworkConnectionMsg(MainActivity.this);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    JSONObject jobject = array.getJSONObject(i);

                    Intent intent = new Intent(MainActivity.this,Category_DetailsView.class);
                    intent.putExtra("Name",jobject.getString("Category"));
                    intent.putExtra("ID",jobject.getString("ID"));
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private HttpURLConnection urlConnection;
    StringBuilder strbuilderObj = null;

    public class getCategoryList extends AsyncTask<URL, Context, JSONArray> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(MainActivity.this,"","Please wait loading your data...", true);
            super.onPreExecute();
        }

        @Override
        protected JSONArray doInBackground(URL... urls) {

            URL url = urls[0];
            String line;
            strbuilderObj = new StringBuilder();
            try {
                urlConnection = (HttpURLConnection)url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                while ((line = in.readLine()) != null) {
                    strbuilderObj.append(line);
                }
                array = new JSONArray(strbuilderObj.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return array;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
           super.onPostExecute(jsonArray);

            try {
                if(jsonArray !=null && jsonArray.length()>0) {
                    string.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        string.add(jsonArray.getJSONObject(i).getString("ContentCount"));
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            data.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            dialog.dismiss();//closing the loading dialog here
        }
    }
}

package com.plusonesoftwares.plusonesoftwares.jokesworld;

import android.app.ProgressDialog;
import android.content.Intent;
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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    Spinner spinnerLang, category;
    List<String> string;
    ArrayAdapter<String> adapter1;
    View v;
    HttpConnection httpConnection;
    ProgressDialog dialog;
    ListView data;
    CategoryListAdapter adapter;
    String Url = "http://ssmasti.com/api/Category/GetAll";
    String getLanguageCategory_Url = "http://ssmasti.com/api/Category/GetCategoryByLanguageId?id=";

    Utils utils;
    JSONArray jsonArray;
    JSONObject jsonObject;

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
                sendRequest(Url);//sending request to fetch category data here
            }
            else
            {
                utils.showNetworkConnectionMsg(MainActivity.this);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Spinner change event
        /*spinnerLang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                try {
                    if(utils.haveNetworkConnection(getApplicationContext())) {
                        String langId = spinnerLang.getSelectedItem().equals("Hindi")? "1":"2";
                        sendRequest(getLanguageCategory_Url + langId);//sending request to fetch category data here by language
                    }
                    else
                    {
                        utils.showNetworkConnectionMsg(MainActivity.this);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });*/

        data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    JSONObject jobject = jsonArray.getJSONObject(i);

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

    public void sendRequest(String httpUrl) throws JSONException {
        try {
            jsonArray = httpConnection.new FetchData(MainActivity.this).execute(new URL(httpUrl)).get();

            if(jsonArray !=null && jsonArray.length()>0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    string.add(jsonObject.getString("ContentCount"));
                }
            }
            else
            {
                Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();
            }
            // dialog.dismiss();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        adapter = new CategoryListAdapter(this, R.layout.category_list_items, jsonArray, string);
        data.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}

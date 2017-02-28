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
    Spinner lang, category;
    List<String> string;
    ArrayAdapter<String> adapter1;
    View v;
    HttpConnection httpConnection;
    ProgressDialog dialog;
    ListView data;
    CategoryListAdapter adapter;
    String Url = "http://192.168.1.104/api/Category";
    JSONArray jsonArray;
    JSONObject jsonObject;
    HashMap<Integer,String> contentData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lang  = (Spinner)findViewById(R.id.lang);
        v = (View)findViewById(R.id.include_data);
        data = (ListView)v.findViewById(R.id.Stored_data);
        string = new ArrayList<String>();
        httpConnection = new HttpConnection();
        contentData = new HashMap<>();

        try {
            sendRequest();//sending request to fetch category data here
        } catch (JSONException e) {
            e.printStackTrace();
        }

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

    public void sendRequest() throws JSONException {
        try {
            jsonArray = httpConnection.new FetchData(MainActivity.this).execute(new URL(Url)).get();

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                string.add(jsonObject.getString("ContentCount"));
            }
            // dialog.dismiss();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        adapter = new CategoryListAdapter(this,R.layout.category_list_items,jsonArray, string);
        data.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}

package com.plusonesoftwares.plusonesoftwares.jokesworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Category_DetailsView extends AppCompatActivity {
    List<String> Details_List;
    CategoryDetailsAdator adapter;
    ListView Detail_View_List;
    String Title;
    String ID;
    String Url = "http://192.168.1.104/api/content";
    HttpConnection httpConnection;
    JSONArray jsonArray;
    JSONObject jobject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category__details_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Details_List = new ArrayList<>();
        Detail_View_List = (ListView)findViewById(R.id.detail_list);
        Intent intent = getIntent();
        Title = intent.getStringExtra("Name");
        ID = intent.getStringExtra("ID");
        setTitle(Title);
        httpConnection = new HttpConnection();
       // Details_List = intent.getStringArrayListExtra("List");
        //adapter = new CategoryDetailsAdator(this,R.layout.detail_items, Details_List);
       // Detail_View_List.setAdapter(adapter);

        Detail_View_List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    JSONObject jobject = jsonArray.getJSONObject(i);
                    Intent intent = new Intent(Category_DetailsView.this,DetailsViewActivity.class);
                    intent.putExtra("Content",jobject.getString("Content"));
                    intent.putExtra("Category",Title);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            jsonArray = httpConnection.new FetchData(Category_DetailsView.this).execute(new URL(Url+"/"+ID)).get();

            for (int i = 0; i < jsonArray.length(); i++) {
                jobject = jsonArray.getJSONObject(i);
                Details_List.add(jobject.getString("ID"));
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter = new CategoryDetailsAdator(this,R.layout.detail_items, jsonArray,Details_List);
        Detail_View_List.setAdapter(adapter);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

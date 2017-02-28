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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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
    CustomMainListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lang  = (Spinner)findViewById(R.id.lang);
        v = (View)findViewById(R.id.include_data);
        data = (ListView)v.findViewById(R.id.Stored_data);
        string = new ArrayList<>();
        httpConnection = new HttpConnection();

        sendRequest("");//sending request to fetch category data here

        data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,DetailsViewActivity.class);
                intent.putExtra("Name",string.get(i));
                startActivity(intent);

            }
        });
    }

    public void sendRequest(String name){
        try {
            string =httpConnection.new FetchData(MainActivity.this).execute(new URL("https://api.github.com/users")).get();
            // dialog.dismiss();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //adapter1 = new ArrayAdapter<String>(getApplicationContext(), R.layout.data_item, R.id.data_items,string);
        adapter = new CustomMainListAdapter(this,R.layout.list_items,string);
        data.setAdapter(adapter);
    }
}

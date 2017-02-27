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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lang  = (Spinner)findViewById(R.id.lang);
        category = (Spinner)findViewById(R.id.category);
        v = (View)findViewById(R.id.include_data);
        data = (ListView)v.findViewById(R.id.Stored_data);
        string = new ArrayList<>();
        httpConnection = new HttpConnection();

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(adapterView.getSelectedItem().equals("Jokes")){

                    sendRequest(adapterView.getSelectedItem().toString());
                }else if(adapterView.getSelectedItem().equals("Shayri")){

                    System.out.println("Yopu are selected: "+adapterView.getSelectedItem() );
                }else if(adapterView.getSelectedItem().equals("Funny")){
                    System.out.println("Yopu are selected: "+adapterView.getSelectedItem() );
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),DetailsViewActivity.class);
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
        adapter1 = new ArrayAdapter<String>(getApplicationContext(), R.layout.data_item, R.id.data_items,string);
        data.setAdapter(adapter1);
    }
}

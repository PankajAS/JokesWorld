package com.plusonesoftwares.plusonesoftwares.jokesworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Spinner lang, category;
    List<String> string;
    ArrayAdapter<String> adapter,adapter1;
    View v;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lang  = (Spinner)findViewById(R.id.lang);
        category = (Spinner)findViewById(R.id.category);
        v = (View)findViewById(R.id.include_data);
        ListView data = (ListView)v.findViewById(R.id.Stored_data);
        string = new ArrayList<>();
        string.add("Jokes1");
        string.add("Jokes1");
        string.add("Jokes1");
        string.add("Jokes1");
        string.add("Jokes1");
        string.add("Jokes1");
        string.add("Jokes1");
        string.add("Jokes1");
        string.add("Jokes1");
        string.add("Jokes1");
        string.add("Jokes1");
        string.add("Jokes1");
        string.add("Jokes1");
        /*adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, string);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lang.setAdapter(adapter);*/
        adapter1 = new ArrayAdapter<String>(getApplicationContext(), R.layout.data_item, R.id.data_items,string);
        data.setAdapter(adapter1);
        data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),DetailsViewActivity.class);
                intent.putExtra("Name",string.get(i));
                startActivity(intent);

            }
        });


    }
}

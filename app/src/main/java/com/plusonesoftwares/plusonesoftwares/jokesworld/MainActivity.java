package com.plusonesoftwares.plusonesoftwares.jokesworld;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Spinner lang, category;
    ArrayList<String> string;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lang  = (Spinner)findViewById(R.id.lang);
        category = (Spinner)findViewById(R.id.category);

        string = new ArrayList<>();
        string.add("English");
        string.add("Hindi");
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, string);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lang.setAdapter(adapter);

    }
}

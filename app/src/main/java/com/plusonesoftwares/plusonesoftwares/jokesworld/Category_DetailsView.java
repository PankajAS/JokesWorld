package com.plusonesoftwares.plusonesoftwares.jokesworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Category_DetailsView extends AppCompatActivity {
    List<String> Details_List;
    CategoryDetailsAdator adapter;
    ListView Detail_View_List;
    String Title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category__details_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Details_List = new ArrayList<>();
        Detail_View_List = (ListView)findViewById(R.id.detail_list);
        Intent intent = getIntent();
        Title = intent.getStringExtra("Name");
        setTitle(Title);
        Details_List = intent.getStringArrayListExtra("List");
        adapter = new CategoryDetailsAdator(this,R.layout.detail_items, Details_List);
        Detail_View_List.setAdapter(adapter);

        Detail_View_List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Category_DetailsView.this,DetailsViewActivity.class);
                intent.putExtra("Name",Details_List.get(i));
                startActivity(intent);
            }
        });


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

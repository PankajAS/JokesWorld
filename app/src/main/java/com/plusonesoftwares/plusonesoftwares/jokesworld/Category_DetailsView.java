package com.plusonesoftwares.plusonesoftwares.jokesworld;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.plusonesoftwares.plusonesoftwares.jokesworld.sqliteDatabase.ContentRepo;

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
import java.util.HashMap;
import java.util.List;

public class Category_DetailsView extends AppCompatActivity {
    List<String> Details_List;
    CategoryDetailsAdapter adapter;
    ListView Detail_View_List;
    String Title;
    String ID;
    JSONArray array;
    Utils utils;
    ContentRepo contentOperation;
    ArrayList<HashMap<String, String>> contentListObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Details_List = new ArrayList<>();
        Detail_View_List = (ListView) findViewById(R.id.detail_list);
        Intent intent = getIntent();
        Title = intent.getStringExtra("Name");
        ID = intent.getStringExtra("ID");
        setTitle(Title);
        utils = new Utils();

        contentOperation = new ContentRepo(getApplicationContext());
        contentListObj = contentOperation.getContentByCategoryId(ID);
        reloadListView(contentListObj);

        Detail_View_List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                array = utils.convertHashMapArrayListToJsonArray(contentListObj);
                Intent intent = new Intent(Category_DetailsView.this, DetailsViewActivity.class);
                intent.putExtra("Content", array.toString());
                intent.putExtra("Category", Title);
                intent.putExtra("SelectedIndex", String.valueOf(i));
                startActivity(intent);
            }
        });
    }
    private void reloadListView(ArrayList<HashMap<String, String>> contentList) {
        adapter = new CategoryDetailsAdapter(Category_DetailsView.this, R.layout.detail_items, contentList, contentList);
        Detail_View_List.setAdapter(adapter);
        //Detail_View_List.noti
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuoptions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_Share:
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "For best Sahayri & Jokes please download : "+"https://play.google.com/store/apps/details?id=" + appPackageName);
                shareIntent.setType("text/plain");
                startActivity(Intent.createChooser(shareIntent, "Choose sharing method"));
                return true;
            case R.id.menu_RateUS:
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
                return true;
            case R.id.menu_AboutUS:
                Intent intent = new Intent(Category_DetailsView.this, AboutUs.class);
                startActivity(intent);
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == android.R.id.home) {
//            finish();
//        }
//        return super.onOptionsItemSelected(item);
//    }
}

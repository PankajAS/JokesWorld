package com.plusonesoftwares.plusonesoftwares.jokesworld;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    String Url = "http://ssmasti.com/api/Content/GetContentByCategoryId?id=";
    JSONArray jsonArray;
    JSONObject jobject;
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
        Detail_View_List = (ListView)findViewById(R.id.detail_list);
        Intent intent = getIntent();
        Title = intent.getStringExtra("Name");
        ID = intent.getStringExtra("ID");
        setTitle(Title);
        utils = new Utils();

        contentOperation = new ContentRepo(getApplicationContext());
        contentListObj = contentOperation.getContentByCategoryId(Integer.valueOf(ID));
        reloadListView(contentListObj);

        Detail_View_List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // JSONObject jobject = array.getJSONObject(i);
                Intent intent = new Intent(Category_DetailsView.this,DetailsViewActivity.class);
                intent.putExtra("Content", contentListObj.toString());
                intent.putExtra("Category",Title);
                intent.putExtra("SelectedIndex", String.valueOf(i));
                startActivity(intent);
            }
        });

//        if(utils.haveNetworkConnection(getApplicationContext())) {
//            try {
//                new getCategoryDetails().execute(new URL(Url + ID));
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//        }
//        else
//        {
//            utils.showNetworkConnectionMsg(Category_DetailsView.this);
//        }
    }

    private void reloadListView(ArrayList<HashMap<String, String>> contentList) {
        adapter = new CategoryDetailsAdapter(Category_DetailsView.this, R.layout.detail_items, contentList, contentList);
        Detail_View_List.setAdapter(adapter);
        Detail_View_List.deferNotifyDataSetChanged();
    }

    /*private HttpURLConnection urlConnection;
    StringBuilder sb = null;

    public class getCategoryDetails extends AsyncTask<URL, Context, JSONArray> {

        private Context context;
        List<String> list = new ArrayList<>();
        HashMap<String, String> contentId = new HashMap<>();
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
           // dialog = ProgressDialog.show(Category_DetailsView.this, "","Please wait loading your data...", true);
            super.onPreExecute();
        }

        @Override
        protected JSONArray doInBackground(URL... urls) {
            URL url = urls[0];
            String line;
            String c;
            sb = new StringBuilder();
            try {
                urlConnection = (HttpURLConnection)url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }
                array = new JSONArray(sb.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return array;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            //dialog.dismiss();
            super.onPostExecute(jsonArray);

            if(jsonArray !=null && jsonArray.length()>0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        jobject = jsonArray.getJSONObject(i);
                        Details_List.add(jobject.getString("ID"));
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            else
            {
                Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();
            }

            adapter = new CategoryDetailsAdapter(Category_DetailsView.this, R.layout.detail_items, jsonArray, Details_List);
            Detail_View_List.setAdapter(adapter);
            Detail_View_List.deferNotifyDataSetChanged();
        }
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

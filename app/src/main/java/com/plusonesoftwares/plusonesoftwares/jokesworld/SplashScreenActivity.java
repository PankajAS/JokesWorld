package com.plusonesoftwares.plusonesoftwares.jokesworld;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.plusonesoftwares.plusonesoftwares.jokesworld.sqliteDatabase.Category;
import com.plusonesoftwares.plusonesoftwares.jokesworld.sqliteDatabase.Content;
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
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

public class SplashScreenActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private int progressStatus = 0;
    private TextView textView;
    private Handler handler = new Handler();
    List<String> string;
    ListView data;
    CategoryListAdapter adapter;
    String getAllCategoryUrl = "http://ssmasti.com/api/Category/GetAll";
    String getCategoryByLangUrl = "http://ssmasti.com/api/Category/GetCategoryByLanguageId?id=";
    String getAllContentUrl = "http://ssmasti.com/api/Content/GetAll";

    Utils utils;
    //JSONArray jsonArray;
    JSONArray array;
    JSONObject jsonObj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textView = (TextView) findViewById(R.id.textView);
        utils = new Utils();

        try {
            if(utils.haveNetworkConnection(getApplicationContext())) {
                new getCategoryList().execute(new URL(getAllCategoryUrl));//start async task to get all categories
                new getAllContentList().execute(new URL(getAllContentUrl));//start async task to get all content
            }
            else
            {
                utils.showNetworkConnectionMsg(SplashScreenActivity.this);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        // Start long running operation in a background thread
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 1;
                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus);
                            textView.setText(progressStatus+"/"+progressBar.getMax());
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (progressStatus>=100)
                {
                    Intent intent = new Intent(SplashScreenActivity.this, MainTabViewActivity.class);
                    startActivity(intent);
                    //overridePendingTransition(R.transition.move, R.transition.stay);
                }
            }
        }).start();
    }

    //************ Get all Categories and insert it into sqlite db for offline availability ************
    private HttpURLConnection urlConnection;
    StringBuilder strbuilderObj = null;

    public class getCategoryList extends AsyncTask<URL, Context, JSONArray> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            //dialog = ProgressDialog.show(SplashScreenActivity.this,"","Please wait loading your data...", true);
            super.onPreExecute();
        }

        @Override
        protected JSONArray doInBackground(URL... urls) {

            URL url = urls[0];
            String line;
            strbuilderObj = new StringBuilder();
            try {
                urlConnection = (HttpURLConnection)url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                while ((line = in.readLine()) != null) {
                    strbuilderObj.append(line);
                }
                array = new JSONArray(strbuilderObj.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return array;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            super.onPostExecute(jsonArray);

            ContentRepo categoryOperation = new ContentRepo(getApplicationContext());
            //Beforing updating the data need to clear all previous data
            categoryOperation.delete_All_Categories();

            List<Category> categoryList = new ArrayList<>();

            Category singleCatObj;
            if(jsonArray !=null && jsonArray.length()>0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            singleCatObj = new Category();
                            singleCatObj.category_ID = jsonArray.getJSONObject(i).getString("ID");
                            singleCatObj.language_ID = jsonArray.getJSONObject(i).getString("LanguageID");
                            singleCatObj.Category = jsonArray.getJSONObject(i).getString("Category");
                            singleCatObj.Image = jsonArray.getJSONObject(i).getString("Image");
                            singleCatObj.ContentCount = jsonArray.getJSONObject(i).getString("ContentCount");
                            categoryList.add(singleCatObj);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    categoryOperation.insert_Categories(categoryList);
                }
            }
        }

    //************ Get all content and insert it into sqlite db for offline availability ************

    private HttpURLConnection urlContentConnection;
    StringBuilder contentStringBuilder = null;

    public class getAllContentList extends AsyncTask<URL, Context, JSONArray> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONArray doInBackground(URL... urls) {

            URL url = urls[0];
            String line;
            contentStringBuilder = new StringBuilder();
            try {
                urlContentConnection = (HttpURLConnection)url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(urlContentConnection.getInputStream()));

                while ((line = in.readLine()) != null) {
                    contentStringBuilder.append(line);
                }
                array = new JSONArray(contentStringBuilder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return array;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            super.onPostExecute(jsonArray);

            ContentRepo contentOperation = new ContentRepo(getApplicationContext());

            //Beforing updating the data need to clear all previous data
            contentOperation.delete_All_Content();

            List<Content> contentList = new ArrayList<>();

            Content singleCatObj;
            if(jsonArray !=null && jsonArray.length()>0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        singleCatObj = new Content();
                        singleCatObj.content_ID = jsonArray.getJSONObject(i).getString("ID");
                        singleCatObj.category_ID = jsonArray.getJSONObject(i).getString("CategoryID");
                        singleCatObj.Content = jsonArray.getJSONObject(i).getString("Content");
                        singleCatObj.CreatedDate = jsonArray.getJSONObject(i).getString("CreatedDate");
                        singleCatObj.IsPopular = jsonArray.getJSONObject(i).getString("IsPopular");
                        contentList.add(singleCatObj);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                contentOperation.insert_Content(contentList);
            }
        }
    }
}


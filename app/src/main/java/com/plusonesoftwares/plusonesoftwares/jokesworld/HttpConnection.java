package com.plusonesoftwares.plusonesoftwares.jokesworld;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Plus 3 on 27-02-2017.
 */

public class HttpConnection {
    private HttpURLConnection urlConnection;
    private Context mContext;
    StringBuilder sb = null;
    JSONObject jsonData;

    public class FetchData extends AsyncTask<URL,Context,List<String>>{
        private Context context;
        List<String> list = new ArrayList<>();
        ProgressDialog dialog;

        public FetchData(Context context1){
            context = context1;
        }

        @Override
        protected void onPreExecute() {
           dialog = ProgressDialog.show(context, "","Loading...", true);
            super.onPreExecute();
        }

        @Override
        protected List<String> doInBackground(URL... urls) {
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
                JSONArray array = new JSONArray(sb.toString());
                for (int i = 0; i < array.length(); i++) {
                    jsonData = array.getJSONObject(i);
                    list.add(jsonData.getString("login"));
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            dialog.dismiss();
            super.onPostExecute(strings);
        }
    }
}

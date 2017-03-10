package com.plusonesoftwares.plusonesoftwares.jokesworld;

import android.content.Context;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by cdsdev2 on 07/11/16.
 */

public class Utils {


    public static final String MyPREFERENCES = "MyPrefs";
    public static final String categoryList = "categoryList";

    SharedPreferences sharedpreferences;

    public void setUserPrefs(String key, String value, Context context) {
        if (sharedpreferences == null) {
            sharedpreferences = context.getSharedPreferences(MyPREFERENCES, context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getUserPrefs(String key, Context context) {
        if (sharedpreferences == null) {
            sharedpreferences = context.getSharedPreferences(MyPREFERENCES, context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = sharedpreferences.edit();
        String value = sharedpreferences.getString(key, null);
        editor.commit();
        return value;
    }

    public void clearUserPrefs(Context context) {
        if (sharedpreferences == null) {
            sharedpreferences = context.getSharedPreferences(MyPREFERENCES, context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
    }


    public void showNetworkConnectionMsg(final Activity activity) {
        activity.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(activity, R.string.connectionError, Toast.LENGTH_LONG).show();
            }
        });
    }

    public boolean haveNetworkConnection(Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    public JSONArray convertHashMapArrayListToJsonArray(ArrayList<HashMap<String, String>> dataMap)
    {

        List<JSONObject> jsonObj = new ArrayList<JSONObject>();

        for(HashMap<String, String> data : dataMap) {
            JSONObject obj = new JSONObject(data);
            jsonObj.add(obj);
        }

        JSONArray contentJsonArray = new JSONArray(jsonObj);

        return contentJsonArray;
    }
}
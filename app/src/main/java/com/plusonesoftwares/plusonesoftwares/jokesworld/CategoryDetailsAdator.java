package com.plusonesoftwares.plusonesoftwares.jokesworld;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Plus 3 on 28-02-2017.
 */

public class CategoryDetailsAdator extends ArrayAdapter {
    List<String> list;
    JSONArray jarray;
    public CategoryDetailsAdator(Context context, int resource, JSONArray jarray, List objects) {
        super(context, resource, objects);
        list = objects;
        this.jarray=jarray;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View rowView= inflater.inflate(R.layout.detail_items, null, true);
        TextView detail_title = (TextView) rowView.findViewById(R.id.detail_title);
        try {
            JSONObject jobject = jarray.getJSONObject(position);
            detail_title.setText(jobject.getString("Content"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
       // detail_title.setText(list.get(position));
        return rowView;
    }

}

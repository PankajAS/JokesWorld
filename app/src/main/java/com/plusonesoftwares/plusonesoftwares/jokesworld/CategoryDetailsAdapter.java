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

public class CategoryDetailsAdapter extends ArrayAdapter {
    List<String> list;
    JSONArray jarray;
    public CategoryDetailsAdapter(Context context, int resource, JSONArray jarray, List objects) {
        super(context, resource, objects);
        list = objects;
        this.jarray=jarray;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CategoryDetailsAdapter.ViewHolder holder= null;
        if(convertView == null) {
            holder = new CategoryDetailsAdapter.ViewHolder();
            LayoutInflater inflater=LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.detail_items, null ,true);
            holder.selectedCatDetail = (TextView) convertView.findViewById(R.id.detail_title);

            try {
                JSONObject jobject = jarray.getJSONObject(position);
                holder.selectedCatDetail.setText(jobject.getString("Content"));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }else{
            holder = (CategoryDetailsAdapter.ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    private class ViewHolder{
        TextView selectedCatDetail;
    }

}

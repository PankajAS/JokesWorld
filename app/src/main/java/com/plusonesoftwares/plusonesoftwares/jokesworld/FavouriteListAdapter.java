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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Plus 3 on 28-02-2017.
 */

public class FavouriteListAdapter extends ArrayAdapter {
    ArrayList<HashMap<String, String>> favouriteListObj;

    public FavouriteListAdapter(Context context, int resource, ArrayList<HashMap<String, String>> favouriteList, List objects) {
        super(context, resource, objects);
        favouriteListObj = favouriteList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FavouriteListAdapter.ViewHolder holder= null;
        if(convertView == null) {
            holder = new FavouriteListAdapter.ViewHolder();
            LayoutInflater inflater=LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.detail_items, null ,true);
            holder.selectedCatDetail = (TextView) convertView.findViewById(R.id.detail_title);
            convertView.setTag(holder);
        }else{
            holder = (FavouriteListAdapter.ViewHolder) convertView.getTag();
        }
        holder.selectedCatDetail.setText(favouriteListObj.get(position).get("content"));

        return convertView;
    }

    private class ViewHolder{
        TextView selectedCatDetail;
    }

}

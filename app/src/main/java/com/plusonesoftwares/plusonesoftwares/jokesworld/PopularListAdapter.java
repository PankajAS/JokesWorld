package com.plusonesoftwares.plusonesoftwares.jokesworld;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Plus 3 on 28-02-2017.
 */

public class PopularListAdapter extends ArrayAdapter {
    ArrayList<HashMap<String, String>> popularListObj;

    public PopularListAdapter(Context context, int resource, ArrayList<HashMap<String, String>> popularList, List objects) {
        super(context, resource, objects);
        popularListObj = popularList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PopularListAdapter.ViewHolder holder= null;
        if(convertView == null) {
            holder = new PopularListAdapter.ViewHolder();
            LayoutInflater inflater=LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.detail_items, null ,true);
            holder.selectedCatDetail = (TextView) convertView.findViewById(R.id.detail_title);
            convertView.setTag(holder);
        }else{
            holder = (PopularListAdapter.ViewHolder) convertView.getTag();
        }
        holder.selectedCatDetail.setText(popularListObj.get(position).get("content"));

        return convertView;
    }

    private class ViewHolder{
        TextView selectedCatDetail;
    }

}

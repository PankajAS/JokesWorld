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

public class LatestListAdapter extends ArrayAdapter {
    ArrayList<HashMap<String, String>> latestListObj;

    public LatestListAdapter(Context context, int resource, ArrayList<HashMap<String, String>> latestList, List objects) {
        super(context, resource, objects);
        latestListObj = latestList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LatestListAdapter.ViewHolder holder= null;
        if(convertView == null) {
            holder = new LatestListAdapter.ViewHolder();
            LayoutInflater inflater=LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.detail_items, null ,true);
            holder.selectedCatDetail = (TextView) convertView.findViewById(R.id.detail_title);

            holder.selectedCatDetail.setText(latestListObj.get(position).get("content"));

        }else{
            holder = (LatestListAdapter.ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    private class ViewHolder{
        TextView selectedCatDetail;
    }
}

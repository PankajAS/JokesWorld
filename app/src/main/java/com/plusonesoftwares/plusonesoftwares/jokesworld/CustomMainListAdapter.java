package com.plusonesoftwares.plusonesoftwares.jokesworld;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Plus 3 on 28-02-2017.
 */

public class CustomMainListAdapter extends ArrayAdapter {
    List<String> list;
    Context context;

    public CustomMainListAdapter(Context context, int resource, List<String> string) {
        super(context, resource, string);
        list = string;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View rowView= inflater.inflate(R.layout.list_items, null, true);
        TextView title = (TextView)rowView.findViewById(R.id.Title);
        title.setText(list.get(position));

        return rowView;
    }

 private class ViewHolder{
     TextView title;
     ImageView title_image;

    }
}

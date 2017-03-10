package com.plusonesoftwares.plusonesoftwares.jokesworld;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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

public class CategoryListAdapter extends ArrayAdapter {
    ArrayList<HashMap<String, String>> categoryListobj;
    Context context;

    public CategoryListAdapter(Context context, int resource, ArrayList<HashMap<String, String>> categoryList, List<String> string) {
        super(context, resource, categoryList);
        categoryListobj = categoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CategoryListAdapter.ViewHolder holder= null;
        if(convertView == null) {
            holder = new CategoryListAdapter.ViewHolder();
            LayoutInflater inflater=LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.category_list_items, null ,true);
            holder.category = (TextView) convertView.findViewById(R.id.Title);
            holder.badge = (TextView) convertView.findViewById(R.id.txtbadge_count);
            holder.category_image = (ImageView) convertView.findViewById(R.id.category_image);
            convertView.setTag(holder);

        }else{
            holder = (CategoryListAdapter.ViewHolder) convertView.getTag();
        }

        holder.category.setText(categoryListobj.get(position).get("category"));
        holder.badge.setText(categoryListobj.get(position).get("contentcount"));
        String base64Image = (String) categoryListobj.get(position).get("image");

        if(base64Image != null && !base64Image.isEmpty()) {
            byte[] imageAsBytes = Base64.decode(base64Image.getBytes(), Base64.DEFAULT);
            Bitmap image = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
            holder.category_image.setImageBitmap(image);
        }

        return convertView;
    }

 private class ViewHolder{
         TextView category, badge;
         ImageView category_image;
    }
}

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

import java.util.List;

/**
 * Created by Plus 3 on 28-02-2017.
 */

public class CategoryListAdapter extends ArrayAdapter {
    JSONArray list;
    Context context;
    List<String> badge;

    public CategoryListAdapter(Context context, int resource, JSONArray NameID, List<String> string) {
        super(context, resource, string);
        list = NameID;
        this.context = context;
        badge=string;
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

            try {
                JSONObject jobject = list.getJSONObject(position);
                holder.category.setText(jobject.getString("Category"));
                holder.badge.setText(jobject.getString("ContentCount"));

                String base64Image = (String) jobject.getString("Image");
                if(base64Image != null && !base64Image.isEmpty()) {
                    byte[] imageAsBytes = Base64.decode(base64Image.getBytes(), Base64.DEFAULT);
                    Bitmap image = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
                    //piclist.add(image);
                    holder.category_image.setImageBitmap(image);
                }else{
                    //pic = BitmapFactory.decodeResource(getContext().getResources(),
                      //      R.drawable.profile);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }else{
            holder = (CategoryListAdapter.ViewHolder) convertView.getTag();
        }
        return convertView;
    }

 private class ViewHolder{
     TextView category, badge;
     ImageView category_image;

    }
}

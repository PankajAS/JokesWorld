package com.plusonesoftwares.plusonesoftwares.jokesworld;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * Created by ashoksharma on 27/02/17.
 */

public class DetailsViewActivity extends Activity {
    Button Close;
    Button Create;
    TextView selectedData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_view);
        Create = (Button) findViewById(R.id.btnShare);
        selectedData = (TextView)findViewById(R.id.selected_Item);
        Intent intent = getIntent();
        selectedData.setText(intent.getStringExtra("Name"));

        Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // TODO Auto-generated method stub
                showPopup();
            }
        });
    }

    private PopupWindow pw;
    private void showPopup() {
        try {
            // We need to get the instance of the LayoutInflater
            LayoutInflater inflater = (LayoutInflater)DetailsViewActivity.this.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);

            View layout = inflater.inflate(R.layout.popup,
                    (ViewGroup) findViewById(R.id.popup_1));
            pw = new PopupWindow(layout, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
            pw.showAtLocation(layout, Gravity.CENTER, 0, 0);

            Close = (Button) layout.findViewById(R.id.close_popup);
            Close.setOnClickListener(cancel_button);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener cancel_button = new View.OnClickListener() {
        public void onClick(View v) {
            pw.dismiss();
        }
    };
}

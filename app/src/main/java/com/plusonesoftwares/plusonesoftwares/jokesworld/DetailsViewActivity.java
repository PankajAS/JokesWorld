package com.plusonesoftwares.plusonesoftwares.jokesworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by ashoksharma on 27/02/17.
 */

public class DetailsViewActivity extends Activity {
    Button Close;
    Button Create;
    Button SMS;
    Button FB;
    TextView selectedItem;
    String jokesDetails;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_view);
        Create = (Button) findViewById(R.id.btnShare);
        selectedItem = (TextView)findViewById(R.id.selected_Item);
        Intent intent = getIntent();
        jokesDetails = intent.getStringExtra("Name");
        selectedItem.setText(jokesDetails);

        Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // TODO Auto-generated method stub
                //showPopup();
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, jokesDetails);
                shareIntent.setType("text/plain");
                startActivity(Intent.createChooser(shareIntent, "Choose sharing method"));

            }
        });
    }

   /* private PopupWindow pw;
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

            SMS = (Button) layout.findViewById(R.id.btnShareOnSMS);
            SMS.setOnClickListener(openSMS);
            FB = (Button) layout.findViewById(R.id.btnShareOnFB);
            FB.setOnClickListener(PostOnFB);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener cancel_button = new View.OnClickListener() {
        public void onClick(View v) {
            pw.dismiss();
        }
    };

    private View.OnClickListener openSMS = new View.OnClickListener() {
        public void onClick(View v) {
            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
            sendIntent.setData(Uri.parse("sms:"));
            sendIntent.putExtra("sms_body", intentText);
            startActivity(sendIntent);
        }
    };
    private View.OnClickListener PostOnFB = new View.OnClickListener() {
        public void onClick(View v) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
            shareIntent.setType("text/plain");
            startActivity(Intent.createChooser(shareIntent, "Choose sharing method"));
        }
    };*/
}

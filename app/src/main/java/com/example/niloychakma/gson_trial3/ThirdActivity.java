package com.example.niloychakma.gson_trial3;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class ThirdActivity extends Activity {

    private static final String TAB = "Third Activity";
    DatabaseHelper myDb;

    TextView textBrand;
    TextView textGeneric;
    TextView textIndications;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        myDb = new DatabaseHelper(ThirdActivity.this);
        int position = getIntent().getExtras().getInt("position");
        Log.i("position", Integer.toString(position));

        textBrand = findViewById(R.id.textBrand);
        textGeneric = findViewById(R.id.textGeneric);
        textIndications = findViewById(R.id.textIndic);

        // brand name
        Cursor data = myDb.getAllData();
        ArrayList<String> listBrand = new ArrayList<>();
        while(data.moveToNext()) {
            listBrand.add(data.getString(1));
        }
        // generic name
        Cursor data2 = myDb.getAllData();
        ArrayList<String> listGeneric = new ArrayList<>();
        while(data2.moveToNext()) {
            listGeneric.add(data2.getString(2));
        }
        // indications
        Cursor data3 = myDb.getAllData();
        ArrayList<String> listIndications = new ArrayList<>();
        while(data3.moveToNext()) {
            listIndications.add(data3.getString(3));
        }

        textBrand.setText(listBrand.get(position));
        textGeneric.setText(listGeneric.get(position));
        textIndications.setText(listIndications.get(position));

    }

}

package com.example.niloychakma.gson_trial3;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import java.util.ArrayList;

public class SecondActivity extends Activity {

    private static final String TAB = "Second Activity";
    DatabaseHelper myDb;
    ListView savedDrugsList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        savedDrugsList = findViewById(R.id.savedDrugsList);
        myDb = new DatabaseHelper(this);

        // brand name
        Cursor data1 = myDb.getAllData();
        ArrayList<String> listbrandData = new ArrayList<>();
        while(data1.moveToNext()) {
            listbrandData.add(data1.getString(1));
        }
        // generic name
        Cursor data2 = myDb.getAllData();
        ArrayList<String> listgenericData = new ArrayList<>();
        while(data2.moveToNext()) {
            listgenericData.add(data2.getString(2));
        }

        SecondListAdapter whatever = new SecondListAdapter(this, listbrandData, listgenericData);
        savedDrugsList.setAdapter(whatever);
    }

}

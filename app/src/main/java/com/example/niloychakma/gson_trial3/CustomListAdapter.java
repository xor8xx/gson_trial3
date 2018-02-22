package com.example.niloychakma.gson_trial3;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;

public class CustomListAdapter extends ArrayAdapter {

    DatabaseHelper myDb;

    //to reference the Activity
    private final Activity context;

    //to store the message
    private final Integer limit;
    private final String[] message;
    private final String[] strGeneric;
    private final String[] strBrand;
    private final String[] strIndications;


    public CustomListAdapter(Activity context, Integer limitParam, String[] messageArrayParam, String[] strGenericArrayParam, String[] strBrandArrayParam, String[] strIndicationsArrayParam) {

        super(context, R.layout.listview_row, messageArrayParam);

        this.context = context;

        this.limit = limitParam;
        this.message = messageArrayParam;
        this.strGeneric = strGenericArrayParam;
        this.strBrand = strBrandArrayParam;
        this.strIndications = strIndicationsArrayParam;
        myDb = new DatabaseHelper(context);

    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.listview_row, null, true);

        TextView messageTextViewField = rowView.findViewById(R.id.messageTextViewID);
        messageTextViewField.setText(message[position]);

        Button saveButton = rowView.findViewById(R.id.saveButton);
        myDb = new DatabaseHelper(context);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(strGeneric[position], strBrand[position], strIndications[position]);
                if (isInserted = true) {
                    Toast.makeText(context, "Data Inserted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Data Not Inserted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rowView;
    }
}

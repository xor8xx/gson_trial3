package com.example.niloychakma.gson_trial3;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SecondListAdapter extends ArrayAdapter {

    DatabaseHelper myDb;

    //to reference the Activity
    private final Activity context;

    private final ArrayList<String> brandName;
    private final ArrayList<String> genericName;



    public SecondListAdapter(Activity context, ArrayList<String> brandNameParam, ArrayList<String> genericNameParam) {

        super(context, R.layout.listview_row2, genericNameParam);

        this.context = context;
        this.brandName = brandNameParam;
        this.genericName = genericNameParam;
        myDb = new DatabaseHelper(context);

    }

    @Override
    public View getView(final int position, View view, final ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.listview_row2, null, true);

        TextView messageTextViewField = rowView.findViewById(R.id.messageTextViewID);
        messageTextViewField.setText(genericName.get(position));

        Button delButton = rowView.findViewById(R.id.delButton);
        Button editButton = rowView.findViewById(R.id.editButton);

        myDb = new DatabaseHelper(context);

        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedRows = myDb.deleteData(brandName.get(position), genericName.get(position));
                if(deletedRows > 0) {
                    Toast.makeText(context, "Data Updated", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Data Not Updated", Toast.LENGTH_SHORT).show();
                }
                context.recreate();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editDrugs = new Intent(parent.getContext(), ThirdActivity.class);
                editDrugs.putExtra("position", position);
                parent.getContext().startActivity(editDrugs);
            }
        });


        return rowView;
    }
}
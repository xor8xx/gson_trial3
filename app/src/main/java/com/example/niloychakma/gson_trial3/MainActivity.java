package com.example.niloychakma.gson_trial3;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MainActivity extends AppCompatActivity {

    EditText inputDrug;
    ListView listView;
    DatabaseHelper myDb;

    public void clickFunction (View view) {
        Log.i("Input Drug", inputDrug.getText().toString());
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(inputDrug.getWindowToken(),0);
        Transfer task = new Transfer();
        task.execute("https://api.fda.gov/drug/label.json?api_key=6h0UxgzAwMQzAeCDEbHsDpJUICnk3XdF363P4STG&search=generic_name:\"" + inputDrug.getText().toString() + "\"+brand_name:\"" + inputDrug.getText().toString() + "\"&limit=3");
    }

    public void savedClick (View view) {
        Intent savedDrugs = new Intent(this, SecondActivity.class);
        startActivity(savedDrugs);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputDrug = findViewById(R.id.inputDrug);
        listView = findViewById(R.id.listviewID);
        myDb = new DatabaseHelper(this);

    }

    public class Transfer extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            Log.i("URL", url);
            return url;
        }

        @Override
        protected void onPostExecute(String url) {
            super.onPostExecute(url);
            StringRequest request = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("CODE", response);
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    Drugs drugsResponse = gson.fromJson(response, Drugs.class);

                    int n = 0;
                    int limit = 3;
                    String[] message = new String[limit];
                    String[] strGeneric = new String[limit];
                    String[] strBrand = new String[limit];
                    String[] strIndications = new String[limit];

                    while (n <= (limit-1)) {

                        strGeneric[n] = drugsResponse.getResults().get(n).getOpenfda().getGenericName().toString().substring(1, drugsResponse.getResults().get(n).getOpenfda().getGenericName().toString().length() - 1);
                        strBrand[n] = drugsResponse.getResults().get(n).getOpenfda().getBrandName().toString().substring(1, drugsResponse.getResults().get(n).getOpenfda().getBrandName().toString().length() - 1);
                        strIndications[n] = drugsResponse.getResults().get(n).getIndicationsAndUsage().toString().substring(1, drugsResponse.getResults().get(n).getIndicationsAndUsage().toString().length() - 1);

                        message[n] = "\r\n" + "Generic Name: " + strGeneric[n] + "\r\n" + "\r\n" + "Brand Name: "
                            + strBrand[n] + "\r\n" + "\r\n" + strIndications[n] + "\r\n" ;
                    n++; }

                    CustomListAdapter whatever = new CustomListAdapter(MainActivity.this, limit, message, strGeneric, strBrand, strIndications);
                    listView.setAdapter(whatever);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, "Please type in a drug with correct spelling", Toast.LENGTH_SHORT).show();
                }
            });

            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            queue.add(request);

        }
    }
}

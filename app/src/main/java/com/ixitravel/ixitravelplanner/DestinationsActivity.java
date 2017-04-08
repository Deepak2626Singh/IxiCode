package com.ixitravel.ixitravelplanner;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.R.attr.name;
import static android.R.id.list;

public class DestinationsActivity extends AppCompatActivity {
    String TAG = "DestinationsActivity";
    String jasonResult;
    EditText editText;
    ArrayList<Destination> destinations = new ArrayList<>();
    private ProgressDialog pDialog;
    private String urlJsonObj = "http://build2.ixigo.com/api/v2/widgets/brand/inspire?product=1&apiKey=ixicode!2$";

    ListView list;
    String[] web = {
    } ;
    Integer[] imageId = {
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destinations);
        //editText = (EditText) findViewById(R.id.editText);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        makeJsonObjectRequest();



        CustomList adapter = new
                CustomList(DestinationsActivity.this, destinations);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(DestinationsActivity.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void makeJsonObjectRequest() {
        showpDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlJsonObj, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    // Parsing json object response
                    // response will be a json object
                    JSONObject dataObj = response.getJSONObject("data");
                    JSONArray flights = dataObj.getJSONArray("flight");
                    for (int i = 0; i < flights.length(); i++) {
                        JSONObject c = flights.getJSONObject(i);

                        String image = c.getString("image");
                        Log.d(TAG, "image: "+ image);
                        String countryName = String.valueOf(c.getString("countryName"));
                        Log.d(TAG, "countryName: "+ countryName);
                        String url = c.getString("url");
                        String data = c.getString("data");
                        String text = c.getString("text");
                        String type = c.getString("type");
                        String cityName = c.getString("cityName");
                        String stateName = c.getString("stateName");
                        String price = String.valueOf(c.getInt("price"));
                        String currency = c.getString("currency");
                        String cityId = c.getString("cityId");
                        JSONArray destinationCategories = c.getJSONArray("destinationCategories");
                        Toast.makeText(getApplicationContext(),
                                image, Toast.LENGTH_SHORT).show();
                        destinations.add(new Destination(image,
                                name,
                                countryName,
                                url,
                                data,
                                text,
                                type,
                                cityName,
                                stateName,
                                price,
                                currency,
                                cityId));
                    }


                } catch (/*JSON*/Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                hidepDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance(getApplicationContext()).addToRequestQueue(jsonObjReq);
    }



    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}

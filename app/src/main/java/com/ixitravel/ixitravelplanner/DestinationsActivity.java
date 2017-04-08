package com.ixitravel.ixitravelplanner;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class DestinationsActivity extends AppCompatActivity {
    String TAG = "DestinationsActivity";
    String jasonResult;
    CustomList adapter;
    EditText editText;
    ArrayList<Destination> destinations = new ArrayList<>();
    //String[] destinationss = {"d","h","h","f"};
    private ProgressDialog pDialog;
    private String urlJsonObj = "http://build2.ixigo.com/api/v2/widgets/brand/inspire?product=1&apiKey=ixicode!2$";

    ListView list;
    String[] web = {
    } ;
    Integer[] imageId = {
    };

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("deepak", "onResume");
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destinations);
        //editText = (EditText) findViewById(R.id.editText);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        //makeJsonObjectRequest();
        GithubQueryTask task = new GithubQueryTask();
        try {
            task.execute(new URL(urlJsonObj));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }




        /*CustomList adapter = new
                CustomList(DestinationsActivity.this, destinations);*/
        adapter = new
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

    public class GithubQueryTask extends AsyncTask<URL, Void, String> {

        // COMPLETED (26) Override onPreExecute to set the loading indicator to visible
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showpDialog();
        }
        public String getResponseFromHttpUrl(URL url) throws IOException {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = urlConnection.getInputStream();

                Scanner scanner = new Scanner(in);
                scanner.useDelimiter("\\A");

                boolean hasInput = scanner.hasNext();
                if (hasInput) {
                    return scanner.next();
                } else {
                    return null;
                }
            } finally {
                urlConnection.disconnect();
            }
        }
        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String githubSearchResults = null;
            try {
                githubSearchResults = getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return githubSearchResults;
        }
        private void showJsonDataView(JSONObject response) {
            try {
                JSONObject dataObj = response.getJSONObject("data");

            JSONArray flights = dataObj.getJSONArray("flight");
            for (int i = 0; i < flights.length(); i++) {
                JSONObject c = flights.getJSONObject(i);

                String image = c.getString("image");
                String name = c.getString("name");
                Log.d(TAG, "image: " + image);
                String countryName = c.getString("countryName");
                Log.d(TAG, "countryName: " + countryName);
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
                adapter.notifyDataSetChanged();
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        @Override
        protected void onPostExecute(String githubSearchResults) {
            // COMPLETED (27) As soon as the loading is complete, hide the loading indicator
            hidepDialog();
            if (githubSearchResults != null && !githubSearchResults.equals("")) {
                // COMPLETED (17) Call showJsonDataView if we have valid, non-null results
                try {
                    JSONObject jobj = new JSONObject(githubSearchResults);
                    showJsonDataView(jobj);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                // COMPLETED (16) Call showErrorMessage if the result is null in onPostExecute
               Log.e("deepak", "error");
            }
        }
    }
    /*public void makeJsonObjectRequest() {
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
                        String name = c.getString("name");
                        Log.d(TAG, "image: "+ image);
                        String countryName = c.getString("countryName");
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
                        adapter.notifyDataSetChanged();
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

                } catch (*//*JSON*//*Exception e) {
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
    }*/



    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}

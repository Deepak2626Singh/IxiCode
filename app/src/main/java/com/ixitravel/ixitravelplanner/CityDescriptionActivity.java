package com.ixitravel.ixitravelplanner;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Deepak on 4/8/2017.
 */

public class CityDescriptionActivity extends AppCompatActivity {

    CustomList adapter;
    String TAG = "CityDescriptionActivity";
    private ProgressDialog pDialog;
    String cityId;
    TextView cityText;
    TextView cityDesc;
    TextView cityVisit;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_desc);
        cityId = getIntent().getStringExtra("cityId");
        //TextView textView = (TextView)findViewById(R.id.cityname);
        //textView.setText(cityId);

        cityText = (TextView)findViewById(R.id.cityname);

        cityDesc = (TextView)findViewById(R.id.citydesc);

        cityVisit = (TextView)findViewById(R.id.whytovisit);


        Uri builtUri = Uri.parse("http://build2.ixigo.com/api/v3/namedentities/id")
                .buildUpon()
                .appendPath(cityId)
                .appendQueryParameter("apiKey","ixicode!2$").build();

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        //makeJsonObjectRequest();
        GithubQueryTask task = new GithubQueryTask();
        try {
            task.execute(new URL(builtUri.toString()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }



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

        @Override
        protected void onPostExecute(String githubSearchResults) {
            // COMPLETED (27) As soon as the loading is complete, hide the loading indicator
            hidepDialog();

            ((TextView)findViewById(R.id.about)).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.visit)).setVisibility(View.VISIBLE);
            cityText.setVisibility(View.VISIBLE);
            cityDesc.setVisibility(View.VISIBLE);
            cityVisit.setVisibility(View.VISIBLE);
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

        private void showJsonDataView(JSONObject response) {
            try {
                JSONObject c = response.getJSONObject("data");

                //JSONArray flights = dataObj.getJSONArray("flight");
                    //JSONObject c = flights.getJSONObject(i);

                    String countryName = c.getString("countryName");
                    Log.d(TAG, "countryName" + countryName);
                    String description = c.getString("description");
                    Log.d(TAG, "description" + description);
                    String whyToVisit = c.getString("whyToVisit");
                    Log.d(TAG, "whyToVisit" + whyToVisit);
                    String keyImageUrl = c.getString("keyImageUrl");
                    //TextView cityText = (TextView)findViewById(R.id.cityname);
                    cityText.setText(countryName);
                    //TextView cityDesc = (TextView)findViewById(R.id.citydesc);
                    cityDesc.setText(description);
                    //TextView cityVisit = (TextView)findViewById(R.id.whytovisit);
                    cityVisit.setText(whyToVisit);
                    //ImageView cityImage = (ImageView)findViewById(R.id.cityImage);
                    DecodeTask decodeTask = new DecodeTask();
                    URL url = null;
                    try {
                        url = new URL(keyImageUrl);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    decodeTask.execute(url);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void showpDialog() {

            ((TextView)findViewById(R.id.about)).setVisibility(View.INVISIBLE);
            ((TextView)findViewById(R.id.visit)).setVisibility(View.INVISIBLE);
            cityText.setVisibility(View.INVISIBLE);
            cityDesc.setVisibility(View.INVISIBLE);
            cityVisit.setVisibility(View.INVISIBLE);
            if (!pDialog.isShowing()) {
                pDialog.show();
            }
        }

        private void hidepDialog() {
            if (pDialog.isShowing())
                pDialog.dismiss();
        }
    }

    public class DecodeTask extends AsyncTask<URL, Void, Bitmap> {

        // COMPLETED (26) Override onPreExecute to set the loading indicator to visible
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected Bitmap doInBackground(URL... params) {
            Bitmap bp = null;
            try {
                bp = BitmapFactory.decodeStream(params[0].openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bp;
        }
        @Override
        protected void onPostExecute(Bitmap bp) {
            ImageView cityImage = (ImageView)findViewById(R.id.cityImage);
            cityImage.setImageBitmap(bp);
        }
    }
}

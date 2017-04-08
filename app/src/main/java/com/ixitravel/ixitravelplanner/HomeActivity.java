package com.ixitravel.ixitravelplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {
    Button b;
    AutoCompleteTextView autoCompleteTextView;
    String cityName[] = {"Tokyo","Manchester","Phuket","Los Angeles","Kuwait","Paris","Vijayawada",
            "Singapore","Dimapur","London","Las Vegas","Dibrugarh","Shanghai","Aizawl","New York",
            "Vancouver","Sydney","Pune","Dusseldorf","Istanbul","Vienna","Ahmedabad","Amritsar",
            "Abu Dhabi","Bhubaneshwar","Vadodara","Bhopal","Bangkok","Bengaluru","Mumbai","Calicut",
            "Kolkata","Coimbatore","Cochin","Dehradun","Dharmsala","Dibrugarh","Dimapur","Dubai",
            "Guwahati","Goa","Gorakhpur","Hyderabad"};

    String cityId[] = {"503b2a45e4b032e338f08631","503b2a40e4b032e338f07a67","503b2a4be4b032e338f0941b",
            "503b2a57e4b032e338f0b0bd","503b2a46e4b032e338f08799","503b2a3ee4b032e338f074e5",
            "503b2a5ee4b032e338f0c015","503b2acde4b032e338f1cd9f","503b2a8ae4b032e338f12c29",
            "503b2a40e4b032e338f07a8d","503b2a58e4b032e338f0b377","503b2a8ae4b032e338f12cb5",
            "503b2a38e4b032e338f06907","503b2a99e4b032e338f15197","503b2a54e4b032e338f0ab1b",
            "503b2a37e4b032e338f06581","503b2a32e4b032e338f05b9b","503b2a6be4b032e338f0e1a5",
            "503b2a3ae4b032e338f06db1","503b2a4be4b032e338f0954f","503b2a30e4b032e338f05999",
            "503b2a99e4b032e338f151db","503b2a98e4b032e338f14f01","503b2a2fe4b032e338f0583f",
            "503b2a91e4b032e338f13e53","503b2a5ee4b032e338f0c23d","503b2a91e4b032e338f13e79",
            "503b2a4be4b032e338f09475","503b2a95e4b032e338f14729","503b2a90e4b032e338f13ba5",
            "503b2a7ae4b032e338f106d3","503b2a8fe4b032e338f139ed","503b2a8de4b032e338f13391",
            "503b2a8de4b032e338f13395","503b2ab5e4b032e338f191ed","503b2a8ae4b032e338f12dc3",
            "503b2a8ae4b032e338f12cb5","503b2a8ae4b032e338f12c29","503b2a2fe4b032e338f05835",
            "503b2a87e4b032e338f1266b","503b2a87e4b032e338f124ab","503b2a86e4b032e338f12369",
            "503b2a84e4b032e338f11d53"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        b = (Button) findViewById(R.id.autoCompButton);

        autoCompleteTextView =
                (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView2);

        ArrayAdapter<String> autocompletetextAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, cityName);

        autoCompleteTextView.setAdapter(autocompletetextAdapter);
        /*
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(after != 0) {
                    b.setText("Go to This Location");

                } else {
                    b.setText("Show Me Trending Locations");
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        }); */
        //Drawable thumb = ContextCompat.getDrawable(this, R.mipmap.tourstravels);
        //l.setBackgroundResource(R.drawable.tourstravels);
        //l.setBackground(thumb);
    }
    public void goToCityActvity(View view){
        String city = autoCompleteTextView.getEditableText().toString();
        int c=0;
        for (String s : cityName) {
            c++;
            if (s.equals(city)) {
                String id = cityId[c];
                Intent intent = new Intent(this, CityDescriptionActivity.class);
                intent.putExtra("cityId", id);
                startActivity(intent);
            }
        }

    }

    public void trendingActvity(View view){
        Intent intent = new Intent(this, DestinationsActivity.class);
        startActivity(intent);
    }
}

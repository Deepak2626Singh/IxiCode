package com.ixitravel.ixitravelplanner;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Deepak on 4/8/2017.
 */


    public class CustomList extends ArrayAdapter<String> {
        private final Activity context;
        private ArrayList<Destination> destination;
        public CustomList(Activity context,
                          ArrayList<Destination> destination) {
            super(context, R.layout.list_single);
            this.context = context;
            this.destination = destination;

        }
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView= inflater.inflate(R.layout.list_single, null, true);
            TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

            ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
            txtTitle.setText(destination.get(position).cityName);

            URL url = null;
            try {
                url = new URL(destination.get(position).image);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Bitmap bmp = null;
            try {
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageView.setImageBitmap(bmp);
            //imageView.setImageResource(destination.get(position).image);
            return rowView;
        }
    }



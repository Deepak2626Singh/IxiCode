package com.ixitravel.ixitravelplanner;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
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


    public class CustomList extends ArrayAdapter<Destination> {
        private final Activity context;
        private ArrayList<Destination> destination;
        ImageView imageView;
        public CustomList(Activity context,
                          ArrayList<Destination> destination) {
            super(context, R.layout.list_single, destination);
            this.context = context;
            this.destination = destination;

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
            imageView.setImageBitmap(bp);
        }
    }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView==null)
            {
                LayoutInflater inflater = context.getLayoutInflater();
                convertView= inflater.inflate(R.layout.list_single,parent,false);
            }

            //View rowView= inflater.inflate(R.layout.list_single, null, true);
            TextView txtTitle = (TextView) convertView.findViewById(R.id.txt);


            imageView = (ImageView) convertView.findViewById(R.id.img);
            txtTitle.setText(destination.get(position).cityName);

            Log.v("rohit", "txtTtile = " + txtTitle);

            URL url = null;
            try {
                url = new URL(destination.get(position).image);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            DecodeTask decodeTask = new DecodeTask();
            decodeTask.execute(url);
            /*Bitmap bmp = null;
           try {
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageView.setImageBitmap(bmp);*/
            //imageView.setImageResource(destination.get(position).image);
            return convertView;
        }
    }



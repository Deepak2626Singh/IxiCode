package com.ixitravel.ixitravelplanner;

/**
 * Created by Deepak on 4/8/2017.
 */

public class Destination {
    String image;
    String name;
    String countryName;
    String url;
    String data;
    String text;
    String type;
    String cityName;
    String stateName;
    String price;
    String currency;
    String cityId;

    public Destination(String image, String name, String countryName, String url, String data,
                       String text, String type, String cityName, String stateName, String price,
                       String currency, String cityId) {
        this.image = image;
        this.name = name;
        this.countryName = countryName;
        this.url = url;
        this.data = data;
        this.text = text;
        this.type = type;
        this.cityName = cityName;
        this.stateName = stateName;
        this.price = price;
        this.currency = currency;
        this.cityId = cityId;
    }
}

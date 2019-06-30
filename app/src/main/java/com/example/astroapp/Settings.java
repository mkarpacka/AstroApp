package com.example.astroapp;

import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

class Settings {

    public static final String OPEN_WEATHER_MAP_API_KEY = "a203203f305d74fc5b59e13c09c6f48b";
    public static OpenWeatherMapHelper helper = new OpenWeatherMapHelper(OPEN_WEATHER_MAP_API_KEY);
    public static OpenWeatherMapHelper helper1 = new OpenWeatherMapHelper(OPEN_WEATHER_MAP_API_KEY);

    public static int numberofCities = 0;
    public static List<String> cities = new ArrayList<>();
    public static LinkedHashSet<String> hashSet = new LinkedHashSet<String>();
    public static int iteration;

    public static boolean afterUpdate = false;
    public static int refresh = 5000;
    public static boolean units;
    public static String inputCityToCheck;



    //first
    public static String city = " ";
    public static String description = " ";
    public static String temperature = " ";
    public static String pressure = " ";
    public static String image;
    public static double latitude = 50.68;
    public static double longitude = 19.85;


    //second
    public static String windSpeed = " ";
    public static String windDegree = " ";
    public static String humidity = " ";
    public static String clouds = " ";


    //third
    public static String temp1 = " ";
    public static String wind1 = " ";
    public static String image1;
    public static String temp2 = " ";
    public static String wind2 = " ";
    public static String image2;
    public static String temp3 = " ";
    public static String wind3 = " ";
    public static String image3;
    public static String temp4 = " ";
    public static String wind4 = " ";
    public static String image4;

}

package com.example.astroapp;

import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

class Settings {

    public static final String OPEN_WEATHER_MAP_API_KEY = "a203203f305d74fc5b59e13c09c6f48b";
    public static OpenWeatherMapHelper helper = new OpenWeatherMapHelper(OPEN_WEATHER_MAP_API_KEY);

    public static boolean afterUpdate = false;
    public static int updateIterator = 10;

    public static double latitude = 50.68;
    public static double longitude = 19.85;

    //first
    public static String city = " ";
    public static String description = " ";
    public static String temperature = " ";
    public static String pressure = " ";
    public static String lat = " ";
    public static String lon = " ";
    public static String image;


    public static int numberofCities = 0;
    public static List<String> cities = new ArrayList<>();
    public static LinkedHashSet<String> hashSet = new LinkedHashSet<String>();
    public static int iteration;
}

package com.example.astroapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kwabenaberko.openweathermaplib.implementation.callbacks.CurrentWeatherCallback;
import com.kwabenaberko.openweathermaplib.implementation.callbacks.ThreeHourForecastCallback;
import com.kwabenaberko.openweathermaplib.models.currentweather.CurrentWeather;
import com.kwabenaberko.openweathermaplib.models.threehourforecast.ThreeHourForecast;


public class WeatherInfo extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private TextView cityName;
    private TextView latitudeText;
    private TextView longitudeText;
    private TextView windSpeed;
    private TextView windDegree;
    private TextView humidity;
    private TextView clouds;


    public WeatherInfo() {
        // Required empty public constructor
    }

    public static WeatherInfo newInstance(String param1, String param2) {
        WeatherInfo fragment = new WeatherInfo();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_info, container, false);

        cityName = view.findViewById(R.id.cityName);
        latitudeText = view.findViewById(R.id.latitude);
        longitudeText = view.findViewById(R.id.longitude);
        windSpeed = view.findViewById(R.id.windSpeed);
        windDegree = view.findViewById(R.id.windDegree);
        humidity = view.findViewById(R.id.humidity);
        clouds = view.findViewById(R.id.clouds);

        cityName.setText(Settings.city);
        latitudeText.setText(Double.toString(Settings.latitude));
        longitudeText.setText(Double.toString(Settings.longitude));
        windSpeed.setText(Settings.windSpeed);
        windDegree.setText(Settings.windDegree);
        clouds.setText(Settings.clouds);
        humidity.setText(Settings.humidity);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void sampleExpandedWeatherInfo(String city) {
        Settings.helper.getCurrentWeatherByCityName(city, new CurrentWeatherCallback() {
            @Override
            public void onSuccess(CurrentWeather currentWeather) {
                Log.v("pogoda", "Coordinates: " + currentWeather.getCoord().getLat() + ", " + currentWeather.getCoord().getLon() + "\n"
                        + "Humidity " + currentWeather.getMain().getHumidity() + "\n"
                        + "Wind degree: " + currentWeather.getMain().getTempMax() + "\n"
                        + "Wind Speed: " + currentWeather.getWind().getSpeed() + "\n"
                        + "Clouds: " + currentWeather.getClouds().getAll() + "\n"
                        + "City, Country: " + currentWeather.getName() + ", " + currentWeather.getSys().getCountry()
                );


                cityName.setText(Settings.city);

                latitudeText.setText(Double.toString(Settings.latitude));

                longitudeText.setText(Double.toString(Settings.longitude));

                Settings.windSpeed = String.valueOf(currentWeather.getWind().getSpeed());
                windSpeed.setText(Settings.windSpeed);

                Settings.windDegree = String.valueOf(currentWeather.getWind().getDeg());
                windDegree.setText(Settings.windDegree);

                Settings.clouds = String.valueOf(currentWeather.getClouds().getAll());
                clouds.setText(Settings.clouds);

                Settings.humidity = String.valueOf(currentWeather.getMain().getHumidity());
                humidity.setText(Settings.humidity);

            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.v("pogoda", throwable.getMessage());
            }
        });
    }

}

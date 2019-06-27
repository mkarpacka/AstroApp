package com.example.astroapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kwabenaberko.openweathermaplib.constants.Units;
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;
import com.kwabenaberko.openweathermaplib.implementation.callbacks.CurrentWeatherCallback;
import com.kwabenaberko.openweathermaplib.models.currentweather.CurrentWeather;


public class Weather extends Fragment {


    private View view;
    private TextView cityName;
    private TextView info;
    private Button update;
    private TextView latitudeText;
    private TextView longitudeText;
    public static final String OPEN_WEATHER_MAP_API_KEY = "a203203f305d74fc5b59e13c09c6f48b";


    public Weather() {
    }

    public void sampleWeatherInfo() {
        OpenWeatherMapHelper helper = new OpenWeatherMapHelper(OPEN_WEATHER_MAP_API_KEY);
        helper.setUnits(Units.IMPERIAL);

        helper.getCurrentWeatherByCityName("Berlin", new CurrentWeatherCallback() {
            @Override
            public void onSuccess(CurrentWeather currentWeather) {
                Log.v("pogoda", "Coordinates: " + currentWeather.getCoord().getLat() + ", " + currentWeather.getCoord().getLon() + "\n"
                        + "Weather Description: " + currentWeather.getWeather().get(0).getDescription() + "\n"
                        + "Temperature: " + currentWeather.getMain().getTempMax() + "\n"
                        + "Wind Speed: " + currentWeather.getWind().getSpeed() + "\n"
                        + "City, Country: " + currentWeather.getName() + ", " + currentWeather.getSys().getCountry()
                );

                cityName.setText(currentWeather.getName());

                latitudeText.setText(Double.toString(currentWeather.getCoord().getLat()));
                longitudeText.setText(Double.toString(currentWeather.getCoord().getLon()));

            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.v("pogoda", throwable.getMessage());
                Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_weather, container, false);

        cityName = view.findViewById(R.id.cityName);
        info = view.findViewById(R.id.info);
        update = view.findViewById(R.id.update);
        latitudeText = (TextView) view.findViewById(R.id.latitude);
        longitudeText = (TextView) view.findViewById(R.id.longitude);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sampleWeatherInfo();
            }

        });


        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


}

package com.example.astroapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kwabenaberko.openweathermaplib.constants.Units;
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;
import com.kwabenaberko.openweathermaplib.implementation.callbacks.CurrentWeatherCallback;
import com.kwabenaberko.openweathermaplib.models.currentweather.CurrentWeather;
import com.squareup.picasso.Picasso;



public class Weather extends Fragment {


    private View view;
    private TextView cityName;
    private TextView temp;
    private TextView pres;
    private Button update;
    private TextView latitudeText;
    private TextView longitudeText;
    private ImageView weatherImage;
    private TextView description;
    public static final String OPEN_WEATHER_MAP_API_KEY = "a203203f305d74fc5b59e13c09c6f48b";


    public Weather() {
    }

    public void sampleWeatherInfo(String city) {
        OpenWeatherMapHelper helper = new OpenWeatherMapHelper(OPEN_WEATHER_MAP_API_KEY);
        helper.setUnits(Units.METRIC);


        helper.getCurrentWeatherByCityName(city, new CurrentWeatherCallback() {
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

                temp.setText(Double.toString(currentWeather.getMain().getTempMax()) + "°C");
                pres.setText(Double.toString(currentWeather.getMain().getPressure()) + "hPa");

                setWeahterImage(currentWeather.getWeather().get(0).getIcon());

                description.setText(currentWeather.getWeather().get(0).getDescription());

            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.v("pogoda", throwable.getMessage());
                Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setWeahterImage(String s) {
        String tempUrl = "http://openweathermap.org/img/wn/" + s + ".png";
        Picasso.with(getContext()).load(tempUrl).into(weatherImage);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_weather, container, false);

        cityName = view.findViewById(R.id.cityName);
        temp = view.findViewById(R.id.temp);
        pres = view.findViewById(R.id.pres);
        update = view.findViewById(R.id.update);
        latitudeText = (TextView) view.findViewById(R.id.latitude);
        longitudeText = (TextView) view.findViewById(R.id.longitude);
        description = view.findViewById(R.id.description);

        weatherImage = view.findViewById(R.id.weatherImage);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sampleWeatherInfo("Łódź");
            }

        });


        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


}

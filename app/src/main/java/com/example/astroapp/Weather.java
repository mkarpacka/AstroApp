package com.example.astroapp;

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
    private TextView latitudeText;
    private TextView longitudeText;
    private ImageView weatherImage;
    private TextView description;


    public Weather() {
    }

    public void sampleWeatherInfo(String city) {

        Settings.helper.setUnits(Units.METRIC);


        Settings.helper.getCurrentWeatherByCityName(city, new CurrentWeatherCallback() {
            @Override
            public void onSuccess(CurrentWeather currentWeather) {
                Log.v("pogoda", "Coordinates: " + currentWeather.getCoord().getLat() + ", " + currentWeather.getCoord().getLon() + "\n"
                        + "Weather Description: " + currentWeather.getWeather().get(0).getDescription() + "\n"
                        + "Temperature: " + currentWeather.getMain().getTempMax() + "\n"
                        + "Wind Speed: " + currentWeather.getWind().getSpeed() + "\n"
                        + "City, Country: " + currentWeather.getName() + ", " + currentWeather.getSys().getCountry()
                );

                Settings.city = currentWeather.getName();
                cityName.setText(Settings.city);

                Settings.latitude = currentWeather.getCoord().getLat();
                latitudeText.setText(Double.toString(Settings.latitude));

                Settings.longitude = currentWeather.getCoord().getLon();
                longitudeText.setText(Double.toString(Settings.longitude));

                Settings.temperature = Double.toString(currentWeather.getMain().getTempMax()) + "°C";
                temp.setText(Settings.temperature);

                Settings.pressure = currentWeather.getMain().getPressure() + "hPa";
                pres.setText(Settings.pressure);

                Settings.image = currentWeather.getWeather().get(0).getIcon();
                setWeahterImage(Settings.image);

                Settings.description = currentWeather.getWeather().get(0).getDescription();
                description.setText(Settings.description);


//                Toast.makeText(getActivity(), "Data downloaded", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.v("pogoda", throwable.getMessage());
                Toast.makeText(getActivity(), "Unable to download data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setWeahterImage(String s) {
        String tempUrl = "http://openweathermap.org/img/wn/" + s + ".png";
        Picasso.get().load(tempUrl).fit().into(weatherImage);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_weather, container, false);


        cityName = view.findViewById(R.id.cityName);
        temp = view.findViewById(R.id.temp);
        pres = view.findViewById(R.id.pres);
        latitudeText = view.findViewById(R.id.latitude);
        longitudeText = view.findViewById(R.id.longitude);
        description = view.findViewById(R.id.description);

        weatherImage = view.findViewById(R.id.weatherImage);

        cityName.setText(Settings.city);
        temp.setText(Settings.temperature);
        pres.setText(Settings.pressure);
        description.setText(Settings.description);
        latitudeText.setText(Double.toString(Settings.latitude));
        longitudeText.setText(Double.toString(Settings.longitude));

        sampleWeatherInfo("Los Angeles");
        setWeahterImage(Settings.image);

        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


}

package com.example.astroapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kwabenaberko.openweathermaplib.implementation.callbacks.ThreeHourForecastCallback;
import com.kwabenaberko.openweathermaplib.models.threehourforecast.ThreeHourForecast;
import com.squareup.picasso.Picasso;


public class WeatherForecast extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private TextView temp1;
    private TextView wind1;
    private ImageView image1;
    private TextView temp2;
    private TextView wind2;
    private ImageView image2;
    private TextView temp3;
    private TextView wind3;
    private ImageView image3;
    private TextView temp4;
    private TextView wind4;
    private ImageView image4;

    private TextView cityName;
    private TextView latitudeText;
    private TextView longitudeText;
    View view;

    public WeatherForecast() {
        // Required empty public constructor
    }

    public static WeatherForecast newInstance(String param1, String param2) {
        WeatherForecast fragment = new WeatherForecast();
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
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_weather_forecast, container, false);

        cityName = view.findViewById(R.id.cityName);
        latitudeText = view.findViewById(R.id.latitude);
        longitudeText = view.findViewById(R.id.longitude);

        temp1 = view.findViewById(R.id.temp1);
        wind1 = view.findViewById(R.id.wind1);
        image1 = view.findViewById(R.id.image1);
        temp2 = view.findViewById(R.id.temp2);
        wind2 = view.findViewById(R.id.wind2);
        image2 = view.findViewById(R.id.image2);
        temp3 = view.findViewById(R.id.temp3);
        wind3 = view.findViewById(R.id.wind3);
        image3 = view.findViewById(R.id.image3);
        temp4 = view.findViewById(R.id.temp4);
        wind4 = view.findViewById(R.id.wind4);
        image4 = view.findViewById(R.id.image4);


        cityName.setText(Settings.city);
        latitudeText.setText(Double.toString(Settings.latitude));
        longitudeText.setText(Double.toString(Settings.longitude));

        temp1.setText(Settings.temp1);
        temp2.setText(Settings.temp2);
        temp3.setText(Settings.temp3);
        temp4.setText(Settings.temp4);

        wind1.setText(Settings.wind1);
        wind2.setText(Settings.wind2);
        wind3.setText(Settings.wind3);
        wind4.setText(Settings.wind4);

        setWeahterImage(Settings.image1, image1);
        setWeahterImage(Settings.image2, image2);
        setWeahterImage(Settings.image3, image3);
        setWeahterImage(Settings.image4, image4);

        return view;
    }

    public void sampleForecastInfo(String city) {
        Settings.helper1.getThreeHourForecastByCityName(city, new ThreeHourForecastCallback() {
            @Override
            public void onSuccess(ThreeHourForecast threeHourForecast) {
                Log.v("pogoda", "City/Country: " + threeHourForecast.getCity().getName() + "/" + threeHourForecast.getCity().getCountry() + "\n"
                        + "Forecast Array Count: " + threeHourForecast.getCnt() + "\n"
                        //For this example, we are logging details of only the first forecast object in the forecasts array
                        + "First Forecast Date Timestamp: " + threeHourForecast.getList().get(0).getDt() + "\n"
                        + "First Forecast Weather Description: " + threeHourForecast.getList().get(0).getWeatherArray().get(0).getDescription() + "\n"
                        + "First Forecast Max Temperature: " + threeHourForecast.getList().get(0).getMain().getTempMax() + "\n"
                        + "First Forecast Wind Speed: " + threeHourForecast.getList().get(0).getWind().getSpeed() + "\n"
                );
                Log.v("pogoda", "City/Country: " + threeHourForecast.getCity().getName() + "/" + threeHourForecast.getCity().getCountry() + "\n"
                        + "Forecast Array Count: " + threeHourForecast.getCnt() + "\n"
                        //For this example, we are logging details of only the first forecast object in the forecasts array
                        + "Second Forecast Date Timestamp: " + threeHourForecast.getList().get(8).getDt() + "\n"
                        + "Second Forecast Weather Description: " + threeHourForecast.getList().get(8).getWeatherArray().get(0).getDescription() + "\n"
                        + "Second Forecast Max Temperature: " + threeHourForecast.getList().get(8).getMain().getTempMax() + "\n"
                        + "Second Forecast Wind Speed: " + threeHourForecast.getList().get(8).getWind().getSpeed() + "\n"
                );

                Log.v("pogoda", "City/Country: " + threeHourForecast.getCity().getName() + "/" + threeHourForecast.getCity().getCountry() + "\n"
                        + "Forecast Array Count: " + threeHourForecast.getCnt() + "\n"
                        //For this example, we are logging details of only the first forecast object in the forecasts array
                        + "Third Forecast Date Timestamp: " + threeHourForecast.getList().get(16).getDt() + "\n"
                        + "Third Forecast Weather Description: " + threeHourForecast.getList().get(16).getWeatherArray().get(0).getDescription() + "\n"
                        + "Third Forecast Max Temperature: " + threeHourForecast.getList().get(16).getMain().getTempMax() + "\n"
                        + "Third Forecast Wind Speed: " + threeHourForecast.getList().get(16).getWind().getSpeed() + "\n"
                );

                Settings.city = threeHourForecast.getCity().getName();

                cityName.setText(Settings.city);

                latitudeText.setText(Double.toString(Settings.latitude));

                longitudeText.setText(Double.toString(Settings.longitude));



                Settings.temp1 = String.valueOf(threeHourForecast.getList().get(0).getMain().getTempMax());
                Settings.wind1 = String.valueOf(threeHourForecast.getList().get(0).getWind().getSpeed());
                Settings.image1 = String.valueOf(threeHourForecast.getList().get(0).getWeatherArray().get(0).getIcon());

                Settings.temp2 = String.valueOf(threeHourForecast.getList().get(8).getMain().getTempMax());
                Settings.wind2 = String.valueOf(threeHourForecast.getList().get(8).getWind().getSpeed());
                Settings.image2 = String.valueOf(threeHourForecast.getList().get(8).getWeatherArray().get(0).getIcon());

                Settings.temp3 = String.valueOf(threeHourForecast.getList().get(16).getMain().getTempMax());
                Settings.wind3 = String.valueOf(threeHourForecast.getList().get(16).getWind().getSpeed());
                Settings.image3 = String.valueOf(threeHourForecast.getList().get(16).getWeatherArray().get(0).getIcon());

                Settings.temp4 = String.valueOf(threeHourForecast.getList().get(24).getMain().getTempMax());
                Settings.wind4 = String.valueOf(threeHourForecast.getList().get(24).getWind().getSpeed());
                Settings.image4 = String.valueOf(threeHourForecast.getList().get(24).getWeatherArray().get(0).getIcon());

                temp1.setText(Settings.temp1);
                wind1.setText(Settings.wind1);
                setWeahterImage(Settings.image1, image1);

                temp2.setText(Settings.temp2);
                wind2.setText(Settings.wind2);
                setWeahterImage(Settings.image2, image2);

                temp3.setText(Settings.temp3);
                wind3.setText(Settings.wind3);
                setWeahterImage(Settings.image3, image3);

                temp4.setText(Settings.temp4);
                wind4.setText(Settings.wind4);
                setWeahterImage(Settings.image4, image4);


            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.v("pogoda", throwable.getMessage());
            }
        });
    }

    public void setWeahterImage(String s, ImageView imageView) {
        String tempUrl = "http://openweathermap.org/img/wn/" + s + ".png";
        Picasso.get().load(tempUrl).fit().into(imageView);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


}

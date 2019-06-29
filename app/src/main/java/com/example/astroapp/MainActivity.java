package com.example.astroapp;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends FragmentActivity implements FragmentChangeListener, InputFragment.InputFragmentListener {

    private SunFragment sunFragment;
    private MoonFragment moonFragment;
    private Weather weatherFragment;
    private WeatherInfo weatherInfo;
    private WeatherForecast weatherForecast;
    private InputFragment inputFragment;
    private Button button;
    private Button localButton;
    private Button button2;
    private Button button3;
    private int currentFragment = 0;
    private TextView timeText;
    Thread t;

    SlidePagerAdapter mPagerAdapter;
    ViewPager mPager;


    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        inputFragment = InputFragment.newInstance("Ustawienia lokalizacji");

        inputFragment.show(fm, "fragment_edit_name");
    }

    public void startTimeThread() {
        t = new Thread() {


            @Override
            public void run() {

                while (!isInterrupted()) {
                    if (MainActivity.this == null)
                        return;

                    try {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                timeText = findViewById(R.id.time_place);

                                Calendar c = Calendar.getInstance();
                                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                                String formattedDate = df.format(c.getTime());

                                timeText.setText(formattedDate);
                            }
                        });
                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };

        t.start();
    }

    public void save() {
        SharedPreferences myPreferences
                = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor myEditor = myPreferences.edit();

        myEditor.putString("description", Settings.description);
        myEditor.putString("city", Settings.city);
        myEditor.putString("temperature", Settings.temperature);
        myEditor.putString("pressure", Settings.pressure);
        myEditor.putString("latitude", Settings.lat);
        myEditor.putString("longitude", Settings.lon);
        myEditor.putString("image", Settings.image);
        myEditor.commit();
        load();
        Log.v("pogoda", "saved");
        Log.v("pogoda", Settings.description);
    }

    public void load() {
        SharedPreferences myPreferences
                = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        Settings.description = myPreferences.getString("description", " ");
        Settings.city = myPreferences.getString("city", " ");
        Settings.temperature = myPreferences.getString("temperature", " ");
        Settings.pressure = myPreferences.getString("pressure", " ");
        Settings.lat = myPreferences.getString("latitude", " ");
        Settings.lon = myPreferences.getString("longitude", " ");
        Settings.image = myPreferences.getString("image", " ");

//        Settings.latitude = Double.parseDouble(Settings.lat);
//        Settings.longitude = Double.parseDouble(Settings.lon);
        fun();
        Log.v("pogoda", "loaded");
    }

    public void loadalways() {
        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        Settings.numberofCities = Integer.valueOf(myPreferences.getString("numberofCities", "0"));
        for (int i = 0; i <= Settings.numberofCities; i++) {
            Settings.hashSet.add("Lodz");
            Settings.cities.add(i, myPreferences.getString(("miasto" + i), "Lodz"));
        }
        Settings.iteration += 1;
    }

    @Override
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);

        fragmentTransaction.commit();
        Log.i("hej", "replaced");
    }

    @Override
    public void onFinishEditDialog(String inputText, String inputText2) {
        if (inputText != null && inputText2 != null) {
            moonFragment.setCoordinates(Settings.lat, Settings.lon);
            sunFragment.setCoordinates(Settings.lat, Settings.lon);
        }
    }

    public void fun() {
        moonFragment.setCoordinates(Settings.lat, Settings.lon);
        sunFragment.setCoordinates(Settings.lat, Settings.lon);
    }


    @Override
    public void setRefreshFrequency(int time) {
        sunFragment.refresh(time);
        moonFragment.refresh(time);
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if(Settings.iteration == 0) {
//            loadalways();
//        }

        setContentView(R.layout.activity_main);

        startTimeThread();

        sunFragment = new SunFragment();
        moonFragment = new MoonFragment();
        weatherFragment = new Weather();
        weatherInfo = new WeatherInfo();
        weatherForecast = new WeatherForecast();


        localButton = findViewById(R.id.set_localization_button);

        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);

        if (tabletSize) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, sunFragment);
            fragmentTransaction.replace(R.id.fragment_container2, moonFragment);

            fragmentTransaction.commit();
        } else {
            mPager = findViewById(R.id.ViewPager);

            List<Fragment> fragments = new ArrayList<>();
            fragments.add(weatherFragment);
            fragments.add(weatherInfo);
            fragments.add(weatherForecast);
            fragments.add(sunFragment);
            fragments.add(moonFragment);
            mPagerAdapter = new SlidePagerAdapter(getSupportFragmentManager(), fragments);
            mPager.setAdapter(mPagerAdapter);
        }


        localButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog();
            }

        });

        Log.v("pogoda", String.valueOf(haveNetworkConnection()));

        load();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (!haveNetworkConnection()) {
            Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();
            load();
        } else {
//            weatherFragment.sampleWeatherInfo("New York");
            save();
            Toast.makeText(getApplicationContext(), "Data saved", Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putDouble("s1", ((SunFragment) sunFragment).latitude);
        savedInstanceState.putDouble("s2", ((SunFragment) sunFragment).longitude);
        savedInstanceState.putInt("i1", sunFragment.refreshTimeToSafe);

        savedInstanceState.putDouble("s3", ((MoonFragment) moonFragment).latitude);
        savedInstanceState.putDouble("s4", ((MoonFragment) moonFragment).longitude);
        savedInstanceState.putInt("i2", moonFragment.refreshTimeToSafe);

        savedInstanceState.putInt("fragmentId", currentFragment);

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

//        sunFragment.latitude = savedInstanceState.getDouble("s1");
//        sunFragment.longitude = savedInstanceState.getDouble("s2");
//
//        sunFragment.longitudeText.setText(Double.toString(sunFragment.longitude));
//        sunFragment.latitudeText.setText(Double.toString(sunFragment.latitude));
//
//        moonFragment.latitude = savedInstanceState.getDouble("s3");
//        moonFragment.longitude = savedInstanceState.getDouble("s4");
//
//        moonFragment.refreshTimeToSafe = savedInstanceState.getInt("i2");
//        sunFragment.refreshTimeToSafe = savedInstanceState.getInt("i1");


//        int temp = savedInstanceState.getInt("fragmentId");
//
//        if (temp == 0) {
//            replaceFragment(sunFragment);
//        } else if (temp == 1) {
//            replaceFragment(moonFragment);
//            currentFragment = 1;
//        } else {
//            replaceFragment(weatherFragment);
//            currentFragment = 2;
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        t.isInterrupted();
    }


    @Override
    protected void onPause() {
        super.onPause();
        save();
    }


    @Override
    protected void onResume() {
        super.onResume();
        load();
    }
}







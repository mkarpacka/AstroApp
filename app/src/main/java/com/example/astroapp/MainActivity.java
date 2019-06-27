package com.example.astroapp;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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


public class MainActivity extends FragmentActivity implements FragmentChangeListener, InputFragment.InputFragmentListener {

    private SunFragment sunFragment;
    private MoonFragment moonFragment;
    private Weather weatherFragment;
    private InputFragment inputFragment;
    private Button button;
    private Button localButton;
    private Button button2;
    private Button button3;
    private int currentFragment = 0;


    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        inputFragment = InputFragment.newInstance("Ustawienia lokalizacji");

        inputFragment.show(fm, "fragment_edit_name");
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
            moonFragment.setCoordinates(inputText, inputText2);
        }
    }

    @Override
    public void onFinish(String inputText, String inputText2) {
        if (inputText != null && inputText2 != null) {
            sunFragment.setCoordinates(inputText, inputText2);
        }
    }

    @Override
    public void setRefreshFrequency(int time) {
        sunFragment.refresh(time);
        moonFragment.refresh(time);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sunFragment = new SunFragment();
        moonFragment = new MoonFragment();
        weatherFragment = new Weather();


        localButton = findViewById(R.id.set_localization_button);

        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);

        if (tabletSize) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, sunFragment);
            fragmentTransaction.replace(R.id.fragment_container2, moonFragment);

            fragmentTransaction.commit();
        } else {
            replaceFragment(sunFragment);


            button = (Button) findViewById(R.id.fragment_sun_button);
            button2 = (Button) findViewById(R.id.fragment_moon_button);
            button3 = findViewById(R.id.fragment_weather_button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replaceFragment(sunFragment);
                    currentFragment = 0;
                    System.out.println(currentFragment);

                }
            });
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replaceFragment(moonFragment);
                    currentFragment = 1;
                    System.out.println(currentFragment);
                }

            });
            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replaceFragment(weatherFragment);
                    currentFragment = 2;
                    System.out.println(currentFragment);
                }

            });
        }


        localButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog();
            }

        });


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

        sunFragment.latitude = savedInstanceState.getDouble("s1");
        sunFragment.longitude = savedInstanceState.getDouble("s2");

        sunFragment.longitudeText.setText(Double.toString(sunFragment.longitude));
        sunFragment.latitudeText.setText(Double.toString(sunFragment.latitude));

        moonFragment.latitude = savedInstanceState.getDouble("s3");
        moonFragment.longitude = savedInstanceState.getDouble("s4");

        moonFragment.refreshTimeToSafe = savedInstanceState.getInt("i2");
        sunFragment.refreshTimeToSafe = savedInstanceState.getInt("i1");


        int temp = savedInstanceState.getInt("fragmentId");

        if (temp == 0) {
            replaceFragment(sunFragment);
        } else {
            replaceFragment(moonFragment);
            currentFragment = 1;
        }
    }

}







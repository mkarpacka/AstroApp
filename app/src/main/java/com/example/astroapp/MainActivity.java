package com.example.astroapp;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends FragmentActivity implements FragmentChangeListener{
    private final FragmentManager fm = getSupportFragmentManager();
    private Fragment sunFragment;
    private Fragment moonFragment;
    private Button button;
    private Button button2;
    private TextView timeText;
//    final FragmentTransaction ft = this.fm.beginTransaction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sunFragment = new SunFragment();
        moonFragment = new MoonFragment();


        button = (Button) findViewById(R.id.fragment_sun_button);
        button2 = (Button) findViewById(R.id.fragment_moon_button);


        startTimeThread();
        replaceFragment(sunFragment);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(sunFragment);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(moonFragment);
            }

        });



    }

    @Override
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
//        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.commit();
    }

    public void startTimeThread(){
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                timeText = findViewById(R.id.time_place);

                                Calendar c = Calendar.getInstance();
                                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String formattedDate = df.format(c.getTime());

                                timeText.setText(formattedDate);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();
    }
}







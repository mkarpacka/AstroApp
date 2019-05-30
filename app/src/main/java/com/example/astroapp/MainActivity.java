package com.example.astroapp;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends FragmentActivity implements FragmentChangeListener, InputFragment.InputFragmentListener {
    private final FragmentManager fm = getSupportFragmentManager();
    private SunFragment sunFragment;
    private MoonFragment moonFragment;
    private Fragment inputFragment;
    private Button button;
    private Button localButton;
    private Button button2;
    private TextView timeText;

    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        InputFragment editNameDialogFragment = InputFragment.newInstance("Ustawienia lokalizacji");

        editNameDialogFragment.show(fm, "fragment_edit_name");
    }

    @Override
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
//        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.commit();
    }

    @Override
    public void onFinishEditDialog(String inputText, String inputText2) {

//        moonFragment = new MoonFragment();

        if(inputText != null && inputText2 != null){
            moonFragment.setCoordinates(inputText, inputText2);
            replaceFragment(moonFragment);
        }else Toast.makeText(this, "Podano złe dane", Toast.LENGTH_SHORT).show();




    }

    @Override
    public void onFinish(String inputText, String inputText2) {

        if(inputText != null && inputText2 != null){
            sunFragment.setCoordinates(inputText, inputText2);
//            sunFragment.setCoordinatesLongitude(inputText2);
            replaceFragment(sunFragment);
        }else Toast.makeText(this, "Podano złe dane", Toast.LENGTH_SHORT).show();

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sunFragment = new SunFragment();
        moonFragment = new MoonFragment();
        inputFragment = new InputFragment();



        localButton = findViewById(R.id.set_localization_button);

        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        if (tabletSize) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, sunFragment);
            fragmentTransaction.replace(R.id.fragment_container2, moonFragment);
//        fragmentTransaction.addToBackStack(fragment.toString());
            fragmentTransaction.commit();
        } else {
            replaceFragment(sunFragment);


            button = (Button) findViewById(R.id.fragment_sun_button);
            button2 = (Button) findViewById(R.id.fragment_moon_button);

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

//        startTimeThread();

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

        savedInstanceState.putDouble("s3", ((MoonFragment) moonFragment).latitude);
        savedInstanceState.putDouble("s4", ((MoonFragment) moonFragment).longitude);

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        ((SunFragment) sunFragment).setCoordinates(Double.toString(savedInstanceState.getDouble("s1")), Double.toString(savedInstanceState.getDouble("s2")));
//        ((SunFragment) sunFragment).setCoordinatesLongitude(Double.toString(savedInstanceState.getDouble("s2")));

        ((MoonFragment) moonFragment).setCoordinates(Double.toString(savedInstanceState.getDouble("s3")), Double.toString(savedInstanceState.getDouble("s4")));
//        ((MoonFragment) moonFragment).setCoordinatesLongitude(Double.toString(savedInstanceState.getDouble("s4")));
    }

}







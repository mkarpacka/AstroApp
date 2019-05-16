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


public class MainActivity extends FragmentActivity implements FragmentChangeListener, InputFragment.InputFragmentListener, MoonFragment.MoonFragmentListener {
    private final FragmentManager fm = getSupportFragmentManager();
    private Fragment sunFragment;
    private Fragment moonFragment;
    private Fragment inputFragment;
    private Button button;
    private Button localButton;
    private Button button2;
    private TextView timeText;
    String m_Text="";
//    final FragmentTransaction ft = this.fm.beginTransaction();

    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        InputFragment editNameDialogFragment = InputFragment.newInstance("Some Title");

        editNameDialogFragment.show(fm, "fragment_edit_name");
    }

    @Override
    public void onFinishEditDialog(String inputText) {
        Toast.makeText(this, "Hi, " + inputText, Toast.LENGTH_SHORT).show();
        MoonFragment moonFragment = new MoonFragment();
        getSupportFragmentManager().beginTransaction().
                add(R.id.fragment_container, moonFragment).commit();
        moonFragment.setCoordinatesLongitude(inputText);
    }

    @Override
    public void onFragmentInteraction(String s) {
        Toast.makeText(this, "Hi, " + s, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sunFragment = new SunFragment();
        moonFragment = new MoonFragment();
        inputFragment = new InputFragment();


        button = (Button) findViewById(R.id.fragment_sun_button);
        button2 = (Button) findViewById(R.id.fragment_moon_button);

        localButton = findViewById(R.id.set_localization_button);


//        startTimeThread();
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
        localButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog();
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


}







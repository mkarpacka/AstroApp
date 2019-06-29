package com.example.astroapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.kwabenaberko.openweathermaplib.constants.Units;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;


public class InputFragment extends DialogFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText mEditText;
    private EditText mEditText2;
    private Button okButton;
    private Button cancelButton;
    private Spinner spinner;
    private Switch mySwitch;
    View view;
    private InputFragmentListener listener;
    int newRefreshRate;
    private List<String> options = Arrays.asList("5 seconds", "15 seconds", "1 minute", "5 minutes");



    public InputFragment() { }

    public static InputFragment newInstance(String title) {
        InputFragment frag = new InputFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_blank, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEditText = (EditText) view.findViewById(R.id.dlugosc);
        okButton = (Button) view.findViewById(R.id.ok);
        cancelButton = (Button) view.findViewById(R.id.cancel);
        spinner = view.findViewById(R.id.settingsSpinner);
        mySwitch = view.findViewById(R.id.switch1);

        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings.units = isChecked;
                Settings.helper.setUnits(Units.IMPERIAL);
                // do something, the isChecked will be
                // true if the switch is in the On position
            }
        });

        spinner.setAdapter(new ArrayAdapter<String>(view.getContext(), R.layout.support_simple_spinner_dropdown_item, options));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0: newRefreshRate = 5000; break;
                    case 1: newRefreshRate = 15000; break;
                    case 2: newRefreshRate = 60000; break;
                    case 3: newRefreshRate = 300000; break;
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                newRefreshRate = 15000;
            }

        });


        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Settings.inputCityToCheck = mEditText.toString();
                Settings.refresh = newRefreshRate;

                dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });

    }



    public interface InputFragmentListener {
        void onFinishEditDialog(String inputText, String inputText2);
        void setRefreshFrequency(int time);
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InputFragmentListener) {
            listener = (InputFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }



}

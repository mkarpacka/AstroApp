package com.example.astroapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;


public class FavouritesFragment extends DialogFragment {

    private EditText mEditText;
    private Button okButton;
    private Button cancelButton;
    private Spinner spinner;
    private List<String> options = Arrays.asList(" ", " ");


    public FavouritesFragment() {
        // Required empty public constructor
    }

    public static FavouritesFragment newInstance(String title) {
        FavouritesFragment fragment = new FavouritesFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEditText = (EditText) view.findViewById(R.id.dlugosc);
        okButton = (Button) view.findViewById(R.id.ok);
        cancelButton = (Button) view.findViewById(R.id.cancel);
        spinner = view.findViewById(R.id.settingsSpinner);

        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        if(Settings.cities != null) {
            options = Settings.cities;
        }

        spinner.setAdapter(new ArrayAdapter<String>(view.getContext(), R.layout.support_simple_spinner_dropdown_item, options));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 0:
                        System.out.println(" zero"); break;
                    case 1: System.out.println(" one"); break;
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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

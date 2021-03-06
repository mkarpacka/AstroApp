package com.example.astroapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SunFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SunFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SunFragment extends Fragment {
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    private OnFragmentInteractionListener mListener;

    private TextView sunriseText;
    private TextView sunsetText;
    private TextView twilightMorningText;
    private TextView twilightEveningText;
     TextView latitudeText;
     TextView longitudeText;
    private View view;
    private String formattedDate;

    double latitude = 51.7;
    double longitude = 19.4;

    int refreshTimeToSafe;


    Thread t2;

    public SunFragment() {
        // Required empty public constructor
    }



    public void setCoordinates(String s, String s2){

        try{
            longitude = Double.parseDouble(s);
            latitude = Double.parseDouble(s2);
        }catch (Exception e){
//            makeErrorToast();
        }

        boolean check = checkValueOfCoordinates();
        if(longitudeText != null && latitudeText != null && check){
            longitudeText.setText(Double.toString(longitude));
            latitudeText.setText(Double.toString(latitude));
        }

    }

    public void refresh(int refTime){

        refreshTimeToSafe = refTime;
        final int refreshTime = refreshTimeToSafe;

//        Log.i("hej", "sun " + Integer.toString(refreshTime));
        t2 = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        if(getActivity() == null)
                            return;

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                longitudeText.setText(Double.toString(longitude));
                                latitudeText.setText(Double.toString(latitude));
                                sampleAstroInfo();
                                Log.i("hej", "sun " + Integer.toString(refreshTime));
                                if(getActivity()!=null){
                                    Toast.makeText(getActivity(), "Zaktualizowano", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                        Thread.sleep(refreshTime);
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t2.start();
    }

    public boolean checkValueOfCoordinates(){
        if(((longitude < -180.0 || longitude > 180.0) && ( latitude < -90.0 || latitude > 90.0))) {
            return true;
        }else return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sun, container,
                false);
        latitudeText = (TextView) view.findViewById(R.id.latitude);
        latitudeText.setText(Double.toString(latitude));

        longitudeText = (TextView) view.findViewById(R.id.longitude);
        longitudeText.setText(Double.toString(longitude));

        sampleAstroInfo();
        return view;
    }


    public void sampleAstroInfo(){
        sunriseText= (TextView) view.findViewById(R.id.sunrise);
        sunsetText = (TextView) view.findViewById(R.id.sunset);
        twilightMorningText = (TextView) view.findViewById(R.id.twilightMorning);
        twilightEveningText = (TextView) view.findViewById(R.id.twilightEvening);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formattedDate = df.format(c.getTime());

        String [] splitedDate = splitDate();
        String [] splitedTime = splitTime();


        AstroCalculator.Location astroLoc = new AstroCalculator.Location(latitude, longitude);

        AstroDateTime astroDateTime = new AstroDateTime();
        astroDateTime.setDay(Integer.parseInt(splitedDate[2]));
        astroDateTime.setMonth(Integer.parseInt(splitedDate[1]));
        astroDateTime.setYear(Integer.parseInt(splitedDate[0]));

        astroDateTime.setHour(Integer.parseInt(splitedTime[0]));
        astroDateTime.setMinute(Integer.parseInt(splitedTime[1]));
        astroDateTime.setSecond(Integer.parseInt(splitedTime[2]));

        astroDateTime.setTimezoneOffset(2);

        AstroCalculator astroCalculator = new AstroCalculator(astroDateTime, astroLoc);

        String sunrise = getPartOfSplitedDate(astroCalculator.getSunInfo().getSunrise().toString(),1);
        String sunset = getPartOfSplitedDate(astroCalculator.getSunInfo().getSunset().toString(), 1);
        String twilightMorning = getPartOfSplitedDate(astroCalculator.getSunInfo().getTwilightMorning().toString(), 1);
        String twilightEvening = getPartOfSplitedDate(astroCalculator.getSunInfo().getTwilightEvening().toString(), 1);


        sunriseText.setText(sunrise);
        sunsetText.setText(sunset);
        twilightMorningText.setText(twilightMorning);
        twilightEveningText.setText(twilightEvening);
    }


    public String[] splitDateTime(String formattedDate){

        String [] separateDateTime = formattedDate.split(" ");

        return separateDateTime;
    }

    public String[] splitTime(){

        String [] separateDateTime = splitDateTime(formattedDate);
        String [] separateHourMinSec = separateDateTime[1].split(":");

        return separateHourMinSec;
    }

    public String[] splitDate(){
        String [] separateDateTime = splitDateTime(formattedDate);
        String [] separateYearMonthDay = separateDateTime[0].split("-");

        return separateYearMonthDay;
    }

    public String getPartOfSplitedDate(String str, int index){
        String [] tempToFormatDate = splitDateTime(str);

        String returnedString = tempToFormatDate[index];

        return returnedString;
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment SunFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static SunFragment newInstance(String param1, String param2) {
//        SunFragment fragment = new SunFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_sun, container, false);
//    }
//
//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
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
//        t.isInterrupted();
//        t2.isInterrupted();


    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }

}

package com.example.astroapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    private TextView timeText;
    private TextView sunriseText;
    private TextView sunsetText;
    private TextView twilightMorningText;
    private TextView twilightEveningText;
    private View view;

    public SunFragment() {
        // Required empty public constructor
    }

    public void showOtherFragment()
    {
        Fragment fr=new MoonFragment();
        FragmentChangeListener fc=(FragmentChangeListener)getActivity();
        fc.replaceFragment(fr);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sun, container,
                false);

        startTimeThread();
        sampleAstroInfo();
        return view;
    }

    public void startTimeThread(){
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        if(getActivity() == null)
                            return;

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                timeText = (TextView) view.findViewById(R.id.time_place);

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

    public void sampleAstroInfo(){
        sunriseText= (TextView) view.findViewById(R.id.sunrise);
        sunsetText = (TextView) view.findViewById(R.id.sunset);
        twilightMorningText = (TextView) view.findViewById(R.id.twilightMorning);
        twilightEveningText = (TextView) view.findViewById(R.id.twilightEvening);

        AstroCalculator.Location astroLoc = new AstroCalculator.Location(51.7, 19.4);

        AstroDateTime astroDateTime = new AstroDateTime();
        astroDateTime.setDay(16);
        astroDateTime.setMonth(5);
        astroDateTime.setYear(2019);

        AstroCalculator astroCalculator = new AstroCalculator(astroDateTime, astroLoc);

        String sunrise = astroCalculator.getSunInfo().getSunrise().getHour()+ ":" + astroCalculator.getSunInfo().getSunrise().getMinute() + ":" + astroCalculator.getSunInfo().getSunrise().getSecond();
        String sunset = astroCalculator.getSunInfo().getSunset().getHour()+ ":" + astroCalculator.getSunInfo().getSunset().getMinute() + ":" + astroCalculator.getSunInfo().getSunset().getSecond();
        String twilightMorning = astroCalculator.getSunInfo().getTwilightMorning().toString();
        String twilightEvening = astroCalculator.getSunInfo().getTwilightEvening().toString();


        sunriseText.setText(sunrise);
        sunsetText.setText(sunset);
        twilightMorningText.setText(twilightMorning);
        twilightEveningText.setText(twilightEvening);
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
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
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

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class InputFragment extends DialogFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Zabezpieczenie wpisywanych danych, layouty na tablet i horyzontalny na telefon, refresh, zapamiętywanie przy obracaniu
    private String mParam1;
    private String mParam2;
    private EditText mEditText;
    private EditText mEditText2;
    private Button okButton;
    private Button cancelButton;
    View view;

//    private OnFragmentInteractionListener mListener;

    public InputFragment() {
    }

    public static InputFragment newInstance(String title) {
        InputFragment frag = new InputFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
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

        view = inflater.inflate(R.layout.fragment_blank, container, false);
        String pass = "";


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEditText = (EditText) view.findViewById(R.id.dlugosc);
        mEditText2 = (EditText) view.findViewById(R.id.szerokosc);
        okButton = (Button) view.findViewById(R.id.ok);
        cancelButton = (Button) view.findViewById(R.id.cancel);




        String title = getArguments().getString("title", "Podaj współrzędne");
        getDialog().setTitle(title);

        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("hej", "clicked");
                InputFragmentListener listener = (InputFragmentListener) getActivity();
                    listener.onFinishEditDialog(mEditText.getText().toString(), mEditText2.getText().toString());
                    listener.onFinish(mEditText.getText().toString(), mEditText2.getText().toString());
//                    Toast.makeText(getContext(), "Zaktualizowano ustawienia", Toast.LENGTH_SHORT).show();
                    dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("hej", "clicked");
                dismiss();
            }
        });

    }

//    @Override
//    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//        if (EditorInfo.IME_ACTION_DONE == actionId) {
//            // Return input text back to activity through the implemented listener
//            InputFragmentListener listener = (InputFragmentListener) getActivity();
//            listener.onFinishEditDialog(mEditText.getText().toString());
//            // Close the dialog and return back to the parent activity
//            dismiss();
//            return true;
//        }
//        return false;
//    }


    public interface InputFragmentListener {
        void onFinishEditDialog(String inputText, String inputText2);
        void onFinish(String inputText, String inputText2);
    }





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

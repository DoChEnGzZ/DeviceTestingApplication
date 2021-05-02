package com.example.devicetestingapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.FileDescriptor;
import java.io.PrintWriter;

import me.rosuh.filepicker.config.FilePickerManager;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link menuFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link menuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class menuFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button btn_connect;
    private Button btn_addr;
    private Button btn_update;
    private View thisview;
    private boolean afterRotate=false;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        afterRotate=true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!(afterRotate || getActivity() == null)) {
            final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            final Fragment fragment = fragmentManager.findFragmentById(R.id.container);
            if (fragment != null)
                fragmentManager.beginTransaction().remove(fragment).commit();
        }
        super.onDestroyView();
    }

    @Override
    public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
        super.dump(prefix, fd, writer, args);
    }

    private OnFragmentInteractionListener mListener;

    public menuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment menuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static menuFragment newInstance(String param1, String param2) {
        menuFragment fragment = new menuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        // Inflate the layout for this fragment
        try {
            thisview=inflater.inflate(R.layout.fragment_menu, container, false);
        } catch (Exception e) {
            Log.d("debug",e.getMessage());
            throw  e;
        }
        initviews();
        setOnclick();
        return thisview;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private <T extends View> T f(int ViewID){
        return thisview.findViewById(ViewID);
    }

    private void initviews(){
        btn_connect=f(R.id.btn_id_connect);
        btn_addr=f(R.id.btn_id_setaddr);
        btn_update=f(R.id.btn_id_update);
    }

    private void setOnclick()
    {
        btn_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2Connect=new Intent(getActivity(),ConnectActivity.class);
                startActivity(intent2Connect);
            }
        });
        btn_addr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2setAddr=new Intent(getActivity(),SetAddrActivity.class);
                startActivity(intent2setAddr);
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2Update=new Intent(getActivity(),UpdateActivity.class);
                startActivity(intent2Update);
            }
        });
    }
}

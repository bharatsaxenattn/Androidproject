package com.example.fragmentlifecycle;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Fragment1 extends Fragment {
    public static final String TAG = "Fragmeent --------";

    public Fragment1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_1, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, " oncreate executed");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.v(TAG, " onATtach executed");

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.v(TAG, " onViewcreate executed");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(TAG, " onResume executed");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.v(TAG, " onPause executed");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.v(TAG, " onStop executed");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.v(TAG, " onDestroy executed");
    }

    @Override
    public void onDetach() {
        super.onDetach();

        Log.v(TAG, " onDetach executed");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(TAG, " onDestroy executed");
    }


}

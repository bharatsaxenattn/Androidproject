package com.example.roomdatabase;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements View.OnClickListener {
    Button btnAddEmp,displayBtn,delBtn,updateBtn;

    public MainFragment() {
        // Required empty public constructor
    }


    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();

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
        View view=inflater.inflate(R.layout.fragment_main, container, false);
        btnAddEmp=view.findViewById(R.id.addBtn);
        delBtn=view.findViewById(R.id.delBtn);
        updateBtn=view.findViewById(R.id.updateBtn);
        btnAddEmp.setOnClickListener(this);
        displayBtn=view.findViewById(R.id.viewBtn);
        displayBtn.setOnClickListener(this);
        delBtn.setOnClickListener(this);
        updateBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.addBtn:
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragmentContainer,new AddemployeeFragment()).
                        addToBackStack(null).commit();
                        break;

            case R.id.viewBtn:
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragmentContainer,new DisplayEmployeeFragment())
                        .addToBackStack(null).commit();
                break;

            case R.id.delBtn:
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragmentContainer,new DeleteFragment())
                        .addToBackStack(null).commit();
                break;


            case R.id.updateBtn:
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragmentContainer,new UpdateFragment())
                        .addToBackStack(null).commit();
                break;



        }

    }
}

package com.example.fragmentlifecycle;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListDescriptionFragement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListDescriptionFragement extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView textView;
    public ListDescriptionFragement() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListDescriptionFragement.
     */
    // TODO: Rename and change types and number of parameters
    public static ListDescriptionFragement newInstance(String param1, String param2) {
        ListDescriptionFragement fragment = new ListDescriptionFragement();
        Bundle args = new Bundle();
        args.putString(TITLE, param1);
        args.putString(DESCRIPTION, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(TITLE);
            mParam2 = getArguments().getString(DESCRIPTION);

        }
    }

    private void setText(String mParam1, String mParam2) {
        textView.setText(mParam2+" ");


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_list_description_fragement, container, false);
        if(view instanceof TextView)
        {
            Log.v("exe","true");
            textView=(TextView)view;
            setText( mParam1, mParam2);
        }



        // Inflate the layout for this fragment
        return view;
    }
}

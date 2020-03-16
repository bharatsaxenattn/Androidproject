package com.example.fragmentlifecycle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.fragmentlifecycle.POJO.List_Items;


public class ListFragment extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener {
    FragmentManager fragmentManager;
    private static final String TAG = "ListFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_fragment);
        fragmentManager = getSupportFragmentManager();

        if(getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE)
        {
            makeLandscapeView();
            Log.v(TAG,"exe");
        }

        else
        {
            makePortraitOrientation();
            Log.v(TAG,"portrait");
        }



    }


    private void makePortraitOrientation() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ItemFragment fragment1 = new ItemFragment();
        fragmentTransaction.replace(R.id.list, fragment1, "fragList");
      //  fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    private void makeLandscapeView() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ItemFragment fragment1 = new ItemFragment();
        fragmentTransaction.replace(R.id.list, fragment1, "fragList");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


        FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
        ListDescriptionFragement listDescriptionFragement = ListDescriptionFragement.newInstance("hello", "welcome to ttn");

        fragmentTransaction1.replace(R.id.listItems, listDescriptionFragement, "fragDescription");
        //fragmentTransaction1.addToBackStack(null);
        fragmentTransaction1.commit();

    }

    @Override
    public void onListFragmentInteraction(List_Items item) {

        if(getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE)
        {
            ListDescriptionFragement listDescriptionFragement = new ListDescriptionFragement();
            Bundle bundle = new Bundle();
            bundle.putString("title", item.getTitle());
            bundle.putString("description", item.getDescription());
            listDescriptionFragement.setArguments(bundle);
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
         //  fragmentTransaction.addToBackStack(null) ;
            fragmentTransaction.replace(R.id.listItems, listDescriptionFragement, "frag2").commit();

        }
        else
        {
            ListDescriptionFragement listDescriptionFragement = new ListDescriptionFragement();
            Bundle bundle = new Bundle();
            bundle.putString("title", item.getTitle());
            bundle.putString("description", item.getDescription());
            listDescriptionFragement.setArguments(bundle);
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.replace(R.id.list, listDescriptionFragement, "frag2").commit();
        }
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if(getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE)
//            makeLandscapeView();
//        else
//            makePortraitOrientation();
//    }
}

package com.example.firstlayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ActivityFragment extends AppCompatActivity {
    Button buttonAdd,buttonAdd2,button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        buttonAdd=findViewById(R.id.buttonAdd);
        buttonAdd2=findViewById(R.id.buttonAdd1);
        button3=findViewById(R.id.buttonAdd2);
        final FragmentManager fragmentManager=getSupportFragmentManager();
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                Fragment1 fragment1=new Fragment1();
                fragmentTransaction.add(R.id.LinearFragment,fragment1,"frag1");
                fragmentTransaction.commit();


            }
        });
        buttonAdd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                Fragment2 fragment2=new Fragment2();
                fragmentTransaction.add(R.id.LinearFragment1,fragment2,"frag2");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment2 fragment2=new Fragment2();
                 Fragment fragment = getSupportFragmentManager().findFragmentByTag("frag2");
                if(fragment != null) {
                    Log.v("Activityfragment","exe");
                }
                else
                {
                    Log.v("Activityfragment","else exe");
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.LinearFragment,fragment2,"frag2").commit();
            }
        });

    }
}

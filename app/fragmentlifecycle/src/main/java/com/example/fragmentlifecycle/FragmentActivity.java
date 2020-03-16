package com.example.fragmentlifecycle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentActivity extends AppCompatActivity implements View.OnClickListener {
    Button buttonAdd,buttonAdd2,button3,hide,show,dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        buttonAdd=findViewById(R.id.buttonAdd);
        buttonAdd2=findViewById(R.id.buttonAdd1);
        button3=findViewById(R.id.buttonAdd2);
        hide=findViewById(R.id.buttonAdd3);
        show=findViewById(R.id.buttonAdd4);
        dialog=findViewById(R.id.dialog);

        /*implementing  click listener of all buttons  */
        buttonAdd.setOnClickListener(this);
        buttonAdd2.setOnClickListener(this);
        button3.setOnClickListener(this);
        hide.setOnClickListener(this);
        show.setOnClickListener(this);
        dialog.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        final FragmentManager fragmentManager=getSupportFragmentManager();
    switch (v.getId()){

        case R.id.buttonAdd:
            //for adding fragment 1
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            Fragment1 fragment1=new Fragment1();
            fragmentTransaction.add(R.id.LinearFragment,fragment1,"frag1");
            fragmentTransaction.commit();
            break;


        case R.id.buttonAdd1:
            //for adding fragment 2
            Intent intent=new Intent(this,ListFragment.class);
            startActivity(intent);

            /*
            FragmentTransaction fragmentTransaction1=fragmentManager.beginTransaction();
            Fragment2 fragment2=new Fragment2();
            fragmentTransaction1.add(R.id.LinearFragment1,fragment2,"frag2");
            fragmentTransaction1.addToBackStack(null);
            fragmentTransaction1.commit();

             */
            break;
        case R.id.buttonAdd2:
            //for replacing the fragment
            Fragment2 fragment21=new Fragment2();
            Fragment fragment = getSupportFragmentManager().findFragmentByTag("frag2");
            if(fragment != null) {
                Log.v("Activityfragment","exe");
            }
            else
            {
                Log.v("Activityfragment","else exe");
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.LinearFragment,fragment21,"frag2").commit();
            break;

        case R.id.buttonAdd3:
            //for hiding fragment
            Fragment2 fragment22= (Fragment2) getSupportFragmentManager().findFragmentByTag("frag2");

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.hide(fragment22).commit();
            break;

        case R.id.buttonAdd4:

            //for showing fragment
            Fragment2 frag2= (Fragment2) getSupportFragmentManager().findFragmentByTag("frag2");
            FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
            fragmentTransaction2.show(frag2).commit();
            break;

        case R.id.dialog:
            DialogBox dialogBox=new DialogBox();

            FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
            dialogBox.show(fragmentTransaction3,"dialogbox");
            break;






    }
    }
}

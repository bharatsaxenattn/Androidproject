package com.example.roomdatabase;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DisplayEmployeeFragment extends Fragment {

    TextView textView;
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    List<Employee> list=new ArrayList<>();
    public DisplayEmployeeFragment() {
        // Required empty public constructor
    }


    public static DisplayEmployeeFragment newInstance(String param1, String param2) {
        DisplayEmployeeFragment fragment = new DisplayEmployeeFragment();

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
        View view=inflater.inflate(R.layout.fragment_display_employee, container, false);
       // textView=view.findViewById(R.id.txtDisplaydata);
        recyclerView=view.findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        GetEmployeeAsyncTask getEmployeeAsyncTask=new GetEmployeeAsyncTask();
        getEmployeeAsyncTask.execute();



        //textView.setText(info);

        return view;
    }

    class GetEmployeeAsyncTask extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {
            List<Employee> employees=MainActivity.roomDatabase.myDao().getEmployee();

            String info="";
            for (Employee employee:employees) {
                int id=employee.getId();
                String name=employee.getName();
                String add=employee.getAddress();
                String num=employee.getNumber();

                info=info+"\n\n"+"Id : "+id+"\n name: "+name+"\n address : "+add+"\nnumber : "+num;
                list.add(new Employee(id,name,add,num));
            }

            return null ;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            myAdapter=new MyAdapter(getContext(),list);;
            recyclerView.setAdapter(myAdapter);
            myAdapter.notifyDataSetChanged();

        }
    }
}

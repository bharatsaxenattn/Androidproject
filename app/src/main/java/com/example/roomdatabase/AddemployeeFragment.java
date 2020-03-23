package com.example.roomdatabase;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AddemployeeFragment extends Fragment implements View.OnClickListener {
    Button addBtn;
    EditText id,name,address,number;
    String emp_name,emp_number,emp_address;
    int emp_id;


    public AddemployeeFragment() {
        // Required empty public constructor
    }


    public static AddemployeeFragment newInstance(String param1, String param2) {
        AddemployeeFragment fragment = new AddemployeeFragment();

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
        View view=inflater.inflate(R.layout.fragment_addemployee, container, false);
        addBtn=view.findViewById(R.id.addEmpBtn);
        id=view.findViewById(R.id.edt_emp_id);
        name=view.findViewById(R.id.edt_emp_name);
        number=view.findViewById(R.id.edt_emp_number);
        address=view.findViewById(R.id.edt_emp_add);

        addBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        id.getText().toString();
        if(v.getId()==R.id.addEmpBtn)
        {
           String id1= id.getText().toString();
             emp_id=Integer.parseInt(id1);
             emp_name=name.getText().toString();
             emp_number=number.getText().toString();
             emp_address=address.getText().toString();

             AddEmpAsynTask addEmpAsynTask=new AddEmpAsynTask();
             addEmpAsynTask.execute();







        }


    }

    class AddEmpAsynTask extends AsyncTask<Void,Void,Void>
    {


        @Override
        protected Void doInBackground(Void... voids) {
            Employee employee=new Employee(emp_id,emp_name,emp_number,emp_address);
//            employee.setId(emp_id);
//            employee.setName(emp_name);
//            employee.setNumber(emp_number);
//            employee.setAddress(emp_address);

            MainActivity.roomDatabase.myDao().addEmployee(employee);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getActivity(),"emp added sucessfully",Toast.LENGTH_LONG).show();
            name.setText("");
            number.setText("");
            address.setText("");
            id.setText("");
        }
    }
}

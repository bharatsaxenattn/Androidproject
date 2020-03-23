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

public class UpdateFragment extends Fragment implements View.OnClickListener {
    Button updateBtn;
    EditText id,name,address,number;
    String emp_name,emp_number,emp_address;
    int emp_id;


    public UpdateFragment() {
        // Required empty public constructor
    }


    public static UpdateFragment newInstance(String param1, String param2) {
        UpdateFragment fragment = new UpdateFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_update, container, false);
        // Inflate the layout for this fragment
        updateBtn=view.findViewById(R.id.updateEmpBtn);
        id=view.findViewById(R.id.edt_emp_id);
        name=view.findViewById(R.id.edt_emp_name);
        number=view.findViewById(R.id.edt_emp_number);
        address=view.findViewById(R.id.edt_emp_add);

        updateBtn.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
    if (v.getId()==R.id.updateEmpBtn)
    {
        String id1= id.getText().toString();
         emp_id=Integer.parseInt(id1);
         emp_name=name.getText().toString();
         emp_number=number.getText().toString();
         emp_address=address.getText().toString();
        UpdateEmpAsynTask updateEmpAsynTask=new UpdateEmpAsynTask();
        updateEmpAsynTask.execute();

    }
    }
    class UpdateEmpAsynTask extends AsyncTask<Void,Void,Void>
    {


        @Override
        protected Void doInBackground(Void... voids) {
            Employee employee=new Employee(emp_id,emp_name,emp_number,emp_address);
//            employee.setId(emp_id);
//            employee.setName(emp_name);
//            employee.setNumber(emp_number);
//            employee.setAddress(emp_address);

            MainActivity.roomDatabase.myDao().updateEmployee(employee);

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

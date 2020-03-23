package com.example.roomdatabase;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Delete;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class DeleteFragment extends Fragment implements View.OnClickListener {

    EditText editText;
    Button button;

    public DeleteFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DeleteFragment newInstance(String param1, String param2) {
        DeleteFragment fragment = new DeleteFragment();

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
        View view=inflater.inflate(R.layout.fragment_delete, container, false);
        button=view.findViewById(R.id.delEmployee);
        editText=view.findViewById(R.id.txt_del);
        button.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.delEmployee)
        {

            int id= Integer.parseInt(editText.getText().toString());
            DeleteEmpAsynTask deleteEmpAsynTask=new DeleteEmpAsynTask();
            deleteEmpAsynTask.execute(id);

        }

    }

    class DeleteEmpAsynTask extends AsyncTask
    {


        @Override
        protected Object doInBackground(Object[] objects) {
            int id=(int)objects[0];
            Employee employe=new Employee();
            employe.setId(id);
            MainActivity.roomDatabase.myDao().deleteUser(employe);


            return id;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            int id=(int)o;
            Toast.makeText(getContext(),"Employee with id "+id+" is deleted",Toast.LENGTH_LONG).show();
        }
    }
}

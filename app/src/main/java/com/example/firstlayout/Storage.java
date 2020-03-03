package com.example.firstlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Storage extends AppCompatActivity {
    EditText editText;
    TextView textView;
    Button submit,retrieve,read,write;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);
        editText=findViewById(R.id.storageEdit);
        textView=findViewById(R.id.storageText);
        submit=findViewById(R.id.submit);
        retrieve=findViewById(R.id.retrieve);
        read=findViewById(R.id.read);
        write=findViewById(R.id.write);
        sharedPreferences=getSharedPreferences("info",MODE_PRIVATE);
        editor=sharedPreferences.edit();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=editText.getText().toString();
                if(text.equals(""))
                {
                    editText.setError("Please enter the text");
                }
                else
                {
                 editor.putString("text",text).commit();
                 Toast.makeText(Storage.this,"Stored Sucessfully text =="+text,Toast.LENGTH_SHORT).show();;
                }
            }
        });
        retrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=sharedPreferences.getString("text","");
                Toast.makeText(Storage.this," text =="+text,Toast.LENGTH_SHORT).show();
                textView.setText(text);
            }
        });

        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=editText.getText().toString();
                if(text.equals(""))
                {
                    editText.setError("please provide the text");
                }
                else
                {
                    writeIntoFile(text);
                }
            }
        });
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readFile();
            }
        });



    }

    private void readFile() {
        FileInputStream fin = null;
        int c;
        String temp="";
        try {
            fin = openFileInput("file.txt");
            while( (c = fin.read()) != -1){
                temp = temp + Character.toString((char)c);
            }
            fin.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        editText.setText("");
        Toast.makeText(Storage.this,"text=="+temp,Toast.LENGTH_SHORT).show();




    }

    private void writeIntoFile(String text) {
        try {
            FileOutputStream fOut = openFileOutput("file.txt", Context.MODE_PRIVATE);
            fOut.write(text.getBytes());
            Toast.makeText(Storage.this,"text writtten sucessfully in file",Toast.LENGTH_SHORT).show();

            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package com.example.activityintents;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;

public class Home extends AppCompatActivity implements View.OnClickListener {
    private static final int CAMERACODE =101 ;
    TextView name,mobile,email,password;
    EditText url;
    Intent intent;
    Button button,cameraBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        intent=getIntent();
        String user=intent.getStringExtra(MainActivity.USER_NAME);
        String mobile1=intent.getStringExtra(MainActivity.MOBILE);
        String email1=intent.getStringExtra(MainActivity.EMAIL);
        String password1=intent.getStringExtra(MainActivity.PASSWORD);

        button=findViewById(R.id.BtnUrl);
        name=findViewById(R.id.username);
        email=findViewById(R.id.email);
        mobile=findViewById(R.id.mobile);
        password=findViewById(R.id.password);
        cameraBtn=findViewById(R.id.cameraBtn);
        url=findViewById(R.id.url);

        name.setText("User name : "+user);
        mobile.setText("Mobile :"+mobile1);
        email.setText("email :"+email1);
        password.setText("password "+password1);
        button.setOnClickListener(this);
        cameraBtn.setOnClickListener(this);


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.BtnUrl:
                String urlText=url.getText().toString();
                if(!urlText.equals("") && (urlText.contains("http://wwww") || urlText.contains("https://www")))
                {
                    Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(urlText));
                    startActivity(intent);
                }
                else {

                    Toast.makeText(Home.this,"Please enter valid url to browse",Toast.LENGTH_SHORT).show();
                }
                break;

            case  R.id.cameraBtn:
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERACODE);
                }
                else
                {
                    Toast.makeText(Home.this,"permission  already granted",Toast.LENGTH_LONG).show();
                }

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==CAMERACODE && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(Home.this,"permission granted",Toast.LENGTH_LONG).show();
        }else
        {
            Toast.makeText(Home.this,"permission denied by user",Toast.LENGTH_LONG).show();
        }
    }
}

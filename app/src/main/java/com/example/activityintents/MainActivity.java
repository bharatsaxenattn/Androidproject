package com.example.activityintents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText name,email,phone,password;
    Button signup;
    public static final String USER_NAME="user_name";
    public static final String MOBILE="mobile";
    public static final String EMAIL="email";
    public static final String PASSWORD="password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name=findViewById(R.id.usr_name);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        password=findViewById(R.id.password);

        signup=findViewById(R.id.btn);
        signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn:
                validateFields();
        }


    }

    private void validateFields() {

        if(name.getText().toString().equals(""))
        {
            name.setError("Name canot be empty");
        }
        else if(!validateEmail(email.getText().toString()))
        {
            email.setError("Please provide valid email");
        }
        else if(!validatePhone())
            phone.setError("Please provide valid phone number");
        else if(password.getText().length()<1)
            password.setError("please provide valid password");
        else
        {
            Intent intent=new Intent(MainActivity.this,Home.class);
            intent.putExtra(USER_NAME,name.getText().toString());
            intent.putExtra(EMAIL,email.getText().toString());
            intent.putExtra(MOBILE,phone.getText().toString());
            intent.putExtra(PASSWORD,password.getText().toString());
            startActivity(intent);
            finish();
        }
    }

    private boolean validatePhone() {
        String phno=phone.getText().toString();
        return phno.length()==10?true:false;
    }

    private boolean validateEmail(String email) {
        String pat="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(pat);

    }
}

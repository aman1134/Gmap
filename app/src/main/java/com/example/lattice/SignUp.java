package com.example.lattice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;

public class SignUp extends AppCompatActivity {

    public static LatLng location;
    String name, email, address, phone, password;
    EditText et_name , et_email, et_address, et_phone, et_password;
    Button loc, signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        et_name = (EditText) findViewById(R.id.name);
        et_email = (EditText) findViewById(R.id.email);
        et_address = (EditText) findViewById(R.id.address);
        et_phone = (EditText) findViewById(R.id.phone);
        et_password = (EditText) findViewById(R.id.password);

        loc = (Button) findViewById(R.id.loc);
        signup = (Button) findViewById(R.id.signup);

    }
}
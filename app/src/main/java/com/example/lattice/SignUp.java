package com.example.lattice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    public static LatLng location;
    String name, email, address, phone, password;
    EditText et_name , et_email, et_address, et_phone, et_password;
    Button loc;
    public static Button signup;

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
        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = et_email.getText().toString();
                name = et_name.getText().toString();
                address = et_address.getText().toString();
                password = et_password.getText().toString();
                phone = et_phone.getText().toString();

                if(!isValidEmail(email)) {
                    et_email.setError("Enter the valid email address");
                    et_email.setFocusable(true);
                }
                if(!isValidPassword(password)) {
                    et_password.setError("Password must contain one lowercase one uppercase and one no.");
                    et_password.setFocusable(true);
                }
                if(!isValidString(name , 4)){
                    et_name.setError("Enter your containing atleast 4 characters");
                    et_name.setFocusable(true);
                }
                if(!isValidString(address , 10)){
                    et_address.setError("Enter address containing atleast 10 characters");
                    et_address.setFocusable(true);
                }
                if(!isValidPhone(phone)){
                    et_phone.setError("Enter the phone no. with the country code");
                    et_phone.setFocusable(true);
                }

                if(isValidPhone(phone) && isValidString(name , 4) && isValidString(address , 10 ) && isValidEmail(email) && isValidPassword(password)){
                    startActivity(new Intent(SignUp.this , MapsActivity.class));
                }
            }
        });

        signup = (Button) findViewById(R.id.signup);
        signup.setVisibility(View.INVISIBLE);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = et_email.getText().toString();
                name = et_name.getText().toString();
                address = et_address.getText().toString();
                password = et_password.getText().toString();
                phone = et_phone.getText().toString();

                if(!isValidEmail(email)) {
                    et_email.setError("Enter the valid email address");
                    et_email.setFocusable(true);
                }
                if(!isValidPassword(password)) {
                    et_password.setError("Enter the Password ");
                    et_password.setFocusable(true);
                }
                if(!isValidString(name , 4)){
                    et_name.setError("Enter your containing atleast 4 characters");
                    et_name.setFocusable(true);
                }
                if(!isValidString(address , 10)){
                    et_password.setError("Enter address containing atleast 10 characters");
                    et_password.setFocusable(true);
                }
                if(!isValidPhone(phone)){
                    et_phone.setError("Enter the phone no. with the country code");
                    et_phone.setFocusable(true);
                }
                if(location == null){
                    Toast.makeText(SignUp.this ,"Select your Location by clicking \nthe Select your Location button" , Toast.LENGTH_LONG).show();
                    signup.setVisibility(View.INVISIBLE);
                }
                if(isValidPhone(phone) && isValidString(name , 4) && isValidString(address , 10 ) && isValidEmail(email) && isValidPassword(password) && location != null){
                    User user = new User(email, name, phone, address, password, location.latitude , location.longitude);
                    String jsonString = user.toString();
                    AppDatabase db = AppDatabase.getInstance(SignUp.this);
                    db.userDao().insert(user);
                    SharedPreferences sharedPreferences = SignUp.this.getSharedPreferences("user" , MODE_PRIVATE);
                    sharedPreferences.edit().putBoolean("logged_in" , true).apply();
                    Toast.makeText(SignUp.this , "You are logged in" , Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUp.this , DisplayUser.class));
                }
            }
        });

    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])" +
                "(?=.*[a-z])" +
                "(?=.*[A-Z])" +
                "(?=\\S+$).{8,15}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }

    public boolean isValidString(final String str , int length){
        if(str == null || str.length() < length)
            return false;
        else
            return true;
    }

    public boolean isValidPhone(final String target){
        Pattern pattern;
        Matcher matcher;

        final String PHONE_PATTERN = "^(?=.*[0-9])" +
                "(?=.*+)"+
                "(?=\\S+$).{13,}$";

        pattern = Pattern.compile(PHONE_PATTERN);
        matcher = pattern.matcher(target);

        return matcher.matches();
    }

}
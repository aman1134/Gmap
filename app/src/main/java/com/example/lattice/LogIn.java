package com.example.lattice;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class LogIn extends AppCompatActivity {

    EditText et_email, et_password;
    Button signin, signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        et_email = (EditText) findViewById(R.id.email);
        et_password = (EditText) findViewById(R.id.password);

        signup = (Button) findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogIn.this , SignUp.class));
            }
        });
        signin = findViewById(R.id.login);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isValidEmail(et_email.getText().toString())) {
                    et_email.setError("Enter the valid email address");
                    et_email.setFocusable(true);
                }
                if(et_password.getText().toString().isEmpty()) {
                    et_password.setError("Enter the Password ");
                    et_password.setFocusable(true);
                }

                if(isValidEmail(et_email.getText().toString()) && !et_password.getText().toString().isEmpty()){

                    Observable.fromCallable(() -> {

                        AppDatabase db = AppDatabase.getInstance(LogIn.this);
                        User user = db.userDao().checkEmail(et_email.getText().toString());
                        if(user != null && user.getPassword().equals(et_password.getText().toString())) {
                            startActivity(new Intent(LogIn.this, DisplayUser.class));
                        }
                        else
                            return true;
                        return false;
                    })
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe((result) -> {
                                if(result)
                                    Toast.makeText(LogIn.this , "Entered email or password \nis incorrect" , Toast.LENGTH_SHORT).show();
                            });

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
}
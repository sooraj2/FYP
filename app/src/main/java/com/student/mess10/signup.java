package com.student.mess10;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity {

    EditText name,pass,con_pass,email,phone;
    String data_name,data_pass,data_conf_pass, data_phone,data_email;

    private String url = "https://172.16.163.31:2048/fyp/signup.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name=(EditText) findViewById(R.id.s_name);
        pass=(EditText) findViewById(R.id.s_password);
        con_pass=(EditText) findViewById(R.id.s_confirmpass);
        email=(EditText) findViewById(R.id.s_email);
        phone=(EditText) findViewById(R.id.s_phone);

    }

    public void login_redirect(View view){
        startActivity(new Intent(signup.this, MainActivity.class));
    }

    public void signup(View view){
        data_name = name.getText().toString().trim();
        data_pass = pass.getText().toString().trim();
        data_conf_pass = con_pass.getText().toString().trim();
        data_email = email.getText().toString().trim();
        data_phone = phone.getText().toString().trim();



        if(!data_name.equals("")&& !data_pass.equals("")&& !data_conf_pass.equals("")&& !data_email.equals("")&& !data_phone.equals("")){

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("Success")) {
                        Toast.makeText(signup.this, "signup successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(signup.this, MainActivity.class));
                    }
                    else
                        Toast.makeText(signup.this, response, Toast.LENGTH_SHORT).show();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(signup.this, error.toString().trim(),Toast.LENGTH_LONG).show();
                    Log.e("error in signup", "----- ye kya ho gaya" );
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> data= new HashMap<>();
                    data.put("username",data_name);
                    data.put("password",data_pass);
                    return data;
                }
            };

            RequestQueue requestqueue= Volley.newRequestQueue(getApplicationContext());
            requestqueue.add(stringRequest);

        }
        else{
            Toast.makeText(signup.this, "Please Enter all the information to proceed.",Toast.LENGTH_LONG).show();
        }


    }




}
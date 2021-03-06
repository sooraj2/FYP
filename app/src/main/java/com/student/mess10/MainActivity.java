package com.student.mess10;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText username,password;
    private String uname, pass;
    private String url = "https://10.102.128.73:2048/fyp/login";
    public static final String PREF_FILE = "com.student.mess10";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences(PREF_FILE, MODE_PRIVATE);

        username= (EditText) findViewById(R.id.username);
        password= (EditText) findViewById(R.id.input_password);

    }

    public void Register(View view){
        startActivity(new Intent(MainActivity.this, signup.class));
    }

    public void Login(View view){
        startActivity(new Intent(MainActivity.this, Dashboard.class));
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", "Demo name");
        editor.putString("logged_in", "true");
        editor.apply();

        uname= username.getText().toString().trim();
        pass= password.getText().toString().trim();
        if(!uname.equals("")&& !pass.equals("")){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("Success")) {
                        Toast.makeText(MainActivity.this, "login successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, Dashboard.class));
                    }
                    else
                        Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, error.toString().trim(),Toast.LENGTH_LONG).show();

                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> data= new HashMap<>();
                    data.put("username",uname);
                    data.put("password",pass);
                    return data;
                }
            };
            ApiCall.getInstance(this).addToRequestQueue(stringRequest);
        }
        else{
            Toast.makeText(MainActivity.this, "Please Enter all the data",Toast.LENGTH_LONG).show();
        }
    }
}
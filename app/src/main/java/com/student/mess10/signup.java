package com.student.mess10;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class signup extends AppCompatActivity {

    EditText name,pass,con_pass,email,phone;
    String data_name,data_pass,data_conf_pass, data_phone,data_email, mess_id;

//    String[] items =  {"mess-1", "mess-2", "mess-3"};
    ArrayList<String> items = new ArrayList<>();
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;

    private String url = "http://192.168.1.108:9093/user/save";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name=(EditText) findViewById(R.id.s_name);
        pass=(EditText) findViewById(R.id.s_password);
        con_pass=(EditText) findViewById(R.id.s_confirmpass);
        email=(EditText) findViewById(R.id.s_email);
        phone=(EditText) findViewById(R.id.s_phone);

        autoCompleteTxt = findViewById(R.id.mess_auto_complete);
        getMesses();

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                mess_id= item;
                Toast.makeText(getApplicationContext(),"Item: "+item,Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getMesses(){

        String url = "http://192.168.1.108:9093/mess/findAllMessIds";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,null , new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            Gson gson= new Gson();
            Type type = new TypeToken<ArrayList<String>>(){}.getType();
            items = gson.fromJson(String.valueOf(response), type);
            adapterItems = new ArrayAdapter<String>(signup.this,R.layout.list_item,items);
            autoCompleteTxt.setAdapter(adapterItems);
        }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(signup.this, error.toString().trim(),Toast.LENGTH_LONG).show();
            }
        });

            ApiCall.getInstance(signup.this).addToRequestQueue(request);

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

        if(!data_name.equals("") && !data_pass.equals("") &&
            !data_conf_pass.equals("")&& !data_email.equals("")
            && !data_phone.equals("") && mess_id.equals("")) {

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
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> data= new HashMap<>();
                    data.put("username",data_name);
                    data.put("contact",data_phone);
                    data.put("email",data_email);
                    data.put("password",data_pass);
                    data.put("mess_id",data_pass);
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
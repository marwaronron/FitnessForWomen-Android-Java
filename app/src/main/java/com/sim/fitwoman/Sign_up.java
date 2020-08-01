package com.sim.fitwoman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sim.fitwoman.R;
import com.sim.fitwoman.service.MySingleton;
import com.sim.fitwoman.utils.WSadressIP;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Sign_up extends AppCompatActivity {
    Button btnLogin;
    EditText etName , etEmail , etPwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // add toolbar back button
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(getString(R.string.app_name));
        mToolbar.setNavigationIcon(R.drawable.m31);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   finish();
                Intent i = new Intent(Sign_up.this, MainActivity.class);
                i.putExtra("okok", "ok");

                startActivity(i);
            }
        });

        btnLogin = (Button) findViewById(R.id.email_sign_up_button);
        etName = (EditText) findViewById(R.id.input_name);
        etEmail = (EditText) findViewById(R.id.email);
        etPwd = (EditText) findViewById(R.id.password);
        RegisterProcess();
    }
    void RegisterProcess(){


        btnLogin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    if(!etName.getText().toString().equals("") && !etEmail.getText().toString().equals("") && !etPwd.getText().toString().equals("")){

                        dontExist();

                    }else{
                    Toast.makeText(getApplicationContext(),"Fill all data before register",Toast.LENGTH_SHORT).show();
                    }

                    }
                }
        );



    }



    public void dontExist(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ WSadressIP.WSIP+"/FitWomanServices/MIfUserEmailExist.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                            if(array.length() == 0){
                                addThisUser();
                            }else{
                                Toast.makeText(getApplicationContext(),"YOUR EMAIL ALREADY EXIST",Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                params.put("Content-Type","application/x-www-form-urlencoded");


                params.put("emailUser", etEmail.getText().toString());

                return params;
            }
        };

        //adding our stringrequest to queue
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);

    }



    public void addThisUser(){
        final String   URL = "http://"+ WSadressIP.WSIP+"/FitWomanServices/addUserFromApp.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("success")) {

                    ////////////////////////////////////
                    Intent intent = new Intent("com.sim.fitwoman.MainActivity");


                    startActivity(intent);
                    ////////////////////
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"failed to Register",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                params.put("name", etName.getText().toString());
                params.put("email",etEmail.getText().toString());
                params.put("pwd",etPwd.getText().toString());
                params.put("logintype", "AndroidApp");
                params.put("photo", "nothing");
                return params;
            }
        };

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}




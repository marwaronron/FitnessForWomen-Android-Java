package com.sim.fitwoman;


import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.sim.fitwoman.R;
import com.sim.fitwoman.service.MySingleton;
import com.sim.fitwoman.utils.WSadressIP;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddMealActivity extends AppCompatActivity {


    Button btnAddActivity,skip;
    RadioButton Breakfast , Lunch , Dinner , Other ;
   // Integer idUser = 4;
    String SPname , SPemail , SPweight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);


        //get user data from shared preferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(AddMealActivity.this);
        SPname = preferences.getString("name", "");
        SPemail = preferences.getString("email", "");
        SPweight = preferences.getString("weight", "");


        // Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Meal");

        String userName = this.getIntent().getStringExtra("userName");
        String userEmail = this.getIntent().getStringExtra("userEmail");

        //find user by email

        Breakfast = findViewById(R.id.breakfast);
        Lunch = findViewById(R.id.lunch);
        Dinner = findViewById(R.id.dinner);
        Other = findViewById(R.id.other);
        btnAddActivity = findViewById(R.id.ok);
        skip = findViewById(R.id.cancel);

        BtnToAddActivity();

        skip.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Intent intent = new Intent(AddMealActivity.this,Home.class);

                        startActivity(intent);



                    }
                }
        );


    }



    public void BtnToAddActivity(){
        btnAddActivity.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AddMeal();





                    }
                }
        );
    }



    public void AddMeal(){

        final String   URL = "http://"+ WSadressIP.WSIP+"/FitWomanServices/Meal/addMeal.php";





        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("success")) {
                    Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddMealActivity.this,SelectIngredient.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"failed to add",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
               String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                params.put("Content-Type","application/x-www-form-urlencoded");

                if(Breakfast.isChecked())
                {params.put("type","Breakfast");}

                else if(Lunch.isChecked())
                { params.put("type","Lunch");}

                else if(Dinner.isChecked())
                {  params.put("type","Dinner");}

                else if(Other.isChecked())
                {  params.put("type","Other");}


                  params.put("day", date);
                params.put("emailUser", SPemail);
               // params.put("idUser", String.valueOf(idUser));

                return params;
            }
        };

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);


    }




}

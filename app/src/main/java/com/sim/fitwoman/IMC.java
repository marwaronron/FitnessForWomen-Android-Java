package com.sim.fitwoman;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sim.fitwoman.R;
import com.sim.fitwoman.service.MySingleton;
import com.sim.fitwoman.utils.WSadressIP;

import java.util.HashMap;
import java.util.Map;

public class IMC extends AppCompatActivity {
    TextView txtUserName;
    EditText txtWeight, txtHeight ,txtAge ;
    ImageView imgNext;
    String userName;
    String userEmail , userPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imc);
        txtUserName = findViewById(R.id.MtextView);
        txtWeight =findViewById(R.id.MeditText);
        txtHeight =findViewById(R.id.MeditText2);
        txtAge =findViewById(R.id.MeditText3);
        imgNext = findViewById(R.id.MimageView);

       userName = this.getIntent().getStringExtra("userName");
        userEmail = this.getIntent().getStringExtra("userEmail");
        userPhoto = this.getIntent().getStringExtra("photo");
        imgNext.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!txtWeight.getText().toString().equals("") && !txtHeight.getText().toString().equals("") && !txtAge.getText().toString().equals("")) {

                                    goToImcResult();
                        } else {
                            Toast.makeText(getApplicationContext(), "Enter Data", Toast.LENGTH_SHORT).show();
                        }
                    }});

        txtUserName.setText(userName);

    }
    void goToImcResult(){

                        final String   URL = "http://"+ WSadressIP.WSIP+"/FitWomanServices/updateUser.php";





                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.contains("success")) {
                                    Intent intent = new Intent("com.sim.fitwoman.Home");
                                    intent.putExtra("userName", userName); //this 2 lines for pass the values to the next activity
                                    intent.putExtra("userEmail", userEmail);
                                    startActivity(intent);

                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(),"failed to update",Toast.LENGTH_SHORT).show();
                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("Content-Type","application/x-www-form-urlencoded");

                                params.put("email", userEmail);

                                params.put("lastWeight", txtWeight.getText().toString());
                                params.put("height", txtHeight.getText().toString());
                                params.put("age", txtAge.getText().toString());
                                params.put("mesureUnit", "metric");
                                return params;
                            }
                        };

                        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

                        //SHARED PREF
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(IMC.this);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("name",userName);
                        editor.putString("email",userEmail);
                        editor.putString("weight",txtWeight.getText().toString());
                        editor.putString("height",txtHeight.getText().toString());
                        editor.putString("age",txtAge.getText().toString());
                        editor.putString("photo",userPhoto);
                        String StringBMI = getBMI(txtWeight.getText().toString(),txtHeight.getText().toString());

                        editor.putString("bmi",StringBMI);
                        editor.commit();


    }
    public String getBMI(String TWeight,String THeight){
        Float FWeight = Float.valueOf(TWeight);
        Float FHeight = Float.valueOf(THeight);
        Float IMC = 10000 *(FWeight/(FHeight * FHeight));
        String Res="";
        if (IMC < 18.5){
            Res = "1";
        } else if(IMC > 18.5 && IMC < 25){
            Res = "2";
        } else if(IMC > 25 ){
            Res = "3";
        }
        return Res;
    }
}

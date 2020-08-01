package com.sim.fitwoman;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class updateWeight extends AppCompatActivity {
EditText theWeight;
Button btnOk , btnCancel;
    String SPname, SPemail, SPweight ,SPheight, SPBMI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_weight);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SPname = preferences.getString("name", "");
        SPemail = preferences.getString("email", "");
        SPweight = preferences.getString("weight", "");
        SPheight = preferences.getString("height", "");
        SPBMI = preferences.getString("bmi", "");
        ////////////////

        btnCancel = findViewById(R.id.button5cancel);
        btnCancel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );
        ////////////////
        theWeight = findViewById(R.id.editText3);
        btnOk = findViewById(R.id.button4);
        btnOk.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                   if(!theWeight.getText().toString().equals("") ){
                       /////////////////
                       final String   URL = "http://"+ WSadressIP.WSIP+"/FitWomanServices/MAddDailyBMI.php";
                       StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                           @Override
                           public void onResponse(String response) {
                               if(response.contains("success")) {

                                   //update weight and bmi
                                   SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                   SPheight = preferences.getString("height", "");
                                   SharedPreferences.Editor prefsEditr = preferences.edit();
                                   prefsEditr.putString("weight",theWeight.getText().toString());
                                   String StringBMI = getBMI(theWeight.getText().toString(),SPheight);

                                   prefsEditr.putString("bmi",StringBMI);
                                   prefsEditr.apply();


                                   //go back to profil
                                   Intent i = new Intent(updateWeight.this, Home.class);
                                   i.putExtra("openprofil", "open");

                                   startActivity(i);

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

                               params.put("day", date);

                               params.put("emailUser", SPemail);

                               params.put("weight",  theWeight.getText().toString());
                               params.put("height",  SPheight);
                               return params;
                           }
                       };

                       MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
                       /////////////////


                   }else{
                        Toast.makeText(getApplicationContext(),"Enter your weight ",Toast.LENGTH_SHORT).show();
                   }
                   }
                }
        );


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

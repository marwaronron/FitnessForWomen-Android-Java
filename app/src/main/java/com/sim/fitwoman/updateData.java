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

public class updateData extends AppCompatActivity {
    EditText txtAge, txtHeight;
    Button btnAdd, btnCancel;
    String SPname, SPemail, SPweight ,SPheight, SPBMI, SPAge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);

        //1:set items
        txtAge = findViewById(R.id.editText313);
        txtHeight = findViewById(R.id.editText3456);
        btnCancel = findViewById(R.id.button5cancelagain);
        btnAdd = findViewById(R.id.button4confirm);

        //2: get shared preferences data
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SPname = preferences.getString("name", "");
        SPemail = preferences.getString("email", "");
        SPweight = preferences.getString("weight", "");
        SPheight = preferences.getString("height", "");
        SPBMI = preferences.getString("bmi", "");
        SPAge = preferences.getString("age", "");

        //3: set cancel btn
        btnCancel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );

        //4: confirm btn
        btnAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!txtAge.getText().toString().equals("") && !txtHeight.getText().toString().equals("") ){
                         updateData(txtAge.getText().toString(),txtHeight.getText().toString());
                        }else if(txtAge.getText().toString().equals("") && !txtHeight.getText().toString().equals("") ){
                            updateData(SPAge,txtHeight.getText().toString());
                        } else if(!txtAge.getText().toString().equals("") && txtHeight.getText().toString().equals("") ){
                            updateData(txtAge.getText().toString(),SPheight);
                        }else{
                            Toast.makeText(getApplicationContext(),"Enter your height and/or age",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    public void updateData(final String UserNewAge , final String UserNewHeigth){
        final String   URL = "http://"+ WSadressIP.WSIP+"/FitWomanServices/MUpdateAgeHeight.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("success")) {

                    //update weight and bmi
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SPheight = preferences.getString("height", "");
                    SharedPreferences.Editor prefsEditr = preferences.edit();
                    prefsEditr.putString("height",UserNewHeigth);
                    prefsEditr.putString("age",UserNewAge);
                    String StringBMI = getBMI(SPweight,UserNewHeigth);

                    prefsEditr.putString("bmi",StringBMI);
                    prefsEditr.apply();


                    //go back to profil
                    Intent i = new Intent(updateData.this, Home.class);
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



                params.put("emailUser", SPemail);
                params.put("weight",  SPweight);
                params.put("age",  UserNewAge);
                params.put("height",  UserNewHeigth);
                return params;
            }
        };

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
        /////////////////
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

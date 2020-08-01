package com.sim.fitwoman;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sim.fitwoman.R;
import com.sim.fitwoman.adapter.BMIHistoricAdapter;
import com.sim.fitwoman.model.MBMIHistoric;
import com.sim.fitwoman.utils.WSadressIP;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BMI_Historic extends AppCompatActivity {
    ListView listView;
    List<MBMIHistoric> lstccM;
    String SPname, SPemail, SPweight ,SPheight;
    private static final String URL_Activities = "http://"+ WSadressIP.WSIP+"/FitWomanServices/MgetDailyBMIByUser.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi__historic);

        //1: toolbar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar4);
        mToolbar.setTitle(getString(R.string.app_name));
        mToolbar.setNavigationIcon(R.drawable.m31);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        //2: set list view
        listView = (ListView) findViewById(R.id.bmi_historic_listview);
        lstccM = new ArrayList<>();
        loadActivities();

        //3: get data from shared preferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SPname = preferences.getString("name", "");
        SPemail = preferences.getString("email", "");
        SPweight = preferences.getString("weight", "");
        SPheight = preferences.getString("height", "");

    }
    private void loadActivities() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_Activities,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                lstccM.add(new MBMIHistoric(

                                        product.getString("day"),

                                       product.getString("weight"),

                                       product.getString("BMI"),
                                       product.getString("BMI_result")
                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            BMIHistoricAdapter adapter = new BMIHistoricAdapter(getApplicationContext(), R.layout.bmi_historic_row,lstccM);
                            adapter.notifyDataSetChanged();
                            listView.setAdapter(adapter);




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
                params.put("Content-Type","application/x-www-form-urlencoded");



                params.put("emailUser", SPemail);

                return params;
            }
        };;

        //adding our stringrequest to queue
        Volley.newRequestQueue(BMI_Historic.this).add(stringRequest);
    }
}

package com.sim.fitwoman;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sim.fitwoman.R;
import com.sim.fitwoman.adapter.MealHistoricAdapter;
import com.sim.fitwoman.model.MealHistoric;
import com.sim.fitwoman.utils.WSadressIP;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Meals_Historic extends AppCompatActivity {
    ListView listView;
    List<MealHistoric> lstccM;
    List<MealHistoric> lstcs;
    private static final String URL_Activities = "http://"+ WSadressIP.WSIP+"/FitWomanServices/Meal/getMealByUser.php";
    String SPname, SPemail;
    EditText searchingActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals__historic);

        //1: toolbar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar5);
        mToolbar.setTitle(getString(R.string.app_name));
        mToolbar.setNavigationIcon(R.drawable.m31);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        TextView nomeals = (TextView) findViewById(R.id.nomeals);

        //2: list view
        listView = (ListView) findViewById(R.id.meals_historic_listview);
        lstccM = new ArrayList<>();
        //get searched text
        searchingActivity = findViewById(R.id.editTexti);
        searchingActivity.setOnKeyListener(
                new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {


                        //loadActivities();
                       searchByDate();

                        //on item list click move to add activity

                        return true;
                    }
                }
        );

        loadMeals();
        listView.setEmptyView(nomeals);
        //3: get data from shared preferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SPname = preferences.getString("name", "");
        SPemail = preferences.getString("email", "");

    }
    private void loadMeals() {


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
                                lstccM.add(new MealHistoric(
                                        product.getInt("id"),
                                        product.getString("day"),
                                        product.getString("type"),
                                        product.getString("totalCalories") ,
                                        product.getInt("idUser")
                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                           MealHistoricAdapter adapter = new    MealHistoricAdapter(getApplicationContext(), R.layout.meals_historic_row,lstccM);
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
        Volley.newRequestQueue(Meals_Historic.this).add(stringRequest);
    }


    public void searchByDate(){
        String s = searchingActivity.getText().toString();

        if(s.length() > 0) {
            lstcs = new ArrayList<>();
            for (MealHistoric i : lstccM) {
                if (i.getDay().toUpperCase().contains(s.toUpperCase())) {
                    lstcs.add(new MealHistoric(i.getId(), i.getDay(),i.getType(), i.getTotalCalories()));
                }
            }

            MealHistoricAdapter adapter = new   MealHistoricAdapter (Meals_Historic.this, R.layout.meals_historic_row, lstcs);
            adapter.notifyDataSetChanged();
            listView.setAdapter(adapter);
        }else{
            MealHistoricAdapter  adapter = new    MealHistoricAdapter (Meals_Historic.this, R.layout.meals_historic_row, lstccM);
            adapter.notifyDataSetChanged();
            listView.setAdapter(adapter);
        }

    }
}

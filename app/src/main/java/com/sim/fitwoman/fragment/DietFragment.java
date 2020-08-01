package com.sim.fitwoman.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import android.support.v4.app.FragmentActivity;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sim.fitwoman.R;
import com.sim.fitwoman.SingleDiet;
import com.sim.fitwoman.adapter.DietAdapter;
import com.sim.fitwoman.model.diet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sim.fitwoman.utils.WSadressIP;

public class DietFragment extends Fragment {

    ListView lv;
    List<diet> lstcc;
    private static final String URL_Activities = "http://"+ WSadressIP.WSIP+"/FitWomanServices/Meal/MgetDietByBMI.php";
    String SharedPrefBMI;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_diet, container, false);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext() );
        String name = preferences.getString("name", "");
        SharedPrefBMI  = preferences.getString("bmi", "");

        lv =  v.findViewById(R.id.diet_listview);
        lstcc = new ArrayList<>();

        loadActivities();

        ///////////////
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
                diet data= (diet) arg0.getItemAtPosition(arg2);
               // String nameS = data.getDay();
                String type = data.getType();
                String descriptionS = data.getDescription();
                String imageS = data.getImage();
              //  String calories = data.getCalories();
                //Log.d("DATA ITEM MEALS : " ,descriptionS ) ;
                //  Toast.makeText(getContext(),stepsS,Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getContext(), SingleDiet.class);
             //   i.putExtra("day", nameS);
           //  Toast.makeText(getContext(),type,Toast.LENGTH_SHORT).show();
                i.putExtra("type",type);
                i.putExtra("description",descriptionS);
                i.putExtra("imageS", imageS);
                // i.putExtra("stepsS",stepsS);

                startActivity(i);
            }

        });

        //////////////
        return v;
    }


    protected FragmentActivity getActivityNonNull() {
        if (super.getActivity() != null) {
            return super.getActivity();
        } else {
            throw new RuntimeException("null returned from getDiet()");
        }
    }
    private void loadActivities() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_Activities,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("ResponseMeal" , response);
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                lstcc.add(new diet(

                                        product.getString("Day"),
                                        product.getString("Type"),
                                        product.getString("image"),
                                        product.getString("description"),
                                       product.getString("calories")

                                ));
                            }
                          //  Log.d("ResponseDietArray" , ""+lstcc.size());

                            //creating adapter object and setting it to recyclerview
                            DietAdapter adapter = new DietAdapter(getContext() ,R.layout.diet_row,lstcc );
                            adapter.notifyDataSetChanged();
                            lv.setAdapter(adapter);




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

                params.put("bmi", SharedPrefBMI );

                return params;
            }
        };

        //adding our stringrequest to queue
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }
}

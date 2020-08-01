package com.sim.fitwoman.fragment;

import android.app.AuthenticationRequiredException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sim.fitwoman.MealDetailsActivity;
import com.sim.fitwoman.R;

import com.sim.fitwoman.adapter.MealAdapter;
import com.sim.fitwoman.model.Meal;
import com.sim.fitwoman.utils.WSadressIP;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MealsFragment extends Fragment {
    Button addmeal;
    ListView lv;
    List<Meal> lstcc;
    private static  String URL_Meals = "http://"+ WSadressIP.WSIP+"/FitWomanServices/Meal/getMealByUserDay.php";
   // public int usrId = 4 ;
   String SPname, SPemail, SPweight;
Button delete;
TextView nomeals;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_meals, container, false);





        lv =  v.findViewById(R.id.simpleListView);
        lstcc = new ArrayList<>();
        Log.d("URL" , URL_Meals);
nomeals = v.findViewById(R.id.nomeals);
loadMeals();
////////////////////

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
                Meal data= (Meal) arg0.getItemAtPosition(arg2);
                final int idS = data.getId();
                Integer calories = data.getTotalCalories();
                 //Toast.makeText(getContext(), idS,Toast.LENGTH_SHORT).show();
                // Intent i = new Intent(MealsFragment.this, MealDetailsActivity.class);
                Intent i = new Intent(getContext(), MealDetailsActivity.class);

                 //   i.putExtra("day",data.getDay());
                i.putExtra("calories",calories);
                i.putExtra("idMeal",idS);

                startActivity(i);
               // Toast.makeText(getContext(),String.valueOf(idS),Toast.LENGTH_SHORT).show();
            }
        });
/////////////////////////

        lv.setEmptyView(nomeals);




        return v;


    }



    protected FragmentActivity getActivityNonNull() {
        if (super.getActivity() != null) {
            return super.getActivity();
        } else {
            throw new RuntimeException("null returned from getMeal()");
        }
    }


    private void loadMeals() {


       // StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_Meals+"?id=4",
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_Meals,
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
                                lstcc.add(new Meal(
                                        product.getInt("id"),
                                        product.getString("day"),
                                        product.getString("type"),
                                        product.getInt("totalCalories") ,
                                        product.getInt("idUser")


                                ));

                              // Log.d("DATA ITEM MEALS : " ,String.valueOf( lstcc.get(0).getId())) ;
                            }

                            Log.d("ResponseMealArray" , ""+lstcc.size());

                            //creating adapter object and setting it to recyclerview
                            MealAdapter adapter = new MealAdapter(getContext() ,R.layout.meals_list,lstcc );
                            adapter.notifyDataSetChanged();
                            lv.setAdapter(adapter);




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                       // if(lv==null){nomeals.setText("click to add your meals for today!");}
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                })

                {
            @Override
            protected Map<String, String> getParams() throws AuthenticationRequiredException{
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                String datee = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                //params.put("id", "4");
                params.put("emailUser", SPemail);
                params.put("day", datee);

                return params;
            }
        };

        //adding our stringrequest to queue
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get data from Shared preferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SPname = preferences.getString("name", "");
        SPemail = preferences.getString("email", "");
        SPweight = preferences.getString("weight", "");
        if (getArguments() != null) {

        }
    }




}

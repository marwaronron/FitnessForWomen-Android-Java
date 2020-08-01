package com.sim.fitwoman.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sim.fitwoman.MFitnessSingleWorkout;
import com.sim.fitwoman.R;
import com.sim.fitwoman.adapter.FitnessAdapter;
import com.sim.fitwoman.model.MFitness;
import com.sim.fitwoman.utils.WSadressIP;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FitnessFragment extends Fragment {
    ListView lv;
    List<MFitness> lstcc;
    private static final String URL_Activities = "http://"+ WSadressIP.WSIP+"/FitWomanServices/MgetWorkoutByBMI.php";
    String SharedPrefBMI ;
    TextView title;



    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fitness, container, false);

        //1: get shared pref data
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext() );
        String name = preferences.getString("name", "");
        SharedPrefBMI  = preferences.getString("bmi", "");

        //2: set title (workout category)
        title = (TextView) v.findViewById(R.id.textView38);
        if(SharedPrefBMI.equals("1")){
            title.setText("To Gain Weight");
        }
        else  if(SharedPrefBMI.equals("2")){
            title.setText("To Maintain Weight");
        }else  if(SharedPrefBMI.equals("3")){
            title.setText("To Loose Weight");
        }

        //3: set list view
        lv =  v.findViewById(R.id.fitness_listview);

        lstcc = new ArrayList<>();
        loadActivities();
        ///////////////
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
                MFitness data= (MFitness) arg0.getItemAtPosition(arg2);
                String nameS = data.getName();
                String descriptionS = data.getDescription();
                String imageS = data.getImage();
                String videoS = data.getVideo();
                String mistakesS = data.getMistakes();

                Intent i = new Intent(getContext(), MFitnessSingleWorkout.class);
                i.putExtra("nameS", nameS);
                i.putExtra("descriptionS",descriptionS);
                i.putExtra("imageS", imageS);
                i.putExtra("videoS",videoS);
                i.putExtra("mistakesS", mistakesS);


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
            throw new RuntimeException("null returned from getActivity()");
        }
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
                                lstcc.add(new MFitness(

                                        product.getString("Name"),

                                        product.getString("Description"),
                                        product.getString("Image"),
                                      // product.getString("Steps"),
                                        product.getString("Video"),
                                        product.getString("mistakes")

                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            FitnessAdapter adapter = new FitnessAdapter(getContext() ,R.layout.fitness_row,lstcc );
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

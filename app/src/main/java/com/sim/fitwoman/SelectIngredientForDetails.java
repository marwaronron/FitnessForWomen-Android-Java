package com.sim.fitwoman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sim.fitwoman.R;
import com.sim.fitwoman.adapter.Allingredients;

import com.sim.fitwoman.model.allingredients;
import com.sim.fitwoman.utils.WSadressIP;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SelectIngredientForDetails extends AppCompatActivity {

int idMeal=0;

    ListView listView;
    List<allingredients> lstcc;
    //search list
    List<allingredients> lstcs;
    EditText searchingActivity;
    private static final String URL_Activities = "http://"+ WSadressIP.WSIP+"/FitWomanServices/Meal/getIngredients.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_ingredient_for_details);


        //1: toolbar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar100);
        mToolbar.setTitle(getString(R.string.app_name));
        mToolbar.setNavigationIcon(R.drawable.m31);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SelectIngredientForDetails.this,Home.class);
                i.putExtra("gotomeal", "ok");
                startActivity(i);
                finish();
            }
        });

        // add toolbar back button


        // View v = getLayoutInflater().inflate(R.layout.activity_home,null);
        // Toolbar  mToolbar = (Toolbar) v.findViewById(R.id.toolbar);

    /*    Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(getString(R.string.app_name));
        mToolbar.setNavigationIcon(R.drawable.m31);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   finish();
                Intent i = new Intent(SelectIngredient.this, Home.class);
                i.putExtra("okok", "ok");

                startActivity(i);
            }
        });
*/


        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            idMeal=extras.getInt("idMeal");
        }




        listView = (ListView) findViewById(R.id.listingredient);
        lstcc = new ArrayList<>();

        //get searched text
        searchingActivity = findViewById(R.id.editTexti);
        searchingActivity.setOnKeyListener(
                new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {


                        //loadActivities();
                        searchByName();

                        //on item list click move to add activity

                        return true;
                    }
                }
        );

        loadActivities();





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
                                lstcc.add(new allingredients(

                                        product.getInt("id"),

                                        product.getString("name"),

                                        product.getInt("calories")




                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            Allingredients adapter = new Allingredients(SelectIngredientForDetails.this ,R.layout.ingredient_list,lstcc );
                            adapter.notifyDataSetChanged();
                            listView.setAdapter(adapter);


                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
                                    allingredients data= (allingredients) arg0.getItemAtPosition(arg2);
                                    String name = data.getName();
                                    Integer calories = data.getCalories();
                                    //  Toast.makeText(getContext(),stepsS,Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(SelectIngredientForDetails.this, addToMealForDetails.class);
                                    i.putExtra("name", name);
                                    i.putExtra("calories",calories);
                                    i.putExtra("id",data.getId());
                                    i.putExtra("idMeal",idMeal);
                                    startActivity(i);

                                }
                            });




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

                params.put("name", searchingActivity.getText().toString() );

                return params;
            }
        };;


        //adding our stringrequest to queue
        Volley.newRequestQueue(SelectIngredientForDetails.this).add(stringRequest);

    }



    public void searchByName(){
        String s = searchingActivity.getText().toString();

        if(s.length() > 0) {
            lstcs = new ArrayList<>();
            for (allingredients i : lstcc) {
                if (i.getName().toUpperCase().contains(s.toUpperCase())) {
                    lstcs.add(new allingredients(i.getId(), i.getName(), i.getCalories()));
                }
            }

            Allingredients adapter = new Allingredients(SelectIngredientForDetails.this, R.layout.ingredient_list, lstcs);
            adapter.notifyDataSetChanged();
            listView.setAdapter(adapter);
        }else{
            Allingredients adapter = new Allingredients(SelectIngredientForDetails.this, R.layout.ingredient_list, lstcc);
            adapter.notifyDataSetChanged();
            listView.setAdapter(adapter);
        }

    }

}


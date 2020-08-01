package com.sim.fitwoman;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.sim.fitwoman.R;
import com.sim.fitwoman.adapter.IngredientAdapter;
import com.sim.fitwoman.model.Ingredient;
import com.sim.fitwoman.service.MySingleton;
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

public class MealDetailsActivity extends AppCompatActivity {
    TextView tv_cal;
    TextView day;

    Button adding;

    ListView lv;
    List<Ingredient> lstcc;

    private static  String URL_Meals = "http://"+ WSadressIP.WSIP+"/FitWomanServices/Meal/getIngredientsByMeal.php";
    public int usrId = 4 ;
    Integer TotalCalories = 0;

    int id = 0 ;


    Integer TotalActivities = 0;
    TextView tvActivities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_details);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar102);
        mToolbar.setTitle(getString(R.string.app_name));
        mToolbar.setNavigationIcon(R.drawable.m31);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MealDetailsActivity.this,Home.class);
                i.putExtra("gotomeal", "ok");
                i.putExtra("tt",TotalCalories);
                startActivity(i);
                finish();
            }
        });
        tv_cal = (TextView) findViewById(R.id.calories);


     TextView nomeals = (TextView) findViewById(R.id.nomeals);


        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        day = findViewById(R.id.date);
        day.setText("Today, "+ date);
       // SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
     //   day = (TextView) findViewById(R.id.date);

      //  tv_cal.setText(""+this.getIntent().getIntExtra("calories",3500));

      id=this.getIntent().getIntExtra("idMeal",0);
//         Toast.makeText(MealDetailsActivity.this,id,Toast.LENGTH_SHORT).show();

      //   Log.d("DATA ITEM MEALS : " ,String.valueOf(id) );
      //  day .setText(fmt.format(this.getIntent().getStringExtra("day")));

       /* Bundle extras = getIntent().getExtras();
        if (extras != null) {

            tv_cal.setText(""+extras.getInt("calories"));
            day.setText(extras.getString("day"));
            id = extras.getInt("idMeal");
        }*/





        adding = (Button) findViewById(R.id.adding);

        adding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MealDetailsActivity.this,SelectIngredientForDetails.class);
                intent.putExtra("idMeal",id);
                startActivity(intent);
            }
        });

        tvActivities = findViewById(R.id.nbing);


        lv =  (ListView) findViewById(R.id.listing);
        lstcc = new ArrayList<Ingredient>();

        loadIng();

        lv.setEmptyView(nomeals);


        ImageView delete=(ImageView) findViewById(R.id.hellook);
        //DELETE item by position => LISTENER CLICK
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
                Ingredient data= (Ingredient) arg0.getItemAtPosition(arg2);
                final int idS = data.getId();
                //Toast.makeText(getContext(),String.valueOf(idS),Toast.LENGTH_SHORT).show();

                final AlertDialog.Builder aBuilder = new AlertDialog.Builder(MealDetailsActivity.this);
                aBuilder.setMessage(" Do you want to delete this ingredient ?")
                        .setCancelable(false)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                deleteIng(idS);
                             //  lstcc.clear();
                               // loadIng();
                                finish();
                                startActivity(getIntent());

                              // IngredientAdapter.notifyDataSetChanged();
                            // Intent i = new Intent(MealDetailsActivity.this,Home.class);
                              // i.putExtra("gotomeal", "ok");

                          //   startActivity(i);
                            // MealDetailsActivity.this.finish();



                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.cancel();
                            }
                        });
                AlertDialog alert =  aBuilder.create();
                alert.setTitle("Delete ingredient");
                alert.show();

            }
        });




    }



    private void loadIng() {

      //  id=this.getIntent().getIntExtra("idMeal",0);
        //Log.d("DATA ITEM MEALS : " ,String.valueOf(id) );
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_Meals+"?idMeal="+id,
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
                                lstcc.add(new Ingredient(
                                        product.getInt("id"),
                                        product.getString("name"),
                                        product.getInt("quantity"),
                                        product.getInt("calories") ,
                                        product.getInt("idMeal"),
                                        product.getInt("idIngredient")


                                ));
                                TotalCalories = TotalCalories + Integer.valueOf(product.getInt("calories"));
                             //   Log.d("DATA ITEM MEALS : " , lstcc.get(0).getName()) ;
                            }

                            TotalActivities = array.length();
                            tvActivities.setText(String.valueOf(TotalActivities));
                            tv_cal.setText(String.valueOf(TotalCalories));
                            Log.d("ResponseMealArray" , ""+lstcc.size());

                            //creating adapter object and setting it to recyclerview
                           IngredientAdapter adapter = new IngredientAdapter(MealDetailsActivity.this ,R.layout.ingredient_meal_list,lstcc  );
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
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(MealDetailsActivity.this).add(stringRequest);
    }



    private void deleteIng(final int id) {
        final String   URL = "http://"+ WSadressIP.WSIP+"/FitWomanServices/Meal/deleteIngredient.php";





        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("success")) {

                    // loadActivities();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MealDetailsActivity.this,"failed to delete",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                params.put("Content-Type","application/x-www-form-urlencoded");
                params.put("id", String.valueOf(id));

                return params;
            }
        };

        MySingleton.getInstance(MealDetailsActivity.this).addToRequestQueue(stringRequest);

    }

}

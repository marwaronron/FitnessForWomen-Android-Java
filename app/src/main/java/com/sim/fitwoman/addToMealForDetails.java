package com.sim.fitwoman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sim.fitwoman.R;
import com.sim.fitwoman.utils.WSadressIP;

import java.util.HashMap;
import java.util.Map;

public class addToMealForDetails extends AppCompatActivity {

    TextView tv_name;
    TextView tv_cal;
    Button addtomeal ;
    EditText getquantity ;
    int id = 0 ;
    int idMeal=0;

    private static final String URL_Activities = "http://"+ WSadressIP.WSIP+"/FitWomanServices/Meal/addIngredientTomealForDetails.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_meal_for_details);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar101);
        mToolbar.setTitle(getString(R.string.app_name));
        mToolbar.setNavigationIcon(R.drawable.m31);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //l'affichage mtee el choix selectionné
        tv_name = (TextView) findViewById(R.id.name);
        tv_cal = (TextView) findViewById(R.id.calories);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tv_name.setText(extras.getString("name"));
            tv_cal.setText(""+extras.getInt("calories"));
            id = extras.getInt("id");
            idMeal=extras.getInt("idMeal");
        }

        addtomeal = (Button) findViewById(R.id.addtomeal);
        getquantity = (EditText) findViewById(R.id.getquantity);


        addtomeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Toast.makeText(addToMealForDetails.this, "id : "+id, Toast.LENGTH_LONG).show();
              //  Toast.makeText(addToMealForDetails.this, "id : "+idMeal, Toast.LENGTH_LONG).show();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_Activities,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                String sUsername = getquantity.getText().toString();
                                if (sUsername.matches("")) {
                                    Toast.makeText(getApplicationContext(), "Please enter a quantity", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                else if(Integer.valueOf(sUsername) > 1000) {
                                    Toast.makeText(getApplicationContext(), "Please enter a quantity under 1000", Toast.LENGTH_SHORT).show();
                                    return;
                                } else
                                if(response.contains("success")) {
                                    Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
                                   // Intent intent = new Intent(addToMealForDetails.this,SelectIngredientForDetails.class);
                                    //startActivity(intent);
                                    finish();
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

                        params.put("name", tv_name.getText().toString());
                        params.put("calories", tv_cal.getText().toString());
                        params.put("quantity", getquantity.getText().toString() );
                        params.put("idIngredient", String.valueOf(id));
                        params.put("idMeal", String.valueOf(idMeal));


                        return params;
                    }
                };
                Volley.newRequestQueue(addToMealForDetails.this).add(stringRequest);
            }
        });





    }
}

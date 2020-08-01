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
import com.sim.fitwoman.adapter.listSearchAdapter;
import com.sim.fitwoman.model.MlistSearch;
import com.sim.fitwoman.model.allingredients;
import com.sim.fitwoman.utils.WSadressIP;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.PendingIntent.getActivity;

public class MarwaFirstAddActivity extends AppCompatActivity {
    ListView listView;
    List<MlistSearch> lstcc;
    List<MlistSearch> lstcs;
    EditText searchingActivity;
  private static final String URL_Activities = "http://"+ WSadressIP.WSIP+"/FitWomanServices/MgetAllMET.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marwa_first_add);

        // add toolbar back button
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(getString(R.string.app_name));
        mToolbar.setNavigationIcon(R.drawable.m31);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   finish();
                Intent i = new Intent(MarwaFirstAddActivity.this, Home.class);
                  i.putExtra("okok", "ok");

                startActivity(i);
            }
        });


        //get searched text
      /*  searchingActivity = findViewById(R.id.editText);
        searchingActivity.setOnKeyListener(
                new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        listView = (ListView) findViewById(R.id.listSearch);
                        lstcc = new ArrayList<>();
                        loadActivities();

                        //on item list click move to add activity
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
                                MlistSearch data= (MlistSearch) arg0.getItemAtPosition(arg2);
                                String nameS = data.getName();
                                String iconS = data.getIcon();
                                String metS = data.getMet();
                                //  Toast.makeText(getContext(),stepsS,Toast.LENGTH_SHORT).show();



                                Intent i = new Intent(MarwaFirstAddActivity.this, MarwaAdd.class);
                                i.putExtra("nameS", nameS);
                                i.putExtra("iconS", iconS);
                                i.putExtra("metS",metS);

                                startActivity(i);

                            }
                        });
                        return true;
                    }
                }
        );


*/
        listView = (ListView) findViewById(R.id.listSearch);
        lstcc = new ArrayList<>();

        //get searched text
        searchingActivity = findViewById(R.id.editText);
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
                                lstcc.add(new MlistSearch(

                                        product.getString("name"),
                                        product.getString("icon"),
                                        product.getString("met_value")


                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            listSearchAdapter adapter = new listSearchAdapter(MarwaFirstAddActivity.this ,R.layout.listsearch_row,lstcc );
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

                params.put("name", searchingActivity.getText().toString() );

                return params;
            }
        };;

        //adding our stringrequest to queue
        Volley.newRequestQueue(MarwaFirstAddActivity.this).add(stringRequest);
    }

    public void searchByName(){
    String s = searchingActivity.getText().toString();

        if(s.length() > 0) {
        lstcs = new ArrayList<>();
        for (MlistSearch i : lstcc) {
            if (i.getName().toUpperCase().contains(s.toUpperCase())) {
                lstcs.add(new MlistSearch( i.getName(), i.getIcon(),i.getMet()));
                //on item list click move to add activity
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
                        MlistSearch data= (MlistSearch) arg0.getItemAtPosition(arg2);
                        String nameS = data.getName();
                        String iconS = data.getIcon();
                        String metS = data.getMet();
                        //  Toast.makeText(getContext(),stepsS,Toast.LENGTH_SHORT).show();



                        Intent i = new Intent(MarwaFirstAddActivity.this, MarwaAdd.class);
                        i.putExtra("nameS", nameS);
                        i.putExtra("iconS", iconS);
                        i.putExtra("metS",metS);

                        startActivity(i);

                    }
                });
            }
        }

            listSearchAdapter adapter = new listSearchAdapter(MarwaFirstAddActivity.this ,R.layout.listsearch_row,lstcs );
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
    }else{
            listSearchAdapter adapter = new listSearchAdapter(MarwaFirstAddActivity.this ,R.layout.listsearch_row,lstcc );
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
    }

}

}

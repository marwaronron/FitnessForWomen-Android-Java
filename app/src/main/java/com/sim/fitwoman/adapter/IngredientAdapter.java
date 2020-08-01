package com.sim.fitwoman.adapter;

import android.support.annotation.NonNull;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sim.fitwoman.R;
import com.sim.fitwoman.model.Ingredient;

import java.util.List;

public class IngredientAdapter extends ArrayAdapter<Ingredient> {
        Context context;
        int resource;

public IngredientAdapter(@NonNull Context context, int resource, @NonNull List<Ingredient> objects) {
        super(context, 0, objects);
        }
        @Override
        public View  getView(final int position, View convertView, ViewGroup parent) {
                // Get the data item for this position
                Ingredient user = getItem(position);
                // Check if an existing view is being reused, otherwise inflate the view
                if (convertView == null) {
                        convertView = LayoutInflater.from(getContext()).inflate(R.layout.ingredient_meal_list, parent, false);
                }
                // Lookup view for data population
                TextView tvName = (TextView) convertView.findViewById(R.id.name);
                TextView tvcal = (TextView) convertView.findViewById(R.id.calories);
                TextView tvqu = (TextView) convertView.findViewById(R.id.quantity);
               // ImageView delete=(ImageView) convertView.findViewById(R.id.hellook);

                tvName.setText(user.getName());

                tvcal.setText(""+user.getCalories());
                tvqu.setText(""+user.getQuantity());

              /*  delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                                final int idS = getItem(position).getId();
                                  Log.d("DATA ITEM MEALS : " ,String.valueOf( idS)) ;

                                AlertDialog.Builder aBuilder = new AlertDialog.Builder(v.getRootView().getContext());
                                aBuilder.setMessage(" Do you want to delete this ingredient ?")
                                        .setCancelable(false)
                                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                        deleteIng(idS);
                                                    notifyDataSetChanged();
                                                    Intent i = new Intent(getContext(),MealDetailsActivity.class);
                                                    i.putExtra("gotomeal", "ok");

                                                    context.startActivity(i);




                                                }
                                        })
                                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                        dialog.cancel();
                                                }
                                        });
                                AlertDialog alert =  aBuilder.create();
                                alert.setTitle("Delete Ingredient");
                                alert.show();

                        }
                });*/
                return convertView;
        }



     /*   private void deleteIng(final int id) {
                final String   URL = "http://192.168.1.4/FitWomanServices/Meal/deleteIngredient.php";





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
                                Toast.makeText(getContext(),"failed to delete",Toast.LENGTH_SHORT).show();
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

                MySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);

        }*/
        }

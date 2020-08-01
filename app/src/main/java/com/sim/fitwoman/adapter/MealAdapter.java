package com.sim.fitwoman.adapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sim.fitwoman.Home;
import com.sim.fitwoman.R;
import com.sim.fitwoman.model.Meal;
import com.sim.fitwoman.service.MySingleton;
import com.sim.fitwoman.utils.WSadressIP;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MealAdapter extends ArrayAdapter<Meal>  {

        Context context;
        int resource;

public MealAdapter(@NonNull Context context, int resource, @NonNull List<Meal> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        }

@NonNull
@Override
public View getView(final int position, View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate(resource, parent, false);

        TextView txt_type = (TextView) v.findViewById(R.id.typed);
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        TextView txt_date = (TextView) v.findViewById(R.id.date);
        TextView txt_calories = (TextView) v.findViewById(R.id.totalCalories);
        ImageView delete=(ImageView) v.findViewById(R.id.hellook);


        txt_type.setText(getItem(position).getType());
        txt_date.setText(fmt.format(getItem(position).getDay()));
        txt_calories.setText(String.valueOf(getItem(position).getTotalCalories()));

        delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                      final int idS = getItem(position).getId();
                     //   Log.d("DATA ITEM MEALS : " ,String.valueOf( idS)) ;

                        AlertDialog.Builder aBuilder = new AlertDialog.Builder(v.getRootView().getContext());
                        aBuilder.setMessage(" Do you want to delete this Meal ?")
                                .setCancelable(false)
                                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                                deleteMeal(idS);
                                                notifyDataSetChanged();
                                               Intent i = new Intent(getContext(),Home.class);
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
                        alert.setTitle("Delete Meal");
                        alert.show();

                }
        });
        return v;
        }


        private void deleteMeal(final int id) {
                final String   URL = "http://"+ WSadressIP.WSIP+"/FitWomanServices/Meal/deleteMeal.php";





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

        }
}

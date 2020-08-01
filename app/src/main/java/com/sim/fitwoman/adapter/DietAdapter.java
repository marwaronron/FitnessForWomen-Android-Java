package com.sim.fitwoman.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.sim.fitwoman.R;
import com.sim.fitwoman.model.diet;
import com.sim.fitwoman.service.MySingleton;
import com.sim.fitwoman.utils.WSadressIP;

import java.util.List;

public class DietAdapter  extends ArrayAdapter<diet> {

    Context context;
    int resource;

    public DietAdapter(@NonNull Context context, int resource, @NonNull List<diet> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
       View v = inflater.inflate(resource, parent, false);

       TextView txt_day = (TextView) v.findViewById(R.id.dayd);
        TextView txt_type = (TextView) v.findViewById(R.id.typed);
        TextView txt_cal = (TextView) v.findViewById(R.id.caloriesd);
        final ImageView img_img = (ImageView) v.findViewById(R.id.imageView2d) ;

      txt_day.setText(getItem(position).getDay());

       txt_type.setText(getItem(position).getType());
        txt_cal.setText(getItem(position).getCalories());

        //  img_img.setImageResource(R.drawable.m15);
        String theImage_URL = "http://"+ WSadressIP.WSIP+"/"+getItem(position).getImage();


        ImageLoader imageLoader = MySingleton.getInstance(getContext()).getImageLoader();

        imageLoader.get(theImage_URL, new ImageLoader.ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "big error" ,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {


                    img_img.setImageBitmap(response.getBitmap());

                }
            }
        });
        return v;
    }


}

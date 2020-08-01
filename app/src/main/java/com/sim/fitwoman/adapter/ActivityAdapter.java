package com.sim.fitwoman.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.sim.fitwoman.R;
import com.sim.fitwoman.model.MActivity;
import com.sim.fitwoman.service.MySingleton;
import com.sim.fitwoman.utils.WSadressIP;

import java.util.List;

public class ActivityAdapter extends ArrayAdapter<MActivity> {

    Context context;
    int resource;

    public ActivityAdapter(@NonNull Context context, int resource, @NonNull List<MActivity> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(resource, parent, false);
        TextView txt_name = (TextView) v.findViewById(R.id.MtextView17);
        TextView txt_duration = (TextView) v.findViewById(R.id.MtextView18);
        TextView txt_burned = (TextView) v.findViewById(R.id.MtextView19);
        TextView txt_description = (TextView) v.findViewById(R.id.MtextView20);
        final ImageView imgIcon = (ImageView) v.findViewById(R.id.imageView12);

        txt_name.setText(getItem(position).getName());
        txt_duration.setText("Duration: " + getItem(position).getDuration()+ " Minutes");
        txt_burned.setText("Burned Calories: " + getItem(position).getBurnedCalories()+" KCAL");
        txt_description.setText( getItem(position).getDescription());
        String theImage_URL = "http://"+ WSadressIP.WSIP+"/"+getItem(position).getIcon();


        ImageLoader imageLoader = MySingleton.getInstance(getContext()).getImageLoader();

        imageLoader.get(theImage_URL, new ImageLoader.ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(this, "big error" ,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {


                    imgIcon.setImageBitmap(response.getBitmap());

                }
            }
        });

        return v;
    }
}
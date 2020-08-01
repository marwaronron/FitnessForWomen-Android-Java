package com.sim.fitwoman.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.sim.fitwoman.R;

import android.widget.ImageView;
import android.widget.TextView;


import com.sim.fitwoman.model.MlistSearch;
import com.sim.fitwoman.service.MySingleton;
import com.sim.fitwoman.utils.WSadressIP;

import java.util.List;

public class listSearchAdapter extends ArrayAdapter<MlistSearch> {

    public listSearchAdapter(@NonNull Context context, int resource, @NonNull List<MlistSearch> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        MlistSearch user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listsearch_row, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.textView18);


        tvName.setText(user.getName());


      final  ImageView imgIcon = (ImageView) convertView.findViewById(R.id.imageView11);
        String theImage_URL = "http://"+ WSadressIP.WSIP+"/"+user.getIcon();


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

        return convertView;
    }

}

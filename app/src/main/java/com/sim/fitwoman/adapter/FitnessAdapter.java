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
import com.sim.fitwoman.model.MFitness;
import com.sim.fitwoman.service.MySingleton;
import com.sim.fitwoman.utils.WSadressIP;

import java.util.List;

public class FitnessAdapter extends ArrayAdapter<MFitness> {
    Context context;
    int resource;

    public FitnessAdapter(@NonNull Context context, int resource, @NonNull List<MFitness> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(resource, parent, false);
        TextView txt_name = (TextView) v.findViewById(R.id.textView2);
        TextView txt_description = (TextView) v.findViewById(R.id.textView3);
       final ImageView img_img = (ImageView) v.findViewById(R.id.imageView2) ;

        txt_name.setText(getItem(position).getName());

        txt_description.setText(getItem(position).getDescription() );
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

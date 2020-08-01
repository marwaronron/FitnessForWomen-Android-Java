package com.sim.fitwoman.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sim.fitwoman.R;
import com.sim.fitwoman.model.allingredients;

import java.util.List;

public class Allingredients extends ArrayAdapter<allingredients> {

public  Allingredients(@NonNull Context context, int resource, @NonNull List<allingredients> objects) {
        super(context, 0, objects);
        }


@Override
public View  getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
     allingredients user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.ingredient_list, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.name);
    TextView tvcal = (TextView) convertView.findViewById(R.id.calories);

        tvName.setText(user.getName());

    tvcal.setText(""+user.getCalories());

        return convertView;
        }

        }

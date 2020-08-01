package com.sim.fitwoman.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sim.fitwoman.R;

import com.sim.fitwoman.model.MealHistoric;

import java.util.List;

public class MealHistoricAdapter  extends ArrayAdapter<MealHistoric>  {
    public MealHistoricAdapter (@NonNull Context context, int resource, @NonNull List<MealHistoric> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        MealHistoric user = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.meals_historic_row, parent, false);
        }


        TextView tvtype = (TextView) convertView.findViewById(R.id.textView6);
       // SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        TextView tvDay = (TextView) convertView.findViewById(R.id.textView27);
        TextView tvBurnedCal= (TextView) convertView.findViewById(R.id.textView28);



        tvtype.setText(user.getDay());
        tvDay.setText("Meal: "+(user.getType()));
      tvBurnedCal.setText("Total Calories: "+ user.getTotalCalories()+" KCAL");




        return convertView;
    }
}

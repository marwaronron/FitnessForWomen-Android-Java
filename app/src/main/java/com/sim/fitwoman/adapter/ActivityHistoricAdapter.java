package com.sim.fitwoman.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sim.fitwoman.R;
import com.sim.fitwoman.model.MActivityHistoric;

import java.util.List;

public class ActivityHistoricAdapter extends ArrayAdapter<MActivityHistoric> {

    public ActivityHistoricAdapter (@NonNull Context context, int resource, @NonNull List<MActivityHistoric> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        MActivityHistoric user = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activities_historic_row, parent, false);
        }


        TextView tvName = (TextView) convertView.findViewById(R.id.textView6);
        TextView tvDay = (TextView) convertView.findViewById(R.id.textView27);
        TextView tvDuration = (TextView) convertView.findViewById(R.id.textView28);
        TextView tvBurnedCal = (TextView) convertView.findViewById(R.id.textView29);


        tvName.setText(user.getName());
        tvDay.setText("Day "+ user.getDay());
        tvDuration.setText("Duration "+ user.getDuration()+" Minutes");
        tvBurnedCal.setText("Burned Calories "+ user.getBurnedCalories()+" KCAL");
       // tvDescription.setText( user.getDescription());



        return convertView;
    }
}

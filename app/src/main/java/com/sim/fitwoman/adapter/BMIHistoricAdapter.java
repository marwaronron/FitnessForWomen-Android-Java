package com.sim.fitwoman.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sim.fitwoman.R;
import com.sim.fitwoman.model.MBMIHistoric;



import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class BMIHistoricAdapter extends ArrayAdapter<MBMIHistoric> {
    String SPname, SPemail, SPweight ,SPheight;
    public BMIHistoricAdapter (@NonNull Context context, int resource, @NonNull List<MBMIHistoric> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        MBMIHistoric user = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bmi_historic_row, parent, false);
        }

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SPname = preferences.getString("name", "");
        SPemail = preferences.getString("email", "");
        SPweight = preferences.getString("weight", "");
        SPheight = preferences.getString("height", "");

        TextView tvDay = (TextView) convertView.findViewById(R.id.textView7);
        TextView tvWeight = (TextView) convertView.findViewById(R.id.textView23);
        TextView tvBMI = (TextView) convertView.findViewById(R.id.textView24);
        TextView tvBMI_result = (TextView) convertView.findViewById(R.id.textView25);
        TextView tvConclusion = (TextView) convertView.findViewById(R.id.textView26);
      //  ImageView img = (ImageView) convertView.findViewById(R.id.imageView14);
      //  img.setImageResource(R.drawable.m102);

        tvDay.setText("Day "+ user.getDay());
        tvWeight.setText("Your weight was " +user.getWeight()+" kg");
        tvBMI.setText("Your BMI is "+ user.getBMI().substring(0,4));
        tvBMI_result.setText("So you are " + user.getBMI_result());


            if (Double.valueOf(user.getWeight()) > Double.valueOf(SPweight)) {
                tvConclusion.setText("You've earned weight");
            } else if (Double.valueOf(user.getWeight()) < Double.valueOf(SPweight)) {
                tvConclusion.setText("You've lost weight! ");
            } else {
                tvConclusion.setText("Your weight is still stable");
            }


        return convertView;
    }
}

package com.sim.fitwoman.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sim.fitwoman.Home;
import com.sim.fitwoman.R;

public class HomeFragment extends Fragment {
    String SPname, SPemail, SPweight;
    TextView txtWelcome;
    TextView txtDiet;
    TextView txtWorkout;
    ImageView goToActivities;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container ,false);
        txtWelcome = v.findViewById(R.id.textView30);
        txtWelcome.setText("Welcome "+ SPname);


        txtDiet = v.findViewById(R.id.textView35);
        txtWorkout = v.findViewById(R.id.textView33);
        goToActivities = v.findViewById(R.id.imageView19921);

        txtDiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Home.class);
               i.putExtra("goToDiet", "2");


                startActivity(i);
            }
        });

        txtWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Home.class);
                i.putExtra("goToWorkout", "3");


                startActivity(i);
            }
        });
        goToActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Home.class);
                i.putExtra("goToActivitiess", "4");


                startActivity(i);
            }
        });
        return v;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get data from Shared preferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SPname = preferences.getString("name", "");
        SPemail = preferences.getString("email", "");
        SPweight = preferences.getString("weight", "");
        if (getArguments() != null) {

        }
    }





}

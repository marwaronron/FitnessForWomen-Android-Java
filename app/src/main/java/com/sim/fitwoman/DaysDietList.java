package com.sim.fitwoman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sim.fitwoman.R;

public class DaysDietList extends AppCompatActivity {


    ListView simpleList;
    String countryList[] = {"Day 1", "Day 2", "Day 3", "Day 4", "Day 5", "Day 6", "Day 7"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_days_diet_list);

        simpleList = (ListView)findViewById(R.id.simpleListView);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_days_diet_list, R.id.textView, countryList);
        simpleList.setAdapter(arrayAdapter);
    }

    }


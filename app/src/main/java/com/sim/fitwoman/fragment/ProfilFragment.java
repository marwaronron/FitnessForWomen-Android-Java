package com.sim.fitwoman.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sim.fitwoman.Activities_Historic;
import com.sim.fitwoman.BMI_Historic;
import com.sim.fitwoman.Meals_Historic;
import com.sim.fitwoman.R;
import com.sim.fitwoman.updateData;
import com.sim.fitwoman.updateWeight;
import com.sim.fitwoman.utils.WSadressIP;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ProfilFragment extends Fragment {
    TextView tvUserName, txtAdd, showWeight, showHeight, showBMI, showStatus;
    String SPname, SPemail, SPweight ,SPheight, SPBMI, SPPhoto;
    TextView   btnBMIHistoric , btnActivitiesHistoric,btnMealsHistoric;
    ImageView UserProfilPic ;
    Button btngo, btnUpdateData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_profil, container ,false);

        //1: set user name and data to show
        tvUserName = v.findViewById(R.id.Mtext2710);
        tvUserName.setText(SPname);

        //Set profil pic
        UserProfilPic = v.findViewById(R.id.imageView8ProfilPic);

        //set weight
        showWeight = v.findViewById(R.id.textView45);
        showWeight.setText(SPweight);

        //set height
        showHeight = v.findViewById(R.id.textView41);
        showHeight.setText(SPheight);

        //set bmi
        showBMI = v.findViewById(R.id.textView14);
        String CalculateBMI = getCalculateBMI(SPweight,SPheight);
        showBMI.setText(CalculateBMI);

        //set the weight satus
        showStatus = v.findViewById(R.id.textView11);
        if(SPBMI.equals("1")){
            showStatus.setText("Under Weight");
        } else  if(SPBMI.equals("2")){
            showStatus.setText("Normal Weight");
        } else  if(SPBMI.equals("3")){
            showStatus.setText("Over Weight");
        }

        //2: add weight for this day
        btngo =  v.findViewById(R.id.button2);
        btngo.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dontExist();




                    }
                }
        );





        //3: See BMI HISTORIC
        btnBMIHistoric = v.findViewById(R.id.textView8);
        btnBMIHistoric.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //Intent i = new Intent(getContext(), BMI_Historic.class);
                        Intent i = new Intent(getContext(), BMI_Historic.class);
                        startActivity(i);




                    }
                }
        );

        //4: See Activities Historic
        btnActivitiesHistoric = v.findViewById(R.id.textView9);
        btnActivitiesHistoric .setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(getContext(), Activities_Historic.class);

                        startActivity(i);




                    }
                }
        );

        //4: See Meals Historic
        btnMealsHistoric = v.findViewById(R.id.textView109);
        btnMealsHistoric .setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(getContext(), Meals_Historic.class);

                        startActivity(i);




                    }
                }
        );

        //6: Update DAta
        btnUpdateData = v.findViewById(R.id.button5UpdateData);
        btnUpdateData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(getContext(), updateData.class);

                        startActivity(i);




                    }
                }
        );

        //7: show facebook profil picture

        if(!SPPhoto.equals("nothibg")) {
         Bundle params = new Bundle();
        params.putString("fields", "id,email,gender,cover,picture.type(large)");
        new GraphRequest(AccessToken.getCurrentAccessToken(), "me", params, HttpMethod.GET,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        if (response != null) {
                            try {
                                JSONObject data = response.getJSONObject();
                                if (data.has("picture")) {
                                    URL profilePicUrl = new URL (data.getJSONObject("picture").getJSONObject("data").getString("url"));
                                    Bitmap profilePic= BitmapFactory.decodeStream(profilePicUrl.openConnection().getInputStream());
                                    UserProfilPic.setImageBitmap(profilePic);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).executeAsync();
         ////////////////
         }
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
        SPheight = preferences.getString("height", "");
        SPBMI = preferences.getString("bmi", "");
        SPPhoto = preferences.getString("photo", "");
        if (getArguments() != null) {

        }
    }

public  Integer x = 2; // ???

    public void dontExist(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ WSadressIP.WSIP+"/FitWomanServices/MgetDailyBMIByUserDay.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                            if(array.length() == 0){

                               Intent i = new Intent(getContext(), updateWeight.class);

                                startActivity(i);
                            }else{
                                Toast.makeText(getContext(),"YOU ALREADY ADDED YOUR WEIGHT FOR TODAY",Toast.LENGTH_SHORT).show();

                                //txtAdd.setText("Daily Weight is Added");

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                params.put("Content-Type","application/x-www-form-urlencoded");
                params.put("day", date);

                params.put("emailUser", SPemail);

                return params;
            }
        };

        //adding our stringrequest to queue
        Volley.newRequestQueue(getContext()).add(stringRequest);

    }


    public String getCalculateBMI(String TWeight,String THeight){
        Float FWeight = Float.valueOf(TWeight);
        Float FHeight = Float.valueOf(THeight);
        Float IMC = 10000 *(FWeight/(FHeight * FHeight));
        String test = String.valueOf(IMC);

        return test.substring(0,4);
    }
}

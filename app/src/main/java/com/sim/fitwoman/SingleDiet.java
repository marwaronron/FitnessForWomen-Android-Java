package com.sim.fitwoman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.sim.fitwoman.R;
import com.sim.fitwoman.service.MySingleton;
import com.sim.fitwoman.utils.WSadressIP;

public class SingleDiet extends AppCompatActivity {

    ImageView SWimage;
    TextView SWname, SWdesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_diet);



      /*  String versionName = "";
        try {
            PackageInfo pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = pinfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/

      //  TextView aboutTextView = (TextView) findViewById(R.id.desc);

      /*  Spanned aboutText = Html.fromHtml("<h1>Your App Name, Version " + versionName + "</h1>"
                + this.getIntent().getStringExtra("description"));
        aboutTextView.setText(aboutText);*/




        SWimage = findViewById(R.id.imageView4);
        SWname = findViewById(R.id.typed);
        SWdesc = findViewById(R.id.desc);


       SWname.setText(this.getIntent().getStringExtra("type"));


     //   ScrollView sv=(ScrollView)findViewById(R.id.scrollView1);
       SWdesc.setText(this.getIntent().getStringExtra("description"));
      //  sv.addView(SWdesc);



        String theImage_URL = "http://"+ WSadressIP.WSIP+"/"+this.getIntent().getStringExtra("imageS");



        ImageLoader imageLoader = MySingleton.getInstance(SingleDiet.this).getImageLoader();

        imageLoader.get(theImage_URL, new ImageLoader.ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SingleDiet.this, "big error" ,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {


                    SWimage.setImageBitmap(response.getBitmap());

                }
            }
        });
        //////////////////////////////////////////////////////////////////////
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(getString(R.string.app_name));
        mToolbar.setNavigationIcon(R.drawable.m31);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //////////////////////////


    }


    }


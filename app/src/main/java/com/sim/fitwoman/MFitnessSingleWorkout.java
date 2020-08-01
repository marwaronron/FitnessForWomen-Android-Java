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
import com.sim.fitwoman.service.MySingleton;
import com.sim.fitwoman.utils.WSadressIP;

public class MFitnessSingleWorkout extends AppCompatActivity {
    ImageView SWimage, btnMistakes;
    TextView SWname, SWdesc, SWsteps ,SWmistakes;
    //String videoo;

  //  RecyclerView recyclerView;
    //Vector<YouTubeVideos> youtubeVideos = new Vector<YouTubeVideos>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mfitness_single_workout);
        SWimage = findViewById(R.id.imageView4);
        SWname = findViewById(R.id.textView);
        SWdesc = findViewById(R.id.textView4);
        SWmistakes = findViewById(R.id.textView52);



        SWname.setText(this.getIntent().getStringExtra("nameS"));
        SWdesc.setText(this.getIntent().getStringExtra("descriptionS"));
     // final String ok = this.getIntent().getStringExtra("mistakesS");
        SWmistakes.setText(this.getIntent().getStringExtra("mistakesS"));
       //videoo = this.getIntent().getStringExtra("videoS");




        ////////////////////////////////////////////////////////////////////
        String theImage_URL = "http://"+ WSadressIP.WSIP+"/"+this.getIntent().getStringExtra("imageS");


        ImageLoader imageLoader = MySingleton.getInstance(MFitnessSingleWorkout.this).getImageLoader();

        imageLoader.get(theImage_URL, new ImageLoader.ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MFitnessSingleWorkout.this, "big error" ,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {


                    SWimage.setImageBitmap(response.getBitmap());

                }
            }
        });
        //////////////////////////////////////////////////////////////////////
       /* recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));

        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/"+videoo+"\" frameborder=\"0\" allowfullscreen></iframe>") );
        VideoAdapter videoAdapter = new VideoAdapter(youtubeVideos);

        recyclerView.setAdapter(videoAdapter);*/
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
      /*  btnMistakes = findViewById(R.id.imageView3);
        btnMistakes.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                LayoutInflater layoutInflater
                        = (LayoutInflater)getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.workoutmistakespopup, null);
                final PopupWindow popupWindow = new PopupWindow(
                        popupView,
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);

                Button btnDismiss = (Button)popupView.findViewById(R.id.button3);
                TextView mistakesAB = (TextView)popupView.findViewById(R.id.textView16);
                mistakesAB.setText(ok);
                btnDismiss.setOnClickListener(new Button.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        popupWindow.dismiss();
                    }});

                popupWindow.showAsDropDown(btnMistakes, -780, -900);

            }});*/

    }


}

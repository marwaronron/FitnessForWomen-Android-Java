package com.sim.fitwoman;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import android.os.Handler;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.sim.fitwoman.R;


public class ABCActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abc);

        final TextView Title = (TextView) findViewById(R.id.textView49);
        final Animation animation_1 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.frombottom);
        final Animation animation_2 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.antirotate);
        final Animation animation_3 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.abc_fade_out);

        ImageView rocketImage = (ImageView) findViewById(R.id.imageView22);
        rocketImage.setBackgroundResource(R.drawable.animlist);
        final AnimationDrawable rocketAnimation = (AnimationDrawable) rocketImage.getBackground();



final TextView TitleMe = (TextView) findViewById(R.id.textView49);

       TitleMe.startAnimation(animation_2);
        animation_2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {


            }

            @Override
            public void onAnimationEnd(Animation animation) {

                TitleMe.startAnimation(animation_1);
                rocketAnimation.start();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        new Handler().postDelayed(new Runnable() {



            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent intent = new Intent(getApplicationContext(),
                        MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);



    }
}

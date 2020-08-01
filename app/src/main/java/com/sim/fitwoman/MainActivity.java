package com.sim.fitwoman;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sim.fitwoman.R;
import com.sim.fitwoman.model.User;
import com.sim.fitwoman.service.MySingleton;
import com.sim.fitwoman.utils.WSadressIP;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
 EditText txtEmail, txtPwd;
 Button btnLogin;
 TextView txtSignUp;


 //logeD User



private LoginButton loginButton;
private CallbackManager callbackManager;
     String FBEmail = "email";
    String FBName = "email";
    String FBId = "id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        callbackManager = CallbackManager.Factory.create();
        if(AccessToken.getCurrentAccessToken() != null){  //SKIP login if user already signed with FB

            /////////////// SET DATA LOGED USER// NO NEED
            User.session = new User();
            User.session.setId(4);
            /////////////////////:

            Intent intent = new Intent("com.sim.fitwoman.Home");

            startActivity(intent);
        }
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(FBEmail));
        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                ///////////////
                MarwaFBLogin(loginResult);

            }

            @Override
            public void onCancel() {
               Toast.makeText(getApplicationContext(),"u canceled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(),"fixe ur error before", Toast.LENGTH_SHORT).show();
            }


        });
        ///////////////
        //Solving thread problem
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        /////
        txtEmail= findViewById(R.id.MName);
        txtPwd= findViewById(R.id.MEmail);
        btnLogin =  findViewById(R.id.button);
        txtSignUp = findViewById(R.id.signUpTextView);





        LoginAndGoToIMC();
        goToSignUp();
    }

    //For FB Login
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    void  goToSignUp(){
        txtSignUp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, Sign_up.class);

                        startActivity(intent);
                    }
                }
        );

    }


    void LoginAndGoToIMC(){
        btnLogin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                       /////////////////////////////
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ WSadressIP.WSIP+"/FitWomanServices/UserEmailPasswordExist.php",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            //converting the string to json array object
                                            JSONArray array = new JSONArray(response);
                                            if(array.length() != 0){
                                                ////////////////////////////////////
                                              //  for (int i = 0; i < array.length(); i++) {


                                                    JSONObject product = array.getJSONObject(0);


                                                  //  new User(product.getString("name"));
                                              //  }
                                                Intent intent = new Intent("com.sim.fitwoman.IMC");
                                                intent.putExtra("userName", product.getString("name")); //this 2 lines for pass the values to the next activity
                                                intent.putExtra("userEmail", txtEmail.getText().toString());
                                                intent.putExtra("photo", "nothing");
                                                startActivity(intent);
                                                ////////////////////
                                            }else{
                                                Toast.makeText(getApplicationContext(),"DON'T EXIST",Toast.LENGTH_SHORT).show();

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

                                params.put("pwd", txtPwd.getText().toString());
                                params.put("emailUser", txtEmail.getText().toString());

                                return params;
                            }
                        };

                        //adding our stringrequest to queue
                        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
                        ////////////////////////////////
                    }
                }
        );

    }


    public void MarwaFBLogin(LoginResult loginResult){
        AccessToken accessToken = loginResult.getAccessToken();
        Profile profile = Profile.getCurrentProfile();

        // Facebook Email address
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        Log.v("LoginActivity Response ", response.toString());

                        try {
                            FBName = object.getString("name");

                            FBEmail = object.getString("email");
                            FBId = object.getString("id");
                            Log.v("Email = ", " " + FBEmail);
                            InsertUserInDB(FBName , FBEmail , "FB",FBId);

                            //Toast.makeText(getApplicationContext(), "Name " + FBName+ " email "+ FBEmail, Toast.LENGTH_LONG).show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender, birthday");
        request.setParameters(parameters);
        request.executeAsync();

    }


    public void InsertUserInDB(final String UserName, final String UserEmail , final String loginType, final String idid){
        final String   URL = "http://"+ WSadressIP.WSIP+"/FitWomanServices/addUser.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("success")) {

                    ////////////////////////////////////
                    Intent intent = new Intent("com.sim.fitwoman.IMC");
                    intent.putExtra("userName", UserName); //this 2 lines for pass the values to the next activity
                    intent.putExtra("userEmail", UserEmail);
                    intent.putExtra("photo", idid);
                    startActivity(intent);
                    ////////////////////
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"failed to login",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                params.put("name", UserName);
                params.put("email",UserEmail);
                params.put("logintype", loginType);
                params.put("photo", idid);
                return params;
            }
        };

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}

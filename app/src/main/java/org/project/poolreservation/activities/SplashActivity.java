package org.project.poolreservation.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.project.poolreservation.R;

import java.util.HashMap;
import java.util.Map;

public class SplashActivity extends AppCompatActivity {
    SharedPreferences vrificationActivityPreferences,userTypePagePreferences,signUpPagePreferences,preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        vrificationActivityPreferences=getSharedPreferences("VerificatioActivityPref",MODE_PRIVATE);
        userTypePagePreferences=getSharedPreferences("UserTypePagePref",MODE_PRIVATE);
        signUpPagePreferences = getSharedPreferences("SignUpPagePref", MODE_PRIVATE);
        preferences=getSharedPreferences("SplashActivtyPref",MODE_PRIVATE);



        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(vrificationActivityPreferences.getString("password","NOT_FOUND").equals("NOT_FOUND")){
                    Intent intent=new Intent(SplashActivity.this,UserTypePage.class);
                    startActivity(intent);
                }else{
                    System.out.println("***********splash*************");
                    generateTokensendJsonToserver();
                }

                finish();
            }
        },3000);
    }
    //-------------------------------------------------------------------------------------
    public void generateTokensendJsonToserver(){
        String url = "http://waterphone.ir/";

//create post data as JSONObject - if your are using JSONArrayRequest use obviously an JSONArray :)
        JSONObject jsonBody = null;
        try {
            String user_role=userTypePagePreferences.getString("UserRole","NOT_FOUND");
            System.out.println("------user role"+user_role);
            String phoneNumber=signUpPagePreferences.getString("mobile","NOT_FOUND");
            System.out.println("------phoneNumber"+phoneNumber);
            String password=vrificationActivityPreferences.getString("password","NOT_FOUND");
            System.out.println("------password:"+password);
            String test="OTMwNjY0MzIyOA";
            System.out.println(test.toString());
            jsonBody = new JSONObject("{\"name\" " +
                    ": \"generateToken\"," +
                    "\"param\" : {" +
                    "\"user_role\"" +
                    ":" +
                    user_role +
                    "," +
                    "\"mobile\" " +
                    ":" +
                    phoneNumber +
                    "," +
                    "\"password\" " +
                    ":" +
                    password +
                    "}}");
            System.out.println("------user role new "+user_role);
            System.out.println("------phoneNumber new"+phoneNumber);
            System.out.println("---test: "+test);


        } catch (JSONException e) {
            e.printStackTrace();
        }

//request a json object response
        JsonObjectRequest jsonRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, url, jsonBody, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //now handle the response
                Toast.makeText(SplashActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                System.out.println("------------------------------------------");

                System.out.println(response.toString());
                if( response.toString().contains("\"token\"")){

                    String token="";
                    int i=43;
                    while (true){
                        token+=response.toString().charAt(i);
                        i++;
                        if(response.toString().charAt(i)=='"')
                            break;
                    }
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("token",token);
                    editor.apply();
                }
                Intent intent=new Intent(SplashActivity.this,Public_register_Activity.class);
                startActivity(intent);

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //handle the error
                Toast.makeText(SplashActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        }) {    //this is the part, that adds the header to the request
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("content-type", "application/json");
                params.put("cache-control", "no-cache");

                return params;
            }
        };
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
// Add the request to the queue
        Volley.newRequestQueue(this).add(jsonRequest);

    }
}
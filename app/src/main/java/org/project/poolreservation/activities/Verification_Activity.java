package org.project.poolreservation.activities;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.project.poolreservation.R;

import java.util.HashMap;
import java.util.Map;

public class Verification_Activity extends AppCompatActivity {

    PinEntryEditText pinEntry;
    SharedPreferences userTypePagePrefrences,signUpActivityPrefrences,preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_);
        userTypePagePrefrences=getSharedPreferences("UserTypePagePref",MODE_PRIVATE);
        signUpActivityPrefrences = getSharedPreferences("SignUpPagePref", MODE_PRIVATE);
        preferences=getSharedPreferences("VerificatioActivityPref",MODE_PRIVATE);
        pinEntry = (PinEntryEditText) findViewById(R.id.txt_pin_entry);

    }

    public void click(View view) {
        if (pinEntry != null) {
            if (userTypePagePrefrences.getString("UserRole","NOT_FOUND").equals("Owner")){
                sendJsonToserver_OWNER();

            }else if(userTypePagePrefrences.getString("UserRole","NOT_FOUND").equals("Customer")){
                sendJsonToserver_CUSTOMER();
            }

        }else
            Toast.makeText(Verification_Activity.this ,"کد تایید را وارد کنید" ,Toast.LENGTH_LONG).show();

    }
    public void sendJsonToserver_OWNER(){
        String url = "http://waterphone.ir/";

//create post data as JSONObject - if your are using JSONArrayRequest use obviously an JSONArray :)
        JSONObject jsonBody = null;
        try {
            String phoneNumber=signUpActivityPrefrences.getString("mobile","NOT_FOUND");
            String code=pinEntry.getText().toString();

            jsonBody = new JSONObject("{\"name\" " +
                    ": \"activateOwner\"," +
                    "\"param\" : {" +
                    "\"mobile\"" +
                    " :" +
                    phoneNumber +
                    "," +
                    "\"code\" " +
                    ":" +
                    code +
                    "}}");
        } catch (JSONException e) {
            e.printStackTrace();
        }

//request a json object response
        JsonObjectRequest jsonRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, url, jsonBody, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //now handle the response
                Toast.makeText(Verification_Activity.this, response.toString(), Toast.LENGTH_SHORT).show();
                System.out.println("------------------------------------------");

                System.out.println(response.toString());
                if(response.toString().contains("200")){
                    if(response.toString().contains("password")){
                        String password="";
                        int i=46;
                        while (true){
                            password+=response.toString().charAt(i);
                            i++;
                            if(response.toString().charAt(i)=='"')
                                break;


                        }
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("password", password);
                        editor.apply();
                    }
                    startActivity(new Intent(Verification_Activity.this, Public_register_Activity.class));
                }

                else {
                    startActivity(new Intent(Verification_Activity.this, Singup_Activity.class));
                    finish();

                }


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //handle the error
                Toast.makeText(Verification_Activity.this, "An error occurred", Toast.LENGTH_SHORT).show();
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
    public void sendJsonToserver_CUSTOMER(){
        String url = "http://waterphone.ir/";

//create post data as JSONObject - if your are using JSONArrayRequest use obviously an JSONArray :)
        JSONObject jsonBody = null;
        try {
            String phoneNumber=signUpActivityPrefrences.getString("mobile","NOT_FOUND");
            String code=pinEntry.getText().toString();

            jsonBody = new JSONObject("{\"name\" " +
                    ": \"activateCustomer\"," +
                    "\"param\" : {" +
                    "\"mobile\"" +
                    " :" +
                    phoneNumber +
                    "," +
                    "\"code\" " +
                    ":" +
                    code +
                    "}}");
        } catch (JSONException e) {
            e.printStackTrace();
        }

//request a json object response
        JsonObjectRequest jsonRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, url, jsonBody, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //now handle the response
                Toast.makeText(Verification_Activity.this, response.toString(), Toast.LENGTH_SHORT).show();
                System.out.println("------------------------------------------");

                System.out.println(response.toString());
                if(response.toString().contains("200")){
                    if(response.toString().contains("password")){
                        String password="";
                        int i=46;
                        while (true){
                            password+=response.toString().charAt(i);
                            i++;
                            if(response.toString().charAt(i)=='"')
                                break;


                        }
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("password", password);
                        editor.apply();
                    }
                    startActivity(new Intent(Verification_Activity.this, Public_register_Activity.class));
                }

                else {
                    startActivity(new Intent(Verification_Activity.this, Singup_Activity.class));
                    finish();

                }


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //handle the error
                Toast.makeText(Verification_Activity.this, "An error occurred", Toast.LENGTH_SHORT).show();
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

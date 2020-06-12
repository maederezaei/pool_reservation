package org.project.poolreservation.activities;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;
import org.project.poolreservation.R;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Singup_Activity extends AppCompatActivity {
    EditText editText;
    Button button;
    SharedPreferences sharedPreferences, preferences,ownerRegisterPrefrences,coustomerRegirsterPrefrences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup_);
        sharedPreferences = getSharedPreferences("SignUpPagePref", MODE_PRIVATE);
        preferences = getSharedPreferences("UserTypePagePref", MODE_PRIVATE);
        ownerRegisterPrefrences=getSharedPreferences("OwnerRegisterPageFirstPref",MODE_PRIVATE);
        coustomerRegirsterPrefrences=getSharedPreferences("CustomerRegisterPagePref",MODE_PRIVATE);

        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button2);

    }

    public void click(View view) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("mobile", editText.getText().toString().trim());
        editor.apply();
        String user_role = preferences.getString("UserRole", "NOT_FOUND");
        if (user_role.equals("Owner")) {
            String data = "{\"name\" : \"registerOwner\",\"param\" : {\"first_name\" : \"علی\",\"last_name\" : \"محمدی\",\"mobile\" : \"09306643228\"}}";
            sendJsonToserver_OWNER();
        } else if (user_role.equals("Customer")) {
            Log.i("test", "Customer");
            sendJsonToserver_CUSTOMER();
        }

    }


    private void sendJsonToserver_OWNER(){
        String url = "http://waterphone.ir/";

//create post data as JSONObject - if your are using JSONArrayRequest use obviously an JSONArray :)
        JSONObject jsonBody = null;
        try {
            String name=ownerRegisterPrefrences.getString("first_name","NOT_FOUND");
            String family=ownerRegisterPrefrences.getString("last_name","NOT_FOUND");
            String phoneNumber=editText.getText().toString();
            jsonBody = new JSONObject("{\"name\" " +
                    ": \"registerOwner\"," +
                    "\"param\" : {" +
                    "\"first_name\"" +
                    " :" +
                    name +
                    "," +
                    "\"last_name\" " +
                    ":" +
                    family +
                    "," +
                    "\"mobile\" " +
                    ":" +
                    phoneNumber +
                    "}}");
        } catch (JSONException e) {
            e.printStackTrace();
        }

//request a json object response
        JsonObjectRequest jsonRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, url, jsonBody, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //now handle the response
                Toast.makeText(Singup_Activity.this, response.toString(), Toast.LENGTH_SHORT).show();
                System.out.println("------------------------------------------");

                System.out.println(response.toString());
                if(response.toString().contains("200"))
                    startActivity(new Intent(Singup_Activity.this, Verification_Activity.class));

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //handle the error
                Toast.makeText(Singup_Activity.this, "An error occurred", Toast.LENGTH_SHORT).show();
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
            String name=coustomerRegirsterPrefrences.getString("name","NOT_FOUND");
            String gender=coustomerRegirsterPrefrences.getString("gender","NOT_FOUND");
            String phoneNumber=editText.getText().toString();
            jsonBody = new JSONObject("{\"name\" " +
                    ": \"registerCustomer\"," +
                    "\"param\" : {" +
                    "\"name\"" +
                    " :" +
                    name +
                    "," +
                    "\"mobile\" " +
                    ":" +
                    phoneNumber +
                    "," +
                    "\"gender\" " +
                    ":" +
                    gender +
                    "}}");
        } catch (JSONException e) {
            e.printStackTrace();
        }

//request a json object response
        JsonObjectRequest jsonRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, url, jsonBody, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //now handle the response
                Toast.makeText(Singup_Activity.this, response.toString(), Toast.LENGTH_SHORT).show();
                System.out.println("------------------------------------------");

                System.out.println(response.toString());
                if(response.toString().contains("200"))
                    startActivity(new Intent(Singup_Activity.this, Verification_Activity.class));

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //handle the error
                Toast.makeText(Singup_Activity.this, "An error occurred", Toast.LENGTH_SHORT).show();
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

// Add the request to the queue
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(jsonRequest);
    }

}


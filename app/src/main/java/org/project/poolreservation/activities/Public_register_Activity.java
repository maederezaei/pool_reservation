package org.project.poolreservation.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.project.poolreservation.R;
import org.project.poolreservation.UserMainPage;

import java.util.HashMap;
import java.util.Map;

public class Public_register_Activity extends AppCompatActivity {

    Spinner spinner;
    String[] items;
    Button button;
    EditText city;
    String generaltoken;
    SharedPreferences vrificationActivityPreferences,userTypePagePreferences,signUpPagePreferences,splashActivityPreferences,preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_register_);
        vrificationActivityPreferences=getSharedPreferences("VerificatioActivityPref",MODE_PRIVATE);
        userTypePagePreferences=getSharedPreferences("UserTypePagePref",MODE_PRIVATE);
        signUpPagePreferences = getSharedPreferences("SignUpPagePref", MODE_PRIVATE);
        splashActivityPreferences=getSharedPreferences("SplashActivtyPref",MODE_PRIVATE);
        preferences=getSharedPreferences("PublicRegisterActivityPref",MODE_PRIVATE);

        final String user_role=userTypePagePreferences.getString("UserRole","NOT_FOUND");
        button=findViewById(R.id.button2);
        city=findViewById(R.id.city);
        intSpinner();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!splashActivityPreferences.getString("token","NOT_FOUND").equals("NOT_FOUND")){
                    generaltoken=splashActivityPreferences.getString("token","NOT_FOUND");
                    Toast.makeText(Public_register_Activity.this,"we already have toke",Toast.LENGTH_SHORT).show();
                    finishActivity();
                }else{
                    Toast.makeText(Public_register_Activity.this,"we dont have toke",Toast.LENGTH_SHORT).show();
                    generateTokensendJsonToserver();

                }

            }
        });

    }
    public void finishActivity(){
        final String user_role=userTypePagePreferences.getString("UserRole","NOT_FOUND");
        if(user_role.equals("Owner")) {

            Intent intent = new Intent(Public_register_Activity.this, Pool_Register_Page.class);
            System.out.println("token just before send:=="+generaltoken);
            intent.putExtra("token",generaltoken);
            intent.putExtra("state",spinner.getSelectedItem().toString());
            intent.putExtra("city",city.getText().toString());
            startActivity(intent);
        }else {
            Intent intent = new Intent(Public_register_Activity.this, UserMainPage.class);
            System.out.println("token just before send:=="+generaltoken);
            intent.putExtra("token",generaltoken);
            intent.putExtra("state",spinner.getSelectedItem().toString());
            intent.putExtra("city",city.getText().toString());
            startActivity(intent);
        }
    }

    private void intSpinner() {
        spinner=findViewById(R.id.spinner);
        items=getResources().getStringArray(R.array.city_name);

        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
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
                Toast.makeText(Public_register_Activity.this, response.toString(), Toast.LENGTH_SHORT).show();
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
                    generaltoken=token;
                    finishActivity();
                    System.out.println("token:=="+generaltoken);
                }



            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //handle the error
                Toast.makeText(Public_register_Activity.this, "An error occurred", Toast.LENGTH_SHORT).show();
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

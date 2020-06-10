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
    SharedPreferences sharedPreferences, preferences;
    private RequestQueue requestQueue;
    int randomnumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup_);
        sharedPreferences = getSharedPreferences("SignUpPagePref", MODE_PRIVATE);
        preferences = getSharedPreferences("UserTypePagePref", MODE_PRIVATE);

        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button2);
        // StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        // StrictMode.setThreadPolicy(policy);


    }

    public void click(View view) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("mobile", editText.getText().toString().trim());
        editor.apply();
        String user_role = preferences.getString("UserRole", "NOT_FOUND");
        if (user_role.equals("Owner")) {
            String data = "{\"name\" : \"registerOwner\",\"param\" : {\"first_name\" : \"علی\",\"last_name\" : \"محمدی\",\"mobile\" : \"09306643228\"}}";
            sendJsonToserver();
        } else if (user_role.equals("Customer")) {
            Log.i("test", "Customer");
        }
        startActivity(new Intent(Singup_Activity.this, Verification_Activity.class));

    }

    public void apiRequset() {
        final OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(mediaType, "{\"name\" : \"registerOwner\",\"param\" : {\"first_name\" : \"علی\",\"last_name\" : \"محمدی\",\"mobile\" : \"09306643228\"}}");
        System.out.println("---------------------------");
        System.out.println(body.contentType().toString());

        final Request request = new Request.Builder()
                .url("http://waterphone.ir/")
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("cache-control", "no-cache")
                .build();
      /*  final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.i("test","gereeeeeeeeeeee");
                    Response response = client.newCall(request).execute();

                } catch (IOException e) {
                    System.out.println("ggggggggggggggg");

                    e.printStackTrace();
                }
            }
        });*/
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request call, IOException e) {
                Log.i("test","fail");
                e.printStackTrace();
            }
            @Override
            public void onResponse(final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.i("test","fail_2");
                    throw new IOException("Unexpected code " + response);
                }else {
                    Response response1 = client.newCall(request).execute();
                    System.out.println("---------------------------------------------");
                    System.out.println(response.body().string());
                }

                // you code to handle response
            }
        }
);

    }

    private void Submit(String data)
    {
        final String savedata= data;
        String URL="http://waterphone.ir/";

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, URL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject objres=new JSONObject(response);
                    Toast.makeText(getApplicationContext(),objres.toString(),Toast.LENGTH_LONG).show();


                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"Server Error",Toast.LENGTH_LONG).show();

                }
                //Log.i("VOLLEY", response);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                //Log.v("VOLLEY", error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return savedata == null ? null : savedata.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    //Log.v("Unsupported Encoding while trying to get the bytes", data);
                    return null;
                }
            }

        };
        requestQueue.add(stringRequest);
    }
    private void postRequest() {
        RequestQueue requestQueue=Volley.newRequestQueue(Singup_Activity.this);
        String url="http://waterphone.ir/";
        StringRequest stringRequest=new StringRequest(com.android.volley.Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //let's parse json data
                try {
                    JSONObject jsonObject = new JSONObject(response);

                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                params.put("name" , "registerOwner");
                params.put("param","lkhlk");
                params.put("data_3_post","Value 3 Data");
                params.put("data_4_post","Value 4 Data");
                return params;
            }

            @Override
            public Map<String,String> getHeaders() throws AuthFailureError{
                Map<String,String> params=new HashMap<String, String>();
                params.put("content-type", "application/json");
                params.put("cache-control", "no-cache");

                return params;
            }
        };

        requestQueue.add(stringRequest);

    }

    private void sendGetRequest() {
        //get working now
        //let's try post and send some data to server
        RequestQueue queue= Volley.newRequestQueue(Singup_Activity.this);
        String url="http://waterphone.ir/";
        StringRequest stringRequest=new StringRequest(com.android.volley.Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("----------------------");
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(getApplicationContext(),jsonObject.toString(),Toast.LENGTH_LONG).show();


                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        queue.add(stringRequest);
    }

    private void sendJsonToserver(){
        String url = "http://waterphone.ir/";

//create post data as JSONObject - if your are using JSONArrayRequest use obviously an JSONArray :)
        JSONObject jsonBody = null;
        try {
            jsonBody = new JSONObject("{\"name\" : \"registerOwner\",\"param\" : {\"first_name\" : \"علی\",\"last_name\" : \"محمدی\",\"mobile\" : \"09306643228\"}}");
        } catch (JSONException e) {
            e.printStackTrace();
        }

//request a json object response
        JsonObjectRequest jsonRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, url, jsonBody, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //now handle the response
                Toast.makeText(Singup_Activity.this, response.toString(), Toast.LENGTH_SHORT).show();
System.out.println(response.toString());
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
        Volley.newRequestQueue(this).add(jsonRequest);
    }

}


//https://www.youtube.com/watch?v=TB9B3fMvTNU
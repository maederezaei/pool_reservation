package org.project.poolreservation.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.project.poolreservation.R;

import java.util.HashMap;
import java.util.Map;


public class Pool_Register_Page extends AppCompatActivity {
    EditText poolname;
    EditText poolnumber;
    EditText capacity;
    EditText poolInfo;
    EditText addresspool;
    Button sbtbutton;
    public int selected_item=0;
    String token;
    Boolean next=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pool_register_page);
        token=getIntent().getStringExtra("token");

    }

    private void init() {

        poolname = findViewById(R.id.editText);
        poolnumber = findViewById(R.id.editText2);
        capacity = findViewById(R.id.editText3);
        poolInfo = findViewById(R.id.editText4);
        addresspool = findViewById(R.id.editText5);
        sbtbutton = findViewById(R.id.button5);
    }

    public void buttOnClick(View view) {
        init();
        if (view.getId() == sbtbutton.getId()) {
            String poolName = poolname.getText().toString();
            String poolNumber = poolnumber.getText().toString();
            String Capacity = capacity.getText().toString();
            String PoolInfo = poolInfo.getText().toString();
            String AddressPool = addresspool.getText().toString();
            if (isValidInput(poolName, poolNumber, Capacity, PoolInfo,AddressPool )) {
                Toast.makeText(this,"Valid Input.",Toast.LENGTH_SHORT).show();
                sendJsonToserver();
                if(next)
                    showDialog(view);
            }
        }
    }

    private boolean isValidInput(String poolName, String poolNumber, String Capacity, String PoolInfo, String AddressPool) {
        if (poolName.length() < 2 ){
            Toast.makeText(this, "name should be at least 2 caracters", Toast.LENGTH_SHORT).show();
            poolname.requestFocus();
            return false;}

        if (poolNumber.length() != 11  ){
            Toast.makeText(this, "Wrong phone Number", Toast.LENGTH_SHORT).show();
            poolnumber.requestFocus();
            return false;}
        if (Capacity.length() == 0 ){
            Toast.makeText(this, "Please  enter the capacity", Toast.LENGTH_SHORT).show();
            capacity.requestFocus();
            return false;}
        if (PoolInfo.length() == 0){
            Toast.makeText(this, "\"please enter the poolInfo", Toast.LENGTH_SHORT).show();
            poolInfo.requestFocus();
            return false;}
        if(AddressPool.length()==0){
            Toast.makeText(this, "please enter the address", Toast.LENGTH_SHORT).show();
            addresspool.requestFocus();
            return false;}
        else return true;
        }

    public void showDialog(View view){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("قصد انجام جه عملیاتی را دارید؟")
                .setCancelable(false)
                .setSingleChoiceItems(new String[]{"ایجاد سانس جدید", "مشاهده ی سانس های موجود"}, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        selected_item=i;
                    }
                })
                .setPositiveButton("تایید", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(selected_item==0)
                        {
                            Intent intent=new Intent(Pool_Register_Page.this,edit_sans_Activty.class);
                            startActivity(intent);
                        }
                        if(selected_item==1)
                        {
                            Intent intent=new Intent(Pool_Register_Page.this,DayOfWeekActivity.class);
                            startActivity(intent);
                        }


                    }
                });
        builder.show();

    }

    private void sendJsonToserver(){
        String url = "http://waterphone.ir/";

//create post data as JSONObject - if your are using JSONArrayRequest use obviously an JSONArray :)
        JSONObject jsonBody = null;
        try {
            String poolName = poolname.getText().toString();
            System.out.println("=====poolname:=="+poolName);

            String poolNumber = poolnumber.getText().toString();
            System.out.println("=====poolNumber:=="+poolNumber);

            String Capacity = capacity.getText().toString();
            System.out.println("=====Capacity:=="+Capacity);

            String PoolInfo = poolInfo.getText().toString();
            System.out.println("=====PoolInfo:=="+PoolInfo);

            String AddressPool = addresspool.getText().toString();
            System.out.println("=====AddressPool:=="+AddressPool);

            String state="خراسان رضوی";
            String city="مشهد";
            jsonBody = new JSONObject("{\"name\" " +
                    ": \"addPool\"," +
                    "\"param\" : {" +
                    "\"name\"" +
                    " :" +
                    "\"pool\" " +
                    "," +
                    "\"state\" " +
                    ":" +
                    "\"pool\" " +
                    "," +
                    "\"تهران\" " +
                    ":" +
                    "\"pool\" " +
                    "," +
                    "\"phone\" " +
                    ":" +
                    "\"08733660130\" " +
                    "," +
                    "\"capacity\" " +
                    ":" +
                    "\"200\" " +
                    "," +
                    "\"address\" " +
                    ":" +
                    "\"pool\" " +
                    "," +
                    "\"info\" " +
                    ":" +
                    "\"pool\" " +
                    "}}");
        } catch (JSONException e) {
            e.printStackTrace();
        }

//request a json object response
        JsonObjectRequest jsonRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, url, jsonBody, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //now handle the response
                Toast.makeText(Pool_Register_Page.this, response.toString(), Toast.LENGTH_SHORT).show();
                System.out.println("------------------------------------------");

                System.out.println(response.toString());
                if(response.toString().contains("200"))
                    next=true;
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //handle the error
                Toast.makeText(Pool_Register_Page.this, "An error occurred", Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        }) {    //this is the part, that adds the header to the request
            @Override
            public Map<String, String> getHeaders() {
                System.out.println("------token:---------"+token);
                Map<String, String> params = new HashMap<String, String>();
                params.put("content-type", "application/json");
                params.put("cache-control", "no-cache");
                params.put("authorization","Bearer "+token);


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




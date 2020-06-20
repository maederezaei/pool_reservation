package org.project.poolreservation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;
import  org.project.PoolResetvation.*;
import org.project.poolreservation.activities.Public_register_Activity;
import org.project.poolreservation.models.Pool;
import org.project.poolreservation.recyclerviews.PoolsAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


public class UserMainPage extends AppCompatActivity {

    List<Pool> poolsList=new ArrayList<>();
    String token="";
    String state="";
    String city="";
    ArrayList<String> pool_name=new ArrayList<>();
    ArrayList<String> pool_rate=new ArrayList<>();
    ArrayList<String> pool_capacity=new ArrayList<>();
    ArrayList<String> pool_phone=new ArrayList<>();
    ArrayList<String> pool_address=new ArrayList<>();
    ArrayList<String> pool_info=new ArrayList<>();
    ArrayList<String> pool_id=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main_page);
        token=getIntent().getStringExtra("token");
        state=getIntent().getStringExtra("state");
        city=getIntent().getStringExtra("city");


        JsonToserver();



    }

    public void showPoolInfo(Pool pool){
        Intent intent=new Intent(this,pool_info_page.class);
        intent.putExtra("poolName",pool.poolName);
        intent.putExtra("poolRate",pool.poolRate);
        intent.putExtra("poolAddress",pool.poolAddress);
        intent.putExtra("poolInfo",pool.poolInfo);
        intent.putExtra("poolPhone",pool.poolPhone);
        intent.putExtra("poolCapacity",pool.poolCapacity);
        intent.putExtra("poolId",pool.poolId);
        intent.putExtra("token",token);
        startActivity(intent);
    }
    public void JsonToserver(){
        String url = "http://waterphone.ir/";

//create post data as JSONObject - if your are using JSONArrayRequest use obviously an JSONArray :)
        JSONObject jsonBody = null;
        try {

            jsonBody = new JSONObject("{\"name\" " +
                    ": \"getAllPools\"," +
                    "\"param\" : {" +
                    "\"name\" " +
                    ":" +
                    null +
                    "}}");


        } catch (JSONException e) {
            e.printStackTrace();
        }

//request a json object response
        JsonObjectRequest jsonRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, url, jsonBody, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //now handle the response
                Toast.makeText(UserMainPage.this, response.toString(), Toast.LENGTH_SHORT).show();
                System.out.println("------------------------------------------");

                System.out.println(response.toString());
                ArrayList<String> poolArray=new ArrayList<>();
                if( response.toString().contains("200")){

                    int start_array_index=response.toString().indexOf("[")+1;
                    String poolItem="";
                    while (true){
                        poolItem+=response.toString().charAt(start_array_index);
                        if(response.toString().charAt(start_array_index)=='}')
                        {
                            poolArray.add(poolItem);
                            System.out.println(poolItem);
                            poolItem="";
                            start_array_index++;
                            if(response.toString().charAt(start_array_index)==']')
                                break;
                        }
                        start_array_index++;

                    }




                    for(int i=0;i<poolArray.size();i++)
                    {
                        try {
                            JSONObject jsonObject=new JSONObject(poolArray.get(i));
                            if(jsonObject.getString("city").equals(city) & jsonObject.getString("state").equals(state) )
                            {
                            pool_name.add(jsonObject.getString("name"));
                            pool_rate.add(jsonObject.getString("rate"));
                            pool_capacity.add(jsonObject.getString("capacity"));
                            pool_phone.add(jsonObject.getString("phone"));
                            pool_address.add(jsonObject.getString("address"));
                            pool_info.add(jsonObject.getString("info"));
                            pool_id.add(jsonObject.getString("id"));


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    addItem();
                }



            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //handle the error
                Toast.makeText(UserMainPage.this, "An error occurred", Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        }) {    //this is the part, that adds the header to the request
            @Override
            public Map<String, String> getHeaders() {
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
    public void addItem(){
        for(int i=0;i<pool_name.size();i++)
        {
            poolsList.add(new Pool(pool_id.get(i),pool_name.get(i),pool_rate.get(i),pool_capacity.get(i),pool_phone.get(i),pool_address.get(i),pool_info.get(i)));
        }
        //poolsList.add(new Pool("استخر 1 "));


        RecyclerView recyPool=findViewById(R.id.recyPools);
        recyPool.setLayoutManager(new LinearLayoutManager(this));
        recyPool.setAdapter(new PoolsAdapter(poolsList, this));
    }

}

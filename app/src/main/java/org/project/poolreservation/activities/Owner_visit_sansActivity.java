package org.project.poolreservation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.project.poolreservation.R;
import org.project.poolreservation.sans.Sans;
import org.project.poolreservation.sans.SansAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Owner_visit_sansActivity extends AppCompatActivity {
    List<Sans> sans;
    ListView listView;
    SansAdapter adapter;
    String token;
    ArrayList<String> id=new ArrayList<>();
    ArrayList<String> start_hour_arr=new ArrayList<>();
    ArrayList<String> end_hour_arr=new ArrayList<>();
    ArrayList<String> gender_arr=new ArrayList<>();
    ArrayList<String> reserved_num_arr=new ArrayList<>();
    TextView title_date_edittext,date_tv;
    String date="";
    RelativeLayout no_sans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityowner_visit_sans);
        title_date_edittext=findViewById(R.id.title_date);
        listView=findViewById(R.id.listView);
        no_sans=findViewById(R.id.no_sans);
        date_tv=findViewById(R.id.date_tv);
        sans=new ArrayList<>();
        id=getIntent().getStringArrayListExtra("id");
        start_hour_arr=getIntent().getStringArrayListExtra("start_hour");
        end_hour_arr=getIntent().getStringArrayListExtra("end_hour");
        gender_arr=getIntent().getStringArrayListExtra("gender");
        reserved_num_arr=getIntent().getStringArrayListExtra("reserved_number");
        date=getIntent().getStringExtra("date");
        token=getIntent().getStringExtra("token");

        if(!date.equals(""))
        title_date_edittext.setText(date);
        else {
            date_tv.setVisibility(View.INVISIBLE);
            no_sans.setVisibility(View.VISIBLE);
        }
        int size=id.size();
        for(int i=0;i<size;i++)
            //sendJsonToserver(id.get(i),size,i);
        {
            sans.add(new Sans(token,Integer.valueOf(id.get(i)),start_hour_arr.get(i)+" - "+end_hour_arr.get(i),gender_arr.get(i),Integer.valueOf(reserved_num_arr.get(i)),10000));
        }
        adapter=new SansAdapter(this,sans);
        listView.setAdapter(adapter);

    }

    private void prepareDate() {


    }
    private void sendJsonToserver(final String id, final int size, final int i){
        String url = "http://waterphone.ir/";

//create post data as JSONObject - if your are using JSONArrayRequest use obviously an JSONArray :)
        JSONObject jsonBody = null;

        int id_int=Integer.valueOf(id);
        try {

            jsonBody = new JSONObject("{\"name\" " +
                    ": \"getSectionById\"," +
                    "\"param\" : {" +
                    "\"id\"" +
                    " :" +
                    id_int +
                    "}}");
        } catch (JSONException e) {
            e.printStackTrace();
        }

//request a json object response
        JsonObjectRequest jsonRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, url, jsonBody, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //now handle the response
                Toast.makeText(Owner_visit_sansActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                System.out.println("------------------------------------------");
                System.out.println(response.toString());
                if(response.toString().contains("200")){
                    String start_hour="";
                    String end_hour="";
                    String gender="";
                    String reserved_number="";
                    String persian_gender="";

                    int start_hour_index=response.toString().indexOf("start_hour");
                    for(int i=0;i<5;i++){
                        start_hour+=response.toString().charAt(start_hour_index+13+i);
                    }
                    int end_hour_index=response.toString().indexOf("end_hour");
                    for(int i=0;i<5;i++){
                        end_hour+=response.toString().charAt(start_hour_index+13+i);
                    }
                    int gender_index=response.toString().indexOf("gender");
                    for(int i=0;i<6;i++){
                        gender+=response.toString().charAt(gender_index+9+i);
                    }
                    int reserved_number_index=response.toString().indexOf("reserved_number");
                    int a=0;
                    while (true){
                        reserved_number+=response.toString().charAt(reserved_number_index+a+18);
                        a++;
                        if(response.toString().charAt(reserved_number_index+18+a)=='"')
                            break;
                    }
                    if(gender.equals("female"))
                        persian_gender="بانوان";
                    else if(gender.equals("male"))
                        persian_gender="آقایان";



                    System.out.println("*****"+start_hour);
                    System.out.println("*****"+end_hour);
                    System.out.println("*****"+persian_gender);
                    System.out.println("*****"+reserved_number);
                    sans.add(new Sans(token,Integer.valueOf(id),start_hour+" - "+end_hour,persian_gender,Integer.valueOf(reserved_number),10000));


                        System.out.println("i: "+String.valueOf(i)+"size: "+String.valueOf(size));
                        int date_index=response.toString().indexOf("date");
                        for(int i=0;i<10;i++){
                            date+=response.toString().charAt(date_index+7+i);
                        }
                        title_date_edittext.setText(date);
                        prepareDate();

                }


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //handle the error
                Toast.makeText(Owner_visit_sansActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
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



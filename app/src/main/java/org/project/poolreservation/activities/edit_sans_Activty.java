package org.project.poolreservation.activities;

import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.project.poolreservation.R;
import org.project.poolreservation.activities.helper.DateHelper;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class edit_sans_Activty extends AppCompatActivity implements DateHelper.DateHelperPresenter{


    private TextView date_tv;
    private TextView start_time_tv;
    private TextView finish_time_tv;
    private TextView price_tv;
    public TimePicker timePicker;
    public TimePickerDialog picker;
    public DateHelper dateHelper;
    private RadioButton male,female;
    private Button button;
    String start_hour="";
    String end_hour="";
    String gender="";
    String price="";
    String date="";
    String token="";
    String id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sans__activty);
        start_hour=getIntent().getStringExtra("start_hour");
        end_hour=getIntent().getStringExtra("end_hour");
        gender=getIntent().getStringExtra("gender");
        date=getIntent().getStringExtra("date");
        price=getIntent().getStringExtra("price");
        token=getIntent().getStringExtra("token");
        id=getIntent().getStringExtra("id");



        date_tv=findViewById(R.id.date_tv);
        start_time_tv=findViewById(R.id.start_time);
        finish_time_tv=findViewById(R.id.finish_time);
        price_tv=findViewById(R.id.price);
        male=findViewById(R.id.male);
        female=findViewById(R.id.female);
        button=findViewById(R.id.edit_btn);

        start_time_tv.setText(start_hour);
        finish_time_tv.setText(end_hour);
        date_tv.setText(date);
        price_tv.setText(price);
        if(gender.equals("آقایان"))
            male.setChecked(true);
        else female.setChecked(true);

        dateHelper=new DateHelper();
        dateHelper.setDateHelperPresenter(this);


        date_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dateHelper.getDateTimeDialog(edit_sans_Activty.this , 1001);


            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendJsonToserver();
            }
        });



    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void showDialog(final View view){


        final Calendar cldr = Calendar.getInstance();
        int hour = cldr.get(Calendar.HOUR_OF_DAY);
        int minutes = cldr.get(Calendar.MINUTE);
        // time picker dialog
        picker = new TimePickerDialog(edit_sans_Activty.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        if(view== findViewById(R.id.start_time))
                            start_time_tv.setText(sHour + ":" + sMinute);
                        if(view== findViewById(R.id.finish_time))
                            finish_time_tv.setText(sHour + ":" + sMinute);
                    }
                }, hour, minutes, true);
        picker.show();
    }






    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("         yyyy/MM/dd     ");
        Date date = new Date();
        return dateFormat.format(date);
    }

    @Override
    public void onDateSelected(String datePersian, String dateAD, int requestCode) {
        if(requestCode==1001) {
            date_tv.setText(datePersian);


        }
    }


    private void sendJsonToserver(){
        String url = "http://waterphone.ir/";

//create post data as JSONObject - if your are using JSONArrayRequest use obviously an JSONArray :)
        JSONObject jsonBody = null;
        try {
            String date=date_tv.getText().toString();
            String start_time=start_time_tv.getText().toString();
            String finish_time=finish_time_tv.getText().toString();
            int prices=Integer.valueOf(price_tv.getText().toString());
            if(male.isChecked())
                gender="male";
            else if(female.isChecked())
                gender="female";

            if(start_time.charAt(2)!=':')
                start_time="0"+start_time;
            if(start_time.length()<5)
                start_time=start_time.replace(":",":0");
            start_time=start_time+":00";
            // start_time=start_time.replaceAll(":","-");
            System.out.println(start_time);
            start_time="\""+start_time+"\"";
            System.out.println(start_time);


            if(finish_time.charAt(2)!=':')
                finish_time="0"+finish_time;
            if(finish_time.length()<5)
                finish_time=finish_time.replace(":",":0");
            finish_time=finish_time+":00";
            //finish_time=finish_time.replaceAll(":","-");
            finish_time="\""+finish_time+"\"";
            System.out.println(finish_time);
            jsonBody = new JSONObject("{\"name\" " +
                    ": \"updateSection\"," +
                    "\"param\" : {" +
                    "\"id\"" +
                    " :" +
                    id  +
                    "," +
                    "\"date\" " +
                    ":" +
                    date +
                    "," +
                    "\"day\" " +
                    ":" +
                    "\"جمعه\" " +
                    "," +
                    "\"start_hour\" " +
                    ":" +
                    start_time +
                    "," +
                    "\"end_hour\" " +
                    ":" +
                    finish_time +
                    "," +
                    "\"gender\" " +
                    ":" +
                    gender +
                    "," +
                    "\"cost\" " +
                    ":" +
                    price +
                    "}}");
        } catch (JSONException e) {
            e.printStackTrace();
        }

//request a json object response
        JsonObjectRequest jsonRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, url, jsonBody, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //now handle the response
                Toast.makeText(edit_sans_Activty.this, response.toString(), Toast.LENGTH_SHORT).show();
                System.out.println("------------------------------------------");

                System.out.println(response.toString());
                if(response.toString().contains("200"))
                    Toast.makeText(edit_sans_Activty.this,"عملیات با موفقیت انجام شد",Toast.LENGTH_SHORT).show();

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //handle the error
                Toast.makeText(edit_sans_Activty.this, "An error occurred", Toast.LENGTH_SHORT).show();
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

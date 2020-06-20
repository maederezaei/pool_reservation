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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class new_sans_Activity extends AppCompatActivity implements DateHelper.DateHelperPresenter{
    private static final String TAG = "MainActivity";

    private TextView date_tv;
    private TextView start_time_tv;
    private TextView finish_time_tv;
    private TextView price;
    private RadioButton radioButton_male,radioButton_felmale;
    public TimePickerDialog picker;
    public DateHelper dateHelper;
    private Button button;
    private String token;
    private String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sans_);

        token=getIntent().getStringExtra("token");

        date_tv=findViewById(R.id.date_tv);
        start_time_tv=findViewById(R.id.start_time);
        finish_time_tv=findViewById(R.id.finish_time);
        price=findViewById(R.id.price);
        radioButton_felmale=findViewById(R.id.female_radio_button);
        radioButton_male=findViewById(R.id.male_radio_button);
        button=findViewById(R.id.verify);
        Toast.makeText(new_sans_Activity.this,"------------",Toast.LENGTH_SHORT).show();
        dateHelper=new DateHelper();
        dateHelper.setDateHelperPresenter(this);

        date_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dateHelper.getDateTimeDialog(new_sans_Activity.this , 1001);


            }
        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(new_sans_Activity.this,"new Sans clicked",Toast.LENGTH_SHORT).show();
                sendJsonToserver();
            }
        });



    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void showDialog(final View view){

      //  Dialog dialog= new Dialog(this);
      //  dialog.setTitle("Time");
      //  dialog.setContentView(R.layout.time_picker);
        final Calendar cldr = Calendar.getInstance();
        int hour = cldr.get(Calendar.HOUR_OF_DAY);
        int minutes = cldr.get(Calendar.MINUTE);
        // time picker dialog
        picker = new TimePickerDialog(new_sans_Activity.this,
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
            Toast.makeText(new_sans_Activity.this,dateAD,Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onDateSelected: " + dateAD);
            Log.d(TAG, "onDateSelected: " + datePersian);


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
            int prices=Integer.valueOf(price.getText().toString());
            if(radioButton_male.isChecked())
                gender="male";
            else if(radioButton_felmale.isChecked())
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
                    ": \"addSection\"," +
                    "\"param\" : {" +
                    "\"date\"" +
                    " :" +
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
                    prices +
                    "}}");
        } catch (JSONException e) {
            e.printStackTrace();
        }

//request a json object response
        JsonObjectRequest jsonRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, url, jsonBody, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //now handle the response
                Toast.makeText(new_sans_Activity.this, response.toString(), Toast.LENGTH_SHORT).show();
                System.out.println("------------------------------------------");

                System.out.println(response.toString());


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //handle the error
                Toast.makeText(new_sans_Activity.this, "An error occurred", Toast.LENGTH_SHORT).show();
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

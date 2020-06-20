package org.project.poolreservation.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.project.poolreservation.R;
import org.project.poolreservation.models.DayOfWeek;
import org.project.poolreservation.models.TimeOfDay;
import org.project.poolreservation.recyclerviews.DayOfWeekAdapter;
import org.project.poolreservation.recyclerviews.TimeOfDayAdaptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DayOfWeekActivity extends AppCompatActivity {

    List<DayOfWeek> dayOfWeekList = new ArrayList<>();
    List<TimeOfDay> timeOfDayList = new ArrayList<>();
    RecyclerView dayOfWeekRecy, timeOfDayRecy;
    TextView title;
    RelativeLayout filterLayout;
    Button topBtn, maleFilter, femaleFilter;
    SharedPreferences userTypePagePref;
    String token;
    String date;
    String day;
    ArrayList<String> dateArray=new ArrayList<>();
    ArrayList<String> dayArray1=new ArrayList<>();
    ArrayList<String> dayArray2=new ArrayList<>();
    ArrayList<String> dayArray3=new ArrayList<>();
    ArrayList<String> dayArray4=new ArrayList<>();
    ArrayList<String> dayArray5=new ArrayList<>();
    ArrayList<String> dayArray6=new ArrayList<>();
    ArrayList<String> dayArray7=new ArrayList<>();
    String poolId="";
    String user_role="";

    ArrayList<String> genderArr=new ArrayList<>();
    ArrayList<String> startHourArr=new ArrayList<>();
    ArrayList<String> endHourArr=new ArrayList<>();
    ArrayList<String> priceArr=new ArrayList<>();
    ArrayList<String> reservedNumArr=new ArrayList<>();
    ArrayList<String> sectionIdArr=new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_of_week);
        userTypePagePref=getSharedPreferences("UserTypePagePref",MODE_PRIVATE);

        user_role=userTypePagePref.getString("UserRole","NOT_FOUND");

        dateArray=getIntent().getStringArrayListExtra("dateArray");
        token=getIntent().getStringExtra("token");
        if(user_role.equals("Customer"))
            poolId=getIntent().getStringExtra("poolId");

        setArrayValues();

        setDateFake();

        dayOfWeekRecy = findViewById(R.id.dayOfWeekRecy);
        timeOfDayRecy = findViewById(R.id.timeOfDayRecy);
        title = findViewById(R.id.title);
        topBtn = findViewById(R.id.btnTop);
        filterLayout = findViewById(R.id.filterLayout);
        maleFilter = findViewById(R.id.maleFilter);
        femaleFilter = findViewById(R.id.femaleFilter);
        dayOfWeekRecy.setLayoutManager(new LinearLayoutManager(this));
        timeOfDayRecy.setLayoutManager(new LinearLayoutManager(this));
        dayOfWeekRecy.setAdapter(new DayOfWeekAdapter(dayOfWeekList, this));





        topBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dayOfWeekRecy.getVisibility() == View.VISIBLE) {

                } else if (timeOfDayRecy.getVisibility() == View.VISIBLE) {
                    filterLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        maleFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterLayout.setVisibility(View.GONE);
                for (int i = 0; i < timeOfDayList.size(); i++) {
                    if (timeOfDayList.get(i).getGender().equals("female")) {
                        timeOfDayList.get(i).setShow(false);
                    } else {
                        timeOfDayList.get(i).setShow(true);
                    }
                }
                timeOfDayRecy.getAdapter().notifyDataSetChanged();
            }
        });

        femaleFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterLayout.setVisibility(View.GONE);
                for (int i = 0; i < timeOfDayList.size(); i++) {
                    if (timeOfDayList.get(i).getGender().equals("male")) {
                        timeOfDayList.get(i).setShow(false);
                    } else {
                        timeOfDayList.get(i).setShow(true);
                    }
                }
                timeOfDayRecy.getAdapter().notifyDataSetChanged();
            }
        });


    }

    private void setDateFake() {

        List<TimeOfDay> times = new ArrayList<>();
       /* for(int i=0;i<genderArr.size();i++) {
            times.add(new TimeOfDay(sectionIdArr.get(i), priceArr.get(i), startHourArr.get(i) + " - " + endHourArr.get(i),
                    genderArr.get(i), 0));
        }*/
        times.add(new TimeOfDay("1", "0", "5", "male",2));
       /* times.add(new TimeOfDay("10-12", 0, 5, 20));
        times.add(new TimeOfDay("13-15", 0, 20, 50));
        times.add(new TimeOfDay("15-18", 1, 5, 10));
        times.add(new TimeOfDay("18-20", 1, 50, 0));*/

        int size=dateArray.size();
        System.out.println("------------"+size);
        if(size==1)
            dayOfWeekList.add(new DayOfWeek(dayArray1.get(0), dateArray.get(0).replace("\\/", "-")));

        if(size==2)
            for(int i=0;i<2;i++)
                dayOfWeekList.add(new DayOfWeek(dayArray2.get(i), dateArray.get(i).replace("\\/", "-")));
        if(size==3)
            for(int i=0;i<3;i++)
                dayOfWeekList.add(new DayOfWeek(dayArray3.get(i), dateArray.get(i).replace("\\/", "-")));
        if(size==4)
            for(int i=0;i<4;i++)
                dayOfWeekList.add(new DayOfWeek(dayArray4.get(i), dateArray.get(i).replace("\\/", "-")));

        if(size==5)
            for(int i=0;i<5;i++)
                dayOfWeekList.add(new DayOfWeek(dayArray5.get(i), dateArray.get(i).replace("\\/", "-")));

        if(size==6)
            for(int i=0;i<6;i++)
                dayOfWeekList.add(new DayOfWeek(dayArray6.get(i), dateArray.get(i).replace("\\/", "-")));

        if(size==7)
            for(int i=0;i<7;i++)
                dayOfWeekList.add(new DayOfWeek(dayArray7.get(i), dateArray.get(i).replace("\\/", "-")));
/*
        dayOfWeekList.add(new DayOfWeek("شنبه", "1399/03/01", times));
        dayOfWeekList.add(new DayOfWeek("یکشنبه", "1399/03/02", times));
        dayOfWeekList.add(new DayOfWeek("دوشنبه", "1399/03/03", times));
        dayOfWeekList.add(new DayOfWeek("سه شنبه", "1399/03/04", times));
        dayOfWeekList.add(new DayOfWeek("چهارشنبه", "1399/03/05", times));
        dayOfWeekList.add(new DayOfWeek("پنجشنبه", "1399/03/06", times));
        dayOfWeekList.add(new DayOfWeek("جمعه", "1399/03/07", null));*/
    }


    public void showTimeOfDay(DayOfWeek dayOfWeek) {
        /*if(user_role.equals("Customer")) {

            sendJsonToserver_getSection(dayOfWeek.getDate(),dayOfWeek.getDay(),poolId);
            title.setText(dayOfWeek.getDay() + " (" + dayOfWeek.getDate() + ")");
            topBtn.setText("فیلتر");

          //  timeOfDayList = dayOfWeek.getTimeOfDays();
            if (timeOfDayList != null && timeOfDayList.size() > 0) {
                for (int i = 0; i < timeOfDayList.size(); i++) {
                    timeOfDayList.get(i).setShow(true);
                }
                dayOfWeekRecy.setVisibility(View.GONE);
                timeOfDayRecy.setVisibility(View.VISIBLE);
                timeOfDayRecy.setAdapter(new TimeOfDayAdaptor(timeOfDayList, this));
            } else {
                Toast.makeText(this, "زمانی یافت نشد", Toast.LENGTH_SHORT).show();
            }
        }
        else if(user_role.equals("Owner")){
            date=dayOfWeek.getDate();
            day=dayOfWeek.getDay();
            sendJsonToserver();

        }*/
        if (user_role.equals("Customer")) {

            sendJsonToserver_getSection(dayOfWeek.getDate(), dayOfWeek.getDay(), poolId);
            title.setText(dayOfWeek.getDay() + " (" + dayOfWeek.getDate() + ")");
            topBtn.setText("فیلتر");

        } else if (user_role.equals("Owner")) {
            date = dayOfWeek.getDate();
            day = dayOfWeek.getDay();
            sendJsonToserver();

        }
    }



    @Override
    public void onBackPressed() {
        if (filterLayout.getVisibility() == View.VISIBLE) {
            filterLayout.setVisibility(View.GONE);
            return;
        }
        if (timeOfDayRecy.getVisibility() == View.VISIBLE) {
            timeOfDayRecy.setVisibility(View.GONE);
            dayOfWeekRecy.setVisibility(View.VISIBLE);
            topBtn.setText("هفته بعد");
            title.setText("انتخاب سانس");
        }

    }
    public void setArrayValues(){

        dayArray1.add("جمعه");

        dayArray2.add("پنج شنبه");
        dayArray2.add("جمعه");

        dayArray3.add("چهارشنبه");
        dayArray3.add("پنج شنبه");
        dayArray3.add("جمعه");

        dayArray4.add("سه شنبه");
        dayArray4.add("چهارشنبه");
        dayArray4.add("پنج شنبه");
        dayArray4.add("جمعه");

        dayArray5.add("دوشنبه");
        dayArray5.add("سه شنبه");
        dayArray5.add("چهارشنبه");
        dayArray5.add("پنج شنبه");
        dayArray5.add("جمعه");

        dayArray6.add("یک شنبه");
        dayArray6.add("دوشنبه");
        dayArray6.add("سه شنبه");
        dayArray6.add("چهارشنبه");
        dayArray6.add("پنج شنبه");
        dayArray6.add("جمعه");

        dayArray7.add("شنبه");
        dayArray7.add("یک شنبه");
        dayArray7.add("دوشنبه");
        dayArray7.add("سه شنبه");
        dayArray7.add("چهارشنبه");
        dayArray7.add("پنچ شنبه");
        dayArray7.add("جمعه");
    }

    private void sendJsonToserver(){
        String url = "http://waterphone.ir/";

//create post data as JSONObject - if your are using JSONArrayRequest use obviously an JSONArray :)
        JSONObject jsonBody = null;

        date=date.replace("\\/", "-");
        System.out.println("------"+date);
        day=day.replaceAll(" ","-");
        System.out.println(day);
        try {

            jsonBody = new JSONObject("{\"name\" " +
                    ": \"getSectionByDateAndDayOwner\"," +
                    "\"param\" : {" +
                    "\"date\"" +
                    " :" +
                    date +
                    "," +
                    "\"day\" " +
                    ":" +
                    day +
                    "}}");
        } catch (JSONException e) {
            e.printStackTrace();
        }

//request a json object response
        JsonObjectRequest jsonRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, url, jsonBody, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //now handle the response
                Toast.makeText(DayOfWeekActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                System.out.println("------------------------------------------");
                System.out.println(response.toString());
                String date="";
                if(response.toString().contains("\"status\":200")) {
                    ArrayList<String> id = new ArrayList<>();
                    ArrayList<String> start_hour_arr = new ArrayList<>();
                    ArrayList<String> end_hour_arr = new ArrayList<>();
                    ArrayList<String> gender_arr = new ArrayList<>();
                    ArrayList<String> reserved_num_arr = new ArrayList<>();
                    if (response.toString().contains("date"))
                    {
                        int date_index = response.toString().indexOf("date");
                    for (int i = 0; i < 10; i++) {
                        date += response.toString().charAt(date_index + i + 7);
                    }
                    }
                    String response1=response.toString();
                    while (response1.contains("\"id\""))
                    {   String iditem="";
                        int id_index=response1.indexOf("\"id\"");
                        System.out.println(id_index);
                        id_index+=6;
                        while (response1.charAt(id_index)!='"'){
                            iditem+=response1.charAt(id_index);
                            id_index++;
                        }
                        //-----------------------------------------
                        String start_hour="";
                        String end_hour="";
                        String gender="";
                        String reserved_number="";
                        String persian_gender="آقایان";

                        int start_hour_index=response1.indexOf("start_hour");
                        for(int i=0;i<5;i++){
                            start_hour+=response1.charAt(start_hour_index+13+i);
                        }
                        int end_hour_index=response1.indexOf("end_hour");
                        for(int i=0;i<5;i++){
                            end_hour+=response1.charAt(end_hour_index+11+i);
                        }
                        int gender_index=response1.indexOf("gender");
                        for(int i=0;i<6;i++){
                            gender+=response1.charAt(gender_index+9+i);
                        }
                        int reserved_number_index=response1.indexOf("reserved_number");
                        int a=0;
                        while (true){
                            reserved_number+=response1.charAt(reserved_number_index+a+18);
                            a++;
                            if(response1.charAt(reserved_number_index+18+a)=='"')
                                break;
                        }
                        if(gender.equals("female"))
                            persian_gender="بانوان";


                        id.add(iditem);
                        start_hour_arr.add(start_hour);
                        end_hour_arr.add(end_hour);
                        gender_arr.add(persian_gender);
                        reserved_num_arr.add(String.valueOf(reserved_number));

                        response1=response1.replaceFirst("\"id\"","--");
                        response1=response1.replaceFirst("start_hour","--");
                        response1=response1.replaceFirst("end_hour","--");
                        response1=response1.replaceFirst("gender","--");
                        response1=response1.replaceFirst("reserved_number","--");




                    }
                    System.out.println("__"+id);
                    Intent intent=new Intent(DayOfWeekActivity.this,Owner_visit_sansActivity.class);
                    intent.putExtra("token",token);
                    intent.putExtra("date",date);
                    intent.putStringArrayListExtra("id",id);
                    intent.putStringArrayListExtra("start_hour",start_hour_arr);
                    intent.putStringArrayListExtra("end_hour",end_hour_arr);
                    intent.putStringArrayListExtra("gender",gender_arr);
                    intent.putStringArrayListExtra("reserved_number",reserved_num_arr);

                    startActivity(intent);
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //handle the error
                Toast.makeText(DayOfWeekActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
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

    private void sendJsonToserver_getSection(String date1, String day1, final String poolId){
        String url = "http://waterphone.ir/";

//create post data as JSONObject - if your are using JSONArrayRequest use obviously an JSONArray :)
        JSONObject jsonBody = null;

        date1=date1.replace("\\/", "-");
        System.out.println("------"+date1);
        day1=day1.replaceAll(" ","-");
        System.out.println(day1);
        int id=Integer.valueOf(poolId);
        try {

            jsonBody = new JSONObject("{\"name\" " +
                    ": \"getSectionByDateAndDayCustomer\"," +
                    "\"param\" : {" +
                    "\"id\"" +
                    " :" +
                    id +
                    "," +
                    "\"day\"" +
                    " :" +
                    day1 +
                    "," +
                    "\"date\" " +
                    ":" +
                    date1 +
                    "}}");
        } catch (JSONException e) {
            e.printStackTrace();
        }

//request a json object response
        JsonObjectRequest jsonRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, url, jsonBody, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                //now handle the response
                Toast.makeText(DayOfWeekActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                System.out.println("------------------------------------------");
                System.out.println(response.toString());
                ArrayList<String> sectionArray=new ArrayList<>();
                if( response.toString().contains("200")){

                    int start_array_index=response.toString().indexOf("[")+1;
                    String poolItem="";
                    if(response.toString().charAt(start_array_index)!=']') {
                        while (true) {
                            poolItem += response.toString().charAt(start_array_index);
                            if (response.toString().charAt(start_array_index) == '}') {
                                sectionArray.add(poolItem);
                                System.out.println(poolItem);
                                poolItem = "";
                                start_array_index++;
                                if (response.toString().charAt(start_array_index) == ']')
                                    break;
                            }
                            start_array_index++;

                        }
                        System.out.println(sectionArray);

                    }


                    for(int i=0;i<sectionArray.size();i++)
                    {
                        try {
                            JSONObject jsonObject=new JSONObject(sectionArray.get(i));
                            if(jsonObject.getString("pool_id").equals(poolId)  )
                            {
                                genderArr.add(jsonObject.getString("gender"));
                                priceArr.add(jsonObject.getString("cost"));
                                startHourArr.add(jsonObject.getString("start_hour"));
                                endHourArr.add(jsonObject.getString("end_hour"));
                                reservedNumArr.add(jsonObject.getString("reserved_number"));
                                sectionIdArr.add(jsonObject.getString("id"));




                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(genderArr);
                    System.out.println(priceArr);
                    System.out.println(startHourArr);
                    System.out.println(endHourArr);
                    System.out.println(reservedNumArr);
                    System.out.println(sectionIdArr);

                    /* for(int i=0;i<genderArr.size();i++) {
            times.add(new TimeOfDay(sectionIdArr.get(i), priceArr.get(i), startHourArr.get(i) + " - " + endHourArr.get(i),
                    genderArr.get(i), 0));
        }*/

                    timeOfDayList.add(new TimeOfDay("1", "0", "5", "male", 2));
                    timeOfDayList.add(new TimeOfDay("2", "1200", "4", "famle", 2));
                    timeOfDayList.add(new TimeOfDay("3", "15000", "6", "male", 2));

                    if (timeOfDayList != null && timeOfDayList.size() > 0) {
                        for (int i = 0; i < timeOfDayList.size(); i++) {
                            timeOfDayList.get(i).setShow(true);
                        }
                        dayOfWeekRecy.setVisibility(View.GONE);
                        timeOfDayRecy.setVisibility(View.VISIBLE);
                        timeOfDayRecy.setAdapter(new TimeOfDayAdaptor(timeOfDayList, DayOfWeekActivity.this));
                    } else {
                        Toast.makeText(DayOfWeekActivity.this, "زمانی یافت نشد", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //handle the error
                Toast.makeText(DayOfWeekActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
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

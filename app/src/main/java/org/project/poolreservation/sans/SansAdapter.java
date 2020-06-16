package org.project.poolreservation.sans;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.project.poolreservation.R;
import org.project.poolreservation.activities.Owner_visit_sansActivity;
import org.project.poolreservation.activities.Public_register_Activity;
import org.project.poolreservation.activities.Singup_Activity;
import org.project.poolreservation.activities.SplashActivity;
import org.project.poolreservation.activities.UserTypePage;
import org.project.poolreservation.activities.Verification_Activity;
import org.project.poolreservation.activities.edit_sans_Activty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SansAdapter extends ArrayAdapter {
    private List<Sans> sans;
    private int id;
    public SansAdapter(@NonNull Context context, @NonNull List<Sans> sans) {
        super(context, R.layout.sans_item_list, sans);
        this.sans=sans;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Sans san=sans.get(position);
        ViewHolder holder;
        if(convertView== null){
            LayoutInflater inflater= (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.sans_2_item,null,true);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);

        }else {
            holder= (ViewHolder) convertView.getTag();
        }

        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),position+"",Toast.LENGTH_SHORT).show();
                sendJsonToserver(String.valueOf(sans.get(position).getId()),sans.get(position).getToken());

                    }
        });
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendJsonToserver_delete(String.valueOf(sans.get(position).getId()),sans.get(position).getToken());
            }
        });
        holder.fill(san);
        return convertView;
    }
    private class ViewHolder  {
        public TextView tv_time;
        public TextView tv_sex;
        public TextView tv_reserve;
        public ImageView btn_delete;
        public ImageView btn_edit;
        public ViewHolder(View converview ){
            if(converview!= null) {
                tv_time = converview.findViewById(R.id.time);
                tv_sex = converview.findViewById(R.id.sex);
                tv_reserve = converview.findViewById(R.id.reserve);
                btn_delete = converview.findViewById(R.id.delete);
                btn_edit = converview.findViewById(R.id.edit);
            }



        }

        public void fill(Sans sans){
            if(tv_time!=null)
                tv_time.setText(sans.getTime());
            if(tv_sex!=null)
                tv_sex.setText(sans.getSex());
            if(tv_reserve!=null)
                tv_reserve.setText(String.valueOf(sans.getReserves()) );


        }




    }

    private void sendJsonToserver(final String id,final String token){
        String url = "http://waterphone.ir/";

//create post data as JSONObject - if your are using JSONArrayRequest use obviously an JSONArray :)
        JSONObject jsonBody = null;

        final int id_int=Integer.valueOf(id);
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
                Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
                System.out.println("------------------------------------------");
                System.out.println(response.toString());
                if(response.toString().contains("200")){
                    String start_hour="";
                    String end_hour="";
                    String gender="";
                    String reserved_number="";
                    String persian_gender="آقایان";
                    String date="";
                    String price="";

                    int date_index=response.toString().indexOf("date");
                    for( int i=0 ;i<10;i++)
                    {
                        date+=response.toString().charAt(date_index+i+7);
                    }




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
                    int price_index=response.toString().indexOf("discount");
                    int a=0;
                    while (true){
                        price+=response.toString().charAt(price_index+a+11);
                        a++;
                        if(response.toString().charAt(price_index+11+a)=='"')
                            break;
                    }


                    if(gender.equals("female"))
                        persian_gender="بانوان";




                    System.out.println("*****"+start_hour);
                    System.out.println("*****"+end_hour);
                    System.out.println("*****"+persian_gender);
                    System.out.println("*****"+reserved_number);
                    Intent intent=new Intent(getContext(), edit_sans_Activty.class);
                    intent.putExtra("start_hour",start_hour);
                    intent.putExtra("end_hour",end_hour);
                    intent.putExtra("gender",persian_gender);
                    intent.putExtra("date",date);
                    intent.putExtra("price",price);
                    intent.putExtra("token",token);
                    intent.putExtra("id",id);
                    getContext().startActivity(intent);



                }


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //handle the error
                Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_SHORT).show();
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
        Volley.newRequestQueue(getContext()).add(jsonRequest);

    }


    private void sendJsonToserver_delete(final String id,final String token){
        String url = "http://waterphone.ir/";

//create post data as JSONObject - if your are using JSONArrayRequest use obviously an JSONArray :)
        JSONObject jsonBody = null;

        final int id_int=Integer.valueOf(id);
        try {

            jsonBody = new JSONObject("{\"name\" " +
                    ": \"deleteSection\"," +
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
                Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
                System.out.println("------------------------------------------");
                System.out.println(response.toString());
                if(response.toString().contains("200")) {
                    Toast.makeText(getContext(), "عملیات با موفقیت انجام شد", Toast.LENGTH_SHORT).show();

                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //handle the error
                Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_SHORT).show();
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
        Volley.newRequestQueue(getContext()).add(jsonRequest);

    }


}

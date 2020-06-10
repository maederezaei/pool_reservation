package org.project.poolreservation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class user_main_page extends AppCompatActivity {

    String pool_list[]={"استخر 1", "استخر 2", "استخر 3", "استخر 4", "استخر 5" , "استخر 6", "استخر 7", "استخر 8", "استخر 9","استخر 10"};
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main_page);
        final ListView myList=findViewById(R.id.listview);
        final ArrayAdapter<String> ArrayAdapter=new ArrayAdapter<String>(this, R.layout.activity_list_view, R.id.textView, pool_list);
        myList.setAdapter(ArrayAdapter);



    }

}

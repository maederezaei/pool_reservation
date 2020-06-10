package org.project.poolreservation.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import org.project.poolreservation.R;

public class Public_register_Activity extends AppCompatActivity {

    Spinner spinner;
    String[] items;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_register_);
        button=findViewById(R.id.button2);
        intSpinner();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  startActivity(new Intent(Public_register_Activity.this, UserTypePage.class));
                  }
        });

    }

    private void intSpinner() {
        spinner=findViewById(R.id.spinner);
        items=getResources().getStringArray(R.array.city_name);

        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}

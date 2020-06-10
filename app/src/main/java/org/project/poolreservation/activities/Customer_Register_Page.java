package org.project.poolreservation.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import org.project.poolreservation.R;

public class Customer_Register_Page extends AppCompatActivity {
    EditText name_txt;
    Button button;
    RadioButton radioButton_male,radioButton_female;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer__register__page);
        sharedPreferences=getSharedPreferences("CustomerRegisterPagePref",MODE_PRIVATE);
        name_txt=findViewById(R.id.name_tv);
        radioButton_female=findViewById(R.id.female_radio_button);
        radioButton_male=findViewById(R.id.male_radio_button);
        button=findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("name",name_txt.getText().toString().trim());
                if(radioButton_female.isChecked())
                    editor.putString("gender","female");
                else if(radioButton_male.isChecked())
                    editor.putString("gender","male");
                editor.apply();
                startActivity(new Intent(Customer_Register_Page.this, Singup_Activity.class));


            }
        });
    }
}
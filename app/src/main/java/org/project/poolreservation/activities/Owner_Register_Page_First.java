package org.project.poolreservation.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.project.poolreservation.R;

public class Owner_Register_Page_First extends AppCompatActivity {
    EditText name_edittext,family_edittext;
    Button button;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner__register__page__first);
        sharedPreferences=getSharedPreferences("OwnerRegisterPageFirstPref",MODE_PRIVATE);
        name_edittext=findViewById(R.id.name_edittext);
        family_edittext=findViewById(R.id.family_edittext);
        button=findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("first_name",name_edittext.getText().toString().trim());
                editor.putString("last_name",family_edittext.getText().toString().trim());
                editor.apply();
                startActivity(new Intent(Owner_Register_Page_First.this, Singup_Activity.class));



            }
        });
    }
}
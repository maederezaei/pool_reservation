package org.project.poolreservation.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.project.poolreservation.R;

public class UserTypePage extends AppCompatActivity {
    Button button4, button5;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type_page);
        preferences=getSharedPreferences("UserTypePagePref",MODE_PRIVATE);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("UserRole","Owner");
                editor.apply();
               startActivity(new Intent(UserTypePage.this, Owner_Register_Page_First.class));
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("UserRole","Customer");
                editor.apply();
                startActivity(new Intent(UserTypePage.this, Customer_Register_Page.class));

            }
        });
    }

  /*  public void buttonClick(View view) {
        if (view.getId() == R.id.button4)
            startActivity(new Intent(UserTypePage.this, Owner_Register_Page.class));
        if (view.getId() == R.id.button5)
                    startActivity(new Intent(UserTypePage.this, UserTypePage.class));


    }
    */
}

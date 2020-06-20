package org.project.poolreservation;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import org.project.poolreservation.activities.Owner_Register_Page_First;
import org.project.poolreservation.activities.Singup_Activity;

public class Reservation_page extends Activity {

    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_page);
        button=findViewById(R.id.button6);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Reservation_page.this, Customer_Reserved_Page.class));



            }
        });
    }
}

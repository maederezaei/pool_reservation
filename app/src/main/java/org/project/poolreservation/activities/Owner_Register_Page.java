package org.project.poolreservation.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.project.poolreservation.R;


public class Owner_Register_Page extends AppCompatActivity {
    EditText poolname;
    EditText poolnumber;
    EditText responsiblenumber;
    EditText nationalcode;
    EditText addresspool;
    Button sbtbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner__register__page__first);

    }

    private void init() {

        poolname = findViewById(R.id.editText);
        poolnumber = findViewById(R.id.editText2);
        responsiblenumber = findViewById(R.id.editText3);
        nationalcode = findViewById(R.id.editText4);
        addresspool = findViewById(R.id.editText5);
        sbtbutton = findViewById(R.id.button5);
    }

    public void buttOnClick(View view) {
        init();
        if (view.getId() == sbtbutton.getId()) {
            String poolName = poolname.getText().toString();
            String poolNumber = poolnumber.getText().toString();
            String responsibleNumber = responsiblenumber.getText().toString();
            String nationalCode = nationalcode.getText().toString();
            String AddressPool = addresspool.getText().toString();
            if (isValidInput(poolName, poolNumber, responsibleNumber, nationalCode,AddressPool )) {
                Toast.makeText(this,"Valid Input.",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Owner_Register_Page.this, Owner_Register_Page.class));




            }

        }

    }

    private boolean isValidInput(String poolName, String poolNumber, String responsibleNumber, String nationalCode, String AddressPool) {
        if (poolName.length() < 2 ){
            Toast.makeText(this, "name should be at least 2 caracters", Toast.LENGTH_SHORT).show();
            poolname.requestFocus();
            return false;}

        if (poolNumber.length() != 11  ){
            Toast.makeText(this, "Wrong phone Number", Toast.LENGTH_SHORT).show();
            poolnumber.requestFocus();
            return false;}
        if (responsibleNumber.length() != 11 ){
            Toast.makeText(this, "Wrong phone Number", Toast.LENGTH_SHORT).show();
            responsiblenumber.requestFocus();
            return false;}
        if (nationalCode.length() != 10){
            Toast.makeText(this, "Wrong national code", Toast.LENGTH_SHORT).show();
            nationalcode.requestFocus();
            return false;}
        if(AddressPool.length()==0){
            Toast.makeText(this, "please enter the address", Toast.LENGTH_SHORT).show();
            addresspool.requestFocus();
            return false;}
        else return true;
    }


}





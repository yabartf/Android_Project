package com.example.android_project_5840_6639.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android_project_5840_6639.Entities.Travel;
import com.example.android_project_5840_6639.R;

public class AddTravelActivity extends AppCompatActivity {
    private final Travel travel = new Travel();

    TravelViewModel travelViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_travel);
        travelViewModel = new ViewModelProvider(this).get(TravelViewModel.class);
        travelViewModel.getIsSuccess().observe(this, aBoolean ->
                Toast.makeText(getBaseContext(),"updated",Toast.LENGTH_LONG).show());
    }

    public void addAddress(View view) {

    }


    public void submitted(View view) {
        EditText source  = findViewById(R.id.sourceAddressText);
        EditText dest = findViewById(R.id.destinationAddressText);
        EditText mail = findViewById(R.id.TextEmailAddress);
        EditText phone = findViewById(R.id.TextPhone);
        EditText num = findViewById(R.id.travelersNum);
        EditText name = findViewById(R.id.name);
        travel.setClientEmail(mail.getText().toString());
        travel.setAmountTravelers(num.getText().toString());
        travel.setClientName(name.getText().toString());
        travel.setClientPhone(phone.getText().toString());
        travel.setSource(source.getText().toString());
        travel.addDestinations(dest.getText().toString());
        travelViewModel.insert(travel);
    }
}
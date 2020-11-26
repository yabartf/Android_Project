package com.example.android_project_5840_6639.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android_project_5840_6639.Entities.Travel;
import com.example.android_project_5840_6639.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

public class AddTravelActivity extends AppCompatActivity {
    private final Travel travel = new Travel();
    private Date startDate;
    private Date endDate;
    private Boolean choosed = false;
    TravelViewModel travelViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_travel);
        travelViewModel = new ViewModelProvider(this).get(TravelViewModel.class);
        travelViewModel.getIsSuccess().observe(this, aBoolean ->
                Toast.makeText(getBaseContext(),"updated",Toast.LENGTH_LONG).show());
        CalendarView calendar = findViewById(R.id.calendarDate);
        Date now = new Date();
        calendar.setMinDate(now.getTime());
        calendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            if (!choosed){
                Toast.makeText(getBaseContext(),"choosed",Toast.LENGTH_LONG).show();
                String tempDate = "" + year +"/"  + (++month) + "/" + dayOfMonth +" 00:00:00";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                try {
                    startDate = sdf.parse(tempDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //calendar.setMinDate(startDate.getTime() + 86400001);
                choosed = true;
            }
            else
                if (choosed)
            {
                String tempDate = "" + year +"/" + (++month) + "/" + dayOfMonth +" 00:00:00";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                try {
                    endDate = sdf.parse(tempDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (endDate.getTime() < startDate.getTime()) {
                    Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_LONG).show();
                    endDate = null;
                }
                else
                    calendar.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void addAddress(View view) {

    }

    public void changePhoneNumbers(){

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
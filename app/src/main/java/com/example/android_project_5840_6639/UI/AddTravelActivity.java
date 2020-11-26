package com.example.android_project_5840_6639.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_project_5840_6639.Entities.Travel;
import com.example.android_project_5840_6639.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddTravelActivity extends AppCompatActivity {
    private final Travel travel = new Travel();
    private Date startDate;
    private Date endDate;
    private String name;
    private Boolean chosen = false;
    static int addTravelIndex = 0;
    int numOfDestnation = 10;
    TravelViewModel travelViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_travel);
        travelViewModel = new ViewModelProvider(this).get(TravelViewModel.class);
        travelViewModel.getIsSuccess().observe(this, aBoolean ->
                Toast.makeText(getBaseContext(),String.format("Thank you {0}, your request sent", name),Toast.LENGTH_LONG).show());

        CalendarView calendar = findViewById(R.id.calendarDate); // restart CalenderView to the current date
        Date now = new Date();
        calendar.setMinDate(now.getTime());
        calendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            if (!chosen){
                Toast.makeText(getBaseContext(),"chosen",Toast.LENGTH_LONG).show();
                String tempDate = "" + year +"/"  + (++month) + "/" + dayOfMonth +" 00:00:00";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                try {
                    startDate = sdf.parse(tempDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                chosen = true;
            }
            else
            if (chosen)
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

    /*
    add dynamic list addresses
    */
    public void addAddress(View view) {
        LinearLayout linearLayout = findViewById(R.id.addTravelsLayout);
        // restart new Button and Edit text
        EditText editText = new EditText(this);
        Button removeButton = new Button(this);
        editText.setHeight(288);
        editText.setWidth(49);
        editText.setEms(10);
        editText.setId(addTravelIndex + numOfDestnation);
        editText.setHint("Destination address");
        removeButton.setHeight(50);
        removeButton.setWidth(288);
        removeButton.setEms(10);
        removeButton.setId(addTravelIndex++);
        String text = "Remove";
        removeButton.setText(text);
        linearLayout.addView(editText,linearLayout.getChildCount()); // add to leaner layout
        linearLayout.addView(removeButton,linearLayout.getChildCount());

        // apply observer on the Buttons view
        removeButton.setOnClickListener(v -> {
            int id = v.getId();
            linearLayout.removeView(v);
            TextView tv = findViewById(id + numOfDestnation);   // find the TextView of the Button and remove them
            linearLayout.removeView(tv);
            for (int i = 2;i < linearLayout.getChildCount() / 2 +1;i++){        // update all indexes of Buttons and TextViews
                linearLayout.getChildAt(i).setId(i - 2);
                linearLayout.getChildAt(i + numOfDestnation).setId(i + numOfDestnation -2);
                }
            --addTravelIndex;
            });
        }


    /*
    collecting all fields, filling the travel object and send it to DataBase
     */
    public void submitted(View view) {
        EditText source  = findViewById(R.id.sourceAddressText);
        EditText dest = findViewById(R.id.destinationAddressText);
        EditText mail = findViewById(R.id.TextEmailAddress);
        EditText phone = findViewById(R.id.TextPhone);
        EditText num = findViewById(R.id.travelersNum);
        EditText eName = findViewById(R.id.name);
        name = eName.getText().toString();
        travel.setClientEmail(mail.getText().toString());
        travel.setAmountTravelers(num.getText().toString());
        travel.setClientName(name);
        travel.setClientPhone(phone.getText().toString());
        travel.setSource(source.getText().toString());
        travel.addDestinations(dest.getText().toString());
        travel.setEndDate(endDate);
        travel.setStartDate(startDate);
        travelViewModel.insert(travel);
    }
}
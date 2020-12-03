package com.example.android_project_5840_6639.UI;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.android_project_5840_6639.Data.Models.UserLocation;
import com.example.android_project_5840_6639.Entities.Travel;
import com.example.android_project_5840_6639.R;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AddTravelActivity extends AppCompatActivity {
    private final Travel travel = new Travel();
    private Date startDate;
    private Date endDate;
    private Boolean chosen = false;

    EditText source;
    EditText dest;
    EditText mail;
    EditText phone;
    EditText num;
    EditText eName;
    TravelViewModel travelViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_travel);
        travelViewModel = new ViewModelProvider(this).get(TravelViewModel.class);
        travelViewModel.getIsSuccess().observe(this, aBoolean ->
                Toast.makeText(getBaseContext(),"Thank you " + eName.getText().toString()  + " your request sent",Toast.LENGTH_LONG).show());
        source  = findViewById(R.id.sourceAddressText);
        dest = findViewById(R.id.destinationAddressText);
        mail = findViewById(R.id.TextEmailAddress);
        phone = findViewById(R.id.TextPhone);
        num = findViewById(R.id.travelersNum);
        eName = findViewById(R.id.name);
        playCalendar();
    }
    /*
    play calendar, set minimum date and declared listener.
     */
    private void playCalendar() {
        CalendarView calendar = findViewById(R.id.calendarDate); // restart CalenderView to the current date
        Date now = new Date();
        calendar.setMinDate(now.getTime());
        calendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            if (!chosen){
                Toast.makeText(getBaseContext(),"Chosen",Toast.LENGTH_LONG).show();
                String tempDate = "" + year +"/"  + (++month) + "/" + dayOfMonth +" 00:00:00";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                try {
                    startDate = sdf.parse(tempDate);
                    calendar.setMinDate(startDate.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                chosen = true;
            }
            else
                {
                String tempDate = "" + year +"/" + (++month) + "/" + dayOfMonth +" 00:00:00";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                try {
                    if (endDate.getTime() < startDate.getTime())
                        Toast.makeText(getBaseContext(), "Error - choose again please", Toast.LENGTH_LONG).show();
                    else
                        {
                        endDate = sdf.parse(tempDate);
                        Toast.makeText(getBaseContext(), "Chosen", Toast.LENGTH_LONG).show();
                        }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /*
    add dynamic list addresses
    */
    public void addAddress(View view) {
        EditText editText = new EditText(this);
        Button removeButton = new Button(this);
        LinearLayout linearLayout = findViewById(R.id.addTravelsLayout);
        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(800,150);
        editText.setLayoutParams(lParams);
        editText.setHint("Destination address");
        removeButton.setLayoutParams(lParams);
        String text = "Remove";
        removeButton.setText(text);
        linearLayout.addView(editText,linearLayout.getChildCount() - 1);
        linearLayout.addView(removeButton,linearLayout.getChildCount() - 1);

        removeButton.setOnClickListener(v -> {
                    for (int i = 0; i < linearLayout.getChildCount() ;i ++){    // update all indexes
                        if(linearLayout.getChildAt(i) == v) {
                            linearLayout.removeView(linearLayout.getChildAt(i - 1));
                            linearLayout.removeView(v);
                            break;
                        }
                    }
                }
        );
    }
    /*
    return list of all destinations
     */
    private List<UserLocation> destinationAddresses(){
        UserLocation tool = new UserLocation();
        LinearLayout linearLayout = findViewById(R.id.addTravelsLayout); // layout of all destinations
        int n = linearLayout.getChildCount();
        List<UserLocation> destinations = new LinkedList<UserLocation>();
        Location dest = convert_address(((EditText)linearLayout.getChildAt(0)).getText().toString());
        if (dest == null)
            Toast.makeText(getBaseContext(),"destination address " + 1 + " invalid",Toast.LENGTH_LONG).show();
        destinations.add(tool.convertFromLocation(dest));
        for (int i = 1; i < n; i++){
            if(linearLayout.getChildAt(i).getClass() == EditText.class){        // check is not Button "remove"
                dest = convert_address(((EditText)linearLayout.getChildAt(i)).getText().toString());
                destinations.add(tool.convertFromLocation(dest));
            }
            if (destinations.get(i) == null) {
                Toast.makeText(getBaseContext(), "destination address " + i + " invalid", Toast.LENGTH_LONG).show();
                return null;
            }
        }

        return destinations;
    }



    /*
    collecting all fields, filling the travel object and send it to DataBase
     */
    public void submitted(View view) throws InterruptedException {
        if (validFields() == "") {
            UserLocation tool = new UserLocation();
            List<UserLocation> destinations = destinationAddresses();
            if (destinations == null)
                return;
            travel.addDestinations(destinations);
            Location tempLocation = convert_address(source.getText().toString());
            if ( tempLocation == null)
                return;
            travel.setSource(tool.convertFromLocation(tempLocation));
            travel.setClientEmail(mail.getText().toString());
            travel.setAmountTravelers(num.getText().toString());
            travel.setClientName(eName.getText().toString());
            travel.setClientPhone(phone.getText().toString());
            travel.setEndDate(endDate);
            travel.setStartDate(startDate);
            travelViewModel.insert(travel);
            Thread.sleep(1000);
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
        }
        else
            Toast.makeText(getBaseContext(),validFields(),Toast.LENGTH_LONG).show();
    }

    private Location convert_address(String location) {
        Location tempLocation;
        try {
            Geocoder geocoder = new Geocoder(getBaseContext());
            List<Address> d = geocoder.getFromLocationName(location, 1);
            if (!d.isEmpty()) {
                Address temp = d.get(0);
                tempLocation = new Location("destinationLocation");
                tempLocation.setLatitude(temp.getLatitude());
                tempLocation.setLongitude(temp.getLongitude());
                return tempLocation;
            } else {
                Toast.makeText(this, "4:Unable to understand address", Toast.LENGTH_LONG).show();

            }
        } catch (IOException e) {
            Toast.makeText(this, "5:Unable to understand address. Check Internet connection.", Toast.LENGTH_LONG).show();
            return null;
        }
        return null;
    }

    private String validFields() {
        String message = "";
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(mail.getText().toString());
        if (!matcher.matches())
            message += "Error in mail! \n";
        int test = Integer.valueOf(num.getText().toString());
        if ( test < 1)
            message += "Amount travelers empty! \n";
        if (eName.getText().toString().length() < 1)
            message += "Name is empty! \n";
        if (phone.getText().toString().length() != 10)
            message += "Phone numbers have to contain 10 digits \n";
        if (source.getText().toString().length() < 1)
            message += "Source address is empty! \n";
        if (dest.getText().toString().length() < 1 )
            message += "Destination address is empty! \n";
        if (endDate == null)
            message += "Not chosen end date \n";
        if (startDate == null)
            message += "Not chosen start date ";

        return message;
    }
}
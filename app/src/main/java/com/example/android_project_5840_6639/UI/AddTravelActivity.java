package com.example.android_project_5840_6639.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_project_5840_6639.Entities.Travel;
import com.example.android_project_5840_6639.R;

public class AddTravelActivity extends AppCompatActivity {
    private final Travel travel = new Travel();
    static int addTravelIndex = 0;
    int numOfDestnation = 10;

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
        EditText editText = new EditText(this);
        Button remuveButton = new Button(this);
        LinearLayout linearLayout = findViewById(R.id.addTravelsLayout);
        editText.setHeight(288);
        editText.setWidth(49);
        editText.setEms(10);
        editText.setId(addTravelIndex + numOfDestnation);
        editText.setHint("Destination address");
        remuveButton.setHeight(50);
        remuveButton.setWidth(288);
        remuveButton.setEms(10);
        remuveButton.setId(addTravelIndex++);
        String text = "Remove";
        remuveButton.setText(text);
        linearLayout.addView(editText,linearLayout.getChildCount());
        linearLayout.addView(remuveButton,linearLayout.getChildCount());


        remuveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                linearLayout.removeView(v);
                TextView tv = findViewById(id + numOfDestnation);
                linearLayout.removeView(tv);
                for (int i = 2;i < linearLayout.getChildCount() / 2 +1;i++){
                    linearLayout.getChildAt(i).setId(i - 2);
                    linearLayout.getChildAt(i + numOfDestnation).setId(i + numOfDestnation -2);
                }
                --addTravelIndex;
            }
        }
        );
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
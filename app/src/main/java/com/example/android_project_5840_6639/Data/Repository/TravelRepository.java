package com.example.android_project_5840_6639.Data.Repository;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.example.android_project_5840_6639.Entities.Travel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class TravelRepository  {
    private static TravelRepository instance;

    public MutableLiveData<Boolean> getIsSuccess() {
        return travelDataSource.getIsSuccess();
    }
    private TravelDataSource travelDataSource = TravelDataSource.getInstance();


    private TravelRepository(){}


    public static TravelRepository getInstance(){
        if (instance == null)
            instance = new TravelRepository();
        return instance;
    }
    public void addTravel(Travel p) {
        travelDataSource.addTravel(p);
    }
}
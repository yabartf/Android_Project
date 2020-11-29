package com.example.android_project_5840_6639.UI;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.android_project_5840_6639.Data.Repository.TravelDataSource;
import com.example.android_project_5840_6639.Data.Repository.TravelRepository;
import com.example.android_project_5840_6639.Entities.Travel;

public class TravelViewModel extends AndroidViewModel {
    private TravelRepository travelRepository;
    private MutableLiveData<Boolean> allTravels = new MutableLiveData<Boolean>();

    public TravelViewModel(@NonNull Application application) {
        super(application);
        travelRepository = TravelRepository.getInstance();
        allTravels = travelRepository.getIsSuccess();
    }
    public void insert(Travel travel) {
        travelRepository.addTravel(travel);}
    public void update(Travel travel) {}
    public void delete(Travel travel) {}
    public void deleteAllNotes() { }
    public MutableLiveData<Boolean> getIsSuccess() { return travelRepository.getIsSuccess(); }

}

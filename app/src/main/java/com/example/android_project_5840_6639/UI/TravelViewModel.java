package com.example.android_project_5840_6639.UI;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.android_project_5840_6639.Data.Repository.TravelDataSource;
import com.example.android_project_5840_6639.Entities.Travel;

public class TravelViewModel extends AndroidViewModel {
    private TravelDataSource travelDataSource;
    private MutableLiveData<Boolean> allnotes = travelDataSource.getIsSuccess();
    public TravelViewModel(@NonNull Application application) {
        super(application);
        travelDataSource = TravelDataSource.getInstance();
    }
    public void insert(Travel travel) {travelDataSource.addTravel(travel);}//noteRepository.insert(note);}
    public void update(Travel travel) {}
    public void delete(Travel travel) {}
    public void deleteAllNotes() { }
    public MutableLiveData<Boolean> getAllnotes() { return null; }

}

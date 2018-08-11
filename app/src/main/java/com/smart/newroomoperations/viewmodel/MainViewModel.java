package com.smart.newroomoperations.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;


import com.smart.newroomoperations.database.AppRespository;
import com.smart.newroomoperations.database.NoteEntity;


import java.util.List;



public class MainViewModel extends AndroidViewModel{

    private static final String TAG = MainViewModel.class.getSimpleName();
    public LiveData<List<NoteEntity>> mNotes;
    private AppRespository mRespository;

    public MainViewModel(@NonNull Application application) {
        super(application);

        mRespository = AppRespository.getInstance(application.getApplicationContext());
        mNotes = mRespository.mNotes;
        Log.i(TAG, "MainViewModel: Constructor");
    }

    public void addSampleData() {
        mRespository.addSampleData();
    }

    public void deleteAllNotes() {
        mRespository.deleteAllNotes();
    }
}

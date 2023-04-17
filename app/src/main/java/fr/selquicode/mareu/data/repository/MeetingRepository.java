package fr.selquicode.mareu.data.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import fr.selquicode.mareu.data.API.ApiService;
import fr.selquicode.mareu.data.model.Meeting;

public class MeetingRepository {

    private ApiService mApiService;

    public MeetingRepository(ApiService service){
        this.mApiService = service;
    }

    public List<Meeting> getMeetings(){ return mApiService.getMeetings(); }


}

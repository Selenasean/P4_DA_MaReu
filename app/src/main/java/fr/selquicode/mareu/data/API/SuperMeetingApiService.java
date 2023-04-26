package fr.selquicode.mareu.data.API;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.selquicode.mareu.data.model.Meeting;
import fr.selquicode.mareu.data.model.Room;

/**
 * Super mock for the API
 */
public class SuperMeetingApiService {

    private MutableLiveData<List<Meeting>> meetingsMutableLiveData = new MutableLiveData<>();

    public SuperMeetingApiService(List<Meeting> meetings){  meetingsMutableLiveData.setValue(meetings); }

    /**
     * {@inheritDoc}
     */

    public LiveData<List<Meeting>> getMeetingsLiveData() {
        return meetingsMutableLiveData;
    }

    /**
     * {@inheritDoc}
     */

    public void deleteMeeting(long id) {
        List<Meeting> meetingsList = meetingsMutableLiveData.getValue();

        for(Meeting meeting : meetingsList){
            if(meeting.getId() == id){
                meetingsList.remove(meeting) ;
                return;
            }
        }
         meetingsMutableLiveData.setValue(meetingsList);
    }
    /**
     * {@inheritDoc}
     */

    public void createMeeting(Meeting meeting) {
        List<Meeting> meetingsList = meetingsMutableLiveData.getValue();
        meetingsList.add(meeting);
        meetingsMutableLiveData.setValue(meetingsList);
    }
    /**
     * {@inheritDoc}
     */

    public LiveData<List<Meeting>> getMeetingsFilterByDate(LocalDate date) {
        List<Meeting> meetingsList = meetingsMutableLiveData.getValue();
        MutableLiveData<List<Meeting>> list = new MutableLiveData<>(new ArrayList<>());

        for(Meeting meeting : meetingsList){
            if(date.equals(meeting.getDate())){
                list.setValue(Arrays.asList(meeting));
            }
        }
        return list;
    }
    /**
     * {@inheritDoc}
     */

    public LiveData<List<Meeting>> getMeetingsFilterByPlace(Room place) {
        List<Meeting> meetingsList = meetingsMutableLiveData.getValue();
       MutableLiveData<List<Meeting>> list = new MutableLiveData<>(new ArrayList<>());

        for(Meeting meeting : meetingsList){
            if(place.equals(meeting.getRoom())) list.setValue(Arrays.asList(meeting));
        }
        return list;
    }
}

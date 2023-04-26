package fr.selquicode.mareu.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.selquicode.mareu.data.SuperMeetingGenerator;
import fr.selquicode.mareu.data.model.Meeting;
import fr.selquicode.mareu.data.model.Room;

/**
 * Data source for meetings
 */
public class MeetingRepository {

    private final MutableLiveData<List<Meeting>> meetingsMutableLiveData = new MutableLiveData<>();


    public MutableLiveData<List<Meeting>> getMeetingsMutableLiveData() {
        meetingsMutableLiveData.setValue(SuperMeetingGenerator.SUPER_MEETINGS);
        return meetingsMutableLiveData;
    }

    /**
     * To delete a meeting from the list
     * @param id of the meeting selected
     */
    public void deleteMeeting(long id) {
        List<Meeting> meetingsList = meetingsMutableLiveData.getValue();
        if(meetingsList == null){
            Log.i("REPOSITORY", "meetingsMuutableLiveData ne contient rien");
        }else{
            Log.i("REPOSITORY","mutableLivData contient qlqch");
            for(Meeting meeting : meetingsList){
                if(meeting.getId() == id){
                    meetingsList.remove(meeting) ;
                    break;
                }
            }
        }
        meetingsMutableLiveData.setValue(meetingsList);
        Log.i("REPOSITORY", String.valueOf(meetingsMutableLiveData.getValue()));
    }

    /**
     * To create a new meeting
     * @param meeting
     */
    public void createMeeting(Meeting meeting) {
        List<Meeting> meetingsList = meetingsMutableLiveData.getValue();
        meetingsList.add(meeting);
        meetingsMutableLiveData.setValue(meetingsList);
    }

    /**
     * To filtrate the meeting's list by date
     * @param date
     * @return
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
     * To filtrate the meeting's list by room
     * @param room
     * @return
     */
    public LiveData<List<Meeting>> getMeetingsFilterByPlace(Room room) {
        List<Meeting> meetingsList = meetingsMutableLiveData.getValue();
        MutableLiveData<List<Meeting>> list = new MutableLiveData<>(new ArrayList<>());

        for(Meeting meeting : meetingsList){
            if(room.equals(meeting.getRoom())) list.setValue(Arrays.asList(meeting));
        }
        return list;
    }


}

package fr.selquicode.mareu.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import fr.selquicode.mareu.data.model.Meeting;

/**
 * Data source for meetings
 */
public class MeetingRepository {

    private final MutableLiveData<List<Meeting>> meetingsMutableLiveData = new MutableLiveData<>();

    public MeetingRepository(List<Meeting> meetings) {
        meetingsMutableLiveData.setValue(meetings);
    }

    public LiveData<List<Meeting>> getMeetingsLiveData() {
        return meetingsMutableLiveData;
    }

    /**
     * To delete a meeting from the list
     * @param id of the meeting selected
     */
    public void deleteMeeting(long id) {
        List<Meeting> meetingsList = meetingsMutableLiveData.getValue();
        if (meetingsList == null) {
            return;
        }
        for (Meeting meeting : meetingsList) {
            if (meeting.getId() == id) {
                meetingsList.remove(meeting);
                break;
            }
        }
        meetingsMutableLiveData.setValue(meetingsList);
    }

    /**
     * To create a new meeting
     * @param meeting
     */
    public void createMeeting(Meeting meeting) {
        List<Meeting> meetingsList = meetingsMutableLiveData.getValue();
        assert meetingsList != null;
        meetingsList.add(meeting);
        meetingsMutableLiveData.setValue(meetingsList);
    }


    /**
     * To generate an id for a created meeting
     */
    private long lowestMeetingId = 4;
    public long generateId(){
           lowestMeetingId++;
        return lowestMeetingId;
    }

}

package fr.selquicode.mareu.data.API;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.selquicode.mareu.data.API.ApiService;
import fr.selquicode.mareu.data.model.Meeting;
import fr.selquicode.mareu.data.model.Room;

/**
 * Super mock for the API
 */
public class SuperMeetingApiService implements ApiService {

    private List<Meeting> meetings;

    public SuperMeetingApiService(List<Meeting> meetings){ this.meetings = meetings; }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Meeting getOneMeeting(long id) {
        for(Meeting meeting : meetings){
            if(meeting.getId() == id){
                return meeting;
            }
        }
        return null;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteMeeting(long id) {
        for(Meeting meeting : meetings){
            if(meeting.getId() == id){
                meetings.remove(meeting);
                return;
            }
        }
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void createMeeting(Meeting meeting) {
            meetings.add(meeting);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Meeting> getMeetingsFilterByDate(LocalDate date) {
        List<Meeting> list = new ArrayList<>();

        for(Meeting meeting : meetings){
            if(date.equals(meeting.getDate())){
                list.add(meeting);
            }
        }
        return list;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Meeting> getMeetingsFilterByPlace(Room place) {
       List<Meeting> list = new ArrayList<>();

        for(Meeting meeting : meetings){
            if(place.equals(meeting.getPlace())) list.add(meeting);
        }
        return list;
    }
}

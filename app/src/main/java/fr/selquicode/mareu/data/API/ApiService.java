package fr.selquicode.mareu.data.API;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.selquicode.mareu.data.model.Meeting;
import fr.selquicode.mareu.data.model.Room;

/**
 * Meeting API
 */

public interface ApiService {

    /**
     * Get all my Meetings
     * @return {@link List}
     */
    List<Meeting> getMeetings();

    /**
     * Get one meeting
     * @return {@link Meeting}
     */
    Meeting getOneMeeting(long id);

    /**
     * delete a meeting
     */
    void deleteMeeting(long id);

    /**
     * create a meeting
     */
    void createMeeting(Meeting meeting);

    /**
     * filter meeting by date
     * @return list
     */
   List<Meeting> getMeetingsFilterByDate(LocalDate date);

    /**
     * filter meeting by place$
     * @return list
     */
    List<Meeting> getMeetingsFilterByPlace(Room place);
}

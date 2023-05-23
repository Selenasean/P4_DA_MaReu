package fr.selquicode.mareu;

import static org.junit.Assert.assertEquals;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.selquicode.mareu.data.model.Meeting;
import fr.selquicode.mareu.data.model.Room;
import fr.selquicode.mareu.data.repository.MeetingRepository;
import fr.selquicode.mareu.utils.LiveDataTestUtils;

/**
 * Unit Test on Meeting Repository
 */
@RunWith(JUnit4.class)
public class MeetingRepositoryTest {

    private List<Meeting> meetings = new ArrayList<>();

    private Meeting firstMeeting = new Meeting(
            1,
            LocalDate.of(2023,1,1),
            LocalTime.of(10,0),
            Room.ROOM1,
            "hello",
            Arrays.asList("a@lamzone.fr", "b@lamzone.fr", "c@lamzone.fr"));
    private Meeting secondMeeting = new Meeting(
            2,
            LocalDate.of(2023,1,2),
            LocalTime.of(10,0),
            Room.ROOM2,
            "Ni hao",
            Arrays.asList("a@lamzone.fr", "b@lamzone.fr", "c@lamzone.fr"));

    private MeetingRepository repository;


    @Before
    public void setup(){
        meetings.add(firstMeeting);
        meetings.add(secondMeeting);
        repository = new MeetingRepository();
    }

    @Test
    public void getAllMeetings_typeLiveData_WithSuccess() throws InterruptedException {
        //should return LiveData<List<Meeting>>
        List<Meeting> meetingsListExpected = LiveDataTestUtils.getOrAwaitValue(repository.getMeetingsLiveData());
        assertEquals(meetingsListExpected, meetings);
    }

    @Test
    public void deleteMeetingMeetingWithSuccess(){}

    @Test
    public void createMeetingWithSuccess(){}


}
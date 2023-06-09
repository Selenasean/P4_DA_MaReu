package fr.selquicode.mareu;



import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.google.common.truth.Truth;

import org.junit.Before;
import org.junit.Rule;
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

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setup(){
        meetings.add(firstMeeting);
        meetings.add(secondMeeting);
        repository = new MeetingRepository(meetings);
    }

    @Test
    public void getAllMeetings_typeLiveData_WithSuccess() throws InterruptedException {
        // WHEN
        List<Meeting> actualMeetingsList = LiveDataTestUtils.getOrAwaitValue(repository.getMeetingsLiveData());

        //THEN
        Truth.assertThat(actualMeetingsList).containsExactlyElementsIn(meetings);
    }

    @Test
    public void deleteMeetingWithSuccess() throws InterruptedException {
        //GIVEN
        List<Meeting> actualMeetingsList = LiveDataTestUtils.getOrAwaitValue(repository.getMeetingsLiveData());

        //WHEN
        repository.deleteMeeting(1);

        //THEN
        Truth.assertThat(actualMeetingsList).containsExactly(secondMeeting);
    }

    @Test
    public void createMeetingWithSuccess() throws InterruptedException {
        // GIVEN
        List<Meeting> actualMeetingsList = LiveDataTestUtils.getOrAwaitValue(repository.getMeetingsLiveData());
        Meeting createdMeeting = new Meeting(
                3,
                LocalDate.of(2023,6, 12),
                LocalTime.of(8,0),
                Room.ROOM3,
                "subject 3",
                Arrays.asList("a@lamzone.fr", "b@lamzone.fr")
        );

        //WHEN
        repository.createMeeting(createdMeeting);

        //THEN
        Truth.assertThat(actualMeetingsList).containsExactly(firstMeeting,secondMeeting,createdMeeting);
    }

    @Test
    public void generateId_shouldIncrement(){
        long id = repository.generateId();
        Truth.assertThat(5).isEqualTo(id);
    }

}
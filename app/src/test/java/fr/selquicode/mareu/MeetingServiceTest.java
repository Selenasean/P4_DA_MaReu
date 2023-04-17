package fr.selquicode.mareu;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import fr.selquicode.mareu.data.API.ApiService;
import fr.selquicode.mareu.data.DI.DI;
import fr.selquicode.mareu.data.model.Meeting;

/**
 * Unit Test on Meeting service
 */
@RunWith(JUnit4.class)
public class MeetingServiceTest {

    private ApiService service;
    private List<Meeting> meetings = new ArrayList<>();

    @Before
    public void setup(){
        service = DI.getNewInstanceApiService(meetings);
    }

    @Test
    public void getMeetingsWithSuccess(){}

    @Test
    public void getOneMeetingWithSuccess(){}

    @Test
    public void deleteMeetingMeetingWithSuccess(){}

    @Test
    public void createMeetingWithSuccess(){}

    @Test
    public void getMeetingFilterByDate_withSuccess(){}

    @Test
    public void getMeetingFilterByPlace_withSuccès(){}


}
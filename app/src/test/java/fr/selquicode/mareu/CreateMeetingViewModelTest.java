package fr.selquicode.mareu;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;


import com.google.common.truth.Truth;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;


import fr.selquicode.mareu.data.model.Meeting;
import fr.selquicode.mareu.data.model.Room;
import fr.selquicode.mareu.data.repository.MeetingRepository;
import fr.selquicode.mareu.ui.create.CreateMeetingViewModel;
import fr.selquicode.mareu.ui.create.CreateMeetingViewState;
import fr.selquicode.mareu.utils.LiveDataTestUtils;

/**
 * Unit Test on CreateMeeting ViewModel
 */
@RunWith(JUnit4.class)
public class CreateMeetingViewModelTest {

    private final MeetingRepository repository = Mockito.mock(MeetingRepository.class);
    private CreateMeetingViewModel viewModel;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private final String EMAIL = "EMAIL";

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setup() {
        viewModel = new CreateMeetingViewModel(repository);
    }

    @Test
    public void parseToRoomWithSuccess() {
        //WHEN
        Room roomSelected = viewModel.parseToRoom("SalleA");
        //THEN
        Truth.assertThat(roomSelected).isEqualTo(Room.ROOM1);
    }

    @Test
    public void parseToLocalDateWithSuccess() {
        //WHEN
        LocalDate dateSelected = viewModel.parseToLocalDate("01/06/2023");
        //THEN
        Truth.assertThat(dateSelected).isEqualTo(LocalDate.of(2023, 6, 1));
    }

    @Test
    public void parseToLocalTimeWithSuccess() {
        //WHEN
        LocalTime timeSelected = viewModel.parseToLocalTime("12:50");
        //THEN
        Truth.assertThat(timeSelected).isEqualTo(LocalTime.of(12, 50));
    }

    @Test
    public void onDateChanged_shouldUpdate_viewState() throws InterruptedException {
        //WHEN
        viewModel.onDateChanged(1, 5, 2023);
        String dateExpected = dateFormatter.format(LocalDate.of(2023, 6, 1));
        CreateMeetingViewState meetingViewState = LiveDataTestUtils.getOrAwaitValue(viewModel.getCreatedMeetingLiveData());
        //THEN
        Truth.assertThat(meetingViewState.getDate()).isEqualTo(dateExpected);
    }

    @Test
    public void onTimeChanged_shouldUpdate_viewState() throws InterruptedException {
        //WHEN
        viewModel.onTimeChanged(11, 47);
        String timeExpected = timeFormatter.format(LocalTime.of(11, 47));
        CreateMeetingViewState meetingViewState = LiveDataTestUtils.getOrAwaitValue(viewModel.getCreatedMeetingLiveData());
        //THEN
        Truth.assertThat(meetingViewState.getHour()).isEqualTo(timeExpected);
    }

    @Test
    public void onSubjectChanged_shouldUpdate_viewState() throws InterruptedException {
        //WHEN
        String SUBJECT = "SUBJECT";
        viewModel.onSubjectChanged(SUBJECT);
        CreateMeetingViewState meetingViewState = LiveDataTestUtils.getOrAwaitValue(viewModel.getCreatedMeetingLiveData());
        //THEN
        Truth.assertThat(meetingViewState.getSubject()).isEqualTo(SUBJECT);
    }

    @Test
    public void onRoomChanged_shouldUpdate_viewState() throws InterruptedException {
        //WHEN
        String ROOM_NAME = "ROOM_NAME";
        viewModel.onRoomChanged(ROOM_NAME);
        CreateMeetingViewState meetingViewState = LiveDataTestUtils.getOrAwaitValue(viewModel.getCreatedMeetingLiveData());
        //THEN
        Truth.assertThat(meetingViewState.getRoomName()).isEqualTo(ROOM_NAME);
    }

    @Test
    public void onEmailAdded_shouldUpdate_viewState() throws InterruptedException {
        //WHEN
        viewModel.onEmailAdded(EMAIL);
        CreateMeetingViewState meetingViewState = LiveDataTestUtils.getOrAwaitValue(viewModel.getCreatedMeetingLiveData());
        //THEN
        Truth.assertThat(meetingViewState.getMembers()).containsExactly(EMAIL);
    }

    @Test
    public void onEmailRemoved_shouldUpdate_viewState() throws InterruptedException {
        //GIVEN
        viewModel.onEmailAdded(EMAIL);
        CreateMeetingViewState meetingViewStateGiven = LiveDataTestUtils.getOrAwaitValue(viewModel.getCreatedMeetingLiveData());
        Truth.assertThat(meetingViewStateGiven.getMembers()).containsExactly(EMAIL);
        //WHEN
        viewModel.onEmailRemoved(EMAIL);
        CreateMeetingViewState meetingViewState = LiveDataTestUtils.getOrAwaitValue(viewModel.getCreatedMeetingLiveData());
        //THEN
        Truth.assertThat(meetingViewState.getMembers()).doesNotContain(EMAIL);

    }

    @Test
    public void createMeeting_canBeExecuted_becauseAllInputsAreCompleted() throws InterruptedException {
        //GIVEN
        viewModel.onDateChanged(1, 5, 2023);
        viewModel.onTimeChanged(12, 0);
        viewModel.onRoomChanged("Salle A");
        viewModel.onSubjectChanged("subject");
        viewModel.onEmailAdded("email");

        //WHEN
        viewModel.createMeeting();
        CreateMeetingViewState meetingViewState = LiveDataTestUtils.getOrAwaitValue(viewModel.getCreatedMeetingLiveData());
        //THEN
        Truth.assertThat(meetingViewState.isCreatedEnabled()).isTrue();
        // verify that the meeting is created
        Mockito.when(repository.generateId()).thenReturn(5L);
        Mockito.verify(repository).createMeeting(new Meeting(
                repository.generateId(),
                LocalDate.of(2023, 6, 1),
                LocalTime.of(12, 0),
                Room.ROOM1,
                "subject",
                Collections.singletonList("email")
        ));
    }
}

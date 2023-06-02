package fr.selquicode.mareu;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.common.truth.Truth;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.selquicode.mareu.data.model.Meeting;
import fr.selquicode.mareu.data.model.Room;
import fr.selquicode.mareu.data.repository.MeetingRepository;
import fr.selquicode.mareu.ui.list.MeetingViewState;
import fr.selquicode.mareu.ui.list.MeetingsViewModel;
import fr.selquicode.mareu.utils.LiveDataTestUtils;

/**
 * Unit Test on Meetings ViewModel
 */
@RunWith(JUnit4.class)
public class MeetingsViewModelTest {

    private final MeetingRepository repository = Mockito.mock(MeetingRepository.class);
    private MeetingsViewModel viewModel;
    private List<Meeting> meetings;
    private List<MeetingViewState> meetingsViewState;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setup() {
        meetings = Arrays.asList(
                new Meeting(
                        1,
                        LocalDate.of(2023, 06, 12),
                        LocalTime.of(10, 0),
                        Room.ROOM1,
                        "voila",
                        Arrays.asList("a@lamzone.fr", "b@lamzone.fr")
                ),
                new Meeting(
                        2,
                        LocalDate.of(2023, 06, 10),
                        LocalTime.of(8, 0),
                        Room.ROOM2,
                        "voili",
                        Arrays.asList("a@lamzone.fr", "b@lamzone.fr")
                ),
                new Meeting(
                        3,
                        LocalDate.of(2023, 6, 12),
                        LocalTime.of(11, 0),
                        Room.ROOM5,
                        "voilou",
                        Arrays.asList("a@lamzone.fr", "b@lamzone.fr")
                )
        );
        LiveData<List<Meeting>> meetingsListLD = new MutableLiveData<>(meetings);

        Mockito.when(repository.getMeetingsLiveData()).thenReturn(meetingsListLD);
        viewModel = new MeetingsViewModel(repository);

        meetingsViewState = Arrays.asList(
                new MeetingViewState(
                        1,
                        "12/06/2023",
                        "10h00",
                        Room.ROOM1.getRoomName(),
                        "voila",
                        "a@lamzone.fr, b@lamzone.fr",
                        Room.ROOM1.getRoomColor()
                ),
                new MeetingViewState(
                        2,
                        "10/06/2023",
                        "08h00",
                        Room.ROOM2.getRoomName(),
                        "voili",
                        "a@lamzone.fr, b@lamzone.fr",
                        Room.ROOM2.getRoomColor()
                ),
                new MeetingViewState(3,
                        "12/06/2023",
                        "11h00",
                        Room.ROOM5.getRoomName(),
                        "voilou",
                        "a@lamzone.fr, b@lamzone.fr",
                        Room.ROOM5.getRoomColor())
        );
    }

    @Test
    public void should_return_nonFilteredList() throws InterruptedException {
        //WHEN
        List<MeetingViewState> meetingViewStateList = LiveDataTestUtils.getOrAwaitValue(viewModel.getMeetings());

        //THEN
        Truth.assertThat(meetingViewStateList).containsExactlyElementsIn(meetingsViewState);
    }

    @Test
    public void should_return_ListFilteredByDate() throws InterruptedException {
        //WHEN
        viewModel.filterByDate(LocalDate.of(2023, 6, 10));
        List<MeetingViewState> meetingViewStateFilteredByDate = LiveDataTestUtils.getOrAwaitValue(viewModel.getMeetings());

        //THEN
        Truth.assertThat(meetingViewStateFilteredByDate).containsExactly(meetingsViewState.get(1));
    }

    @Test
    public void should_return_ListFilteredByRoom() throws InterruptedException {
        //WHEN
        viewModel.filterByRoom("Salle B");
        List<MeetingViewState> meetingViewStateFilteredByRoom = LiveDataTestUtils.getOrAwaitValue(viewModel.getMeetings());

        //THEN
        Truth.assertThat(meetingViewStateFilteredByRoom).containsExactly(meetingsViewState.get(1));
    }

    @Test
    public void should_return_ListFiltered_byDate_andByRoom() throws InterruptedException {
        //WHEN
        viewModel.filterByDate(LocalDate.of(2023, 6, 12));
        viewModel.filterByRoom("Salle E");
        List<MeetingViewState> meetingViewStateListFiltered = LiveDataTestUtils.getOrAwaitValue(viewModel.getMeetings());

        //THEN
        Truth.assertThat(meetingViewStateListFiltered).containsExactly(meetingsViewState.get(2));
    }

    @Test
    public void should_reset_allFilters() throws InterruptedException {
        //GIVEN
        viewModel.filterByRoom("Salle A");
        List<MeetingViewState> meetingViewStateListFiltered = LiveDataTestUtils.getOrAwaitValue(viewModel.getMeetings());
        Truth.assertThat(1).isEqualTo(meetingViewStateListFiltered.size());

        //WHEN
        viewModel.resetFilters();
        List<MeetingViewState> resetList = LiveDataTestUtils.getOrAwaitValue(viewModel.getMeetings());

        //THEN
        Truth.assertThat(resetList).hasSize(3);
    }
}

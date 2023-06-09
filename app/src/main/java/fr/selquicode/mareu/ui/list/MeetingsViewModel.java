package fr.selquicode.mareu.ui.list;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import fr.selquicode.mareu.data.model.Meeting;
import fr.selquicode.mareu.data.repository.MeetingRepository;

public class MeetingsViewModel extends ViewModel {

    private final MeetingRepository mRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter formatterHour = DateTimeFormatter.ofPattern("HH'h'mm");

    private final MediatorLiveData<List<MeetingViewState>> meetingsListMediatorLiveData = new MediatorLiveData<>();
    private final MutableLiveData<LocalDate> dateToFilterMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> roomToFilterMutableLiveData = new MutableLiveData<>();


    public MeetingsViewModel(MeetingRepository repository) {
        mRepository = repository;
        LiveData<List<Meeting>> listMeetings = mRepository.getMeetingsLiveData();

        //combine in the callback's Observer data for the original list
        meetingsListMediatorLiveData.addSource(
                listMeetings,
                meetings -> combine(
                        meetings,
                        dateToFilterMutableLiveData.getValue(),
                        roomToFilterMutableLiveData.getValue()));

        //combine in the callback's Observer data to filter by date
        meetingsListMediatorLiveData.addSource(
                dateToFilterMutableLiveData,
                dateFilter -> combine(
                        listMeetings.getValue(),
                        dateFilter,
                        roomToFilterMutableLiveData.getValue()));

        //combine in the callback's Observer data to filter by room
        meetingsListMediatorLiveData.addSource(
                roomToFilterMutableLiveData,
                roomToFilter -> combine(
                        listMeetings.getValue(),
                        dateToFilterMutableLiveData.getValue(),
                        roomToFilter));
    }

    /**
     * Method that filter the original list depending on filters chosen by user
     * Also combine 3 parameters -parsed into List<MeetingsViewState>>- to produce a MediatorLiveData
     *
     * @param meetings     : list of meetings
     * @param dateToFilter : date type LocalDate
     * @param roomToFilter : room name type String
     */
    private void combine(List<Meeting> meetings, LocalDate dateToFilter, String roomToFilter) {
        if (meetings == null) {
            return;
        }
        //search for the interested meeting by filter in the list
        List<Meeting> filteredMeetings = new ArrayList<>();
        for (Meeting meeting : meetings) {
            if ((dateToFilter == null || meeting.getDate().equals(dateToFilter))
                    && (roomToFilter == null || meeting.getRoom().getRoomName().equals(roomToFilter))) {
                filteredMeetings.add(meeting);
            }
        }
        //parse into ViewState
        List<MeetingViewState> filteredMeetingsVS = parseToViewState(filteredMeetings);
        meetingsListMediatorLiveData.setValue(filteredMeetingsVS);

        if (filteredMeetings.size() == 0) {
            meetingsListMediatorLiveData.setValue(null);
        }
    }

    /**
     * Method that transform a list into a ViewState list -which is the UI model-
     *
     * @param listMeetings : a list of meeting type List<Meeting>
     * @return meetingsViewState list for the UI
     */
    private List<MeetingViewState> parseToViewState(List<Meeting> listMeetings) {
        List<MeetingViewState> meetingViewState = new ArrayList<>();

        for (Meeting meeting : listMeetings) {
            meetingViewState.add(new MeetingViewState(
                    meeting.getId(),
                    meeting.getDate().format(formatter),
                    meeting.getHour().format(formatterHour),
                    meeting.getRoom().getRoomName(),
                    meeting.getSubject(),
                    String.join(", ", meeting.getMembers()),
                    meeting.getRoom().getRoomColor()
            ));
        }
        return meetingViewState;
    }

    /**
     * Get all meetings
     *
     * @return List of meetings type LiveData
     */
    public LiveData<List<MeetingViewState>> getMeetings() {
        return meetingsListMediatorLiveData;
    }

    /**
     * delete a meeting
     *
     * @param meetingId id of the meeting we want to delete
     */
    public void onDeleteMeetingClicked(long meetingId) {
        mRepository.deleteMeeting(meetingId);
    }

    /**
     * parse date into right format
     *
     * @param year       year -type int-
     * @param month      month -type int-
     * @param dayOfMonth day of the month -type int-
     * @return the date at format LocalDate
     */
    public LocalDate formatDate(int year, int month, int dayOfMonth) {
        return LocalDate.of(year, month, dayOfMonth);
    }

    /**
     * Filter by date using date chosen by user
     *
     * @param date date we use to filter
     */
    public void filterByDate(LocalDate date) {
        dateToFilterMutableLiveData.setValue(date);
    }

    /**
     * Reset filter by making value null for each filter
     */
    public void resetFilters() {
        dateToFilterMutableLiveData.setValue(null);
        roomToFilterMutableLiveData.setValue(null);
    }

    /**
     * Filter by room using room chosen by user
     *
     * @param room we use to filter
     */

    public void filterByRoom(String room) {
        roomToFilterMutableLiveData.setValue(room);
    }
}

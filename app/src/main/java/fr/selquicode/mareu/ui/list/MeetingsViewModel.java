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

    private MeetingRepository mRepository;

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
     * @param meetings : list of meetings
     * @param dateToFilter : date type LocalDate
     * @param roomToFilter : room name type String
     */
    private void combine(List<Meeting> meetings, LocalDate dateToFilter, String roomToFilter){
        if(meetings == null){
            return;
        }
        //search for the interested meeting by filter in the list
        List<Meeting> filteredMeetings = new ArrayList<>();
        for(Meeting meeting : meetings){
            if((dateToFilter == null || meeting.getDate().equals(dateToFilter))
                && (roomToFilter == null || meeting.getRoom().getRoomName().equals(roomToFilter))){

                filteredMeetings.add(meeting);
                List<MeetingViewState> filteredMeetingsVS = parseToViewState(filteredMeetings);
                meetingsListMediatorLiveData.setValue(filteredMeetingsVS);
            }
        }
        //Call in a different method ?
        if(filteredMeetings.size() == 0){
            meetingsListMediatorLiveData.setValue(null);
            //DISPLAY ERROR MESSAGE - NO MEETING FOUNDED use SingleLiveEvent (=LiveData) or an event Wrapper with mutableLiveData
        }
    }

    /**
     * Method that transform a list into a ViewState list -which is the UI model-
     * @param listMeetings : a list of meeting type List<Meeting>
     * @return meetingsViewState list for the UI
     */
    private List<MeetingViewState> parseToViewState(List<Meeting> listMeetings) {
        List<MeetingViewState> meetingViewState = new ArrayList<>();

        for(Meeting meeting : listMeetings){
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
     * @return List of meetings type LiveData
     */
    public LiveData<List<MeetingViewState>> getMeetings(){
        return meetingsListMediatorLiveData;
    }

    public void onDeleteMeetingClicked(long meetingId){
        mRepository.deleteMeeting(meetingId);
    }

    public LocalDate formatDate(int year, int month, int dayOfMonth) {
        return LocalDate.of(year, month, dayOfMonth);
    }

    public void filterByDate(LocalDate date) {
        dateToFilterMutableLiveData.setValue(date);
    }

    public void resetFilters() {
        dateToFilterMutableLiveData.setValue(null);
        roomToFilterMutableLiveData.setValue(null);
    }

    public void filterByRoom(String room) {
        roomToFilterMutableLiveData.setValue(room);
    }
}

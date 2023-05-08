package fr.selquicode.mareu.ui.list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import fr.selquicode.mareu.data.model.Meeting;
import fr.selquicode.mareu.data.repository.MeetingRepository;

public class MeetingsViewModel extends ViewModel {

    private MeetingRepository mRepository;
    private LiveData<List<MeetingsViewState>> mListLiveData;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter formatterHour = DateTimeFormatter.ofPattern("HH'h'mm");

    public MeetingsViewModel(MeetingRepository repository) {
        mRepository = repository;
        LiveData<List<Meeting>> listMeetings = mRepository.getMeetingsLiveData();
        mListLiveData = Transformations.map(listMeetings, this::transformViewState);
    }

    /**
     * Method that transform a list into a ViewState list -which is the UI model-
     * @param listMeetings - list from { @Link  MeetingRepository }
     * @return meetingsViewState list for the UI
     */
    private List<MeetingsViewState> transformViewState(List<Meeting> listMeetings) {
        List<MeetingsViewState> meetingsViewState = new ArrayList<>();

        for(Meeting meeting : listMeetings){

            meetingsViewState.add(new MeetingsViewState(meeting.getId(),
                    meeting.getDate().format(formatter),
                    meeting.getHour().format(formatterHour),
                    meeting.getRoom().getRoomName(),
                    meeting.getSubject(),
                    String.join(", ", meeting.getMembers()),
                    meeting.getRoom().getRoomColor()
                    ));
        }
        return meetingsViewState;
    }

    /**
     * Get all meetings
     * @return List of meetings type LiveData
     */
    public LiveData<List<MeetingsViewState>> getMeetings(){
        return mListLiveData;
    }

    /**
     * Logic method for delete a meeting
     */
    public void onDeleteMeetingClicked(long meetingId){
        mRepository.deleteMeeting(meetingId);
    }
}

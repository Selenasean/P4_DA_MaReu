package fr.selquicode.mareu.ui.list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import fr.selquicode.mareu.data.model.Meeting;
import fr.selquicode.mareu.data.repository.MeetingRepository;

public class MeetingsViewModel extends ViewModel {

    private MeetingRepository mRepository;
    private MutableLiveData<List<MeetingsViewState>> mListMutableLiveData = new MutableLiveData<>();

    public MeetingsViewModel(MeetingRepository repository) {
        mRepository = repository;
        List<Meeting> listMeetings = mRepository.getMeetingsMutableLiveData().getValue();
        List<MeetingsViewState> listMeetingsViewState = transformViewState(listMeetings);
        mListMutableLiveData.setValue(listMeetingsViewState);
    }

    /**
     * Method that transform a list into a ViewState list -which is the UI model-
     * @param listMeetings - list from {@Link MeetingRepository}
     * @return meetingsViewState list for the UI
     */
    private List<MeetingsViewState> transformViewState(List<Meeting> listMeetings) {
        List<MeetingsViewState> meetingsViewState = new ArrayList<>();

        for(Meeting meeting : listMeetings){

            meetingsViewState.add(new MeetingsViewState(meeting.getId(),
                    meeting.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    meeting.getHour().format(DateTimeFormatter.ofPattern("HH:mm")),
                    meeting.getRoom().getRoomName(),
                    meeting.getSubject(),
                    meeting.getMember().toString(),
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
        return mListMutableLiveData;
    }

    /**
     * Logic method for delete a meeting
     */
    public void onDeleteMeetingClicked(long meetingId){
        mRepository.deleteMeeting(meetingId);
    }
}

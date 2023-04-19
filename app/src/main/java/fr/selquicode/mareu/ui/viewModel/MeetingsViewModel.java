package fr.selquicode.mareu.ui.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import fr.selquicode.mareu.data.model.Meeting;
import fr.selquicode.mareu.data.repository.MeetingRepository;

public class MeetingsViewModel extends ViewModel {

    private MeetingRepository mRepository;
    private MutableLiveData<List<MeetingsViewState>> mListMutableLiveData = new MutableLiveData<>();

    public MeetingsViewModel(MeetingRepository repository) {
        mRepository = repository;
        List<Meeting> listMeetings = mRepository.getMeetings();
        List<MeetingsViewState> listMeetingsViewState = transformViewState(listMeetings);
        mListMutableLiveData.setValue(listMeetingsViewState);
    }

    private List<MeetingsViewState> transformViewState(List<Meeting> listMeetings) {
        List<MeetingsViewState> meetingsViewStates = new ArrayList<>();

        for(Meeting meeting : listMeetings){
            meetingsViewStates.add(new MeetingsViewState(meeting.getId(),
                    meeting.getDate().toString(),
                    meeting.getHour().toString(),
                    meeting.getPlace().getRoomName(),
                    meeting.getSubject(),
                    meeting.getMember().toString(),
                    meeting.getPlace().getRoomColor()
                    ));
        }
        return meetingsViewStates;
    }


    /**
     * Get all meetings
     * @return List of meetings type LiveData
     */
    public LiveData<List<MeetingsViewState>> getMeetings(){
        return mListMutableLiveData;
    }
}

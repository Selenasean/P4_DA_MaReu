package fr.selquicode.mareu.ui.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import fr.selquicode.mareu.data.model.Meeting;
import fr.selquicode.mareu.data.repository.MeetingRepository;

public class MeetingsViewModel extends ViewModel {

    private MeetingRepository mRepository;
    private MutableLiveData<List<Meeting>> mListMutableLiveData = new MutableLiveData<>();

    public MeetingsViewModel(MeetingRepository repository) {
        mRepository = repository;
        mListMutableLiveData.setValue(mRepository.getMeetings());
    }

    public LiveData<List<Meeting>> getMeetings(){
        return mListMutableLiveData;
    }
}

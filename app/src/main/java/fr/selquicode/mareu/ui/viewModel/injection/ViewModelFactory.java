package fr.selquicode.mareu.ui.viewModel.injection;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import fr.selquicode.mareu.data.API.ApiService;
import fr.selquicode.mareu.data.DI.DI;
import fr.selquicode.mareu.data.repository.MeetingRepository;
import fr.selquicode.mareu.ui.viewModel.MeetingsViewModel;

/**
 * class who will create a new ViewModel for a new activity/fragment
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    private static volatile ViewModelFactory factory;

    /**
     * Get an instance of ViewModelFactory
     * @return factory which is a ViewModelFactory
     */
    public static ViewModelFactory getInstance(){
        if(factory == null){
            synchronized (ViewModelFactory.class){
                if(factory == null){
                    ApiService apiService = DI.getApiService();
                    factory = new ViewModelFactory(new MeetingRepository(apiService));
                }
            }
        }
        return factory;
    }

    @NonNull
    private final MeetingRepository meetingRepository;

    /**
     * Constructor
     */
    private ViewModelFactory(@NonNull MeetingRepository meetingRepository){
        this.meetingRepository = meetingRepository;
    }

    /**
     * Creation of viewModels
     * @param modelClass
     * @return new viewModel
     * @param <T>
     */
    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass){
        // if modelClass - model of classes ViewModel - is the same as new ViewModel created = MeetingsViewModel
        if(modelClass.isAssignableFrom(MeetingsViewModel.class)){
            return (T) new MeetingsViewModel(meetingRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

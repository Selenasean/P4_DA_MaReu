package fr.selquicode.mareu;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import fr.selquicode.mareu.data.repository.MeetingRepository;
import fr.selquicode.mareu.ui.create.CreateMeetingViewModel;

/**
 * Unit Test on CreateMeeting ViewModel
 */
@RunWith(JUnit4.class)
public class CreateMeetingViewModelTest {

    private final MeetingRepository repository = Mockito.mock(MeetingRepository.class);
    private CreateMeetingViewModel viewModel;


    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setup(){
        viewModel = new CreateMeetingViewModel(repository);
    }

    //create meeting test


}

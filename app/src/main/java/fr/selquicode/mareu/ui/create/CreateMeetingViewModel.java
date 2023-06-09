package fr.selquicode.mareu.ui.create;

import android.util.Patterns;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import fr.selquicode.mareu.data.model.Meeting;
import fr.selquicode.mareu.data.model.Room;
import fr.selquicode.mareu.data.repository.MeetingRepository;
import fr.selquicode.mareu.ui.utils.SingleLiveEvent;

public class CreateMeetingViewModel extends ViewModel {

    private final MeetingRepository mRepository;
    private final MutableLiveData<CreateMeetingViewState> createdMeetingMutableLiveData =
            new MutableLiveData<>(new CreateMeetingViewState(
                    "",
                    "",
                    "",
                    "",
                    new ArrayList<>()
            ));
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    private final SingleLiveEvent<Void> closeActivity = new SingleLiveEvent<>();

    public CreateMeetingViewModel(MeetingRepository repository) {
        mRepository = repository;
    }

    /**
     * Get meeting created by user
     *
     * @return meeting type LiveData
     */
    public MutableLiveData<CreateMeetingViewState> getCreatedMeetingLiveData() {
        return createdMeetingMutableLiveData;
    }

    /**
     * Method to create a meeting -adding it in dataBase-
     */
    public void createMeeting() {
        CreateMeetingViewState currentState = getCreatedMeetingLiveData().getValue();
        if (currentState == null) {
            return;
        }
        LocalDate dateMeeting = parseToLocalDate(currentState.getDate());
        LocalTime timeMeeting = parseToLocalTime(currentState.getHour());
        Room roomMeeting = parseToRoom(currentState.getRoomName());

        mRepository.createMeeting(new Meeting(
                mRepository.generateId(),
                dateMeeting,
                timeMeeting,
                roomMeeting,
                currentState.getSubject(),
                currentState.getMembers()
        ));

        //finally close activity
        closeActivity.call();
    }

    /**
     * Parse roomName into the corresponding Room
     *
     * @param roomName type String
     * @return a Room
     */
    public Room parseToRoom(String roomName) {
        Room roomSelected = Room.ROOM1;
        for (Room room : Room.values()) {
            if (roomName.equals(room.getRoomName())) {
                roomSelected = room;
                break;
            }
        }
        return roomSelected;
    }

    /**
     * Parse date into LocalDate format
     *
     * @param date type String
     * @return a LocalDate
     */
    public LocalDate parseToLocalDate(String date) {
        return LocalDate.parse(date, dateFormatter);
    }

    /**
     * Parse hour into LocalTime format
     *
     * @param hour type String
     * @return a LocalTime
     */
    public LocalTime parseToLocalTime(String hour) {
        return LocalTime.parse(hour, timeFormatter);
    }

    /**
     * Check if email is valid and faithful to email pattern
     *
     * @param email of participant written by user
     * @return boolean
     */
    public boolean isEmailValid(String email) {
        if (!email.isEmpty()) {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        } else {
            return false;
        }
    }

    /**
     * Method that update the ViewState with the date chosen by the user
     *
     * @param dayOfMonth -type int-
     * @param month      -type int-
     * @param year       -type int-
     */
    public void onDateChanged(int dayOfMonth, int month, int year) {
        LocalDate date = LocalDate.of(year, month + 1, dayOfMonth);

        //get LiveData value at instant T
        CreateMeetingViewState currentState = createdMeetingMutableLiveData.getValue();
        if (currentState != null) {
            CreateMeetingViewState newState = new CreateMeetingViewState(
                    currentState.getRoomName(),
                    dateFormatter.format(date),
                    currentState.getHour(),
                    currentState.getSubject(),
                    currentState.getMembers()
            );
            createdMeetingMutableLiveData.setValue(newState);
        }
    }

    /**
     * Method that update the ViewState with the time chosen by the user
     *
     * @param selectedHour   -type int-
     * @param selectedMinute -type int-
     */
    public void onTimeChanged(int selectedHour, int selectedMinute) {
        LocalTime time = LocalTime.of(selectedHour, selectedMinute);

        //get LiveData value at instant T
        CreateMeetingViewState currentState = createdMeetingMutableLiveData.getValue();
        if (currentState != null) {
            CreateMeetingViewState newState = new CreateMeetingViewState(
                    currentState.getRoomName(),
                    currentState.getDate(),
                    timeFormatter.format(time),
                    currentState.getSubject(),
                    currentState.getMembers()
            );
            createdMeetingMutableLiveData.setValue(newState);
        }
    }

    /**
     * Method to update the ViewState with the subject written by user
     *
     * @param subject -type String-
     */
    public void onSubjectChanged(String subject) {
        //get LiveData value at instant T
        CreateMeetingViewState currentState = createdMeetingMutableLiveData.getValue();
        if (currentState != null) {
            CreateMeetingViewState newState = new CreateMeetingViewState(
                    currentState.getRoomName(),
                    currentState.getDate(),
                    currentState.getHour(),
                    subject,
                    currentState.getMembers()
            );
            createdMeetingMutableLiveData.setValue(newState);
        }
    }

    /**
     * Method to update the ViewState with the room written by user
     *
     * @param selectedRoom -type String-
     */
    public void onRoomChanged(String selectedRoom) {
        //get LiveData value at instant T
        CreateMeetingViewState currentState = createdMeetingMutableLiveData.getValue();
        if (currentState != null) {
            CreateMeetingViewState newState = new CreateMeetingViewState(
                    selectedRoom,
                    currentState.getDate(),
                    currentState.getHour(),
                    currentState.getSubject(),
                    currentState.getMembers()
            );
            createdMeetingMutableLiveData.setValue(newState);
        }
    }

    /**
     * Method to update the viewState when a email is added to the list of participants
     *
     * @param email -type String-
     */
    public void onEmailAdded(String email) {
        //get LiveData value at instant T
        CreateMeetingViewState currentState = createdMeetingMutableLiveData.getValue();
        if (currentState != null) {
            List<String> emails = new ArrayList<>(currentState.getMembers());
            emails.add(email);
            CreateMeetingViewState newState = new CreateMeetingViewState(
                    currentState.getRoomName(),
                    currentState.getDate(),
                    currentState.getHour(),
                    currentState.getSubject(),
                    emails
            );
            createdMeetingMutableLiveData.setValue(newState);
        }
    }

    /**
     * Method to update the viewState when a email is removed from the list of participants
     *
     * @param email -type String-
     */
    public void onEmailRemoved(String email) {
        //get LiveData value at instant T
        CreateMeetingViewState currentState = createdMeetingMutableLiveData.getValue();
        if (currentState != null) {
            List<String> emails = new ArrayList<>(currentState.getMembers());
            emails.remove(email);
            CreateMeetingViewState newState = new CreateMeetingViewState(
                    currentState.getRoomName(),
                    currentState.getDate(),
                    currentState.getHour(),
                    currentState.getSubject(),
                    emails
            );
            createdMeetingMutableLiveData.setValue(newState);
        }
    }

    /**
     * Called in CreateNewMeetingActivity to close activity
     *
     * @return SingleLiveEvent -type Void-
     */
    public SingleLiveEvent<Void> getCloseActivity() {
        return closeActivity;
    }


}

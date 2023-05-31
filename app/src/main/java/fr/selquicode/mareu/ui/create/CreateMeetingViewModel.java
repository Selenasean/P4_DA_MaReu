package fr.selquicode.mareu.ui.create;

import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import fr.selquicode.mareu.data.model.Meeting;
import fr.selquicode.mareu.data.model.Room;
import fr.selquicode.mareu.data.repository.MeetingRepository;
import fr.selquicode.mareu.ui.utils.SingleLiveEvent;

public class CreateMeetingViewModel extends ViewModel {

    private MeetingRepository mRepository;
    private MutableLiveData<CreateMeetingViewState> createdMeetingMutableLiveData = new MutableLiveData<>();
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    private LocalDate date;
    private LocalTime time;

    private final SingleLiveEvent<Void> closeActivity = new SingleLiveEvent<>();

    public CreateMeetingViewModel(MeetingRepository repository) {
        mRepository = repository;
    }

    /**
     * Get meeting created by user
     * @return meeting type LiveData
     */
    public MutableLiveData<CreateMeetingViewState> getCreatedMeetingLiveData() {
        return createdMeetingMutableLiveData;
    }

    /**
     * Method to add a meeting in dataBase
     * @param date String of date
     * @param hour String of hour
     * @param room String of room's name
     * @param subject String
     * @param email list of participant's emails
     */
    public void createMeeting(@NonNull String date,
                              @NonNull String hour,
                              @NonNull String room,
                              @NonNull String subject,
                              @NonNull List<String> email) {

        LocalDate dateMeeting = parseToLocalDate(date);
        LocalTime timeMeeting = parseToLocalTime(hour);
        Room roomMeeting = parseToRoom(room);

        mRepository.createMeeting(new Meeting(
                mRepository.generateId(),
                dateMeeting,
                timeMeeting,
                roomMeeting,
                subject,
                email
        ));

        //finally close activity
        closeActivity.call();
    }

    /**
     * Parse roomName String into the corresponding Room
     * @param roomName
     * @return
     */
    public Room parseToRoom(String roomName) {
        Room roomSelected = Room.ROOM1;
        for (Room room : Room.values()) {
            if (roomName == room.getRoomName()) {
                roomSelected = room;
                break;
            }
        }
        return roomSelected;
    }

    /**
     * Parse string date into LocalDate format
     * @param date
     * @return
     */
    public LocalDate parseToLocalDate(String date) {
        LocalDate localDate = LocalDate.parse(date, dateFormatter);
        return localDate;
    }

    /**
     * Parse string hour into LocalTime format
     * @param hour
     * @return
     */
    public LocalTime parseToLocalTime(String hour) {
        LocalTime localTime = LocalTime.parse(hour, timeFormatter);
        return localTime;
    }

    /**
     * Check if email is valid and faithful to email pattern
     * @param email
     * @return boolean
     */
    public boolean isEmailValid(String email) {
        if (!email.isEmpty()) {
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Method that update the ViewState with the date chosen by the user
     * @param dayOfMonth
     * @param month
     * @param year
     */
    public void onDateChanged(int dayOfMonth, int month, int year) {
        date = LocalDate.of(year, month + 1, dayOfMonth);

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
     * @param selectedHour
     * @param selectedMinute
     */
    public void onTimeChanged(int selectedHour, int selectedMinute) {
        time = LocalTime.of(selectedHour, selectedMinute);

        CreateMeetingViewState currentState = createdMeetingMutableLiveData.getValue();
        if (currentState != null){
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
     * Method to update the ViewState with the subject -type String- written by user
     */
    public void onSubjectChanged(String subject){
        CreateMeetingViewState currentState = createdMeetingMutableLiveData.getValue();
        if(currentState != null){
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
     * Method to update the ViewState with the room -type String- written by user
     * @param selectedRoom
     */
    public void onRoomChanged(String selectedRoom) {
        CreateMeetingViewState currentState = createdMeetingMutableLiveData.getValue();
        if(currentState != null){
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
     * Called in CreateNewMeetingActivity to close activity
     * @return SingleLiveEvent -type Void-
     */
    public SingleLiveEvent<Void> getCloseActivity() {
        return closeActivity;
    }
}

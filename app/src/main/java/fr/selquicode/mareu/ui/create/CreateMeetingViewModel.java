package fr.selquicode.mareu.ui.create;

import android.content.Context;
import android.util.Patterns;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import fr.selquicode.mareu.data.model.Meeting;
import fr.selquicode.mareu.data.model.Room;
import fr.selquicode.mareu.data.repository.MeetingRepository;

public class CreateMeetingViewModel extends ViewModel {

    private MeetingRepository mRepository;
    private MutableLiveData<CreateMeetingViewState> createdMeetingLV = new MutableLiveData<>();
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH'h'mm");

    private LocalDate date;

    public CreateMeetingViewModel(MeetingRepository repository){
        mRepository = repository;
    }

    /**
     * Generate a id
     */
    public long generateId(){ return mRepository.generateId(); }

    /**
     * Method to add a meeting in dataBase
     */
    public void createMeeting(Meeting meeting){
        mRepository.createMeeting(meeting);
    }

    /**
     * Get meeting created by user
     * @return meeting type LiveData
     */
    public MutableLiveData<CreateMeetingViewState> getCreatedMeetingLiveData(){
        return createdMeetingLV;
    }

    /**
     * Check if email is valid and pop an error message if it is not
     * @param email
     * @param context
     * @return
     */
    public boolean isEmailValid(String email, Context context){
        if(!email.isEmpty()){
            if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                return true;
            } else{
                Toast.makeText(context, "Email invalide !", Toast.LENGTH_SHORT).show();
                return false;
            }
        }else{
            return false;
        }
    }

    /**
     * Parse roomName String into the corresponding Room
     * @param roomName
     * @return
     */
    public Room parseToRoom(String roomName){
        Room roomSelected = Room.ROOM1;
        for(Room room : Room.values()){
            if(roomName == room.getRoomName()){
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
    public LocalDate parseToLocalDate(String date){
        LocalDate localDate = LocalDate.parse(date, dateFormatter);
        return localDate;
    }

    public String formatDate(int dayOfMonth, int month, int year) {
        return String.format(Locale.FRANCE, "%02d/%02d/%04d", dayOfMonth, month, year);
    }

    /**
     * Parse string hour into LocalTime format
     * @param hour
     * @return
     */
    public LocalTime parseToLocalTime(String hour){
        LocalTime localTime = LocalTime.parse(hour, timeFormatter);
        return localTime;
    }

    /**
     * Method that update the ViewState with the date chosen by the user
     * @param dayOfMonth
     * @param month
     * @param year
     */
    public void onDateChanged(int dayOfMonth, int month, int year) {
        date = LocalDate.of(year, (month +1), dayOfMonth);

        CreateMeetingViewState currentState =  createdMeetingLV.getValue();
        if(currentState != null){
            CreateMeetingViewState newState = new CreateMeetingViewState(
                    currentState.getRoomName(),
                    dateFormatter.format(date),
                    currentState.getHour(),
                    currentState.getSubject(),
                    currentState.getMembers()
            );
            createdMeetingLV.setValue(newState);
        }
    }
}

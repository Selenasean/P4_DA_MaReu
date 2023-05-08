package fr.selquicode.mareu.ui.create;

import android.content.Context;
import android.util.Patterns;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
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
    private LiveData<CreateMeetingViewState> createdMeetingLV;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH'h'mm");

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
     * Check if email is valid
     * @param email
     * @param context
     */
    public boolean isEmailValid(String email, Context context){
        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return true;
        } else{
            Toast.makeText(context, "Email invalide", Toast.LENGTH_SHORT).show();
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
     * Check if all the inputs are filled
     */
    public boolean isMeetingInfoComplete(@NonNull String roomName,
                                         @NonNull String date,
                                         @NonNull String hour,
                                         @NonNull String subject,
                                         @NonNull List<String> members){
        if(roomName.isEmpty()
                || date.isEmpty()
                || hour.isEmpty()
                || subject.isEmpty()
                || members.size() == 0) {
            return false;
        }else{
            return true;
        }
    }

}

package fr.selquicode.mareu.ui.create;

import android.content.Context;
import android.util.Patterns;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import fr.selquicode.mareu.data.model.Room;
import fr.selquicode.mareu.data.repository.MeetingRepository;

public class CreateMeetingViewModel extends ViewModel {

    private MeetingRepository mRepository;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH'h'mm");

    public CreateMeetingViewModel(MeetingRepository repository){
        mRepository = repository;
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

    /**
     * Parse string hour into LocalTime format
     * @param hour
     * @return
     */
    public LocalTime parseToLocalTime(String hour){
        LocalTime localTime = LocalTime.parse(hour, timeFormatter);
        return null;
    }
    /**
     * method to generate id
     */
    public long idGenerator(){
        Random random = new Random(4);
        return random.nextLong();
    }



}

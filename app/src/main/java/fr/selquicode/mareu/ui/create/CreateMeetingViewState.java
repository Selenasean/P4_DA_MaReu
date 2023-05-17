package fr.selquicode.mareu.ui.create;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import fr.selquicode.mareu.data.model.Room;

/**
 * This is the model of the created meeting
 * that the data enter by the user have to fit into
 */
public class CreateMeetingViewState {


    @Nullable
    private String roomName, subject;


    @Nullable
    private List<String> members;

    @NonNull
    private String date, hour;

    /**
     * Constructor
     * @param date
     * @param hour
     * @param roomName
     * @param subject
     * @param members
     */
    public CreateMeetingViewState(@Nullable String roomName,
                                  @NonNull String date,
                                  @NonNull String hour,
                                  @Nullable String subject,
                                  @Nullable List<String> members){
        this.roomName = roomName;
        this.date = date;
        this.hour = hour;
        this.subject = subject;
        this.members = members;
    }

    /**
     * Computed property that depends on other values of the ViewState
     * @return boolean
     */
    public Boolean isCreatedEnabled(){
        if(roomName != null && subject != null && members != null){
            if(roomName.isEmpty()
                    || date.isEmpty()
                    || hour.isEmpty()
                    || subject.isEmpty()
                    || members.size() == 0) {
                return false;
            }else{
                return true;
            }
        } else return false;
    }

    @Nullable
    public String getSubject() {
        return subject;
    }

    public void setSubject(@Nullable String subject) {
        this.subject = subject;
    }

    @Nullable
    public List<String> getMembers() {
        return members;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

    @NonNull
    public String getHour() {
        return hour;
    }

    public void setHour(@NonNull String hour) {
        this.hour = hour;
    }

    @Nullable
    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(@Nullable String roomName) {
        this.roomName = roomName;
    }
}

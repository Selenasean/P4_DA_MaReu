package fr.selquicode.mareu.ui.create;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * This is the model of the created meeting
 * that the data enter by the user have to fit into
 */
public class CreateMeetingViewState {


    @Nullable
    private final String roomName;
    @Nullable
    private final String subject;


    @NonNull
    private final List<String> members;

    @NonNull
    private final String date;
    @NonNull
    private final String hour;

    /**
     * Constructor
     *
     * @param date     : string of date
     * @param hour     : string of hour
     * @param roomName : string of room name
     * @param subject  : string of meeting's subject
     * @param members  : list of members' string
     */
    public CreateMeetingViewState(@Nullable String roomName,
                                  @NonNull String date,
                                  @NonNull String hour,
                                  @Nullable String subject,
                                  @NonNull List<String> members) {
        this.roomName = roomName;
        this.date = date;
        this.hour = hour;
        this.subject = subject;
        this.members = members;
    }

    /**
     * Computed property that depends on other values of the ViewState
     *
     * @return boolean
     */
    public Boolean isCreatedEnabled() {
        if (roomName != null && subject != null) {
            return !roomName.isEmpty()
                    && !date.isEmpty()
                    && !hour.isEmpty()
                    && !subject.isEmpty()
                    && members.size() != 0;
        } else return false;
    }

    @Nullable
    public String getSubject() {
        return subject;
    }

    @NonNull
    public List<String> getMembers() {
        return members;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    @NonNull
    public String getHour() {
        return hour;
    }

    @Nullable
    public String getRoomName() {
        return roomName;
    }

}

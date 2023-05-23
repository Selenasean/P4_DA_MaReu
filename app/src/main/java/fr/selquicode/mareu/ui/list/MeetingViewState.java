package fr.selquicode.mareu.ui.list;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import java.util.Objects;

/**
 * This is the model of the view that the data have to fit into
 * ViewState contains data model use for displaying the View
 */
public class MeetingViewState {

    @NonNull
    private final long id;

    @NonNull
    private final String date, hour, roomName,subject, meetingMembers;

    @NonNull
    private final int colorRoom;


    /**
     * Constructor
     * @param id
     * @param date
     * @param hour
     * @param roomName
     * @param subject
     * @param meetingMembers
     */
    public MeetingViewState(long id,
                            @NonNull String date,
                            @NonNull String hour,
                            @NonNull String roomName,
                            @NonNull String subject,
                            @NonNull String meetingMembers,
                            @NonNull int colorRoom) {
        this.id = id;
        this.date = date;
        this.hour = hour;
        this.roomName = roomName;
        this.subject = subject;
        this.meetingMembers = meetingMembers;
        this.colorRoom = colorRoom;
    }

    public long getId() {
        return id;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    @NonNull
    public String getHour() {
        return hour;
    }

    @NonNull
    public String getRoomName() {
        return roomName;
    }

    @NonNull
    public String getSubject() {
        return subject;
    }

    @NonNull
    public String getMeetingMembers() {
        return meetingMembers;
    }

    @DrawableRes
    public int getColorRoom() {
        return colorRoom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MeetingViewState)) return false;
        MeetingViewState that = (MeetingViewState) o;
        return getId() == that.getId() && getColorRoom() == that.getColorRoom() && getDate().equals(that.getDate()) && getHour().equals(that.getHour()) && getRoomName().equals(that.getRoomName()) && getSubject().equals(that.getSubject()) && getMeetingMembers().equals(that.getMeetingMembers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDate(), getHour(), getRoomName(), getSubject(), getMeetingMembers(), getColorRoom());
    }

    @Override
    public String toString() {
        return "MeetingsViewState{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", hour='" + hour + '\'' +
                ", roomName='" + roomName + '\'' +
                ", subject='" + subject + '\'' +
                ", meetingMembers='" + meetingMembers + '\'' +
                ", colorRoom=" + colorRoom +
                '}';
    }


}

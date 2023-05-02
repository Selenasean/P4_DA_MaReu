package fr.selquicode.mareu.data.model;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

/**
 * Model object representing a Meeting
 * */
public class Meeting {

    /** Meeting's id */
    private long id;

    /** Meeting's date */
    private LocalDate date;

    /** meeting's hour */
    private LocalTime hour;

    /** place of meeting */
    private Room room;

    /** subject */
    private String subject;

    /** participants  */
    private List<String> members;


    /**
     * Constructor meeting model
     *
     * @param date
     * @param hour
     * @param room
     * @param subject
     * @param members
     */
    public Meeting(long id, LocalDate date,LocalTime hour, Room room, String subject, List<String> members){
        this.id = id;
        this.date = date;
        this.hour = hour;
        this.room = room;
        this.subject = subject;
        this.members = members;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getHour() {
        return hour;
    }

    public void setHour(LocalTime hour) {
        this.hour = hour;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Meeting)) return false;
        Meeting meeting = (Meeting) o;
        return getId() == meeting.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "id=" + id +
                ", date=" + date +
                ", hour=" + hour +
                ", room=" + room +
                ", subject='" + subject + '\'' +
                ", member=" + members +
                '}';
    }
}

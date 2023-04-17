package fr.selquicode.mareu.data.model;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
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
    private Room place;

    /** subject */
    private String subject;

    /** participants  */
    private List<String> member;


    /**
     * Constructor
     *
     * @param date
     * @param hour
     * @param place
     * @param subject
     * @param member
     */
    public Meeting(long id, LocalDate date,LocalTime hour, Room place, String subject, List<String> member ){
        this.id = id;
        this.date = date;
        this.hour = hour;
        this.place = place;
        this.subject = subject;
        this.member = member;
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

    public Room getPlace() {
        return place;
    }

    public void setPlace(Room place) {
        this.place = place;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getMember() {
        return member;
    }

    public void setMember(List<String> member) {
        this.member = member;
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
}

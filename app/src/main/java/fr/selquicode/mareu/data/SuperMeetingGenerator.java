package fr.selquicode.mareu.data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import fr.selquicode.mareu.data.model.Meeting;
import fr.selquicode.mareu.data.model.Room;

public abstract class SuperMeetingGenerator {

    public static List<Meeting> SUPER_MEETINGS = new ArrayList<>(Arrays.asList(
            new Meeting(1, LocalDate.of(2023, 6, 1), LocalTime.of(10, 0), Room.ROOM1, "Toilette bouchée", Arrays.asList("eli@lamzone.com", "ali@lamzone.com", "eddy@lamzone.com")),
            new Meeting(2, LocalDate.of(2023, 6, 2), LocalTime.of(10, 0), Room.ROOM6, "Panne imprimante",  Arrays.asList("eli@lamzone.com", "ali@lamzone.com", "eddy@lamzone.com")),
            new Meeting(3, LocalDate.of(2023, 6, 1), LocalTime.of(14, 0), Room.ROOM3, "Plus de chauffage",  Arrays.asList("eli@lamzone.com", "ali@lamzone.com", "eddy@lamzone.com")),
            new Meeting(4, LocalDate.of(2023, 6, 3), LocalTime.of(10, 0), Room.ROOM4, "Qui a mangé ma part de gateau ?!",  Arrays.asList("eli@lamzone.com", "ali@lamzone.com", "eddy@lamzone.com"))
    ));

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(SUPER_MEETINGS);
    }

}

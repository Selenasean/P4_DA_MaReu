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
            new Meeting(2, LocalDate.of(2023, 6, 1), LocalTime.of(10, 0), Room.ROOM2, "Panne imprimante",  Arrays.asList("eli@lamzone.com", "ali@lamzone.com", "eddy@lamzone.com")),
            new Meeting(3, LocalDate.of(2023, 6, 1), LocalTime.of(10, 0), Room.ROOM3, "Plus de chauffage",  Arrays.asList("eli@lamzone.com", "ali@lamzone.com", "eddy@lamzone.com")),
            new Meeting(4, LocalDate.of(2023, 6, 1), LocalTime.of(10, 0), Room.ROOM4, "Qui à mangé ma part de gateau ?!",  Arrays.asList("eli@lamzone.com", "ali@lamzone.com", "eddy@lamzone.com")),
            new Meeting(5, LocalDate.of(2023, 6, 1), LocalTime.of(10, 0), Room.ROOM5, "Les retards !",  Arrays.asList("eli@lamzone.com", "ali@lamzone.com", "eddy@lamzone.com")),
            new Meeting(6, LocalDate.of(2023, 6, 1), LocalTime.of(10, 0), Room.ROOM6, "Avancement Projet",  Arrays.asList("eli@lamzone.com", "ali@lamzone.com", "eddy@lamzone.com")),
            new Meeting(7, LocalDate.of(2023, 6, 1), LocalTime.of(10, 0), Room.ROOM7, "Organisation vacances",  Arrays.asList("eli@lamzone.com", "ali@lamzone.com", "eddy@lamzone.com")),
            new Meeting(8, LocalDate.of(2023, 6, 1), LocalTime.of(10, 0), Room.ROOM8, "Ascenseur en panne",  Arrays.asList("eli@lamzone.com", "ali@lamzone.com", "eddy@lamzone.com")),
            new Meeting(9, LocalDate.of(2023, 6, 1), LocalTime.of(10, 0), Room.ROOM9, "Panne generale",  Arrays.asList("eli@lamzone.com", "ali@lamzone.com", "eddy@lamzone.com")),
            new Meeting(10, LocalDate.of(2023, 6, 1), LocalTime.of(10, 0),Room.ROOM10, "AfterWork",  Arrays.asList("eli@lamzone.com", "ali@lamzone.com", "eddy@lamzone.com")),
            new Meeting(11, LocalDate.of(2023, 6, 1), LocalTime.of(10, 0),Room.ROOM1,"Aimer son prochain",  Arrays.asList("eli@lamzone.com", "ali@lamzone.com", "eddy@lamzone.com")),
            new Meeting(12, LocalDate.of(2023, 6, 1), LocalTime.of(10, 0),Room.ROOM6, "Les animaux de compagnie sont-ils autorisés ?",  Arrays.asList("eli@lamzone.com", "ali@lamzone.com", "eddy@lamzone.com"))
    ));

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(SUPER_MEETINGS);
    }

}

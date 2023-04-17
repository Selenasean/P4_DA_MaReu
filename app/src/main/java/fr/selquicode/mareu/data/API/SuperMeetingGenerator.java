package fr.selquicode.mareu.data.API;

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
            new Meeting(1, LocalDate.of(2023, 6, 1), LocalTime.of(10, 0), Room.Ananas, "Toilette bouchée", Arrays.asList("eli@gmail.com", "ali@gmail.com", "eddy@gmail.com")),
            new Meeting(2, LocalDate.of(2023, 6, 1), LocalTime.of(10, 0), Room.Banane, "Panne imprimante",  Arrays.asList("eli@gmail.com", "ali@gmail.com", "eddy@gmail.com")),
            new Meeting(3, LocalDate.of(2023, 6, 1), LocalTime.of(10, 0), Room.CLementine, "Plus de chauffage",  Arrays.asList("eli@gmail.com", "ali@gmail.com", "eddy@gmail.com")),
            new Meeting(4, LocalDate.of(2023, 6, 1), LocalTime.of(10, 0), Room.Durian, "Qui à mangé ma part de gateau ?!",  Arrays.asList("eli@gmail.com", "ali@gmail.com", "eddy@gmail.com")),
            new Meeting(5, LocalDate.of(2023, 6, 1), LocalTime.of(10, 0), Room.Endive, "Les retards !",  Arrays.asList("eli@gmail.com", "ali@gmail.com", "eddy@gmail.com")),
            new Meeting(6, LocalDate.of(2023, 6, 1), LocalTime.of(10, 0), Room.Figue, "Avancement Projet",  Arrays.asList("eli@gmail.com", "ali@gmail.com", "eddy@gmail.com")),
            new Meeting(7, LocalDate.of(2023, 6, 1), LocalTime.of(10, 0), Room.Gingembre, "Organisation vacances",  Arrays.asList("eli@gmail.com", "ali@gmail.com", "eddy@gmail.com")),
            new Meeting(8, LocalDate.of(2023, 6, 1), LocalTime.of(10, 0), Room.Haricot, "Ascenseur en panne",  Arrays.asList("eli@gmail.com", "ali@gmail.com", "eddy@gmail.com")),
            new Meeting(9, LocalDate.of(2023, 6, 1), LocalTime.of(10, 0), Room.Igname, "Panne generale",  Arrays.asList("eli@gmail.com", "ali@gmail.com", "eddy@gmail.com")),
            new Meeting(10, LocalDate.of(2023, 6, 1), LocalTime.of(10, 0),Room.Jujube, "AfterWork",  Arrays.asList("eli@gmail.com", "ali@gmail.com", "eddy@gmail.com")),
            new Meeting(11, LocalDate.of(2023, 6, 1), LocalTime.of(10, 0),Room.Banane,"Aimer son prochain",  Arrays.asList("eli@gmail.com", "ali@gmail.com", "eddy@gmail.com")),
            new Meeting(12, LocalDate.of(2023, 6, 1), LocalTime.of(10, 0),Room.Figue, "Les animaux de compagnie sont-ils autorisés ?",  Arrays.asList("eli@gmail.com", "ali@gmail.com", "eddy@gmail.com"))
    ));

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(SUPER_MEETINGS);
    }

}

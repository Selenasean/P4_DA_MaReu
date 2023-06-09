package fr.selquicode.mareu.data.model;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import fr.selquicode.mareu.R;

public enum Room {

    ROOM1("Salle A", R.drawable.room_ananas),
    ROOM2("Salle B", R.drawable.room_banane),
    ROOM3("Salle C", R.drawable.room_clementine),
    ROOM4("Salle D", R.drawable.room_durian),
    ROOM5("Salle E", R.drawable.room_endive),
    ROOM6("Salle F", R.drawable.room_figue),
    ROOM7("Salle G", R.drawable.room_gingembre),
    ROOM8("Salle H", R.drawable.room_haricot),
    ROOM9("Salle I", R.drawable.room_igname),
    ROOM10("Salle J", R.drawable.room_jujube);

    private final String name;

    @DrawableRes
    private final int color;

    Room(String name, int color) {
        this.name = name;
        this.color = color;
    }

    @DrawableRes
    public int getRoomColor() {
        return color;
    }

    public String getRoomName() {
        return name;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}

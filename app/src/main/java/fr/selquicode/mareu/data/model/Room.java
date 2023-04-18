package fr.selquicode.mareu.data.model;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import fr.selquicode.mareu.R;

public enum Room {

    ROOM1("Ananas",R.drawable.room_ananas),
    ROOM2("Banane", R.drawable.room_banane),
    ROOM3("Clementine", R.drawable.room_clementine),
    ROOM4("Durian", R.drawable.room_durian),
    ROOM5("Endive", R.drawable.room_endive),
    ROOM6("Figue", R.drawable.room_figue),
    ROOM7("Gingembre", R.drawable.room_gingembre),
    ROOM8("Haricot", R.drawable.room_haricot),
    ROOM9("Igname", R.drawable.room_igname),
    ROOM10("Jujube", R.drawable.room_jujube);

    private final String name;

    @DrawableRes
    private final int color;

    Room(String name, int color){
        this.name = name;
        this.color = color;
    }

    public int getRoomColor(){
        return color;
    }

    public String getRoomName(){
        return name;
    }

    @NonNull
    @Override
    public String toString(){
        return name;
    }
}

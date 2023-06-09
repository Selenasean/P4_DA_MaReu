package fr.selquicode.mareu.ui.list.filter_room;

public interface OnSelectedRoomListener {
    /**
     * Allow to access the room chosen from the dialogFragment in the MeetingActivity
     *
     * @param room selected -type String-
     */
    void onSelectedRoom(String room);
}

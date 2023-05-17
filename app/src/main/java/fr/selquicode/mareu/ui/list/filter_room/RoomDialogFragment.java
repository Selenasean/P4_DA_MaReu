package fr.selquicode.mareu.ui.list.filter_room;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;

import fr.selquicode.mareu.data.model.Room;
import fr.selquicode.mareu.databinding.RoomDialogMenuBinding;

public class RoomDialogFragment extends DialogFragment {

    private RoomDialogMenuBinding binding;
    private String selectedRoom;
    private OnSelectedRoomListener roomListener;

    /**
     * Create and return a new instance of this fragment
     * @return @{@link RoomDialogFragment}
     */
    @NonNull
    public static RoomDialogFragment newInstance() {
        return (new RoomDialogFragment());
    }

    /**
     * Called to init OnSelectedRoomListener
     * & remind to implement listener into fragment's Activity root
     * @param context
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof OnSelectedRoomListener) {
            //init listener
            roomListener = (OnSelectedRoomListener) context;
        } else {
            throw new ClassCastException(context
                    + " must implement OnSelectedRoomListener");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = RoomDialogMenuBinding.inflate(getLayoutInflater());

        //AlertDialog.Builder that build an alertDialog using default alert theme
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setView(binding.getRoot()).create();

        List<String> roomList = new ArrayList<>();
        for (Room room : Room.values()) {
            roomList.add(room.getRoomName());
        }
        binding.listviewMenuRoom.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, roomList));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.listviewMenuRoom.setOnItemClickListener((adapter, view1, position, id) -> {
            selectedRoom = binding.listviewMenuRoom.getItemAtPosition(position).toString();

            //use OnSelectedRoomListener Interface to store the room chosen
            roomListener.onSelectedRoom(selectedRoom);

            //close DialogFragment
            dismiss();
        });
    }

}

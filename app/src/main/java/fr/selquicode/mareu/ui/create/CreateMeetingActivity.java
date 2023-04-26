package fr.selquicode.mareu.ui.create;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.selquicode.mareu.R;
import fr.selquicode.mareu.databinding.ActivityCreateBinding;

public class CreateMeetingActivity extends AppCompatActivity {

    private ActivityCreateBinding binding;

    @NonNull
    public static Intent navigate(Context context) {
        return new Intent(context, CreateMeetingActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        binding = ActivityCreateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setRoomChoice();

    }

    private void setRoomChoice() {
        List<String> roomList = new ArrayList<>(Arrays.asList(
                new String("Salle A"),
                new String("Salle B"),
                new String("Salle C"),
                new String("Salle D"),
                new String("Salle E"),
                new String("Salle F"),
                new String("Salle G"),
                new String("Salle H"),
                new String("Salle I"),
                new String("Salle J")
        ));
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.room_list_item, roomList);
        binding.autoCompleteTextV.setAdapter(adapter);
    }
}

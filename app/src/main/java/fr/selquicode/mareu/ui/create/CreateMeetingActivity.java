package fr.selquicode.mareu.ui.create;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import fr.selquicode.mareu.R;
import fr.selquicode.mareu.data.model.Room;
import fr.selquicode.mareu.databinding.ActivityCreateBinding;
import fr.selquicode.mareu.ui.injection.ViewModelFactory;

public class CreateMeetingActivity extends AppCompatActivity {

    private ActivityCreateBinding binding;
    private CreateMeetingViewModel createMeetingViewModel;

    @NonNull
    public static Intent navigate(Context context) {
        return new Intent(context, CreateMeetingActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        createMeetingViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(CreateMeetingViewModel.class);

        // settings for Date & Time Pickers
        binding.datepicker.setFocusable(false);
        binding.datepicker.setOnClickListener(v -> configureDatePicker());
        binding.timepicker.setFocusable(false);
        binding.timepicker.setOnClickListener(v -> configureTimePicker());

        // settings for room selection
        configureRoomChoice();

        // settings for participants' list
        displayParticipantEmail();
    }

    /**
     * Display chips of participants'email regarding the input
     */
    private void displayParticipantEmail() {
        TextInputEditText emailInput = binding.participantsTextInput;
        binding.addEmailParticipant.setOnClickListener(v -> {
            if(!emailInput.toString().isEmpty() && createMeetingViewModel.isEmailValid(emailInput.getEditableText().toString(), this)){
                configureParticipantChip(binding.participantsTextInput);
                emailInput.setText("");
            }
       });
    }
    private void configureParticipantChip(@NonNull TextInputEditText participantsTextInput) {
        Chip chip =  new Chip(this);
        chip.setText(Objects.requireNonNull(participantsTextInput.getText()).toString());
        chip.setChipIconResource(R.drawable.ic_person_black);
        chip.setCloseIconVisible(true);
        chip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.chipGroup.removeView(chip);
            }
        });
        binding.chipGroup.addView(chip);
    }

    /**
     * Method to init the date picker
     */
    private void configureDatePicker() {
        Locale.setDefault(Locale.FRANCE);
        final Calendar c = Calendar.getInstance();

        // get current date, using Calendar's instance
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        // set date picker dialog
        int style = AlertDialog.THEME_HOLO_LIGHT;
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, style,(view, year, month, dayOfMonth) ->
                binding.datepicker.setText(dayOfMonth + "/" + (month + 1) + "/" + year), mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setFirstDayOfWeek(Calendar.MONDAY);
        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
        datePickerDialog.show();
    }

    /**
     * Method to init the date picker
     */
    private void configureTimePicker() {
        //get current hour from France
        ZoneId z = ZoneId.of("Europe/Paris");
        ZonedDateTime zdt = ZonedDateTime.now(z);
        int hour = zdt.getHour();
        int minute = zdt.getMinute();

        TimePickerDialog.OnTimeSetListener timePickerListener = (timePicker, selectedHour, selectedMinute) -> {
            binding.timepicker.setText(String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute));

        };

        int style = AlertDialog.THEME_HOLO_LIGHT;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, timePickerListener, hour, minute, true);
        timePickerDialog.setTitle(R.string.time_picker_text);
        timePickerDialog.show();
    }

    /**
     * method to init the room picker EditText
     */
    private void configureRoomChoice() {
        List<String> roomList = new ArrayList<>();

        for (Room room : Room.values()) {
            roomList.add(room.getRoomName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.room_list_item, roomList);
        binding.autoCompleteTextV.setAdapter(adapter);
    }
}

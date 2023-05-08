package fr.selquicode.mareu.ui.create;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import fr.selquicode.mareu.R;
import fr.selquicode.mareu.data.model.Meeting;
import fr.selquicode.mareu.data.model.Room;
import fr.selquicode.mareu.databinding.ActivityCreateBinding;
import fr.selquicode.mareu.ui.injection.ViewModelFactory;

public class CreateMeetingActivity extends AppCompatActivity {

    private ActivityCreateBinding binding;
    private CreateMeetingViewModel createMeetingViewModel;
    public ZoneId z = ZoneId.of("Europe/Paris");
    public ZonedDateTime zdt = ZonedDateTime.now(z);
    @NonNull
    private String selectedRoom = "";
    @NonNull
    private List<String> emailsParticipants = new ArrayList<>();

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
        getRoomSelected();

        // settings for participants' list
        displayParticipantEmail();

        //create meeting button
        binding.btnCreate.setEnabled(false);
        binding.btnCreate.setOnClickListener(v -> createTheMeeting());
    }


    private void checkInfoCompleted() {
        if (createMeetingViewModel.isMeetingInfoComplete(selectedRoom,
                binding.datepicker.getText().toString(),
                binding.timepicker.getText().toString(),
                binding.subject.getText().toString(),
                emailsParticipants)) {
            binding.btnCreate.setEnabled(true);
        } else {
            binding.btnCreate.setEnabled(false);
        }
    }

    /**
     * Display chips of participants'email regarding the input
     */
    private void displayParticipantEmail() {
        TextInputEditText emailInput = binding.participantsTextInput;
        binding.addEmailParticipant.setOnClickListener(v -> {
            if (!emailInput.toString().isEmpty() && createMeetingViewModel.isEmailValid(emailInput.getEditableText().toString(), this)) {
                configureParticipantChip(binding.participantsTextInput);
                emailInput.setText("");
            }
        });
    }

    private void configureParticipantChip(@NonNull TextInputEditText participantsTextInput) {
        Chip chip = new Chip(this);
        chip.setText(Objects.requireNonNull(participantsTextInput.getText()).toString());
        chip.setChipIconResource(R.drawable.ic_person_black);
        chip.setCloseIconVisible(true);
        chip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.chipGroup.removeView(chip);
                emailsParticipants.remove(chip.getText().toString());
                checkInfoCompleted();
            }
        });
        binding.chipGroup.addView(chip);
        emailsParticipants.add(chip.getText().toString());
        checkInfoCompleted();
    }

    /**
     * Method to init the date picker
     */
    private void configureDatePicker() {
        int mDay = zdt.getDayOfMonth();
        int mMonth = zdt.getMonthValue();
        int mYear = zdt.getYear();

        // set date picker dialog
        int style = AlertDialog.THEME_HOLO_LIGHT;
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                style,
                (view, year, month, dayOfMonth) -> {
                    String date = createMeetingViewModel.formatDate(dayOfMonth, (month + 1), year);
                    binding.datepicker.setText(date);
                },
                mYear,
                (mMonth - 1),
                mDay);
        datePickerDialog.getDatePicker().setMinDate(zdt.toInstant().getEpochSecond());
        datePickerDialog.show();
        checkInfoCompleted();
    }

    /**
     * Method to init the time picker
     */
    private void configureTimePicker() {
        int hour = zdt.getHour();
        int minute = zdt.getMinute();

        //set time picker dialog
        TimePickerDialog.OnTimeSetListener timePickerListener = (timePicker, selectedHour, selectedMinute) -> {
            binding.timepicker.setText(String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute));

        };
        int style = AlertDialog.THEME_HOLO_LIGHT;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, timePickerListener, hour, minute, true);
        timePickerDialog.setTitle(R.string.time_picker_text);
        timePickerDialog.show();

        checkInfoCompleted();
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
        binding.chooseRoomTextV.setAdapter(adapter);
        checkInfoCompleted();
    }

    private void getRoomSelected() {
        binding.chooseRoomTextV.setOnItemClickListener((adapterView, v, position, id) ->
                selectedRoom = adapterView.getItemAtPosition(position).toString());
    }

    private void createTheMeeting() {
        long idMeetingCreated = createMeetingViewModel.generateId();
        LocalDate dateMeetingCreated = createMeetingViewModel.parseToLocalDate(binding.datepicker.getText().toString());
        LocalTime hourMeetingCreated = createMeetingViewModel.parseToLocalTime(binding.timepicker.getText().toString());
        Room roomMeetingCreated = createMeetingViewModel.parseToRoom(selectedRoom);
        String subjectMeetingCreated = binding.subject.getText().toString();

        createMeetingViewModel.createMeeting(new Meeting(idMeetingCreated,
                dateMeetingCreated,
                hourMeetingCreated,
                roomMeetingCreated,
                subjectMeetingCreated,
                emailsParticipants));
    }
}

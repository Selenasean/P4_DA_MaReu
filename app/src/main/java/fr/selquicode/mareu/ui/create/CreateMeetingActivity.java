package fr.selquicode.mareu.ui.create;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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
import fr.selquicode.mareu.ui.utils.injection.ViewModelFactory;

public class CreateMeetingActivity extends AppCompatActivity {

    private ActivityCreateBinding binding;
    private CreateMeetingViewModel createMeetingViewModel;

    public ZoneId z = ZoneId.of("Europe/Paris");
    public ZonedDateTime zdt = ZonedDateTime.now(z);
    @NonNull
    private String selectedRoom = "";
    @NonNull
    private List<String> emailsParticipants = new ArrayList<>();

    /**
     * To navigate from mainActivity to here
     * @param context : of the actual activity
     * @return Intent
     */
    @NonNull
    public static Intent navigate(Context context) {
        return new Intent(context, CreateMeetingActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //settings ViewModel & Observer
        setViewModel();

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

    private void setViewModel() {
        createMeetingViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(CreateMeetingViewModel.class);
        createMeetingViewModel.getCreatedMeetingLiveData().observe(this, state -> CreateMeetingActivity.this.render(state));
    }

    private void render(CreateMeetingViewState state) {
        //refresh UI
        binding.datepicker.setText(state.getDate());

        //enabled create btn
        binding.btnCreate.setEnabled(state.isCreatedEnabled());
    }


    /**
     * Display chips of participants'email regarding the input
     */
    private void displayParticipantEmail() {
        TextInputEditText emailInput = binding.participantsTextInput;
        binding.addEmailParticipant.setOnClickListener(v -> {
            if(createMeetingViewModel.isEmailValid(emailInput.getEditableText().toString(), this)){
                configureParticipantChip(emailInput);
                emailInput.setText("");
            }
        });
    }

    private void configureParticipantChip(@NonNull TextInputEditText participantsTextInput) {
        Chip chip = new Chip(this);
        chip.setText(Objects.requireNonNull(participantsTextInput.getText()).toString());
        chip.setChipIconResource(R.drawable.ic_person_black);
        chip.setCloseIconVisible(true);
        chip.setOnCloseIconClickListener(v -> {
            binding.chipGroup.removeView(chip);
            emailsParticipants.remove(chip.getText().toString());
            //checkInfoCompleted();
        });
        binding.chipGroup.addView(chip);
        emailsParticipants.add(chip.getText().toString());
        //checkInfoCompleted();
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
                    createMeetingViewModel.onDateChanged(dayOfMonth, month, year);
                    // String date = createMeetingViewModel.formatDate(dayOfMonth, (month + 1), year);
                    //binding.datepicker.setText(date);
                },
                mYear,
                (mMonth - 1),
                mDay);
        datePickerDialog.getDatePicker().setMinDate(zdt.toInstant().toEpochMilli());
        datePickerDialog.show();
        //checkInfoCompleted();
    }

    /**
     * Method to init the time picker
     */
    private void configureTimePicker() {
        int hour = zdt.getHour();
        int minute = zdt.getMinute();

        //set time picker dialog
        TimePickerDialog.OnTimeSetListener timePickerListener = (timePicker, selectedHour, selectedMinute) ->
                binding.timepicker.setText(String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute));
        int style = AlertDialog.THEME_HOLO_LIGHT;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, timePickerListener, hour, minute, true);
        timePickerDialog.setTitle(R.string.time_picker_text);
        timePickerDialog.show();

        //checkInfoCompleted();
    }

    /**
     * method to init the room picker EditText
     */
    private void configureRoomChoice() {
        List<String> roomList = new ArrayList<>();

        for (Room room : Room.values()) {
            roomList.add(room.getRoomName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, roomList);
        binding.chooseRoomTextV.setAdapter(adapter);
        //checkInfoCompleted();
    }

    private void getRoomSelected() {
        binding.chooseRoomTextV.setOnItemClickListener((adapterView, v, position, id) ->
                selectedRoom = adapterView.getItemAtPosition(position).toString());
    }

    private void createTheMeeting() {
        long idMeetingCreated = createMeetingViewModel.generateId();
        LocalDate dateMeetingCreated = createMeetingViewModel.parseToLocalDate(Objects.requireNonNull(binding.datepicker.getText()).toString());
        LocalTime hourMeetingCreated = createMeetingViewModel.parseToLocalTime(Objects.requireNonNull(binding.timepicker.getText()).toString());
        Room roomMeetingCreated = createMeetingViewModel.parseToRoom(selectedRoom);
        String subjectMeetingCreated = Objects.requireNonNull(binding.subject.getText()).toString();

        createMeetingViewModel.createMeeting(new Meeting(idMeetingCreated,
                dateMeetingCreated,
                hourMeetingCreated,
                roomMeetingCreated,
                subjectMeetingCreated,
                emailsParticipants));
        finish();
    }

}

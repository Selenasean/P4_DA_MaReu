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

        //update subject
        getSubjectWritten();

        // settings for participants' list
        displayParticipantEmail();

        //create meeting button
        binding.btnCreate.setEnabled(false);
        binding.btnCreate.setOnClickListener(v -> createTheMeeting());
    }

    /**
     * Settings for viewModel & Observer for the UI
     */
    private void setViewModel() {
        createMeetingViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(CreateMeetingViewModel.class);
        createMeetingViewModel.getCreatedMeetingLiveData().observe(this, this::render);

        // observer for closing activity when createTheMeeting is called
        createMeetingViewModel.getCloseActivity().observe(this, closeActivitySingleLiveEvent -> finish());
    }

    /**
     * Render method to display the correct info regarding the state of the UI
     * @param state
     */
    private void render(@NonNull CreateMeetingViewState state) {
        //refresh UI
        if(!binding.chooseRoomTextV.getText().toString().equals(state.getRoomName())){
            binding.chooseRoomTextV.setText(state.getRoomName());
        }
        if(!Objects.requireNonNull(binding.datepicker.getText()).toString().equals(state.getDate())){
            binding.datepicker.setText(state.getDate());
        }
        if(!Objects.requireNonNull(binding.timepicker.getText()).toString().equals(state.getHour())){
            binding.timepicker.setText(state.getHour());
        }
        if(!Objects.requireNonNull(binding.subject.getText()).toString().equals(state.getSubject())){
               binding.subject.setText(state.getSubject());
        }

        //enabled create btn
        binding.btnCreate.setEnabled(state.isCreatedEnabled());
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
    }

    /**
     * Method to save in variable the room selected and update te ViewState
     */
    private void getRoomSelected() {
        binding.chooseRoomTextV.setOnItemClickListener((adapterView, v, position, id) ->
                selectedRoom = adapterView.getItemAtPosition(position).toString());
        createMeetingViewModel.onRoomChanged(selectedRoom);
    }

    /**
     * Method to init the date picker
     * & update the viewState with date selected by user
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
                },
                mYear,
                (mMonth - 1),
                mDay);
        datePickerDialog.getDatePicker().setMinDate(zdt.toInstant().toEpochMilli());
        datePickerDialog.show();
    }

    /**
     * Method to init the time picker
     * & update the ViewState with time selected by user
     */
    private void configureTimePicker() {
        int hour = zdt.getHour();
        int minute = zdt.getMinute();

        //set time picker dialog
        TimePickerDialog.OnTimeSetListener timePickerListener = (timePicker, selectedHour, selectedMinute) ->
                createMeetingViewModel.onTimeChanged(selectedHour,selectedMinute);
        int style = AlertDialog.THEME_HOLO_LIGHT;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, timePickerListener, hour, minute, true);
        timePickerDialog.setTitle(R.string.time_picker_text);
        timePickerDialog.show();
    }

    /**
     * Update the viewState with subject written by user
     */
    private void getSubjectWritten(){
        createMeetingViewModel.onSubjectChanged(binding.subject.toString());
    }

    /**
     * Check if email written by user is correct, if it's not send a error message
     * & passing input to configureParticipantChip() method
     */
    private void displayParticipantEmail() {
        TextInputEditText emailInput = binding.participantsTextInput;
        binding.addEmailParticipant.setOnClickListener(v -> {
            if(createMeetingViewModel.isEmailValid(emailInput.getEditableText().toString())){
                configureParticipantChip(emailInput);
                emailInput.setText("");
            }else{
                if(!emailInput.toString().isEmpty()){
                binding.participantsLyt.setError(getString(R.string.error_email));
                }
            }
        });
    }

    /**
     * Configuration UI for a chip considering the input
     * Called only  if email's pattern is correct
     * @param participantsTextInput
     */
    private void configureParticipantChip(@NonNull TextInputEditText participantsTextInput) {
        Chip chip = new Chip(this);
        chip.setText(Objects.requireNonNull(participantsTextInput.getText()).toString());
        chip.setChipIconResource(R.drawable.ic_person_black);
        chip.setCloseIconVisible(true);
        chip.setOnCloseIconClickListener(v -> {
            binding.chipGroup.removeView(chip);
            emailsParticipants.remove(chip.getText().toString());
        });
        binding.chipGroup.addView(chip);
        emailsParticipants.add(chip.getText().toString());
    }

    /**
     * Method to create a meeting with input completed by user
     */
    private void createTheMeeting() {
        createMeetingViewModel.createMeeting(
                Objects.requireNonNull(binding.datepicker.getText()).toString(),
                Objects.requireNonNull(binding.timepicker.getText()).toString(),
                selectedRoom,
                Objects.requireNonNull(binding.subject.getText()).toString(),
                emailsParticipants
        );
    }

}

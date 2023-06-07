package fr.selquicode.mareu.ui.create;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;

import android.content.Intent;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.selquicode.mareu.R;
import fr.selquicode.mareu.data.model.Room;
import fr.selquicode.mareu.databinding.ActivityCreateBinding;
import fr.selquicode.mareu.ui.utils.injection.ViewModelFactory;

public class CreateMeetingActivity extends AppCompatActivity {

    private ActivityCreateBinding binding;
    private CreateMeetingViewModel createMeetingViewModel;

    public ZoneId z = ZoneId.of("Europe/Paris");
    public ZonedDateTime zdt = ZonedDateTime.now(z);

    /**
     * To navigate from mainActivity to here
     * @param context : of the meetingActivity, where navigate() is called
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

        // settings ViewModel & Observers
        setViewModel();

        // settings for Date & Time Pickers
        binding.datepicker.setFocusable(false);
        binding.datepicker.setOnClickListener(v -> configureDatePicker());
        binding.timepicker.setFocusable(false);
        binding.timepicker.setOnClickListener(v -> configureTimePicker());

        // settings for room selection
        configureRoomChoice();
        setRoomSelected();

        // setting for the subject
        setSubjectWritten();

        // settings for participants' list emails
        displayParticipantEmail();

        // create meeting button
        binding.btnCreate.setEnabled(false);
        binding.btnCreate.setOnClickListener(v -> onCreateMeetingClicked());
    }

    /**
     * Settings for viewModel & Observers for the UI
     */
    private void setViewModel() {
        createMeetingViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(CreateMeetingViewModel.class);
        createMeetingViewModel.getCreatedMeetingLiveData().observe(this, this::render);

        // observer for closing activity when onCreatedMeetingClicked() is called
        createMeetingViewModel.getCloseActivity().observe(this, closeActivitySingleLiveEvent -> finish());
    }

    /**
     * Render method to display the correct info regarding the state of the UI
     * @param state the CreateViewState
     */
    private void render(@NonNull CreateMeetingViewState state) {
        //refresh UI
        binding.chooseRoomTextV.setText(state.getRoomName());
        binding.datepicker.setText(state.getDate());
        binding.timepicker.setText(state.getHour());
        if(!Objects.requireNonNull(binding.subject.getText()).toString().equals(state.getSubject())){
               binding.subject.setText(state.getSubject());
        }

        // erase all chips so the UI displays only what the viewState have in memory
        binding.chipGroup.removeAllViews();
        for(String member : state.getMembers()){
            configureParticipantChip(member);
        }

        //enabled create btn when all the inputs are completed
        binding.btnCreate.setEnabled(state.isCreatedEnabled());
    }

    /**
     * method to init the room picker
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
     * Method who called CreateViewModel by giving him the room selected by user
     * So the ViewModel can update the ViewState with new value
     */
    private void setRoomSelected() {
        binding.chooseRoomTextV.setOnItemClickListener((adapterView, v, position, id) ->
                createMeetingViewModel.onRoomChanged(adapterView.getItemAtPosition(position).toString()));
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
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> createMeetingViewModel.onDateChanged(dayOfMonth, month, year),
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
        int style = AlertDialog.THEME_HOLO_LIGHT;
        TimePickerDialog.OnTimeSetListener timePickerListener = (timePicker, selectedHour, selectedMinute) ->
                createMeetingViewModel.onTimeChanged(selectedHour,selectedMinute);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, timePickerListener, hour, minute, true);
        timePickerDialog.setTitle(R.string.time_picker_text);
        timePickerDialog.show();
    }

    /**
     * Method that call ViewModel by giving him the new value of the subject's input
     * Thereby the viewSate can be update
     */
    private void setSubjectWritten(){
        binding.subject.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    createMeetingViewModel.onSubjectChanged(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    /**
     * Check if email written by user is correct, if it's not send a error message
     * If it fits, called the ViewModel and giving him the new email's value
     * So the ViewState can be update
     */
    private void displayParticipantEmail() {
        TextInputEditText emailInput = binding.participantsTextInput;
        binding.addEmailParticipant.setOnClickListener(v -> {
            if(createMeetingViewModel.isEmailValid(emailInput.getEditableText().toString())){
                createMeetingViewModel.onEmailAdded(emailInput.getEditableText().toString());
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
     */
    private void configureParticipantChip(String email) {
        Chip chip = new Chip(this);
        chip.setText(email);
        chip.setChipIconResource(R.drawable.ic_person_black);
        chip.setCloseIconVisible(true);
        chip.setOnCloseIconClickListener(v -> createMeetingViewModel.onEmailRemoved(email));
        binding.chipGroup.addView(chip);
    }

    /**
     * Method to create a meeting
     */
    private void onCreateMeetingClicked() {
        createMeetingViewModel.createMeeting();
    }

}

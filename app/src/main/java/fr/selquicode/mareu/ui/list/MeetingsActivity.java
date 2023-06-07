package fr.selquicode.mareu.ui.list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;

import fr.selquicode.mareu.R;
import fr.selquicode.mareu.databinding.ActivityMeetingsBinding;
import fr.selquicode.mareu.ui.create.CreateMeetingActivity;
import fr.selquicode.mareu.ui.utils.injection.ViewModelFactory;
import fr.selquicode.mareu.ui.list.filter_room.OnSelectedRoomListener;
import fr.selquicode.mareu.ui.list.filter_room.RoomDialogFragment;

public class MeetingsActivity extends AppCompatActivity implements OnMeetingClickedListener, OnSelectedRoomListener {

    private ActivityMeetingsBinding binding;
    private MeetingsViewModel meetingsViewModel;

    MeetingAdapter mListAdapter = new MeetingAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMeetingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setViewModel();
        setRecycleView();
        initFab();
    }

    private void initFab() {
        binding.fabToCreate.setOnClickListener(view -> startActivity(CreateMeetingActivity.navigate(this)));
    }

    /**
     * To set MeetingsViewModel & the Observer on the list
     */
    private void setViewModel() {
        meetingsViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MeetingsViewModel.class);
        meetingsViewModel.getMeetings().observe(this, listViewState -> {
                mListAdapter.submitList(listViewState);
                if(mListAdapter.getCurrentList().size() == 0){
                    binding.noMeetingFounded.setVisibility(View.VISIBLE);
                    binding.noMeetingFounded.setText(R.string.no_meeting);
                    binding.listMeetings.setVisibility(View.GONE);
                }else{
                    binding.noMeetingFounded.setVisibility(View.GONE);
                    binding.listMeetings.setVisibility(View.VISIBLE);
                }
            });
    }

    /**
     * Settings for Recycleview
     */
    private void setRecycleView(){
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        RecyclerView mRecycleView = binding.listMeetings;
        mRecycleView.addItemDecoration(dividerItemDecoration);

        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.setAdapter(mListAdapter);
    }

    @Override
    public void onDeleteMeetingClicked(long meetingId) {
        meetingsViewModel.onDeleteMeetingClicked(meetingId);
    }

    /**
     * Called to linked layout and menu
     * @param menu  in which you place your items.
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * Called to know which item user selected,
     * and execute a method according to it
     * @param item that was selected.
     *
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter_date:
                openDateDialog();
                return true;
            case R.id.filter_room:
                roomDialog();
                return true;
            case R.id.filter_return:
                resetFilter();
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * To reset all filters
     */
    private void resetFilter() {
        meetingsViewModel.resetFilters();
    }

    /**
     * Open a DialogFragment of Room's list to choose
     */
    private void roomDialog() {
        RoomDialogFragment.newInstance().show(getSupportFragmentManager(), null);
    }

    /**
     * Listener from room selected on FragmentDialog
     * @param room
     */
    @Override
    public void onSelectedRoom(String room) {
        meetingsViewModel.filterByRoom(room);
    }

    /**
     * Open a Date Picker Dialog to choose a date,
     * then used the date selected to sort list
     */
    private void openDateDialog() {
        ZoneId z = ZoneId.of("Europe/Paris");
        ZonedDateTime zdt = ZonedDateTime.now(z);
        int mDay = zdt.getDayOfMonth();
        int mMonth = zdt.getMonthValue();
        int mYear = zdt.getYear();

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    LocalDate date = meetingsViewModel.formatDate(year, (month +1),dayOfMonth);
                    meetingsViewModel.filterByDate(date);
                },
                mYear,
                (mMonth - 1),
                mDay);
        datePickerDialog.getDatePicker().setMinDate(zdt.toInstant().toEpochMilli());
        datePickerDialog.getDatePicker().setFirstDayOfWeek(Calendar.MONDAY);
        datePickerDialog.show();
    }

}
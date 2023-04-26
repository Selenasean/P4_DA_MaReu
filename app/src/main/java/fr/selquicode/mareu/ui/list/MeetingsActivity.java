package fr.selquicode.mareu.ui.list;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

import fr.selquicode.mareu.R;
import fr.selquicode.mareu.databinding.ActivityMeetingsBinding;
import fr.selquicode.mareu.ui.create.CreateMeetingActivity;
import fr.selquicode.mareu.ui.injection.ViewModelFactory;

public class MeetingsActivity extends AppCompatActivity implements OnMeetingClickedListener{

    private ActivityMeetingsBinding binding;
    private MeetingsViewModel meetingsViewModel;

    MeetingAdapter mListAdapter = new MeetingAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings);
        binding = ActivityMeetingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setViewModel();
        setRecycleView();
        initFab();

    }

    private void initFab() {
        binding.fabToCreate.setOnClickListener(view -> startActivity(CreateMeetingActivity.navigate(MeetingsActivity.this)));
    }

    private void setViewModel() {
        meetingsViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MeetingsViewModel.class);

        final Observer<List<MeetingsViewState>> listObserver = new Observer<List<MeetingsViewState>>() {
            @Override
            public void onChanged(@Nullable final List<MeetingsViewState> listMeetingVS) {
                mListAdapter.submitList(listMeetingVS);
            }
        };
        meetingsViewModel.getMeetings().observe(this, listObserver);
    }

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
}
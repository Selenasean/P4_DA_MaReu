package fr.selquicode.mareu.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

import fr.selquicode.mareu.R;
import fr.selquicode.mareu.data.model.Meeting;
import fr.selquicode.mareu.databinding.ActivityMeetingsBinding;
import fr.selquicode.mareu.ui.viewModel.MeetingsViewModel;
import fr.selquicode.mareu.ui.viewModel.MeetingsViewState;
import fr.selquicode.mareu.ui.viewModel.injection.ViewModelFactory;

public class MeetingsActivity extends AppCompatActivity {

    private ActivityMeetingsBinding binding;
    private MeetingsViewModel meetingsViewModel;
    MeetingAdapter mListAdapter = new MeetingAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings);
        binding = ActivityMeetingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setViewModel();
        setRecycleView();

    }

    private void setViewModel() {
        meetingsViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MeetingsViewModel.class);

        final Observer<List<MeetingsViewState>> nameObserver = new Observer<List<MeetingsViewState>>() {
            @Override
            public void onChanged(@Nullable final List<MeetingsViewState> listMeetingVS) {
                mListAdapter.submitList(listMeetingVS);
            }
        };
        meetingsViewModel.getMeetings().observe(this, nameObserver);
    }

    private void setRecycleView(){

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        RecyclerView mRecycleView = binding.listMeetings;
        mRecycleView.addItemDecoration(dividerItemDecoration);

        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.setAdapter(mListAdapter);
    }

}
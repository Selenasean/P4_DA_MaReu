package fr.selquicode.mareu.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import fr.selquicode.mareu.R;
import fr.selquicode.mareu.databinding.ActivityMeetingsBinding;
import fr.selquicode.mareu.ui.viewModel.MeetingsViewModel;
import fr.selquicode.mareu.ui.viewModel.injection.ViewModelFactory;

public class MeetingsActivity extends AppCompatActivity {

    private ActivityMeetingsBinding binding;
    private MeetingsViewModel meetingsViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings);
        binding = ActivityMeetingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // link viewModel & activity
        setViewModel();
        setRecycleView();

    }

    private void setViewModel() {
        this.meetingsViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MeetingsViewModel.class);
    }

    private void setRecycleView(){
        MeetingAdapter mListAdapter = new MeetingAdapter();
        RecyclerView mRecycleView = binding.listMeetings;
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.setAdapter(mListAdapter);
    }

}
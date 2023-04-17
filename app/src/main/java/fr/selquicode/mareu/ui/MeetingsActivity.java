package fr.selquicode.mareu.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
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
        View view = binding.getRoot();
        setContentView(view);

        // link viewModel & activity
        this.meetingsViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MeetingsViewModel.class);


    }

}
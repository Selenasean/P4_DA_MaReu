package fr.selquicode.mareu.ui;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.selquicode.mareu.R;
import fr.selquicode.mareu.data.model.Meeting;
import fr.selquicode.mareu.databinding.ActivityMeetingsBinding;

public class MeetingListAdapter extends ListAdapter<Meeting, MeetingListAdapter.ViewHolder> {

    private List<Meeting> mMeetings;

    public MeetingListAdapter(){
        super(DIFF_CALLBACK);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView, ActivityMeetingsBinding binding) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         ActivityMeetingsBinding binding = ActivityMeetingsBinding.inflate(LayoutInflater.from(parent.getContext()));
         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meeting_item, parent, false);
        return  (new ViewHolder(view, binding)) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    /**
     * ??
     */
    public static final DiffUtil.ItemCallback<Meeting> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Meeting>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull Meeting oldMeeting, @NonNull Meeting newMeeting) {
                    // User properties may have changed if reloaded from the DB, but ID is fixed
                    return oldMeeting.getId() == newMeeting.getId();
                }
                @Override
                public boolean areContentsTheSame(
                        @NonNull Meeting oldMeeting, @NonNull Meeting newMeeting) {
                    // NOTE: if you use equals, your object must properly override Object#equals()
                    // Incorrectly returning false here will result in too many animations.
                    return oldMeeting.equals(newMeeting);
                }
            };

}

package fr.selquicode.mareu.ui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import fr.selquicode.mareu.databinding.MeetingItemBinding;

/**
 * Class displaying data in RecycleView
 */
public class MeetingAdapter extends ListAdapter<MeetingViewState, MeetingAdapter.ViewHolder> {

    @NonNull
    private final OnMeetingClickedListener listener;

    public MeetingAdapter(@NonNull OnMeetingClickedListener deleteListener) {
        super(DIFF_CALLBACK);
        this.listener = deleteListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ViewGroup layout;
        private final TextView date, subject, hour, room, roomImgText, members;
        private final ImageView deleteImg, circleColorRoom;

        public ViewHolder(@NonNull MeetingItemBinding binding) {
            super(binding.getRoot());

            layout = binding.meetingItemLyt;
            date = binding.dateMeeting;
            subject = binding.subjectMeeting;
            room = binding.placeMeeting;
            roomImgText = binding.roomImgText;
            hour = binding.timeMeeting;
            members = binding.participantsMeeting;
            deleteImg = binding.deleteBtn;
            circleColorRoom = binding.imageMeeting;
        }

        public void bind(MeetingViewState item, OnMeetingClickedListener listener) {
            date.setText(item.getDate());
            subject.setText(item.getSubject());
            room.setText(item.getRoomName());
            roomImgText.setText(item.getRoomName().charAt(6) + "");
            circleColorRoom.setImageResource(item.getColorRoom());
            hour.setText(item.getHour());
            members.setText(item.getMeetingMembers());

            deleteImg.setOnClickListener(v -> listener.onDeleteMeetingClicked(item.getId()));
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MeetingItemBinding binding = MeetingItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingAdapter.ViewHolder holder, int position) {
        holder.bind(getItem(position), listener);
    }

    /**
     * method that compare two item and their content to update them if it's necessary
     */
    public static final DiffUtil.ItemCallback<MeetingViewState> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<MeetingViewState>() {
                @Override
                public boolean areItemsTheSame(@NonNull MeetingViewState oldItem, @NonNull MeetingViewState newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull MeetingViewState oldItem, @NonNull MeetingViewState newItem) {
                    return oldItem.equals(newItem);
                }

            };

}
